/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartdia;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
/**
 *
 * @author Danilo
 */
public class AgregarContacto_GUI implements Initializable {
    
    @FXML public Button btnCancelar;
    @FXML public TextField txtNombre;
    @FXML public TextField txtApellidos;
    @FXML public ComboBox cmbTratamiento;
    @FXML public TextField txtEmpresa;
    @FXML public TextField txtCorreo;
    @FXML public TextField txtTelefono;
    @FXML public PasswordField pfContrasenia;
    @FXML public PasswordField pfRepetir;
    @FXML public TextField txtPais;
    @FXML public TextField txtCiudad;
    @FXML public TextField txtCalle;
    @FXML public TextField txtColonia;
    @FXML public TextField txtNumero;
    @FXML public ImageView ivFoto;
    File selectedFile;
    
    @FXML
    private void btnCancelar_clicked(ActionEvent event){
         cerrar();
    }
      
    @FXML
    private void btnAgregarFoto_clicked(ActionEvent event) throws IOException{
        
        Stage stage = new Stage();
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Añadir Foto");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Todas las imagenes", "*.*"),
                new FileChooser.ExtensionFilter(".jpg", "*.jpg*"),
                new FileChooser.ExtensionFilter(".jpeg", ".jpeg"),
                new FileChooser.ExtensionFilter(".png", "*.png*")
        );
        selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            Image imagen = new Image("file:" + selectedFile.getAbsolutePath());
            actualizarFoto(imagen);
        }
       
    }
    
    @FXML
    private void btnAgregarContacto_clicked(ActionEvent event) throws SQLException, IOException{
        if (txtNombre.getText().equals("") ||
                txtApellidos.getText().equals("") ||
                txtEmpresa.getText().equals("") ||
                txtCorreo.getText().equals("") ||
                txtTelefono.getText().equals("") ||
                pfContrasenia.getText().equals("") || 
                pfRepetir.getText().equals("") ||
                txtPais.getText().equals("") ||
                txtCiudad.getText().equals("") ||
                txtCalle.getText().equals("") ||
                txtColonia.getText().equals("") ||
                txtNumero.getText().equals(""))
        {
            //Si los datos no están completos.
            ErrorMessage("Error", "Alguno de los campos está vacio", "Favor de llenar todos los datos");
        } else {
            //Si los datos están llenos.
            if (pfContrasenia.getText().equals(pfRepetir.getText())){
                //Verifica que la contraseña se confirmo al repetir.
                addBasedeDatos();
                cerrar();
            } else {
                //Las contraseñas son diferentes.
                ErrorMessage("Error", "Las contraseñas son diferentes.", "Favor de repetir correctamente la contraseña");
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbTratamiento.getItems().addAll(
            "Sr.",
            "Sra.",
            "Profesor",
            "Ingeniero",
            "Licenciado",
            "Doctor",
            "Ninguno"
        ); 
        Image imagen = new Image(getClass().getResourceAsStream("img/user_pic_default.png"));
        actualizarFoto(imagen);
        
    }    
    
    public void actualizarFoto(Image foto){
        ivFoto.setImage(foto);
    }
    
    public void addBasedeDatos() throws FileNotFoundException, SQLException, IOException{
        EnlaceDatos bd = new EnlaceDatos("localhost", "smarthdia", "root", "toor");
        String[] datos = new String[]{
            txtNombre.getText(),
            txtApellidos.getText(),
            cmbTratamiento.getValue().toString(),
            txtEmpresa.getText(),
            txtCorreo.getText(),
            txtTelefono.getText(),
            txtPais.getText(),
            txtCiudad.getText(),
            txtCalle.getText(),
            txtColonia.getText(),
            pfContrasenia.getText()
        };
        int n = Integer.parseInt(txtNumero.getText());
        if (bd.InsertarClientes(datos, selectedFile.getAbsolutePath(), n) == true){ 
            Notificacion("Registro éxitoso", "Se ha añadido a " + txtNombre.getText() + " a la lista de contactos", 5);
            cerrar();
        } else {
            ErrorMessage("MySQL Error", "Ha ocurrido un error al ingresar un nuevo dato.", "Verifique la conexión a la base de datos.");
        } 
        bd.cierraCnx();
        
    }
    
    public void Notificacion(String titulo, String texto, int duracion){
        Notifications notificationsBuilder = Notifications.create()
                    .title(titulo)           
                    .text(texto)
                    .hideAfter(Duration.seconds(duracion))
                    .position(Pos.BOTTOM_RIGHT);
                    notificationsBuilder.darkStyle();
                    notificationsBuilder.show();
    }
    
    public void cerrar(){
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
    
    public void ErrorMessage(String titulo, String encabezado, String Contenido){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(Contenido);

        alert.showAndWait();
    }
}
