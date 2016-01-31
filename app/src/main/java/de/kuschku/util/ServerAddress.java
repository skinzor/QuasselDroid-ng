package de.kuschku.util;

public class ServerAddress {
    public final String host;
    public final int port;

    public ServerAddress(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String print() {
        return String.format("%s:%s", host, port);
    }
}