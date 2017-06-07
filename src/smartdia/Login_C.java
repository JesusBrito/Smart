package smartdia;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
/**
 *
 * @author juanj
 */
//Paso de mensajes
//hilos de ejecuciÃ²n 
//Eventos en java OnFocus()
/*
    Runnable
    Invoke later
    Thread Safe
    Thread
*/
public class Login_C implements Initializable {
    String Usuario="";
    String Contrasenia="";
    int contadorInicio=0;
    @FXML
    private AnchorPane AnchorPane;
    
    @FXML
    private JFXTextField txtUsuario;
    
    @FXML
    private JFXPasswordField txtContrasenia;

    
    @FXML
    private JFXButton btnIniciar;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        Usuario=txtUsuario.getText();
        Contrasenia=txtContrasenia.getText();
        if((Usuario.equals("Admin"))&&(Contrasenia.equals("123"))){
           try{
               /*
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Principal.fxml"));
            Parent principal =(Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("MenÃº principal");
            stage.setScene(new Scene(principal));
            stage.show();
             */
            Parent root = FXMLLoader.load(getClass().getResource("Eventos.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("Bienvenido");
            Image Icono = new Image(getClass().getResourceAsStream("img/Logo.png"));
            stage.getIcons().add(Icono);
            stage.setScene(new Scene(root));
            
            Notifications notificationsBuilder = Notifications.create()
            .title("Bienvenido "+Usuario)           
            .text("Inicio de sesion existoso")
            .hideAfter(Duration.seconds(3))
            .position(Pos.TOP_RIGHT);
            notificationsBuilder.darkStyle();
            notificationsBuilder.show();

         
           }catch(IOException e){
               System.out.println("Error al abrir la ventana");
           }
        }else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al Iniciar sesion");
            alert.setContentText("Usuario y/o contraseÃ±a incorrectos");
            alert.showAndWait();
            contadorInicio++;
            if (contadorInicio>2) {
                Alert alert2 = new Alert(AlertType.WARNING);
                alert2.setTitle("Error");
                alert2.setHeaderText("Limite de intentos superado");
                alert2.setContentText("El numero de intentos para iniciar sesiÃ³n ha sido superado, por favor contacta con el administrador del sistema :)");
                alert2.showAndWait();
                
                txtUsuario.setDisable(true);
                txtContrasenia.setDisable(true);
                btnIniciar.setDisable(true);
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}