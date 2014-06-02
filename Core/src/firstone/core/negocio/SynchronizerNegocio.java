/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package firstone.core.negocio;

import firstone.core.datos.dao.BitacoraDAO;
import firstone.core.datos.dao.GuardiaDAO;
import firstone.core.datos.dao.IngresoSalidaDAO;
import firstone.core.datos.dao.IngresoSalidaVisitaDAO;
import firstone.core.datos.dao.PropietarioDAO;
import firstone.core.datos.dao.SynchronizerDAO;
import firstone.core.datos.dao.TelefonoDAO;
import firstone.core.datos.dao.TrancaDAO;
import firstone.core.datos.dao.VehiculoDAO;
import firstone.core.datos.dao.VehiculoVisitaDAO;
import firstone.core.datos.dao.VisitaDAO;
import firstone.core.datos.dao.VisitaVehiculoDAO;
import firstone.serializable.Bitacora;
import firstone.serializable.EstructureA;
import firstone.serializable.EstructureB;
import firstone.serializable.Guardia;
import firstone.serializable.IngresoSalida;
import firstone.serializable.IngresoSalidaVisita;
import firstone.serializable.Propietario;
import firstone.serializable.Synchronizer;
import firstone.serializable.Tranca;
import firstone.serializable.Vehiculo;
import firstone.serializable.VehiculoVisita;
import firstone.serializable.Visita;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Milton
 */
public class SynchronizerNegocio {
    
    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(SynchronizerNegocio.class);
    
    public static final String TABLE_VISITA = "visita";
    public static final String TABLE_VISITA_VEHICULO = "visita_vehiculo";
    public static final String TABLE_VEHICULO_VISITA = "vehiculo_visita";
    
    public static final String TABLE_GUARDIA = "guardia";
    public static final String TABLE_PROPIETARIO = "propietario";
    public static final String TABLE_PROPIETARIO_VEHICULO = "propietario_vehiculo";
    public static final String TABLE_TELEFONO_PROPIETARIO = "telefono_propietario";
    public static final String TABLE_VEHICULO = "vehiculo";
    public static final String TABLE_TRANCA   = "tranca";
    
    
    SynchronizerDAO synchronizerDao;
    
    BitacoraDAO bitacoraDao;
    IngresoSalidaDAO isDao;
    IngresoSalidaVisitaDAO isvDao;
    VisitaDAO visitaDao;
    VehiculoVisitaDAO vehiculoVisitaDao;
    VisitaVehiculoDAO visitaVehiculoDao;
    TelefonoDAO telefonoDao;
    PropietarioDAO propietarioDao;
    VehiculoDAO vehiculoDao;
    GuardiaDAO guardiaDao;
    TrancaDAO trancaDao;
    
    public SynchronizerNegocio()
    {
        synchronizerDao = new SynchronizerDAO();
        bitacoraDao = new BitacoraDAO();
        isDao = new IngresoSalidaDAO();
        isvDao = new IngresoSalidaVisitaDAO();
        visitaDao = new VisitaDAO();
        vehiculoVisitaDao = new VehiculoVisitaDAO();
        visitaVehiculoDao = new VisitaVehiculoDAO();
        telefonoDao = new TelefonoDAO();
        propietarioDao = new PropietarioDAO();
        vehiculoDao = new VehiculoDAO();
        guardiaDao = new GuardiaDAO();
        trancaDao = new TrancaDAO();
    }
    
    public void insertar(Synchronizer s, int id_entorno)
    {
        synchronizerDao.insert(s, id_entorno);
    }
    
    public long obtenerMaximoId()
    {
        return synchronizerDao.maximoId();
    }
    
    public List<Object> obtenerDatosABajar(long last_id, int id_entorno)
    {
        List<Object> resultado = new ArrayList<>();
        
        List<Synchronizer> ss = synchronizerDao.obtenerTransacciones(last_id,id_entorno);
        for (Synchronizer s : ss)
        {
            EstructureB eb = new EstructureB();
            eb.setTask(s);
            if (s.getTabla().equalsIgnoreCase(TABLE_TELEFONO_PROPIETARIO)) ////////////////////////////////////////////////////////////
            {
                eb.setObjeto(telefonoDao.getRefCI(Integer.parseInt(s.getRef_id())));                
                resultado.add(eb);
            }else if (s.getTabla().equalsIgnoreCase(TABLE_PROPIETARIO)) ////////////////////////////////////////////////////////////
            {
                if (!s.getTransaccion().equals("E"))
                {
                    Propietario pr = propietarioDao.getPropietarioCI(s.getRef_id());
                    if (pr != null)
                    {
                        eb.setObjeto(pr);
                        resultado.add(eb);
                    }
                }else
                    resultado.add(eb);
            } else if (s.getTabla().equalsIgnoreCase(TABLE_VEHICULO)) ////////////////////////////////////////////////////////////
            {
                if (!s.getTransaccion().equals("E"))
                {
                    Vehiculo vh = vehiculoDao.get(s.getRef_id());
                    if (vh != null)
                    {
                        eb.setObjeto(vh);
                        resultado.add(eb);
                    }
                }else
                    resultado.add(eb);
            } else if (s.getTabla().equalsIgnoreCase(TABLE_PROPIETARIO_VEHICULO)) ////////////////////////////////////////////////////////////
            {
                resultado.add(eb);
            } else if (s.getTabla().equalsIgnoreCase(TABLE_GUARDIA)) ////////////////////////////////////////////////////////////
            {
                if (!s.getTransaccion().equals("E"))
                {
                    Guardia g = guardiaDao.obtenerGuardia(s.getRef_id());
                    if (g != null)
                    {
                        eb.setObjeto(g);
                        resultado.add(eb);
                    }
                }else
                    resultado.add(eb);
            } else if (s.getTabla().equalsIgnoreCase(TABLE_VISITA)) ////////////////////////////////////////////////////////////
            {
                Visita g = visitaDao.get(s.getRef_id());
                if (g != null)
                {
                    eb.setObjeto(g);
                    resultado.add(eb);
                }
            } else if (s.getTabla().equalsIgnoreCase(TABLE_VEHICULO_VISITA)) ////////////////////////////////////////////////////////////
            {
                VehiculoVisita g = vehiculoVisitaDao.get(s.getRef_id());
                if (g != null)
                {
                    eb.setObjeto(g);
                    resultado.add(eb);
                }
            } else if (s.getTabla().equalsIgnoreCase(TABLE_VISITA_VEHICULO)) ////////////////////////////////////////////////////////////
            {
                resultado.add(eb);
            } else if (s.getTabla().equalsIgnoreCase(TABLE_TRANCA)) ////////////////////////////////////////////////////////////
            {
                Tranca tranca = trancaDao.get(Integer.parseInt(s.getRef_id()));
                if (tranca != null && s.getTransaccion().equals("M"))
                {
                    eb.setObjeto(tranca);
                    resultado.add(eb);
                }
            }
            
        }
        return resultado;
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
    
    public void procesarEstructuraB(EstructureB b, int id_entorno)
    {
        Synchronizer s = b.getTask();
        if (s.getTabla().equalsIgnoreCase(TABLE_VISITA))
        {
            Visita visita = (Visita)b.getObjeto();
            if (visitaDao.get(visita.getCi()) == null)
            {
                visitaDao.insert(visita);
                
                Synchronizer szr = new Synchronizer();
                szr.setRef_id(visita.getCi());
                szr.setTabla(TABLE_VISITA);
                szr.setTransaccion("I");
                synchronizerDao.insert(szr,id_entorno);
            }
            else
                log.warn("La visita con CI :"+visita.getCi() + " ya se encuentra registrado en este core");
        }else if (s.getTabla().equalsIgnoreCase(TABLE_VEHICULO_VISITA))
        {
            VehiculoVisita vv = (VehiculoVisita)b.getObjeto();
            if (vehiculoVisitaDao.get(vv.getPlaca()) == null)
            {
                vehiculoVisitaDao.insert(vv);
                
                Synchronizer szr = new Synchronizer();
                szr.setRef_id(vv.getPlaca());
                szr.setTabla(TABLE_VEHICULO_VISITA);
                szr.setTransaccion("I");
                synchronizerDao.insert(szr,id_entorno);
            }
            else
                log.warn("El vehiculo de una visita con PLACA :" + vv.getPlaca() + " ya se encuentra registrado");
        }else if (s.getTabla().equalsIgnoreCase(TABLE_VISITA_VEHICULO))
        {
            String cad = (String)b.getObjeto();
            String keys[] = cad.split(",");
            if (visitaVehiculoDao.existRelation(keys[1], keys[0]))
                log.warn("El vehiculo de visita con PLACA :" + keys[0] + " ya se encuentra relacionada con la visita de CI :" + keys[1]);
            else
            {
                visitaVehiculoDao.insertRelation(keys[0], keys[1]);
                
                Synchronizer szr = new Synchronizer();
                szr.setRef_id(cad);
                szr.setTabla(TABLE_VISITA_VEHICULO);
                szr.setTransaccion("I");
                synchronizerDao.insert(szr,id_entorno);
            }
        }
    }
    
}
