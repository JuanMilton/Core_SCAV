/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package firstone.serializable;

import java.io.Serializable;

/**
 *
 * @author Milton
 */
public class Bitacora implements Serializable{
    
    private long fecha_hora;
    private String accion;
    private String detalle;
    private String ref_ci_guardia;

    /**
     * @return the fecha_hora
     */
    public long getFecha_hora() {
        return fecha_hora;
    }

    /**
     * @param fecha_hora the fecha_hora to set
     */
    public void setFecha_hora(long fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    /**
     * @return the accion
     */
    public String getAccion() {
        return accion;
    }

    /**
     * @param accion the accion to set
     */
    public void setAccion(String accion) {
        this.accion = accion;
    }

    /**
     * @return the detalle
     */
    public String getDetalle() {
        return detalle;
    }

    /**
     * @param detalle the detalle to set
     */
    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    /**
     * @return the ref_ci_guardia
     */
    public String getRef_ci_guardia() {
        return ref_ci_guardia;
    }

    /**
     * @param ref_ci_guardia the ref_ci_guardia to set
     */
    public void setRef_ci_guardia(String ref_ci_guardia) {
        this.ref_ci_guardia = ref_ci_guardia;
    }
    
    
    
}
