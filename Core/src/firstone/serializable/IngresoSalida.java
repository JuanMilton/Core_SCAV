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
public class IngresoSalida implements Serializable{
    
    private String tipo;
    private long fecha_hora;
    private int id_tranca;
    private String placa;

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
     * @return the id_tranca
     */
    public int getId_tranca() {
        return id_tranca;
    }

    /**
     * @param id_tranca the id_tranca to set
     */
    public void setId_tranca(int id_tranca) {
        this.id_tranca = id_tranca;
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
    
    
    
}
