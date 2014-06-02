/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package firstone.core.negocio;

import firstone.core.datos.dao.IngresoSalidaDAO;
import firstone.serializable.HistorialIngresoSalida;
import java.util.List;

/**
 *
 * @author Milton
 */
public class IngresoSalidaNegocio {
    
    IngresoSalidaDAO isDao;
    
    public IngresoSalidaNegocio()
    {
        isDao = new IngresoSalidaDAO();
    }
    
    public List<HistorialIngresoSalida> obtenerHistorialIngresoSalida(String ci)
    {
        return isDao.obtenerHistorialIngresoSalida(ci);
    }
    
}
