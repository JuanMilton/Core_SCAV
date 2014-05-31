/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package firstone.core.negocio;

import firstone.core.datos.dao.SynchronizerDAO;
import firstone.serializable.Synchronizer;

/**
 *
 * @author Milton
 */
public class SynchronizerNegocio {
    
    SynchronizerDAO synchronizerDao;
    
    public SynchronizerNegocio()
    {
        synchronizerDao = new SynchronizerDAO();
    }
    
    public void insertar(Synchronizer s)
    {
        synchronizerDao.insert(s);
    }
    
}
