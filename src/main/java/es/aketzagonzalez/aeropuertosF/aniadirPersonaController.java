package es.aketzagonzalez.aeropuertosF;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.Persona;

/**
 * Clase aniadirPersonaController.
 */
public class aniadirPersonaController {

    /** El btn cancelar. */
    @FXML
    private Button btnCancelar;

    /** El btn guardar. */
    @FXML
    private Button btnGuardar;

    /** El txt edad. */
    @FXML
    private TextField txtEdad;

    /** El txt apellidos. */
    @FXML
    private TextField txtApellidos;

    /** El txt nombre. */
    @FXML
    private TextField txtNombre;
    
    /** La tabla personas. */
    private TableView<Persona> tablaPersonas; 

    /**
     * Comprueba que la persona introducida sea valida, y si lo es la añade a 
     * la tabla.
     *
     * @param event El evento
     */
    @FXML
    void accionAniadir(ActionEvent event) {
    	String error="";
    	String nombre=txtNombre.getText();
    	String apellidos=txtApellidos.getText();
    	int edad=-1;
    	boolean existe=false;
    	if(txtNombre.getText().isEmpty()) {
    		error+="El campo nombre es obligatorio\n";
    	}
    	if(txtApellidos.getText().isEmpty()) {
    		error+="El campo apellidos es obligatorio\n";
    	}
    	if(txtEdad.getText().isEmpty()) {
    		error+="El campo edad es obligatoiro\n";
    	}else {
	    	try {
	    		edad=Integer.parseInt(txtEdad.getText());
	    		if(edad<=0) {
	    			throw new Exception();
	    		}
	    	}catch(NumberFormatException e) {
	    		error+="La edad debe ser un numero entero\n";
	    	}catch(Exception e) {
	    		error+="La edad debe ser superior a 0\n";
	    	}
    	}
    	Persona p=new Persona(nombre, apellidos, edad);
    	for(Persona per:tablaPersonasController.getListaTodas()){
    		if(per.equals(p)) {
    			existe=true;
    		}
    	}
    	if(tablaPersonasController.isEsAniadir()) {
	    	Alert al=new Alert(AlertType.INFORMATION);
	    	al.setHeaderText(null);
	    	if(error.equals("")&&!existe) {
	    		tablaPersonasController.getListaTodas().add(p);
	    		tablaPersonas.refresh();
	    		al.setContentText("Persona añadida correctamente");
	    	}else {
	    		if(error.equals("")){
	    			al.setAlertType(AlertType.WARNING);
	    			error="La persona ya estaba en la lista";
	    		}else {
	    			al.setAlertType(AlertType.ERROR);
	    		}
	    		al.setContentText(error);
	    	}
	    	al.showAndWait();
	    	tablaPersonas.getSelectionModel().clearSelection();
	    	tablaPersonasController.getS().close();
    	}
    	else{
    		Alert al=new Alert(AlertType.INFORMATION);
	    	al.setHeaderText(null);
	    	if(!existe&&error.equals("")) {
	    		tablaPersonas.getItems().set(tablaPersonas.getSelectionModel().getSelectedIndex(), p);
	    		tablaPersonas.refresh();
	    		al.setContentText("Persona modificada correctamente");
	    	}else {
	    		if(error.equals("")){
	    			al.setAlertType(AlertType.WARNING);
	    			error="La persona ya estaba en la lista";
	    		}else {
	    			al.setAlertType(AlertType.ERROR);
	    		}
	    		al.setContentText(error);
	    	}
	    	al.showAndWait();
	    	tablaPersonas.getSelectionModel().clearSelection();
	    	tablaPersonasController.getS().close();
    	}
    }

    /**
     * Cancela en añadir una persona nueva a la tabla.
     *
     * @param event El evento
     */
    @FXML
    void accionCancelar(ActionEvent event) {
    	tablaPersonasController.getS().close();
    }

	/**
	 * Setter de la tabla personas.
	 *
	 * @param tablaPersonas La tabla de personas
	 */
	public void setTablaPersonas(TableView<Persona> tablaPersonas) {
		this.tablaPersonas = tablaPersonas;
	}
	
	/**
	 * Setter del texto de los pellidos.
	 *
	 * @param apellidos Los nuevos apellidos
	 */
	public void setTxtApellidosText(String apellidos) {
		this.txtApellidos.setText(apellidos);
	}
	
	/**
	 * Setter del texto del nombre.
	 *
	 * @param nombre El nuevo nombre
	 */
	public void setTxtNombreText(String nombre) {
		this.txtNombre.setText(nombre);
	}
	
	/**
	 * Setter del texto de la edad.
	 *
	 * @param edad La nueva edad
	 */
	public void setTxtEdadText(String edad) {
		this.txtEdad.setText(edad);
	}

}
