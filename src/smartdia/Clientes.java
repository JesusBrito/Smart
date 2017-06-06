/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartdia;

import java.awt.image.BufferedImage;
import javafx.scene.image.Image;

public class Clientes {
    
    private int id;
    private String nombre;
    private String correo;
    private String telefono;
    private String tratamiento;
    private String empresa;
    private String pais;
    private String direccion;
    private BufferedImage foto;

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public String getEmpresa() {
        return empresa;
    }

    public String getPais() {
        return pais;
    }

    public String getDireccion() {
        return direccion;
    }

    public BufferedImage getFoto() {
        return foto;
    }

    public Clientes(int id, String nombre, String correo, String telefono, String tratamiento, String empresa, String pais, String direccion, BufferedImage foto) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.tratamiento = tratamiento;
        this.empresa = empresa;
        this.pais = pais;
        this.direccion = direccion;
        this.foto = foto;
    }
    
    
}
