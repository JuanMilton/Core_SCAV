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
public class EstructureB implements Serializable{
    
    private Object objeto;
    private Synchronizer task;

    /**
     * @return the objeto
     */
    public Object getObjeto() {
        return objeto;
    }

    /**
     * @param objeto the objeto to set
     */
    public void setObjeto(Object objeto) {
        this.objeto = objeto;
    }

    /**
     * @return the task
     */
    public Synchronizer getTask() {
        return task;
    }

    /**
     * @param task the task to set
     */
    public void setTask(Synchronizer task) {
        this.task = task;
    }
    
    
    
}
