/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package firstone.core.datos.dao;

import firstone.core.datos.conexion.ServiceProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 *
 * @author Milton
 */
public class AvisoTrancaDAO {
    private static final Logger log = Logger.getLogger(GuardiaDAO.class);

    public synchronized int cantidad() {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        int res = 0;

        try {
            con = ServiceProvider.openConnection();

            String sql = "SELECT count(*) as CANT FROM aviso_tranca";
            st = con.prepareStatement(sql);

            if (st != null) {
                rs = st.executeQuery();

                if (rs.next()) {
                    res = rs.getInt("CANT");
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
    
    
}
