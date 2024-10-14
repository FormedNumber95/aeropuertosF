module es.aketzagonzalez {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    opens es.aketzagonzalez.aeropuertosF to javafx.fxml;
    exports es.aketzagonzalez.aeropuertosF;
    exports model;
}