/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package firstone.serializable;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Milton
 */
public class Propietario implements Serializable {
    
    private String ci;
    private String nombres;
    private String apellidos;
    private byte[] foto;
    private String nro_licencia;

    /**
     * @return the ci
     */
    public String getCi() {
        return ci;
    }

    /**
     * @param ci the ci to set
     */
    public void setCi(String ci) {
        this.ci = ci;
    }

    /**
     * @return the nombres
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * @param nombres the nombres to set
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * @return the apellidos
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * @param apellidos the apellidos to set
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * @return the foto
     */
    public byte[] getFoto() {
        return foto;
    }

    /**
     * @param foto the foto to set
     */
    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    /**
     * @return the nro_licencia
     */
    public String getNro_licencia() {
        return nro_licencia;
    }

    /**
     * @param nro_licencia the nro_licencia to set
     */
    public void setNro_licencia(String nro_licencia) {
        this.nro_licencia = nro_licencia;
    }
    
    
}
