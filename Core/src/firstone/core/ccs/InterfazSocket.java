/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package firstone.core.ccs;

import firstone.serializable.Tranca;
import firstone.core.negocio.TrancaNegocio;

/**
 *
 * @author Milton
 */
public class InterfazSocket {
    
    TrancaNegocio trancaNegocio;
    
    public InterfazSocket()
    {
        trancaNegocio = new TrancaNegocio();
    }
    
    public Tranca[] obtenerTrancas(int id_entorno)
    {
        return trancaNegocio.obtenerTodasTrancas(id_entorno).toArray(new Tranca[]{});
    }
    
}
