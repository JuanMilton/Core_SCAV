/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package firstone.core.negocio;

import firstone.serializable.Guardia;
import firstone.core.datos.dao.GuardiaDAO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * @author Milton
 */
public class GuardiaNegocio {
    
    GuardiaDAO guardiaDao;
    
    public GuardiaNegocio()
    {
        guardiaDao = new GuardiaDAO();
    }
    
    public Map<String,Object> obtenerTodosGuardias()
    {
        Map<String,Object> guardias = new HashMap<>();
        List<Guardia> gs = guardiaDao.getAllGuardias();
        for(Guardia g : gs)
        {
            guardias.put(g.getCi(), g);
        }
        return guardias;
    }
    
    public List<String> obtenerGuardiasEntornoCI(int id_entorno)
    {
        return guardiaDao.getGuardiasEntornoCI(id_entorno);
    }
    
    public List<Guardia> obtenerGuardiasEntorno(int id_entorno)
    {
        return guardiaDao.getGuardiasEntorno(id_entorno);
    }
    
}
