package es.aketzagonzalez.aeropuertosF;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase MainApp.
 */
public class MainApp extends Application {
    
    /** El stage. */
    private static Stage stage;

    /**
     * Metodo que pone la imagen en el header y llama al setRoot de 2 parametros.
     *
     * @param s El stage
     * @throws IOException Si hay algun error de entrada/salida.
     */
    @Override
    public void start(@SuppressWarnings("exports") Stage s) throws IOException {
        stage=s;
		Image imagen=new Image(getClass().getResource("/imagenes/agenda.png").toString());
        stage.getIcons().add(imagen);
		setRoot("tablaPersonas","Personas");
    }

    /**
     * Establece the root llamando al setRoot de 2 parametros.
     *
     * @param fxml El nuevo root
     * @throws IOException Si hay algun error de entrada/salida.
     */
    static void setRoot(String fxml) throws IOException {
        setRoot(fxml,stage.getTitle());
    }

    /**
     * Establece el root y el titulo del stage.
     *
     * @param fxml El fxml
     * @param title El titulo
     * @throws IOException Si hay algun error de entrada/salida.
     */
    static void setRoot(String fxml, String title) throws IOException {
        Scene scene = new Scene(loadFXML(fxml));
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Carga el FXML.
     *
     * @param fxml El fxml
     * @return El fxml cargado
     * @throws IOException Señal de que hay un error de entrada/salida.
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/fxml/"+fxml + ".fxml"));
        return fxmlLoader.load();
    }


    /**
     * El metodo main que lanza el programa.
     *
     * @param args los argumetnos que recive por consola, ninguno
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Getter del stage.
     *
     * @return El stage
     */
    public static Stage getStage() {
		return stage;
	}
    
}
