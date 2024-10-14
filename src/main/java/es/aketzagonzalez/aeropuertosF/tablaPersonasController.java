package es.aketzagonzalez.aeropuertosF;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Persona;

/**
 * Clase tablaPersonasController.
 */
public class tablaPersonasController {

	/** El stage. */
	private static Stage s;
	
    /** El btn aniadir. */
    @FXML
    private Button btnAniadir;
    
    /** El btn eliminar. */
    @FXML
    private Button btnEliminar;

    /** El btn modificar. */
    @FXML
    private Button btnModificar;
    
    @FXML
    private Button btnExportar;

    @FXML
    private Button btnImportar;

    /** El id tabla apellido. */
    @FXML
    private  TableColumn<Persona, String> idTablaApellido;

    /** El id tabla edad. */
    @FXML
    private TableColumn<Persona, Integer> idTablaEdad;

    /** El id tabla nombre. */
    @FXML
    private TableColumn<Persona, String> idTablaNombre;

    /** El tabla personas. */
    @FXML
    private TableView<Persona> tablaPersonas=new TableView<Persona>();
    
    @FXML
    private TextField txtFiltro;
    
    /** Identificador de si añade o modifica la persona. */
    private static boolean esAniadir=false;

    /**
     * Aniadir persona a la tabla llamando a una ventana modal.
     *
     * @param event El evento
     */
    @FXML
    void aniadirPersona(ActionEvent event) {
    	esAniadir=true;
    	s=new Stage();
    	Scene scene;
		try {
			 FXMLLoader controlador = new FXMLLoader(MainApp.class.getResource("/fxml/aniadirPersona.fxml"));
			scene = new Scene(controlador.load());
			s.setTitle("Nueva Persona");
			s.setScene(scene);
			aniadirPersonaController controller = controlador.getController();
			controller.setTablaPersonas(tablaPersonas);
		} catch (IOException e) {
			e.printStackTrace();
		}
        s.setResizable(false);
        s.initOwner(MainApp.getStage());
        s.initModality(javafx.stage.Modality.WINDOW_MODAL);
        s.show();
    }
    
    /**
     * Eliminar persona.
     *
     * @param event the event
     */
    @FXML
    void eliminarPersona(ActionEvent event) {
    	Alert al=new Alert(AlertType.CONFIRMATION);
    	al.setHeaderText(null);
    	if(tablaPersonas.getSelectionModel().getSelectedItem()!=null) {
	    	al.setContentText("Estas seguro de que deseas borrar a: "+tablaPersonas.
	    			getSelectionModel().getSelectedItem());
	    	al.showAndWait();
	    	if(al.getResult().getButtonData().name().equals("OK_DONE")) {
	    		tablaPersonas.getItems().remove(tablaPersonas.getSelectionModel().
	    				getSelectedItem());
	    	}
    	}else {
    		al.setAlertType(AlertType.ERROR);
    		al.setContentText("No hay nadie seleccionado, asi que no se puede seleccionar nadie");
        	al.showAndWait();
    	}
    	tablaPersonas.getSelectionModel().clearSelection();
    }

    /**
     * Modificar persona.
     *
     * @param event the event
     */
    @FXML
    void modificarPersona(ActionEvent event) {
    	esAniadir=false;
    	if(tablaPersonas.getSelectionModel().getSelectedItem()!=null) {
	    	s=new Stage();
	    	Scene scene;
			try {
				 FXMLLoader controlador = new FXMLLoader(MainApp.class.getResource("/fxml/aniadirPersona.fxml"));
				scene = new Scene(controlador.load());
				s.setTitle("Modificar Persona");
				s.setScene(scene);
				aniadirPersonaController controller = controlador.getController();
				controller.setTxtApellidosText(tablaPersonas.getSelectionModel().
						getSelectedItem().getApellidos());
				controller.setTxtEdadText(tablaPersonas.getSelectionModel().
						getSelectedItem().getEdad()+"");
				controller.setTxtNombreText(tablaPersonas.getSelectionModel().
						getSelectedItem().getNombre());
				controller.setTablaPersonas(tablaPersonas);
			} catch (IOException e) {
				e.printStackTrace();
			}
	        s.setResizable(false);
	        s.initOwner(MainApp.getStage());
	        s.initModality(javafx.stage.Modality.WINDOW_MODAL);
	        s.show();
    	}
    	else {
    		Alert al=new Alert(AlertType.ERROR);
        	al.setHeaderText(null);
        	al.setContentText("No hay nadie seleccionado, asi que no se puede seleccionar nadie");
        	al.showAndWait();
    	}
    }
    
    @FXML
    void accionFiltrar(ActionEvent event) {
    	//entra al pulsar el ENTER
    }
    

    @FXML
    void exportarCSV(ActionEvent event) {

    }

    @FXML
    void importarCSV(ActionEvent event) {

    }
    
    /**
     * Inicializa el valor de las celdas.
     */
    @FXML
    private void initialize() {
    	idTablaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    	idTablaApellido.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
    	idTablaEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
    }

	/**
	 * Getter del stage.
	 *
	 * @return El stage
	 */
	public static Stage getS() {
		return s;
	}

	/**
	 * Verifica si esta añadiendo una persona o modificandola.
	 *
	 * @return true, si esta añadiendo una persona
	 */
	public static boolean isEsAniadir() {
		return esAniadir;
	}
    
}
