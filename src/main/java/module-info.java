module org.example.ejemplotablasagregarquitarfilas {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.ejemplotablasagregarquitarfilas to javafx.fxml;
    exports org.example.ejemplotablasagregarquitarfilas;
}