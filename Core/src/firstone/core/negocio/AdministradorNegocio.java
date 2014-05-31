/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package firstone.core.negocio;

import firstone.core.datos.dao.AdministradorDAO;

/**
 *
 * @author Milton
 */
public class AdministradorNegocio {
    
    AdministradorDAO administradorDao;
    
    public AdministradorNegocio()
    {
        administradorDao = new AdministradorDAO();
    }
    
    public int autenticarAdministrador(String email, String password)
    {
        return administradorDao.autenticar(email, password);
    }
    
}
