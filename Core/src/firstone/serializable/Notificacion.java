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
public class Notificacion implements Serializable{
    
    private String placa;
    private String marca;
    private String ci;
    private String accion;
    private String ci_guardia;

    public String getCi_guardia() {
		return ci_guardia;
	}

	public void setCi_guardia(String ci_guardia) {
		this.ci_guardia = ci_guardia;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
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
     * @return the marca
     */
    public String getMarca() {
        return marca;
    }

    /**
     * @param marca the marca to set
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

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
    
    
    
}
