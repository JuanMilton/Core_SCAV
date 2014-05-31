/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package firstone.core.negocio;

import firstone.serializable.Tranca;
import firstone.core.datos.dao.TrancaDAO;
import java.util.List;


/**
 *
 * @author Milton
 */
public class TrancaNegocio {
    
    TrancaDAO trancaDao;
    
    public TrancaNegocio()
    {
        trancaDao = new TrancaDAO();
    }
    
    public List<Tranca> obtenerTodasTrancas(int id_entorno)
    {
        return trancaDao.getAllTrancasEntorno(id_entorno);
    }
    
}
