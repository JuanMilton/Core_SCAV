/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firstone.core.datos.dao;

import firstone.core.datos.conexion.ServiceProvider;
import firstone.serializable.VehiculoVisita;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 *
 * @author Milton
 */
public class VehiculoVisitaDAO {

    private static final Logger log = Logger.getLogger(VehiculoVisitaDAO.class);

    
    public synchronized VehiculoVisita get(String placa) {
//        log.debug("obtener VehiculoVisita :: PLACA :" + placa);
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        VehiculoVisita vehiculoVisita = null;

        try {
            con = ServiceProvider.openConnection();

            String sql = "SELECT * FROM vehiculo_visita WHERE placa = ?";
            st = con.prepareStatement(sql);

            if (st != null) {

                st.setString(1, placa);
                rs = st.executeQuery();

                if (rs.next()) {
                    vehiculoVisita = new VehiculoVisita();
                    vehiculoVisita.setPlaca(placa);
                    vehiculoVisita.setMarca(rs.getString("marca"));
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
        return vehiculoVisita;
    }
    
    public synchronized void insert(VehiculoVisita vehiculoVisita) {
        log.info("Guardar VehiculoVisita :: PLACA :" + vehiculoVisita.getPlaca());
        Connection con = null;
        PreparedStatement st = null;

        try {
            con = ServiceProvider.openConnection();

            String sql = "INSERT INTO vehiculo_visita(placa,marca) VALUES(?,?)";

            st = con.prepareStatement(sql);
            if (st != null) {
                st.setString(1, vehiculoVisita.getPlaca());
                st.setString(2, vehiculoVisita.getMarca());
                
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
