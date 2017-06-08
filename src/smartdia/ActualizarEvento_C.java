/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartdia;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
/**
 *
 * @author Danilo
 */
public class ActualizarEvento_C implements Initializable {
    
    @FXML public TextArea txtInformacion;
    @FXML public Button btnCancelar;
    @FXML public Button btnActualizarBD;
    @FXML public ComboBox cmbContactos;
    @FXML public Label txtHoraI;
    @FXML public Label txtMinutoI;
    @FXML public Label txtHoraT;
    @FXML public Label txtMinutoT;
    @FXML public DatePicker dpFechaIni;
    @FXML public DatePicker dpFechaFin;
    
    ObservableList<String> options = FXCollections.observableArrayList();
    ObservableList<String> optionsID = FXCollections.observableArrayList();
    int HoraI = 0;
    int MinI = 0;
    int HoraT = 0;
    int MinT = 0;
    
    ArrayList<String[]> datos = new ArrayList<>();
    
    @FXML
    private void btnCancelar_clicked(ActionEvent event){
         cerrar();
    }
    
    @FXML 
    private void btnActualizarBD_clicked(ActionEvent event) throws SQLException{
        ActualizaComboBox();
    }
    
    @FXML
    private void btnAgregarContacto_clicked(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("AgregarContacto.fxml"));
        
        Stage stage = new Stage();
        stage.setTitle("Nuevo Contacto");
        Image Icono = new Image(getClass().getResourceAsStream("img/Logo.png"));
        stage.getIcons().add(Icono);
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow());
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }
    
    @FXML
    private void btnHIHmax_clicked(ActionEvent event){
        if(HoraI>=0 && HoraI < 23){
            HoraI++;
        }else{
            HoraI = 0;
        }
        txtHoraI.setText((HoraI <=9)? "0" + Integer.toString(HoraI): Integer.toString(HoraI));
    }
    
    @FXML
    private void btnHIHmin_clicked(ActionEvent event){
        if(HoraI > 0 && HoraI <= 23){
            HoraI--;
        }else{
            HoraI = 23;
        }
        txtHoraI.setText((HoraI <=9)? "0" + Integer.toString(HoraI): Integer.toString(HoraI));
    }
    
    @FXML
    private void btnHIMmax_clicked(ActionEvent event){
        if(MinI >= 0 && MinI < 59){
            MinI++;
        }else{
            MinI = 0;
        }
        txtMinutoI.setText((MinI <=9)? "0" + Integer.toString(MinI): Integer.toString(MinI));
    }
    
    @FXML
    private void btnHIMmin_clicked(ActionEvent event){
        if(MinI > 0 && MinI <= 59){
            MinI--;
        }else{
            MinI = 59;
        }
        txtMinutoI.setText((MinI <=9)? "0" + Integer.toString(MinI): Integer.toString(MinI));
    }
    
    @FXML
    private void btnHTHmax_clicked(ActionEvent event){
        if(HoraT>=0 && HoraT < 23){
            HoraT++;
        }else{
            HoraT = 0;
        }
        txtHoraT.setText((HoraT <=9)? "0" + Integer.toString(HoraT): Integer.toString(HoraT));
    }
    
    @FXML
    private void btnHTHmin_clicked(ActionEvent event){
        if(HoraT > 0 && HoraT <= 23){
            HoraT--;
        }else{
            HoraT = 23;
        }
        txtHoraT.setText((HoraT <=9)? "0" + Integer.toString(HoraT): Integer.toString(HoraT));
    }
    
    @FXML
    private void btnHTMmax_clicked(ActionEvent event){
        if(MinT >= 0 && MinT < 59){
            MinT++;
        }else{
            MinT = 0;
        }
        txtMinutoT.setText((MinT <=9)? "0" + Integer.toString(MinT): Integer.toString(MinT));
    }
    
    @FXML
    private void btnHTMmin_clicked(ActionEvent event){
        if(MinT > 0 && MinT <= 59){
            MinT--;
        }else{
            MinT = 59;
        }
        txtMinutoT.setText((MinT <=9)? "0" + Integer.toString(MinT): Integer.toString(MinT));
    }
        
    @FXML
    private void btnActualizarEvento_clicked(ActionEvent event) throws SQLException, IOException, FileNotFoundException, ParseException{
        if(cmbContactos.getSelectionModel().getSelectedIndex() == -1 ||
                txtInformacion.getText().equals("")){
            ErrorMessage("Error", "Alguno de los campos está vacio", "Favor de llenar todos los datos");
        }else{
            addBasedeDatos();
            cerrar();            
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dpFechaIni.setValue(LocalDate.now());
        dpFechaFin.setValue(LocalDate.now());
        try {
            ActualizaComboBox();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }    
    
    public void cerrar(){
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
    
    public void ActualizaComboBox() throws SQLException{
        options.clear();
        optionsID.clear();
        EnlaceDatos bd = new EnlaceDatos("localhost", "smarthdia", "root", "toor");        
        datos = bd.Seleccionar("SELECT * FROM clientes;");    
        bd.cierraCnx();
        for (int i=0; i<=datos.size()-1;i++){
            options.addAll(datos.get(i)[1] + " " + datos.get(i)[2]);
            optionsID.addAll(datos.get(i)[0]);
        }
        cmbContactos.setItems(options);
    }
    
    public void addBasedeDatos() throws FileNotFoundException, SQLException, IOException, ParseException{
        EnlaceDatos bd = new EnlaceDatos("localhost", "smarthdia", "root", "toor");
        
        SimpleDateFormat sdfFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sFechaIni = dpFechaIni.getValue().toString() + " " + ((HoraI <= 9)? "0" + HoraI:HoraI) + ":" + ((MinI <= 9)? "0" + MinI:MinI) + ":00";
        Date FecIni = sdfFecha.parse(sFechaIni);
        
        String sFechaFin = dpFechaFin.getValue().toString() + " " + ((HoraT <= 9)? "0" + HoraT:HoraT) + ":" + ((MinT <= 9)? "0" + MinT:MinT) + ":00";
        Date FecFin = sdfFecha.parse(sFechaFin);
        
        Timestamp FechaInicio = new Timestamp(FecIni.getTime());
        Timestamp FechaFin = new Timestamp(FecFin.getTime());
        
        int ID = Integer.parseInt(optionsID.get(cmbContactos.getSelectionModel().getSelectedIndex()));
        
        if (bd.InserccionEventos(FechaInicio, FechaFin, ID, txtInformacion.getText()) == true){ 
            Notificacion("Registro éxitoso", "Se ha añadido un nuevo evento a la lista de contactos para el dia " + dpFechaIni.getValue().toString(), 5);
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
    
    public void ErrorMessage(String titulo, String encabezado, String Contenido){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(Contenido);

        alert.showAndWait();
    }
}
