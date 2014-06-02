/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package firstone.core.negocio;

import firstone.serializable.Guardia;
import firstone.core.datos.dao.GuardiaDAO;
import firstone.core.datos.dao.PropietarioDAO;
import firstone.serializable.Propietario;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * @author Milton
 */
public class PropietarioNegocio {
    
    PropietarioDAO propietarioDao;
    
    public PropietarioNegocio()
    {
        propietarioDao = new PropietarioDAO();
    }
    
    public List<String> obtenerPropietariosEntornoCI(int id_entorno)
    {
        return propietarioDao.getPropietariosEntornoCI(id_entorno);
    }
    
    public Propietario obtenerPropietario(String ci)
    {
        return propietarioDao.getPropietarioCI(ci);
    }
    
    public firstone.serializable.Propietario getPropietarioCINotUseable(String ci) {
        return propietarioDao.getPropietarioCINotUseable(ci);
    }
    
}
