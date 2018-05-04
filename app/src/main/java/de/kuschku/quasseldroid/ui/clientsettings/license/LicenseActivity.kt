/*
 * Quasseldroid - Quassel client for Android
 *
 * Copyright (c) 2018 Janne Koschinski
 * Copyright (c) 2018 The Quassel Project
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 3 as published
 * by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.kuschku.quasseldroid.ui.clientsettings.license

import android.content.Context
import android.content.Intent
import android.support.annotation.StringRes
import de.kuschku.quasseldroid.util.ui.settings.SettingsActivity

class LicenseActivity : SettingsActivity(LicenseFragment()) {
  companion object {
    fun launch(
      context: Context,
      license_name: String,
      @StringRes license_text: Int
    ) = context.startActivity(intent(context, license_name, license_text))

    fun intent(
      context: Context,
      license_name: String,
      @StringRes license_text: Int
    ) = Intent(context, LicenseActivity::class.java).apply {
      putExtra("license_name", license_name)
      putExtra("license_text", license_text)
    }
  }
}
