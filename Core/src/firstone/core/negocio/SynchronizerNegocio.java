/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package firstone.core.negocio;

import firstone.core.ccs.CoreListener;
import firstone.core.datos.dao.BitacoraDAO;
import firstone.core.datos.dao.IngresoSalidaDAO;
import firstone.core.datos.dao.IngresoSalidaVisitaDAO;
import firstone.core.datos.dao.SynchronizerDAO;
import firstone.core.datos.dao.VehiculoVisitaDAO;
import firstone.core.datos.dao.VisitaDAO;
import firstone.core.datos.dao.VisitaVehiculoDAO;
import firstone.serializable.Bitacora;
import firstone.serializable.EstructureA;
import firstone.serializable.EstructureB;
import firstone.serializable.IngresoSalida;
import firstone.serializable.IngresoSalidaVisita;
import firstone.serializable.Synchronizer;
import firstone.serializable.VehiculoVisita;
import firstone.serializable.Visita;
import java.util.Date;

/**
 *
 * @author Milton
 */
public class SynchronizerNegocio {
    
    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(SynchronizerNegocio.class);
    
    public static final String TABLE_VISITA = "visita";
    public static final String TABLE_VISITA_VEHICULO = "visita_vehiculo";
    public static final String TABLE_VEHICULO_VISITA = "vehiculo_visita";
    
    
    SynchronizerDAO synchronizerDao;
    
    BitacoraDAO bitacoraDao;
    IngresoSalidaDAO isDao;
    IngresoSalidaVisitaDAO isvDao;
    VisitaDAO visitaDao;
    VehiculoVisitaDAO vehiculoVisitaDao;
    VisitaVehiculoDAO visitaVehiculoDao;
    
    public SynchronizerNegocio()
    {
        synchronizerDao = new SynchronizerDAO();
        bitacoraDao = new BitacoraDAO();
        isDao = new IngresoSalidaDAO();
        isvDao = new IngresoSalidaVisitaDAO();
        visitaDao = new VisitaDAO();
        vehiculoVisitaDao = new VehiculoVisitaDAO();
        visitaVehiculoDao = new VisitaVehiculoDAO();
    }
    
    public void insertar(Synchronizer s)
    {
        synchronizerDao.insert(s);
    }
    
    public void procesarEstructuraA(EstructureA a)
    {
        if (a.getObjeto() instanceof Bitacora)
        {
            Bitacora bitacora = (Bitacora)a.getObjeto();
            bitacoraDao.insert(bitacora);
        }else if (a.getObjeto() instanceof IngresoSalida)
        {
            IngresoSalida is = (IngresoSalida)a.getObjeto();
            isDao.insert(is.getTipo(), new Date(is.getFecha_hora()), is.getId_tranca(), is.getPlaca());
        }else if (a.getObjeto() instanceof IngresoSalidaVisita)
        {
            IngresoSalidaVisita isv = (IngresoSalidaVisita)a.getObjeto();
            isvDao.insert(isv.getTipo(), new Date(isv.getFecha_hora()), isv.getId_tranca(), isv.getPlaca());
        }
    }
    
    public void procesarEstructuraB(EstructureB b)
    {
        Synchronizer s = b.getTask();
        if (s.getTabla().equalsIgnoreCase(TABLE_VISITA))
        {
            Visita visita = (Visita)b.getObjeto();
            if (visitaDao.get(visita.getCi()) == null)
                visitaDao.insert(visita);
            else
                log.warn("La visita con CI :"+visita.getCi() + " ya se encuentra registrado en este core");
        }else if (s.getTabla().equalsIgnoreCase(TABLE_VEHICULO_VISITA))
        {
            VehiculoVisita vv = (VehiculoVisita)b.getObjeto();
            if (vehiculoVisitaDao.get(vv.getPlaca()) == null)
                vehiculoVisitaDao.insert(vv);
            else
                log.warn("El vehiculo de una visita con PLACA :" + vv.getPlaca() + " ya se encuentra registrado");
        }else if (s.getTabla().equalsIgnoreCase(TABLE_VISITA_VEHICULO))
        {
            String cad = (String)b.getObjeto();
            String keys[] = cad.split(",");
            if (visitaVehiculoDao.existRelation(keys[1], keys[0]))
                log.warn("El vehiculo de visita con PLACA :" + keys[0] + " ya se encuentra relacionada con la visita de CI :" + keys[1]);
            else
                visitaVehiculoDao.insertRelation(keys[0], keys[1]);
        }
    }
    
}
