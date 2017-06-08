/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartdia;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import java.io.IOException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;

public final class EnlaceDatos {
    
    private Connection cnx;
    private String usr;
    private String cve;
    private String url;
    
    EnlaceDatos(String svr, String bd, String usr, String cve){
        try{
            
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Cargando controlador...");
            this.url = "jdbc:mysql://" + svr + "/" + bd;
            this.cve = cve;
            this.usr = usr;
            cnx = DriverManager.getConnection(url, usr, cve);
            System.out.println("Conexion establecida...");
            
        } catch(ClassNotFoundException ex){
            
            System.err.println("No se pudo cargar el controlador...");
            ErrorMessage("MySQL Error", "Ha ocurrido un error en la Base de Datos", "No se pudo cargar el controlador.");
            
        } catch(SQLException ex) {
            System.err.println("Error en la conexión a BD...");
            ErrorMessage("MySQL Error", "Ha ocurrido un error en la Base de Datos", "No se pudo conectar a la Base de Datos.");
        }
    }
    
    public BufferedImage SeleccionarImagenCliente(String query) throws IOException{
        BufferedImage bifoto=null;
        try{
            Statement cmd = cnx.createStatement();
            ResultSet res = cmd.executeQuery(query);
            
            Blob imagen=null;
            while(res.next()) imagen = res.getBlob("foto");
            
            bifoto= ImageIO.read(imagen.getBinaryStream());
            
            res.close();
            cmd.close();
            
        }catch (SQLException ex){
            System.err.println("Error en la seleccion de la BD...");
            ErrorMessage("MySQL Error", "Ha ocurrido un error en la Base de Datos", "No se pudo seleccionar información desde la Base de Datos.");
        }
        return bifoto;
    }
    
    public ArrayList<String[]> Seleccionar(String query){

        ArrayList<String[]> elementos = new ArrayList<>();
        try{
            Statement cmd = cnx.createStatement();
            ResultSet res = cmd.executeQuery(query);
            
            int clm = res.getMetaData().getColumnCount();
            
            while(res.next()){               
                String[] data = new String[res.getMetaData().getColumnCount()];  
                
                for (int i = 0; i<=clm-1; i++){   
                    String dato = res.getString(res.getMetaData().getColumnName(i+1));
                    data[i] = dato;
                }
                elementos.add(data);
            }
            res.close();
            cmd.close();
            
        }catch (SQLException ex){
            System.err.println("Error en la seleccion de la BD...");
            ErrorMessage("MySQL Error", "Ha ocurrido un error en la Base de Datos", "No se pudo seleccionar información desde la Base de Datos.");
        }
        return elementos;
    }
    
    public boolean Consultar(String query){

        try{
            
            Statement cmd = cnx.createStatement();
            boolean sal = cmd.execute(query);
            cmd.close();
            return sal;    
                        
        }catch (SQLException ex){
            System.err.println("Error al consultar a BD...");
            ErrorMessage("MySQL Error", "Ha ocurrido un error en la Base de Datos", "No se pudo consultar la información de la Base de Datos.");
            return false;
        }
    }
    
    public boolean Inserccion(String query){

        try{
            
            Statement cmd = cnx.createStatement();
            boolean sal = cmd.execute(query);
            cmd.close();
            return sal;    
                        
        }catch (SQLException ex){
            System.err.println("Error al insertar a BD...");
            ErrorMessage("MySQL Error", "Ha ocurrido un error en la Base de Datos", "Error al insertar en la Base de Datos.");
            return false;
        }
        
    }
    
    public boolean InsertarClientes(String[] datos, String foto, int numero) throws SQLException, FileNotFoundException, IOException{
        try{
           cnx.setAutoCommit(false);
           File file = new File(foto);
           FileInputStream fis = new FileInputStream(file);
           PreparedStatement ps = cnx.prepareStatement(
                   "INSERT INTO clientes(nombre, apellido, tratamiento, empresa, correo, telefono, pais, ciudad, calle, colonia, numero, foto)"+
                           " VALUES(?,?,?,?,?,?,?,?,?,?,?,?);");
           
           for(int i=0; i<= 9; i++){
               ps.setString(i+1, datos[i]);
           }
           ps.setInt(11, numero);
           ps.setBinaryStream(12, fis, (int) file.length());   
           ps.executeUpdate();
           cnx.commit();
           ps.close();
           fis.close();
           return true;
        }catch (SQLException | FileNotFoundException ex){
            ErrorMessage("MySQL Error", "Ha ocurrido un error al insertar los datos", ex.getMessage());
            return false;  
        }
    }
    
    public boolean InserccionEventos(Timestamp FechaInicio, Timestamp FechaFin, int IdCliente, String Informacion){
        try{
           cnx.setAutoCommit(false);           
           PreparedStatement ps = cnx.prepareStatement("INSERT INTO eventos(FechaIni, FechaFin, Estado, IdCliente, Informacion) VALUES(?,?,?,?,?);");
 
           ps.setTimestamp(1, FechaInicio);
           ps.setTimestamp(2, FechaFin);
           ps.setString(3, "Pendiente");
           ps.setInt(4, IdCliente);
           ps.setString(5, Informacion);
           
           ps.executeUpdate();
           cnx.commit();
           ps.close();
           return true;
        }catch (SQLException ex){
            ErrorMessage("MySQL Error", "Ha ocurrido un error al insertar los datos", ex.getMessage());
            return false;  
        }
    }
    
    public void cierraCnx() throws SQLException{

        try{
            cnx.close();
        }catch(SQLException ex){
            System.err.println("Error al cerrar la BD...");
            ErrorMessage("MySQL Error", "Ha ocurrido un error en la Base de Datos", "Error al cerrar la Base de Datos.");
        }
    }
    
    public void ErrorMessage(String titulo, String encabezado, String Contenido){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(Contenido);

        alert.showAndWait();
    }
    
    public Connection getCnx() {
        return cnx;
    }
    
}
