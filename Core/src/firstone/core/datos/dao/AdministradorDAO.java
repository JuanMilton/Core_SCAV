/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package firstone.core.datos.dao;

import firstone.serializable.Guardia;
import firstone.core.datos.conexion.ServiceProvider;
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
public class AdministradorDAO {
    private static final Logger log = Logger.getLogger(GuardiaDAO.class);

    public synchronized int autenticar(String email, String password) {
//        log.info("obtener Visita :: CI :" + ci);
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        int res = 0;

        try {
            con = ServiceProvider.openConnection();

            String sql = "SELECT id FROM entorno WHERE licencia_activa = ? AND ci IN (SELECT ci FROM administrador_entorno WHERE email = ? AND password = ?)";
            st = con.prepareStatement(sql);

            if (st != null) {
                st.setBoolean(1, true);
                st.setString(2, email);
                st.setString(3, password);
                rs = st.executeQuery();

                if (rs.next()) {
                    res = rs.getInt("id");
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
        return res;
    }   

    public boolean getLicencia(int id_entorno) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        boolean licencia = false;

        try {
            con = ServiceProvider.openConnection();

            String sql = "SELECT licencia_activa FROM entorno WHERE id = ?";
            st = con.prepareStatement(sql);

            if (st != null) {
                st.setInt(1, id_entorno);
                rs = st.executeQuery();

                if (rs.next()) {
                    licencia = rs.getBoolean("licencia_activa");
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
        return licencia;
    }
    
    
}
