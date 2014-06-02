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
        
        DOMConfigurator.configure("etc" + File.separator + "log4j.xml");
        
        CoreListener coreListener = new CoreListener();
        coreListener.iniciar();
        
    }
    
}
