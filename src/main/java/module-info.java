module com.mediaplayerulti.mediaplayerulti {
    requires javafx.fxml;
    requires org.fxmisc.richtext;
    requires reactfx;
    requires java.compiler;
    requires transitive org.controlsfx.controls;
    requires org.fxmisc.flowless;

    opens com.note_awesome to javafx.fxml;
    
    exports com.note_awesome;
}