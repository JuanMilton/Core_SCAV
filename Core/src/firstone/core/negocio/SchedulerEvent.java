/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package firstone.core.negocio;

import firstone.serializable.Contrato;

/**
 *
 * @author Milton
 */
public interface SchedulerEvent {
    
    public void doTask(Contrato contrato, String key);
    
}
