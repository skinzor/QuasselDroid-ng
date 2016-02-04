/*
 * QuasselDroid - Quassel client for Android
 * Copyright (C) 2016 Janne Koschinski
 * Copyright (C) 2016 Ken Børge Viktil
 * Copyright (C) 2016 Magnus Fjell
 * Copyright (C) 2016 Martin Sandsmark <martin.sandsmark@kde.org>
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.kuschku.util.irc;

import android.support.annotation.NonNull;

import java.util.Locale;

public class IrcCaseMapper {
    private IrcCaseMapper() {

    }

    public static String toLowerCase(@NonNull String s) {
        return s.toLowerCase(Locale.US)
                .replaceAll("\\[", "{")
                .replaceAll("\\]", "}")
                .replaceAll("\\^", "~");
    }

    public static String toUpperCase(@NonNull String s) {
        return s.toUpperCase(Locale.US)
                .replaceAll("\\{", "[")
                .replaceAll("\\}", "]")
                .replaceAll("~", "^");
    }

    public static boolean equalsIgnoreCase(@NonNull String a, @NonNull String b) {
        return toLowerCase(a).equals(toLowerCase(b)) || toUpperCase(a).equals(toUpperCase(b));
    }
}