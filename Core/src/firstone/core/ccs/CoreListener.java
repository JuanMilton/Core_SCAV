package firstone.core.ccs;

import com.firstonesoft.core.Core;
import com.firstonesoft.core.event.EventCore;
import com.firstonesoft.core.util.ObjectUtil;
import firstone.serializable.Contrato;
import firstone.serializable.Guardia;
import firstone.serializable.Propietario;
import firstone.serializable.Tranca;
import firstone.core.negocio.AdministradorNegocio;
import firstone.core.negocio.GuardiaNegocio;
import firstone.core.negocio.IngresoSalidaNegocio;
import firstone.core.negocio.PropietarioNegocio;
import firstone.core.negocio.SchedulerEvent;
import firstone.core.negocio.SchedulerNegocio;
import firstone.core.negocio.SynchronizerNegocio;
import firstone.core.negocio.Task;
import firstone.core.negocio.TrancaNegocio;
import firstone.core.util.Parametros;
import firstone.serializable.Alarma;
import firstone.serializable.Aviso;
import firstone.serializable.EstructureA;
import firstone.serializable.EstructureB;
import firstone.serializable.HistorialIngresoSalida;
import firstone.serializable.Notificacion;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Milton
 */
public class CoreListener implements EventCore, SchedulerEvent {

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(CoreListener.class);

    private static final int PORT_CORE = Parametros.LISTENER_PORT;
    Core core;
    private Map<String, Object> keys;
    private Map<String, Object> conectados;

    private GuardiaNegocio guardiaNegocio;
    private PropietarioNegocio propietarioNegocio;
    private AdministradorNegocio administradorNegocio;
    private TrancaNegocio trancaNegocio;
    private SynchronizerNegocio synchronizerNegocio;
    private IngresoSalidaNegocio isNegocio;
    private SchedulerNegocio sn;
    
    public CoreListener() {
        core = new Core(PORT_CORE);
        guardiaNegocio = new GuardiaNegocio();
        propietarioNegocio = new PropietarioNegocio();
        administradorNegocio = new AdministradorNegocio();
        trancaNegocio = new TrancaNegocio();
        synchronizerNegocio = new SynchronizerNegocio();
        isNegocio = new IngresoSalidaNegocio();
        sn = new SchedulerNegocio(this);
        sn.start();
        
        conectados = new HashMap<String, Object>();
        keys = guardiaNegocio.obtenerTodosGuardias();

        initializeEvents();
    }

    private void initializeEvents() {
        core.setEventCore(this);
    }

    public void iniciar() {
        try {
            core.openSession(keys);
            log.info("Session iniciada del Core IDENTIFOUR :: Escuchando en el puerto : " + core.getPort());
        } catch (IOException ex) {
            log.error("Error al abrir la Session", ex);
        }
    }
    
    private void autenticarAdministrador(byte[] contenido, String key)
    {
        try {
            String email_pass = new String(contenido,"UTF-8");
            String email = email_pass.split(",")[0];
            String pass = email_pass.split(",")[1];
            
            int id_entorno = administradorNegocio.autenticarAdministrador(email, pass);
            List<Tranca> trancas = null;
            List<Guardia> guardias = null;
            if (id_entorno > 0)
            {
                trancas = trancaNegocio.obtenerTodasTrancas(id_entorno);
                guardias = guardiaNegocio.obtenerGuardiasEntorno(id_entorno);
            }
            
            Contrato contrato = new Contrato();
            contrato.setAccion(Accion.AUTENTICAR_ADMINISTRADOR);
            if (trancas != null && guardias != null && trancas.size() > 0 && guardias.size() > 0)
                contrato.setContenido(ObjectUtil.createBytes(trancas));
            else
                contrato.setContenido(null);
            contrato.setId_entorno(id_entorno);
            core.sendPackage(key, ObjectUtil.createBytes(contrato));
            
            if (trancas != null && guardias != null && trancas.size() > 0 && guardias.size() > 0)
            {
                Contrato contrato2 = new Contrato();
                contrato2.setAccion(Accion.GUARDIAS);
                contrato2.setContenido(ObjectUtil.createBytes(guardias));
                contrato2.setId_entorno(id_entorno);
                core.sendPackage(key, ObjectUtil.createBytes(contrato2));
            }
            
        } catch (UnsupportedEncodingException ex) {
            log.error("Error de codificacion",ex);
        }
    }
    
    private void llegoUnAviso(Contrato contrato, String key)
    {
        Aviso aviso = (Aviso)ObjectUtil.createObject(contrato.getContenido());
        List<String> ci = new ArrayList<>();
        switch (aviso.getTo())
        {
            case Aviso.DIRIGIDO_PROPIETARIOS: 
                log.info("Aviso para todos los propietarios ::: MENSAJE :" + aviso.getMensaje());
                ci.addAll(propietarioNegocio.obtenerPropietariosEntornoCI(contrato.getId_entorno()));
                break;
            case Aviso.DIRIGIDO_TRANCAS: 
                log.info("Aviso para todas las trancas ::: MENSAJE :" + aviso.getMensaje());
                ci.addAll(guardiaNegocio.obtenerGuardiasEntornoCI(contrato.getId_entorno()));
                break;
            case Aviso.DIRIGIDO_TODOS:  
                log.info("Aviso para todos ::: MENSAJE :" + aviso.getMensaje());
                ci.addAll(propietarioNegocio.obtenerPropietariosEntornoCI(contrato.getId_entorno()));
                ci.addAll(guardiaNegocio.obtenerGuardiasEntornoCI(contrato.getId_entorno()));
                break;
        }
        for (String c : ci)
        {
            if (conectados.containsKey(c))
            {
                core.sendPackage(c, ObjectUtil.createBytes(contrato));
            }
        }
    }
    
    private void llegoUnaAlarma(Contrato contrato, String key)
    {
        Alarma alarma = (Alarma)ObjectUtil.createObject(contrato.getContenido());
        List<String> ci = new ArrayList<>();
        if (alarma.getPrioridad().equalsIgnoreCase("Rojo"))
        {
            log.info("Alarma ROJA ::: EMISOR :"+ alarma.getEmisor());
            ci.addAll(propietarioNegocio.obtenerPropietariosEntornoCI(contrato.getId_entorno()));
            ci.addAll(guardiaNegocio.obtenerGuardiasEntornoCI(contrato.getId_entorno()));
        }else if (alarma.getPrioridad().equalsIgnoreCase("Amarillo"))
        {
            log.info("Alarma AMARILLA ::: EMISOR :"+ alarma.getEmisor());
            ci.addAll(propietarioNegocio.obtenerPropietariosEntornoCI(contrato.getId_entorno()));
            ci.addAll(guardiaNegocio.obtenerGuardiasEntornoCI(contrato.getId_entorno()));
        }else
        {
            log.info("Alarma VERDE ::: EMISOR :"+ alarma.getEmisor());
            ci.addAll(guardiaNegocio.obtenerGuardiasEntornoCI(contrato.getId_entorno()));
        }
        for (String c : ci)
        {
            if (conectados.containsKey(c))
            {
                core.sendPackage(c, ObjectUtil.createBytes(contrato));
            }
        }
    }
    
    private void llegoNotificacion(byte[] contenido, String key)
    {
        Notificacion notifi = (Notificacion)ObjectUtil.createObject(contenido);
        if (conectados.containsKey(notifi.getCi()))
        {
            Contrato contrato = new Contrato();
            contrato.setAccion(Accion.NOTIFICACION);
            contrato.setContenido(contenido);
            core.sendPackage(notifi.getCi(), ObjectUtil.createBytes(contrato));
        }
    }
    
    private void paqueteUpSincronizacion(Contrato contrato, String key)
    {
        List<Object> paquete = (List<Object>)ObjectUtil.createObject(contrato.getContenido());
        for (Object o : paquete)
        {
            if (o instanceof EstructureA)
            {
//                System.out.println("A");
                synchronizerNegocio.procesarEstructuraA((EstructureA) o);
            }else if (o instanceof EstructureB)
            {
//                System.out.println("B"); 
                synchronizerNegocio.procesarEstructuraB((EstructureB) o,contrato.getId_entorno());
            }
        }
    }
    
    private void solicitudSincronizacion(long last_id, int id_entorno, String key)
    {
        Long max_id = synchronizerNegocio.obtenerMaximoId();
        
        Contrato contrato = new Contrato();
        contrato.setAccion(Accion.MAX_ID);
        contrato.setContenido(ObjectUtil.createBytes(max_id));
        contrato.setId_entorno(id_entorno);
        core.sendPackage(key, ObjectUtil.createBytes(contrato));
        
        List<Object> obs = synchronizerNegocio.obtenerDatosABajar(last_id, id_entorno);
        Contrato contrato2 = new Contrato();
        contrato2.setAccion(Accion.DOWNLOAD);
        contrato2.setContenido(ObjectUtil.createBytes(obs));
        contrato2.setId_entorno(id_entorno);
        log.info("Se enviaron :: " + obs.size() + " :: objetos");
        core.sendPackage(key, ObjectUtil.createBytes(contrato2));
    }
    
    
    private void solicitudAutenticacionPropietario(byte[] contenido, String key)
    {
        try {
            String ci = new String(contenido, "UTF-8");
            Propietario p = propietarioNegocio.getPropietarioCINotUseable(ci);
            if (p != null)
                p.setFoto(null);
            
            Contrato contrato = new Contrato();
            contrato.setAccion(Accion.PROPIETARIO);
            if (p != null)
                contrato.setContenido(ObjectUtil.createBytes(p));
            else
                contrato.setContenido(null);
            core.sendPackage(key, ObjectUtil.createBytes(contrato));
            log.info("Se envio los datos de propietario a :" + key);
        } catch (UnsupportedEncodingException ex) {
            log.error("Error al obtener el CI",ex);
        }
    }
    
    private void solicitudHistoriales(String key)
    {
        List<HistorialIngresoSalida> lista = isNegocio.obtenerHistorialIngresoSalida(key);
        Contrato contrato = new Contrato();
        contrato.setAccion(Accion.HISTORIALES);
        contrato.setContenido(ObjectUtil.createBytes(lista));
        
        log.info("Se envio " + lista.size() + " historiales");
        core.sendPackage(key, ObjectUtil.createBytes(contrato));
    }
    
    private void denegarIngresoSalida(Contrato contrato)
    {
        try {
            String cad = new String(contrato.getContenido(),"UTF-8");
            String ci_guardia = cad.split(",")[0];
            core.sendPackage(ci_guardia, ObjectUtil.createBytes(contrato));
        } catch (UnsupportedEncodingException ex) {
            log.error("No se pudo deserealizar el CI guardia",ex);
        }
    }
    
    private boolean verificarLicencia(Contrato con, String key)
    {
        boolean licencia = administradorNegocio.getLicencia(con.getId_entorno());
        
        if (!licencia)
        {
            Contrato contrato = new Contrato();
            contrato.setAccion(Accion.LICENCIA_INACTIVA);
            contrato.setContenido(null);
            contrato.setId_entorno(con.getId_entorno());
            core.sendPackage(key, ObjectUtil.createBytes(contrato));
        }
        
        return licencia;
    }
    
    @Override
    public void onConnectClientClosed(String key) {
        log.debug("Conexion Cerrada habilitada para : " + key);
        Object o = keys.get(key);
        conectados.put(key, o);
        Guardia g = (Guardia) o;
        log.info("Guardia conectado :: CI :" + g.getCi() + " :: Nombre Apellido :" + g.getNombre() + " " + g.getApellido());
    }

    @Override
    public void onConnectClientOpened(String key, Object o) {
        Propietario propietario = (Propietario)o;
        conectados.put(key, o);
        log.info("Se ha conectado el propietario con CI :" + propietario.getCi());
        
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onDisconnectClient(String key) {
        log.debug("Conexion desconectada para : " + key);
        Object o = conectados.get(key);
        conectados.remove(key);
        if (o instanceof Guardia) {
            log.info("Guardia desconectado :: CI :" + key);
        }
    }

    @Override
    public void onNewPackage(long size, String key) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onNewTrama(int bytesRead, String key) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onNewPackageComplet(byte[] data, String key) {
        Object o = ObjectUtil.createObject(data);
        Contrato contrato = (Contrato)o;
        if (contrato.getId_entorno()>0 && ! verificarLicencia(contrato, key))
            return;
        Task task = new Task();
        task.setContrato(contrato);
        task.setKey(key);
        switch (contrato.getAccion())
        {
            case Accion.AUTENTICAR_ADMINISTRADOR    : sn.addMediumPriorityTask(task); break;
            case Accion.AVISO                       : sn.addHighPriorityTask(task); break;
            case Accion.ALARMA                      : sn.addHighPriorityTask(task); break;
            case Accion.UPDATE                      : sn.addLowPriorityTask(task); break;
            case Accion.DOWNLOAD                    : sn.addLowPriorityTask(task); break;
            case Accion.PROPIETARIO                 : sn.addMediumPriorityTask(task); break;
            case Accion.NOTIFICACION                : sn.addHighPriorityTask(task); break;
            case Accion.HISTORIALES                 : sn.addMediumPriorityTask(task); break;
            case Accion.DENEGAR_INGRESO_SALIDA      : sn.addHighPriorityTask(task); break;
        }
    }

    @Override
    public void onExceptionListening(String key, IOException ioe) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onFailedSend(IOException ioe, String key) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onSendClients(List<String> keys) {
        log.info("Se ha enviado informacion a varios clientes, cantidad :" + keys.size());
    }

    @Override
    public void onSendClient(String key) {
        log.info("Se ha enviado informacion al cliente con el CI :" + key);
    }

    
    
    ///SCHEDULER
    @Override
    public void doTask(Contrato contrato, String key) {
        switch (contrato.getAccion())
        {
            case Accion.AUTENTICAR_ADMINISTRADOR    : autenticarAdministrador(contrato.getContenido(),key); break;
            case Accion.AVISO                       : llegoUnAviso(contrato,key); break;
            case Accion.ALARMA                      : llegoUnaAlarma(contrato,key); break;
            case Accion.UPDATE                      : paqueteUpSincronizacion(contrato,key); break;
            case Accion.DOWNLOAD                    :try {
                                                        String cad = new String(contrato.getContenido(),"UTF-8");
                                                        solicitudSincronizacion(Long.parseLong(cad.split(",")[0]), Integer.parseInt(cad.split(",")[1]), key);
                                                    } catch (UnsupportedEncodingException ex) {
                                                        log.error("Error al obtener informacion del contenido");
                                                    }                                                                       break;
            case Accion.PROPIETARIO                 : solicitudAutenticacionPropietario(contrato.getContenido(), key); break;
            case Accion.NOTIFICACION                : llegoNotificacion(contrato.getContenido(),key); break;
            case Accion.HISTORIALES                 : solicitudHistoriales(key); break;
            case Accion.DENEGAR_INGRESO_SALIDA      : denegarIngresoSalida(contrato);
        }
    }
}
