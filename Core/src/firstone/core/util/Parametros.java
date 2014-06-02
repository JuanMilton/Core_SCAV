package firstone.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Parametros {

	public static Logger log = Logger.getLogger(Parametros.class);

	// Lee los datos del Archivo de configuración
	protected final static String configFile = "etc" + File.separator + "config.properties";
	protected final static Properties prop = getProps(configFile);

	// Datos para Conexion a la BD.
        
	public static String DB_HOST                        = prop.getProperty("BD.servidor");
	public static String DB_BASE_DATOS                  = prop.getProperty("BD.nombre");
	public static String DB_PUERTO                      = prop.getProperty("BD.puerto");
	public static String DB_USUARIO                     = prop.getProperty("BD.usuario");
	public static String DB_CONTRASENIA                 = prop.getProperty("BD.contrasenna");
        
        public static Integer LISTENER_PORT                 = Integer.parseInt(prop.getProperty("LISTENER.PORT"));
        
        public static Integer CANT_HISTORIALES_CONSULTA_PROPIETARIOS = Integer.parseInt(prop.getProperty("CANT.HISTORIALES.CONSULTA.PROPIETARIOS"));
	/** flujo de entrada relacionado con el archivo de configuracion */
	private static InputStream configFileStream;

	public static Properties getProps(String configFile) {
		Properties props = new Properties();
		try {
			// configFileStream = new FileInputStream("etc/" + configFile);
			configFileStream = new FileInputStream(configFile);
			props.load(configFileStream);
		} catch (Exception e) {
			log.error("[getProps]: Error al cargar archivo" + e.getMessage());
		}
		return props;
	}

}
