/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firstone.core.datos.dao;

import firstone.serializable.Tranca;
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
public class TelefonoDAO {

    private static final Logger log = Logger.getLogger(TelefonoDAO.class);

    public synchronized String getRefCI(Integer telefono) {
//        log.info("obtener Visita :: CI :" + ci);
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        String ci = null;

        try {
            con = ServiceProvider.openConnection();

            String sql = "SELECT ci FROM telefono_propietario WHERE telefono = ?";
            st = con.prepareStatement(sql);

            if (st != null) {

                st.setInt(1, telefono);
                rs = st.executeQuery();

                if (rs.next()) {
                    ci = rs.getString("ci");
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
        return ci;
    }
    
}
