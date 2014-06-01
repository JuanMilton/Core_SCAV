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

    public synchronized void insert(Synchronizer s, int id_entorno) {
//        log.info("Guardar Bitacora :: GUARDIA :" + bitacora.getCi_guardia() + " :: ACCION :" + bitacora.getAccion());
        Connection con = null;
        PreparedStatement st = null;

        try {
            con = ServiceProvider.openConnection();

            String sql = "INSERT INTO sincronizador(transaccion,ref_id,tabla,id_entorno) VALUES(?,?,?,?)";

            st = con.prepareStatement(sql);
            if (st != null) {
                st.setString(1, s.getTransaccion());
                st.setString(2, s.getRef_id());
                st.setString(3, s.getTabla());
                st.setInt(4, id_entorno);

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
    
    public List<Synchronizer> obtenerTransacciones(long last_id, int id_entorno)
    {
        log.info("Obteniendo todas las ultimas transacciones realizadas desde el ID : " + last_id);
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        List<Synchronizer> ss = new ArrayList<>();

        try {
            con = ServiceProvider.openConnection();

            String sql = "SELECT * FROM sincronizador WHERE id > ? AND id_entorno = ?";
            st = con.prepareStatement(sql);

            if (st != null) {
                st.setLong(1, last_id);
                st.setInt(2, id_entorno);
                rs = st.executeQuery();

                while (rs.next()) {
                    
                    Synchronizer s = new Synchronizer();
                    s.setRef_id(rs.getString("ref_id"));
                    s.setTabla(rs.getString("tabla"));
                    s.setTransaccion(rs.getString("transaccion"));
                    
                    ss.add(s);
                }
            }

        } catch (SQLException e) {
            log.error("Error al consultar a la base de datos", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                log.error("Error al cerrar el ResultSet", e);
            }

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
        return ss;
    }
    
    public long maximoId()
    {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        long max = 0;
        try {
            con = ServiceProvider.openConnection();

            String sql = "SELECT MAX(id) as MAXIMO FROM sincronizador;";
            st = con.prepareStatement(sql);

            if (st != null) {
                rs = st.executeQuery();

                if (rs.next()) {
                    
                    max = rs.getLong("MAXIMO");
                }
            }

        } catch (SQLException e) {
            log.error("Error al consultar a la base de datos", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                log.error("Error al cerrar el ResultSet", e);
            }

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
        return max;
    }
   
}
