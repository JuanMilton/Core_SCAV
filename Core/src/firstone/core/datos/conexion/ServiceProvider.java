package firstone.core.datos.conexion;

import firstone.core.util.Parametros;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;


import org.apache.log4j.Logger;

public class ServiceProvider {
	
	private static final Logger log = Logger.getLogger(ServiceProvider.class);

	public static synchronized Connection openConnection() {
		Connection conexion = null;
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			String url = "jdbc:postgresql://"+Parametros.DB_HOST+":"+Parametros.DB_PUERTO+"/"+Parametros.DB_BASE_DATOS;
			conexion = DriverManager.getConnection(url, Parametros.DB_USUARIO, Parametros.DB_CONTRASENIA);

		} catch (ClassNotFoundException e) {
                    log.error("Error al encontrar Clase, " + e);
		} catch (    InstantiationException | IllegalAccessException ex) {
                    log.error("Error al Iniciar o al realizar el Acceso a la Base de Datos",ex);
                } catch (SQLException ex) {
                    log.error("Error de consulta",ex);
                }
		return conexion;
	}

}
