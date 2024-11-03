/*
 * Copyright (c) 2013-2023, Tomas Mikula and contributors
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.note_awesome;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class NoteAwesomeEnv {

    public final static String APP_NAME = "Note Awesome";

    public final static String DATABASE_NAME = "note_awesome";

    public final static String ROOT_FOLDER;

    static {
        try {
            ROOT_FOLDER = Paths.get(Objects.requireNonNull(NoteAwesomeEnv.class.getResource("")).toURI()).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public final static String USER_DATA_FOLDER_PATH = ROOT_FOLDER + "user_data/";

    public final static String URL_DATABASE = ROOT_FOLDER + DATABASE_NAME;

    public static final String APP_VERSION = "v0.1";

    public enum ViewComponent {
        MAIN_WINDOW(ROOT_FOLDER + "/fxml/MainWindow.fxml"),
        NOTE_VIEW(ROOT_FOLDER + "/fxml/note_views/NoteView.fxml"),
        NOTE_EDITOR(ROOT_FOLDER + "/fxml/note_views/NoteEditor.fxml"),
        NOTE_BAR(ROOT_FOLDER + "/fxml/note_views/NoteBar.fxml"),
        NOTE_CARD(ROOT_FOLDER + "/fxml/note_views/NoteCard.fxml");

        private final String path;

        ViewComponent(String path) {
            this.path = path;
        }

        public Path getPath() {
            return Path.of(path);
        }

        public URL getURL() {
            try {
                return getPath().toUri().toURL();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public enum Resource {
        CSS(ROOT_FOLDER + "css/NoteAwesome.css"),
        ICONS(ROOT_FOLDER + "icons/"),
        IMAGES(ROOT_FOLDER + "images/");

        private final String path;

        Resource(String path) {
            this.path = path;
        }

        public URL getURL() {
            try {
                return new URL(path);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Path getRootFolder() {
        return Path.of(ROOT_FOLDER);
    }

    public static Path getUserDataFolder() {
        return Path.of(USER_DATA_FOLDER_PATH);
    }


    private NoteAwesomeEnv() {
    }


}
