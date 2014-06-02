/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firstone.core.datos.dao;

import firstone.serializable.Guardia;
import firstone.core.datos.conexion.ServiceProvider;
import firstone.serializable.Propietario;
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
public class PropietarioDAO {

    private static final Logger log = Logger.getLogger(PropietarioDAO.class);

    
    public synchronized List<String> getPropietariosEntornoCI(int id_entorno) {
//        log.info("obtener Visita :: CI :" + ci);
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        List<String> guardias = null;

        try {
            con = ServiceProvider.openConnection();

            String sql = "SELECT ci FROM propietario WHERE id_entorno = ?";
            st = con.prepareStatement(sql);

            if (st != null) {
                st.setInt(1, id_entorno);
                rs = st.executeQuery();

                guardias = new ArrayList<>();
                while (rs.next()) {
                    guardias.add(rs.getString("ci"));
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
        return guardias;
    }
    
    public synchronized firstone.serializable.Propietario getPropietarioCI(String ci) {
//        log.info("obtener Visita :: CI :" + ci);
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        firstone.serializable.Propietario propietario = null;

        try {
            con = ServiceProvider.openConnection();

            String sql = "SELECT * FROM propietario WHERE ci = ?";
            st = con.prepareStatement(sql);

            if (st != null) {
                st.setString(1, ci);
                rs = st.executeQuery();

                if (rs.next()) {
                    propietario = new Propietario();
                    propietario.setApellidos(rs.getString("apellidos"));
                    propietario.setCi(ci);
                    propietario.setFoto(rs.getBytes("foto"));
                    propietario.setNombres(rs.getString("nombres"));
                    propietario.setNro_licencia(rs.getString("nro_licencia"));
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
        return propietario;
    }
    
    public synchronized firstone.serializable.Propietario getPropietarioCINotUseable(String ci) {
//        log.info("obtener Visita :: CI :" + ci);
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        firstone.serializable.Propietario propietario = null;

        try {
            con = ServiceProvider.openConnection();

            String sql = "SELECT * FROM propietario WHERE ci = ?";
            st = con.prepareStatement(sql);

            if (st != null) {
                st.setString(1, ci);
                rs = st.executeQuery();

                if (rs.next()) {
                    propietario = new Propietario();
                    propietario.setApellidos(rs.getString("apellidos"));
                    propietario.setCi(ci);
                    propietario.setFoto(rs.getBytes("foto"));
                    propietario.setNombres(rs.getString("nombres"));
                    propietario.setNro_licencia(rs.getInt("id_entorno")+"");
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
        return propietario;
    }

//    public synchronized void insert(Visita visita) {
//        log.info("Guardar visita :: CI " + visita.getCi());
//        Connection con = null;
//        PreparedStatement st = null;
//
//        try {
//            con = ServiceProvider.openConnection();
//
//            String sql = "INSERT INTO visita(ci,nombres,apellidos) VALUES(?,?,?)";
//
//            st = con.prepareStatement(sql);
//            if (st != null) {
//                st.setString(1, visita.getCi());
//                st.setString(2, visita.getNombres());
//                st.setString(3, visita.getApellidos());
//                
//                st.execute();
//            }
//        } catch (SQLException e) {
//            log.error("Error al realizar la insercion en la base de datos", e);
//        } finally {
//            try {
//                if (st != null) {
//                    st.close();
//                }
//            } catch (SQLException e) {
//                log.error("Error al cerrar el Statement", e);
//            }
//
//            try {
//                if (con != null) {
//                    con.close();
//                }
//            } catch (SQLException e) {
//                log.error("Error al cerrar la conexion a la base de datos", e);
//            }
//        }
//    }
}
