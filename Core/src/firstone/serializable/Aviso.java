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
public class Aviso implements Serializable {
    
    public static final byte DIRIGIDO_TODOS             = 1;
    public static final byte DIRIGIDO_TRANCAS           = 2;
    public static final byte DIRIGIDO_PROPIETARIOS      = 3;
    
    private byte to;
    private String from;
    private String mensaje;
    private long fecha_hora;

    /**
     * @return the to
     */
    public byte getTo() {
        return to;
    }

    /**
     * @param to the to to set
     */
    public void setTo(byte to) {
        this.to = to;
    }

    /**
     * @return the from
     */
    public String getFrom() {
        return from;
    }

    /**
     * @param from the from to set
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
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
    
    
    
}
