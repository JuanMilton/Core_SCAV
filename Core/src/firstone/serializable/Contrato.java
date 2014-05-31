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
public class Contrato implements Serializable{
    
    private int id_entorno;
    private byte accion;
    private byte[] contenido;

    /**
     * @return the id_entorno
     */
    public int getId_entorno() {
        return id_entorno;
    }

    /**
     * @param id_entorno the id_entorno to set
     */
    public void setId_entorno(int id_entorno) {
        this.id_entorno = id_entorno;
    }

    /**
     * @return the accion
     */
    public byte getAccion() {
        return accion;
    }

    /**
     * @param accion the accion to set
     */
    public void setAccion(byte accion) {
        this.accion = accion;
    }

    /**
     * @return the contenido
     */
    public byte[] getContenido() {
        return contenido;
    }

    /**
     * @param contenido the contenido to set
     */
    public void setContenido(byte[] contenido) {
        this.contenido = contenido;
    }
    
    
    
}
