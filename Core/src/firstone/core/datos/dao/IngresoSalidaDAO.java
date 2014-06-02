/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firstone.core.datos.dao;

import firstone.core.datos.conexion.ServiceProvider;
import firstone.core.util.Parametros;
import firstone.serializable.HistorialIngresoSalida;
import firstone.serializable.IngresoSalida;
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
public class IngresoSalidaDAO {

    private static final Logger log = Logger.getLogger(IngresoSalidaDAO.class);

        public synchronized void insert(String tipo, Date fecha_hora, int id_tranca, String placa) {
        log.info("Guardar IngresoSalida de Vehiculo :: PLACA :" + placa + " :: TIPO :" + tipo + " :: FECHA_HORA :" + fecha_hora);
        Connection con = null;
        PreparedStatement st = null;

        try {
            con = ServiceProvider.openConnection();

            String sql = "INSERT INTO ingreso_salida(tipo,fecha_hora,id_tranca,placa) VALUES(?,?,?,?)";

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

    public List<HistorialIngresoSalida> obtenerHistorialIngresoSalida(String ci) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        List<HistorialIngresoSalida> ss = new ArrayList<>();

        try {
            con = ServiceProvider.openConnection();

            String sql = "SELECT i.fecha_hora AS FECHA_HORA, i.tipo AS TIPO, i.placa AS PLACA, t.descripcion AS DESCRIPCION \n" +
                        "	FROM ingreso_salida i INNER JOIN tranca t ON i.id_tranca = t.id \n" +
                        "		WHERE placa IN (SELECT placa FROM propietario_vehiculo v INNER JOIN propietario p ON v.ci = p.ci\n" +
                        "			WHERE p.ci = ? ) ORDER BY i.fecha_hora DESC LIMIT ?";
            st = con.prepareStatement(sql);

            if (st != null) {
                st.setString(1, ci);
                st.setInt(2, Parametros.CANT_HISTORIALES_CONSULTA_PROPIETARIOS);
                rs = st.executeQuery();

                while (rs.next()) {
                    
                    HistorialIngresoSalida his = new HistorialIngresoSalida();
                    his.setFecha_hora(rs.getTimestamp("fecha_hora").getTime());
                    his.setPlaca(rs.getString("placa"));
                    his.setTipo(rs.getString("tipo"));
                    his.setTranca(rs.getString("descripcion"));
                    
                    ss.add(his);
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
     
}
