/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firstone.core.datos.dao;

import firstone.core.datos.conexion.ServiceProvider;
import firstone.serializable.IngresoSalidaVisita;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Milton
 */
public class IngresoSalidaVisitaDAO {

    private static final Logger log = Logger.getLogger(IngresoSalidaVisitaDAO.class);

        public synchronized void insert(String tipo, Date fecha_hora, int id_tranca, String placa) {
        log.info("Guardar IngresoSalida de Visita :: PLACA :" + placa + " :: TIPO :" + tipo + " :: FECHA_HORA :" + fecha_hora);
        Connection con = null;
        PreparedStatement st = null;

        try {
            con = ServiceProvider.openConnection();

            String sql = "INSERT INTO ingreso_salida_visita(tipo,fecha_hora,id_tranca,placa) VALUES(?,?,?,?)";

            st = con.prepareStatement(sql);
            if (st != null) {
                st.setString(1, tipo);
                st.setTimestamp(2, new Timestamp(fecha_hora.getTime()));
                st.setInt(3, id_tranca);
                st.setString(4, placa);
                
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
        
    public List<IngresoSalidaVisita> obtenerIngresosSalidasVisita()
    {
        log.info("Obteniendo todas los ingresos y salidas de las visitas realizados");
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        List<IngresoSalidaVisita> ss = new ArrayList<>();

        try {
            con = ServiceProvider.openConnection();

            String sql = "SELECT * FROM ingreso_salida_visita";
            st = con.prepareStatement(sql);

            if (st != null) {
                rs = st.executeQuery();

                while (rs.next()) {
                    
                    IngresoSalidaVisita s = new IngresoSalidaVisita();
                    s.setFecha_hora(rs.getTimestamp("fecha_hora").getTime());
                    s.setId_tranca(rs.getInt("ref_id_tranca"));
                    s.setPlaca(rs.getString("ref_placa_vehiculo_visita"));
                    s.setTipo(rs.getString("tipo"));
                    
                    ss.add(s);
                }
            }
            st.close();
            
            sql = "TRUNCATE TABLE ingreso_salida_visita";
            st = con.prepareStatement(sql);
            if (st != null)
            {
                st.execute();
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
}
