/*
 * Copyright (C) 2024 Jorge Luis
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package dev.mestizos.server.version;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Jorge Luis
 */
public class VersionDto {

    @Getter
    Application application;

    public VersionDto(String version, String name, String author, String versionOS, String versionJava, String versionDataBase) {
        this.application = new Application(version, name, author, versionOS, versionJava, versionDataBase);
    }    

    @AllArgsConstructor
    class Application {
        @Getter
        @Setter
        String version;
        @Getter
        @Setter
        String name;
        @Getter
        @Setter
        String author;
        @Getter
        @Setter
        String versionOS;
        @Getter
        @Setter
        String versionJava;
        @Getter
        @Setter
        String versionDataBase;
    }
}
