/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartdia;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author juanj
 */


public class Informacion_C implements Initializable {

    /**
     * Initializes the controller class.
     */
@FXML public JFXButton btnCerrar;


@FXML
private void btnCerrar_clicked(ActionEvent event){
  cerrar();
}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
        public void cerrar(){
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }
}
