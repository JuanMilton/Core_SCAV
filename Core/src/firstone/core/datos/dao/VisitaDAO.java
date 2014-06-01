/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firstone.core.datos.dao;

import firstone.core.datos.conexion.ServiceProvider;
import firstone.serializable.Visita;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 *
 * @author Milton
 */
public class VisitaDAO {

    private static final Logger log = Logger.getLogger(VisitaDAO.class);

    public synchronized Visita get(String ci) {
//        log.info("obtener Visita :: CI :" + ci);
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        Visita visita = null;

        try {
            con = ServiceProvider.openConnection();

            String sql = "SELECT * FROM visita WHERE ci = ?";
            st = con.prepareStatement(sql);

            if (st != null) {

                st.setString(1, ci);
                rs = st.executeQuery();

                if (rs.next()) {
                    visita = new Visita();
                    visita.setCi(ci);
                    visita.setNombres(rs.getString("nombres"));
                    visita.setApellidos(rs.getString("apellidos"));
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
        return visita;
    }
    
    public synchronized void insert(Visita visita) {
        log.info("Guardar visita :: CI " + visita.getCi());
        Connection con = null;
        PreparedStatement st = null;

        try {
            con = ServiceProvider.openConnection();

            String sql = "INSERT INTO visita(ci,nombres,apellidos) VALUES(?,?,?)";

            st = con.prepareStatement(sql);
            if (st != null) {
                st.setString(1, visita.getCi());
                st.setString(2, visita.getNombres());
                st.setString(3, visita.getApellidos());
                
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
}
