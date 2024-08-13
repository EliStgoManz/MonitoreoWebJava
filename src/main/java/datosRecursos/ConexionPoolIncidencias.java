package datosRecursos;

/*@Author: Elí Santiago Manzano*/
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

public class ConexionPoolIncidencias {
	
	private static String url="jdbc:postgresql://10.250.193.20:5434/dbpld";
	private static String userName="postgres";
	private static String password="ver9batim";
	private static BasicDataSource pool;
	private static int numConexiones=0;
	
	
	private static BasicDataSource getInstance()throws SQLException{
		
		 if(pool==null) {
			 
		  pool=new BasicDataSource();
		  pool.setUrl(url);
		  pool.setUsername(userName);
		  pool.setPassword(password);
		  pool.setInitialSize(3);
		  pool.setMinIdle(10);
		  pool.setMaxIdle(10);
		  pool.setMaxActive(50);
		  pool.setTimeBetweenEvictionRunsMillis(18000000);
		  pool.setValidationQuery("SELECT 1");
		  
		  
		  pool.setTestOnBorrow(true);
		  pool.setTestOnReturn(true);
		  pool.setTestWhileIdle(true);
		  
		  numConexiones++;
		  System.out.println("pool de conexión para base de datos incidencias: "+ numConexiones);
		 }
		 
		 return pool;
		
		
	}
	
	
	public static Connection getConnection()throws SQLException{
		return getInstance().getConnection();
	}
	

}
