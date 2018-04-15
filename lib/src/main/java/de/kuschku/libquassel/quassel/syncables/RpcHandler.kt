package de.kuschku.libquassel.quassel.syncables

import de.kuschku.libquassel.protocol.*
import de.kuschku.libquassel.protocol.primitive.serializer.StringSerializer
import de.kuschku.libquassel.quassel.BufferInfo
import de.kuschku.libquassel.quassel.syncables.interfaces.INetwork
import de.kuschku.libquassel.quassel.syncables.interfaces.IRpcHandler
import de.kuschku.libquassel.session.BacklogStorage
import de.kuschku.libquassel.session.Session
import de.kuschku.libquassel.util.helpers.deserializeString
import java.nio.ByteBuffer

class RpcHandler(
  override val session: Session,
  private val backlogStorage: BacklogStorage
) : IRpcHandler {
  override fun displayStatusMsg(net: String, msg: String) {
  }

  override fun bufferInfoUpdated(bufferInfo: BufferInfo) {
    session.bufferSyncer.bufferInfoUpdated(bufferInfo)
  }

  override fun identityCreated(identity: QVariantMap) = session.addIdentity(identity)
  override fun identityRemoved(identityId: IdentityId) = session.removeIdentity(identityId)

  override fun networkCreated(networkId: NetworkId) = session.addNetwork(networkId)
  override fun networkRemoved(networkId: NetworkId) = session.removeNetwork(networkId)

  override fun passwordChanged(ignored: Long, success: Boolean) {
  }

  override fun disconnectFromCore() = session.disconnectFromCore()

  override fun objectRenamed(classname: ByteBuffer, newname: String, oldname: String) {
    session.renameObject(classname.deserializeString(StringSerializer.UTF8) ?: "", newname, oldname)
  }

  override fun displayMsg(message: Message) {
    session.bufferSyncer.bufferInfoUpdated(message.bufferInfo)
    backlogStorage.storeMessages(session, message)
  }

  override fun createIdentity(identity: QVariantMap, additional: QVariantMap) =
    RPC(
      "2createIdentity(Identity,QVariantMap)",
      ARG(identity, QType.Identity),
      ARG(additional, Type.QVariantMap)
    )

  override fun removeIdentity(identityId: IdentityId) =
    RPC(
      "2removeIdentity(IdentityId)",
      ARG(identityId, QType.IdentityId)
    )

  override fun createNetwork(networkInfo: INetwork.NetworkInfo, channels: List<String>) =
    RPC(
      "2createNetwork(NetworkInfo,QStringList)",
      ARG(networkInfo, QType.NetworkInfo),
      ARG(channels, Type.QStringList)
    )

  override fun removeNetwork(networkId: NetworkId) =
    RPC(
      "2removeNetwork(NetworkId)",
      ARG(networkId, QType.NetworkId)
    )

  override fun changePassword(peerPtr: Long, user: String, old: String, new: String) =
    RPC(
      "2changePassword(PeerPtr,QString,QString,QString)",
      ARG(peerPtr, QType.PeerPtr),
      ARG(user, Type.QString),
      ARG(old, Type.QString),
      ARG(new, Type.QString)
    )

  override fun requestKickClient(id: Int) =
    RPC(
      "2kickClient(Int)",
      ARG(id, Type.Int)
    )

  override fun sendInput(bufferInfo: BufferInfo, message: String) =
    RPC(
      "2sendInput(BufferInfo,QString)",
      ARG(bufferInfo, QType.BufferInfo),
      ARG(message, Type.QString)
    )

  inline fun RPC(function: String, vararg arg: QVariant_) {
    // Don’t transmit calls back that we just got from the network
    if (session.shouldRpc(function))
      session.callRpc(function, arg.toList())
  }
}
