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

import java.net.URL;
import java.nio.file.Path;
import java.util.Map;

public class NoteAwesomeEnv {

    public final static String APP_NAME = "Note Awesome";

    public final static String DATABASE_NAME = "note_awesome";

    public final static String ROOT_FOLDER = NoteAwesomeEnv.class.getResource("").getPath();

    public final static String USER_DATA_FOLDER_PATH = ROOT_FOLDER + "user_data/";


    public final static String URL_DATABASE = ROOT_FOLDER + DATABASE_NAME;

    public static final String APP_VERSION = "v0.1";

    public enum ViewComponent {
        MAIN_WINDOW("MainWindow.fxml"),
        NOTE_VIEW("NoteView.fxml"),
        NOTE_EDITOR("NoteEditor.fxml"),
        NOTE_BAR("NoteBar.fxml"),
        NOTE_CARD("NoteCard.fxml");

        private final String fxmlFileName;

        ViewComponent(String fxmlFileName) {
            this.fxmlFileName = fxmlFileName;
        }

        public String getFxmlFileName() {
            return fxmlFileName;
        }
    }

    public static final Map<Enum<ViewComponent>, URL> VIEW_COMPONENT_LOAD_PATHS = Map.of(
            ViewComponent.MAIN_WINDOW, NoteAwesomeEnv.class.getResource(Folder.FXML.getFolderName() + ViewComponent.MAIN_WINDOW.getFxmlFileName()),
            ViewComponent.NOTE_VIEW, NoteAwesomeEnv.class.getResource(Folder.NOTE_VIEWS.getFolderName() + ViewComponent.NOTE_VIEW.getFxmlFileName()),
            ViewComponent.NOTE_EDITOR, NoteAwesomeEnv.class.getResource(Folder.NOTE_VIEWS.getFolderName() + ViewComponent.NOTE_EDITOR.getFxmlFileName()),
            ViewComponent.NOTE_BAR, NoteAwesomeEnv.class.getResource(Folder.NOTE_VIEWS.getFolderName() + ViewComponent.NOTE_BAR.getFxmlFileName()),
            ViewComponent.NOTE_CARD, NoteAwesomeEnv.class.getResource(Folder.NOTE_VIEWS.getFolderName() + ViewComponent.NOTE_CARD.getFxmlFileName())
    );

    public enum Folder {
        FXML("fxml/"),
        CSS("css/"),
        IMAGES("images/"),


        NOTE_VIEWS(FXML.getFolderName() + "note_views/");

        private final String folderName;

        Folder(String folderName) {
            this.folderName = folderName;
        }

        public String getFolderName() {
            return folderName;
        }
    }

    private NoteAwesomeEnv() {
    }


}
