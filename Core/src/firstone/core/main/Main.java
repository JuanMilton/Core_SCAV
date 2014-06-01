/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package firstone.core.main;

import firstone.core.ccs.CoreListener;
import java.io.File;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author Milton
 */
public class Main {
    
    
    public static void main(String[] args) {
//        Propietario configurador = new Propietario();
//        configurador.setApellidos("FirstOneSoft");
//        configurador.setCi("000000-0");
//        configurador.setFoto(null);
//        configurador.setNombres("IdentiFour");
//        configurador.setNro_licencia("000000-C");
//        
//        byte[] bytes = ObjectUtil.createBytes(configurador);
//        Propietario p = (Propietario)ObjectUtil.createObject(bytes);
//        System.out.println(""+p.getNro_licencia());-
        
        DOMConfigurator.configure("etc" + File.separator + "log4j.xml");
        
        CoreListener coreListener = new CoreListener();
        coreListener.iniciar();
        
    }
    
}
