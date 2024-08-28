module com.mediaplayerulti.mediaplayerulti {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.mediaplayerulti.mediaplayerulti to javafx.fxml;
    exports com.mediaplayerulti.mediaplayerulti;
}