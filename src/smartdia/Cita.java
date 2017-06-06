/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartdia;

import java.time.LocalDateTime;

public class Cita {
    
    private int noCita;
    private String fechaIni;
    private String fechaFin;
    private String Informacion;
    private String estado;
    private String horaIni;
    private String horaFin;
    private String nombre;
    private String telefono;

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public int getNoCita() {
        return noCita;
    }

    public String getFechaIni() {
        return fechaIni;
    }
    
    public String getFechaFin() {
        return fechaFin;
    }

    public String getInformacion() {
        return Informacion;
    }

    public String getEstado() {
        return estado;
    }

    public String getHoraIni() {
        return horaIni;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public Cita(int noCita, String fechaIni, String horaIni, String fechaFin, String horaFin, String Informacion, String estado, String nombre, String telefono) {
        this.noCita = noCita;
        this.fechaIni = fechaIni;
        this.fechaFin = fechaFin;
        this.Informacion = Informacion;
        this.estado = estado;
        this.horaIni = horaIni;
        this.horaFin = horaFin;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Cita{" + "noCita=" + noCita + ", fechaIni=" + fechaIni + ", fechaFin=" + fechaFin + ", Informacion=" + Informacion + ", estado=" + estado + ", horaIni=" + horaIni + ", horaFin=" + horaFin + ", nombre=" + nombre + ", telefono=" + telefono + '}';
    }
    
    
}
