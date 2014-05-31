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
public class Tranca implements Serializable{
    
    private int id;
    private String descripcion;
    private String tipo;
    private int id_entorno;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
    
}
