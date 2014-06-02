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
public class HistorialIngresoSalida implements Serializable{
    
    private long fecha_hora;
    private String tipo;
    private String placa;
    private String tranca;

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
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the placa
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * @param placa the placa to set
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /**
     * @return the tranca
     */
    public String getTranca() {
        return tranca;
    }

    /**
     * @param tranca the tranca to set
     */
    public void setTranca(String tranca) {
        this.tranca = tranca;
    }
    
    
    
}
