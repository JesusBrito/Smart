package smartdia;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Eventos_C implements Initializable {
    
    @FXML private AnchorPane apEventos;
    @FXML private AnchorPane apContactos;
    //AnchorPane Eventos
    @FXML private TableView<Cita> tvCitas;
    
    @FXML private TableColumn<Cita, String> tcFechaIni;
    @FXML private TableColumn<Cita, String> tcHoraIni;
    @FXML private TableColumn<Cita, String> tcFechaFin;
    @FXML private TableColumn<Cita, String> tcHoraFin;
    @FXML private TableColumn<Cita, String> tcEstado;
    @FXML private TableColumn<Cita, String> tcCliente;
    @FXML private TableColumn<Cita, String> tcTelefono;
    @FXML private TableColumn<Cita, String> tcInformacion;
    @FXML private DatePicker dpFecha;
    
    //Anchor Pane Clientes
    @FXML private TableView<Clientes> tvClientes;
    
    @FXML private TableColumn<Clientes, String> tcNombre;
    @FXML private TableColumn<Clientes, String> tcCorreo;
    @FXML private TableColumn<Clientes, String> tcTelefonoClientes;
    @FXML private TableColumn<Clientes, String> tcTratamiento;
    @FXML private TableColumn<Clientes, String> tcEmpresa;
    @FXML private TableColumn<Clientes, String> tcPais;
    @FXML private TableColumn<Clientes, String> tcDireccion;
    @FXML private DatePicker dpFechaContacto;
    @FXML private Label lbNombre,lbEmpresa,lbCorreo,lbTelefono,lbDireccion;
    @FXML private ImageView imgCliente;
    ObservableList<Cita> data = FXCollections.observableArrayList();
    ObservableList<Clientes> dataClientes = FXCollections.observableArrayList();
    ArrayList<String[]> eventos = new ArrayList<>();
    ArrayList<String[]> cliente = new ArrayList<>();
    
    @FXML
    private void btnAgenda_clicked(ActionEvent event) throws SQLException, ParseException{
        apEventos.setDisable(false);
        apEventos.setVisible(true);
        apContactos.setDisable(true);
        apContactos.setVisible(false);
        
        dpFecha.setValue(LocalDate.now());
        actualizarBD();
    }
    
    @FXML
    private void btnContactos_clicked(ActionEvent event) throws SQLException, ParseException, IOException{
        apEventos.setDisable(true);
        apEventos.setVisible(false);
        apContactos.setDisable(false);
        apContactos.setVisible(true);
        
        dpFechaContacto.setValue(LocalDate.now());
        actualizarBDClientes();
    }
    
    @FXML
    private void btnActualizar_clicked(ActionEvent event) throws SQLException, ParseException{
        actualizarBD();
    }
    
    @FXML
    private void btnAniadir_clicked(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("AgregarEvento.fxml"));
        
        Stage stage = new Stage();
        stage.setTitle("Agregar Evento Nuevo");
        Image Icono = new Image(getClass().getResourceAsStream("img/Logo.png"));
        stage.getIcons().add(Icono);
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow());
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }
    
    @FXML
    private void btnBuscar_clicked(ActionEvent event){
        TextInputDialog dialog = new TextInputDialog("Buscar");
        dialog.setTitle("Buscar");
        dialog.setHeaderText("Ingrese el dato que desea buscar en la tabla.");
        dialog.setContentText("Buscar: ");
        
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(buscar -> System.out.println("Quiere " + buscar));
    }
    
    @FXML
    private void btnVisualizar_clicked(ActionEvent event){
        Clientes tmp = tvClientes.getSelectionModel().getSelectedItem(); 
        //Obtenemos la seleccion de la tabla (Los valores de la tabla son de la clase que almacenan
        // en este caso Clientes), y posteriormente lo almacenamos como una clase Clientes.      
        lbNombre.setText(tmp.getNombre());
        lbEmpresa.setText(tmp.getEmpresa());
        lbCorreo.setText(tmp.getCorreo());
        lbTelefono.setText(tmp.getTelefono());
        lbDireccion.setText(tmp.getDireccion());
        
        Image foto = SwingFXUtils.toFXImage(tmp.getFoto(), null);
        imgCliente.setImage(foto);
    }
    
    @FXML
    private void btnExportarComo_clicked(ActionEvent event){
        //Documento doc = new Documento(System.getProperty("user.dir") + "\\src\\smartdia\\report\\Eventos.jrxml");
    }
    
    @FXML
    private void btnExportar_clicked(ActionEvent event){
       //Documento doc = new Documento(System.getProperty("user.dir") + "\\src\\smartdia\\report\\Contactos.jrxml");
    }
    
    @FXML
    private void btnAniadirContacto_clicked(ActionEvent event) throws IOException{
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        apEventos.setDisable(false);
        apEventos.setVisible(true);
        apContactos.setDisable(true);
        apContactos.setVisible(false);
        
        dpFecha.setValue(LocalDate.now());
        try {
            actualizarBD();
        } catch (SQLException ex) {
            Logger.getLogger(Eventos_C.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Eventos_C.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    
    public void actualizarTabla(){
        //Se actualizan los datos de la tabla, en base a la colección "data".
        tcFechaIni.setCellValueFactory(new PropertyValueFactory<>("fechaIni"));
        tcHoraIni.setCellValueFactory(new PropertyValueFactory<>("horaIni"));
        tcFechaFin.setCellValueFactory(new PropertyValueFactory<>("fechaFin"));
        tcHoraFin.setCellValueFactory(new PropertyValueFactory<>("horaFin"));
        tcEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        tcCliente.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tcTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        tcInformacion.setCellValueFactory(new PropertyValueFactory<>("informacion"));
        tvCitas.setItems(data);
    }
    
    public void actualizarTablaClientes(){
        //Se actualizan los datos de la tabla, en base a la colección "data".
        tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tcCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        tcTelefonoClientes.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        tcTratamiento.setCellValueFactory(new PropertyValueFactory<>("tratamiento"));
        tcEmpresa.setCellValueFactory(new PropertyValueFactory<>("empresa"));
        tcPais.setCellValueFactory(new PropertyValueFactory<>("pais"));
        tcDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        tvClientes.setItems(dataClientes);
    }
    
    public void actualizarBD() throws SQLException, ParseException{
        //Se llena la colección "data" en base a la base de datos.
        data.clear();
        EnlaceDatos db = new EnlaceDatos("localhost", "smarthdia", "root", "toor");
        
        SimpleDateFormat sdfFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        String sFechaIni;
        Date FecIni;
        
        String sFechaFin;
        Date FecFin;

        
        eventos = db.Seleccionar("SELECT * FROM eventos;");
        for (int i=0; i<=eventos.size()-1; i++){
             cliente = db.Seleccionar("SELECT * FROM clientes WHERE idclientes = '" + eventos.get(i)[4] + "';");
             
             sFechaIni = eventos.get(i)[1];
             sFechaFin = eventos.get(i)[2];
             
             FecIni = sdfFecha.parse(sFechaIni);
             FecFin = sdfFecha.parse(sFechaFin);
             
             data.addAll(new Cita(
                     Integer.parseInt(eventos.get(i)[0]),
                     Integer.toString(FecIni.getDate()) + "/" + ((FecIni.getMonth()<= 9)? "0" + Integer.toString(FecIni.getMonth()):Integer.toString(FecIni.getMonth())) + "/" + Integer.toString(FecIni.getYear() + 1900),
                     ((FecIni.getHours() <= 9)? "0" + Integer.toString(FecIni.getHours()):Integer.toString(FecIni.getHours()))+ ":" + ((FecIni.getMinutes()<= 9)? "0" + Integer.toString(FecIni.getMinutes()):Integer.toString(FecIni.getMinutes())),
                     Integer.toString(FecFin.getDate()) + "/" + ((FecFin.getMonth()<= 9)? "0" + Integer.toString(FecFin.getMonth()):Integer.toString(FecFin.getMonth())) + "/" + Integer.toString(FecFin.getYear() + 1900),
                     ((FecFin.getHours() <= 9)? "0" + Integer.toString(FecFin.getHours()):Integer.toString(FecFin.getHours()))+ ":" + ((FecFin.getMinutes()<= 9)? "0" + Integer.toString(FecFin.getMinutes()):Integer.toString(FecFin.getMinutes())),
                     eventos.get(i)[5],
                     eventos.get(i)[3],
                     (cliente.get(0)[1] + " " + cliente.get(0)[2]),
                     cliente.get(0)[6]
             ));
        }
        db.cierraCnx();
        actualizarTabla();
    }
    
    public void actualizarBDClientes() throws SQLException, ParseException, IOException{
        //Se llena la colección "dataClientes" en base a la base de datos.
        dataClientes.clear();
        EnlaceDatos db = new EnlaceDatos("localhost", "smarthdia", "root", "toor");
        cliente = db.Seleccionar("SELECT * FROM clientes;");
        BufferedImage foto = null;
        for (int i=0; i<=cliente.size()-1; i++){
            dataClientes.addAll( new Clientes(
                    Integer.parseInt(cliente.get(i)[0]), //id
                    cliente.get(i)[1] + " " + cliente.get(i)[2], //nombre
                    cliente.get(i)[5],//correo
                    cliente.get(i)[6],//telefono
                    cliente.get(i)[3],//tratamiento
                    cliente.get(i)[4],//empresa
                    cliente.get(i)[7],//país
                    cliente.get(i)[9] + " número " + cliente.get(i)[11] + ", " + cliente.get(i)[10] + ", " + cliente.get(i)[8],//direccion
                    foto = db.SeleccionarImagenCliente("SELECT * FROM clientes WHERE idclientes = " + cliente.get(i)[0]  + ";")//foto
            ));
        }
        db.cierraCnx();
        actualizarTablaClientes();
    }
     
    
}
