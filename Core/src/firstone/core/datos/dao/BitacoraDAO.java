/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firstone.core.datos.dao;

import firstone.core.datos.conexion.ServiceProvider;
import firstone.serializable.Bitacora;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Milton
 */
public class BitacoraDAO {

    private static final Logger log = Logger.getLogger(BitacoraDAO.class);

        public synchronized void insert(Bitacora bitacora) {
        log.info("Guardar Bitacora :: GUARDIA :" + bitacora.getRef_ci_guardia() + " :: ACCION :" + bitacora.getAccion());
        Connection con = null;
        PreparedStatement st = null;

        try {
            con = ServiceProvider.openConnection();

            String sql = "INSERT INTO bitacora_guardia(fecha_hora,accion,detalle,ci_guardia) VALUES(?,?,?,?)";

            st = con.prepareStatement(sql);
            if (st != null) {
                st.setTimestamp(1, new Timestamp(bitacora.getFecha_hora()));
                st.setString(2, bitacora.getAccion());
                st.setString(3, bitacora.getDetalle());
                st.setString(4, bitacora.getRef_ci_guardia());
                
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
