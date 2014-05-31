/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firstone.core.datos.dao;

import firstone.core.datos.conexion.ServiceProvider;
import firstone.serializable.Synchronizer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Milton
 */
public class SynchronizerDAO {

    private static final Logger log = Logger.getLogger(SynchronizerDAO.class);

    public synchronized void insert(Synchronizer s) {
//        log.info("Guardar Bitacora :: GUARDIA :" + bitacora.getCi_guardia() + " :: ACCION :" + bitacora.getAccion());
        Connection con = null;
        PreparedStatement st = null;

        try {
            con = ServiceProvider.openConnection();

            String sql = "INSERT INTO sincronizador(transaccion,ref_id,tabla) VALUES(?,?,?)";

            st = con.prepareStatement(sql);
            if (st != null) {
                st.setString(1, s.getTransaccion());
                st.setString(2, s.getRef_id());
                st.setString(3, s.getTabla());

                st.execute();
            }
        } catch (SQLException e) {
            log.error("Error al realizar la insercion en la base de datos", e);
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                log.error("Error al cerrar el Statement", e);
            }

            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                log.error("Error al cerrar la conexion a la base de datos", e);
            }
        }
    }
    
//    public static List<Synchronizer> obtenerTransacciones()
//    {
//        log.info("Obteniendo todas las ultimas transacciones realizadas");
//        Connection con = null;
//        PreparedStatement st = null;
//        ResultSet rs = null;
//
//        List<Synchronizer> ss = new ArrayList<>();
//
//        try {
//            con = ServiceProvider.openConnection();
//
//            String sql = "SELECT * FROM synchronizer";
//            st = con.prepareStatement(sql);
//
//            if (st != null) {
//                rs = st.executeQuery();
//
//                while (rs.next()) {
//                    
//                    Synchronizer s = new Synchronizer();
//                    s.setRef_id(rs.getString("ref_id"));
//                    s.setTabla(rs.getString("tabla"));
//                    s.setTransaccion(rs.getString("transaccion"));
//                    
//                    ss.add(s);
//                }
//            }
//            st.close();
//            
//            sql = "TRUNCATE TABLE synchronizer";
//            st = con.prepareStatement(sql);
//            if (st != null)
//            {
//                st.execute();
//            }
//
//        } catch (SQLException e) {
//            log.error("Error al consultar a la base de datos", e);
//        } finally {
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//            } catch (SQLException e) {
//                log.error("Error al cerrar el ResultSet", e);
//            }
//
//            try {
//                if (st != null) {
//                    st.close();
//                }
//            } catch (SQLException e) {
//                log.error("Error al cerrar el Statement", e);
//            }
//
//            try {
//                if (con != null) {
//                    con.close();
//                }
//            } catch (SQLException e) {
//                log.error("Error al cerrar la conexion a la base de datos", e);
//            }
//        }
//        return ss;
//    }
   
}
