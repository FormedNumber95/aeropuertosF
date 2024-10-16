package es.aketzagonzalez.aeropuertosF;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
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
    
    /** El btn exportar. */
    @FXML
    private Button btnExportar;

    /** El btn importar. */
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
    private TableView<Persona> tablaPersonas=new TableView<Persona>(listaTodas);
    
    /** El texto del filtro. */
    @FXML
    private TextField txtFiltro;
    
    /** La lista de todas las personas. */
    private static ObservableList<Persona> listaTodas;
    
    /** El filtro. */
    private FilteredList<Persona> filtro;
    
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
    
    /**
     * Filtra en la lista para que solo se vean aquellas personas que su nombre contenga el texto introducido.
     *
     * @param event the event
     */
    @FXML
    void accionFiltrar(ActionEvent event) {
    	tablaPersonas.setItems(filtro);
    	if(txtFiltro.getText().isEmpty()){
    		tablaPersonas.setItems(listaTodas);
    	}else {
    		filtro.setPredicate(persona -> persona.getNombre().contains(txtFiltro.getText()));
    	}
    }
    

    /**
     * Exportar a CSV la tabla actual (todas las personas, no solo las filtradas).
     *
     * @param event the event
     */
    @FXML
    void exportarCSV(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Archivo CSV");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Archivos CSV (*.csv)", "*.csv"));
        File archivo = fileChooser.showSaveDialog(MainApp.getStage());
        if(archivo!=null) {
        	try(BufferedWriter br=new BufferedWriter(new FileWriter(archivo))){
        		br.write("Nombre,Apellidos,Edad");
        		br.newLine();
        		for(Persona p:listaTodas) {
        			br.write(p.getNombre()+","+p.getApellidos()+","+p.getEdad());
        			br.newLine();
        		}
        	} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }

    /**
     * Importar desde CSV las personas para asignar a la tabla las personas del CSV.
     *
     * @param event the event
     */
    @FXML
    void importarCSV(ActionEvent event) {
    	listaTodas.clear();
    	FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir Archivo CSV");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Archivos CSV (*.csv)", "*.csv"));
        File archivo = fileChooser.showOpenDialog(MainApp.getStage());
        if(archivo!=null) {
        	try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
        		String linea=br.readLine();
        			if(linea!=null) {
        				linea=br.readLine();
        			}
        			while(linea!=null) {
        				String[]leido=linea.split(",");
        				Persona p=new Persona(leido[0], leido[1], Integer.parseInt(leido[2]));
        				listaTodas.add(p);
        				linea=br.readLine();
        			}
        			tablaPersonas.refresh();
        		} catch (FileNotFoundException e) {
        			e.printStackTrace();
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
       }
  }
    
    /**
     * Inicializa el valor de las celdas.
     */
    @FXML
    private void initialize() {
    	idTablaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    	idTablaApellido.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
    	idTablaEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
    	listaTodas=tablaPersonas.getItems();
    	filtro = new FilteredList<Persona>(listaTodas);
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
	 * Getter de listaTodas.
	 *
	 * @return la lista de todas las persoans
	 */
	public static ObservableList<Persona> getListaTodas() {
		return listaTodas;
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
