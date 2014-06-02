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
public class TrancaDAO {

    private static final Logger log = Logger.getLogger(TrancaDAO.class);

    public synchronized List<Tranca> getAllTrancasEntorno(int id_entorno) {
//        log.info("obtener Visita :: CI :" + ci);
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        List<Tranca> trancas = null;

        try {
            con = ServiceProvider.openConnection();

            String sql = "SELECT * FROM tranca WHERE id_entorno = ?";
            st = con.prepareStatement(sql);

            if (st != null) {

                st.setInt(1, id_entorno);
                rs = st.executeQuery();

                trancas = new ArrayList<>();
                while (rs.next()) {
                    Tranca tranca = new Tranca();
                    tranca.setId(rs.getInt("id"));
                    tranca.setDescripcion(rs.getString("descripcion"));
                    tranca.setTipo(rs.getString("tipo"));
                    tranca.setId_entorno(rs.getInt("id_entorno"));
                    trancas.add(tranca);
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
        return trancas;
    }
    
    public synchronized Tranca get(int id) {
//        log.info("obtener Visita :: CI :" + ci);
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        Tranca tranca = null;

        try {
            con = ServiceProvider.openConnection();

            String sql = "SELECT * FROM tranca WHERE id = ?";
            st = con.prepareStatement(sql);

            if (st != null) {

                st.setInt(1, id);
                rs = st.executeQuery();
                if (rs.next()) {
                    tranca = new Tranca();
                    tranca.setId(rs.getInt("id"));
                    tranca.setDescripcion(rs.getString("descripcion"));
                    tranca.setTipo(rs.getString("tipo"));
                    tranca.setId_entorno(rs.getInt("id_entorno"));
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
        return tranca;
    }
    
}
