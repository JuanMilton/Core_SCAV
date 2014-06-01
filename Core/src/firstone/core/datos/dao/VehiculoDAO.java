/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firstone.core.datos.dao;

import firstone.core.datos.conexion.ServiceProvider;
import firstone.serializable.Vehiculo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 *
 * @author Milton
 */
public class VehiculoDAO {

    private static final Logger log = Logger.getLogger(VehiculoDAO.class);

    public synchronized Vehiculo get(String placa) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        Vehiculo vehiculo = null;

        try {
            con = ServiceProvider.openConnection();

            String sql = "SELECT * FROM vehiculo WHERE placa = ?";
            st = con.prepareStatement(sql);

            if (st != null) {

                st.setString(1, placa);
                rs = st.executeQuery();

                if (rs.next()) {
                    vehiculo = new Vehiculo();
                    vehiculo.setFoto(rs.getBytes("foto"));
                    vehiculo.setMarca(rs.getString("marca"));
                    vehiculo.setModelo(rs.getString("modelo"));
                    vehiculo.setPlaca(rs.getString("placa"));
                    vehiculo.setRfid(rs.getInt("rfid"));
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
        return vehiculo;
    }

}
