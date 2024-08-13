package beans;


/*@Author: Elí Santiago Manzano */
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import datosRecursos.ConexionPoolIncidencias;
import modelo.Respuestas;
import repositorio.Repositorio;
import repositorio.IncidenciasRepositorioImpl;

public class EjemploJdbc {
	
	public static void main(String[]args) throws SQLException {
		try(Connection conn=ConexionPoolIncidencias.getConnection()){
			
			Repositorio<Respuestas> repositorio=new IncidenciasRepositorioImpl();
			System.out.println("<=========== Listar ============>\n");
			repositorio.listar().forEach(r->System.out.println(r.getHost()));
			
			
		    System.out.println("<=========== Listar por id============>\n");
			System.out.println(repositorio.porId(1L));
			
			System.out.println("<=========== insertar nueva incidencia 1.1 ============>\n");
			Respuestas respuesta=new Respuestas();
		
			respuesta.setPath("PLD");
			respuesta.setHost("localhost");
			respuesta.setResponseCode(404);
			respuesta.setFecha("28-06-2024 16:18:28");
			
			repositorio.guardar(respuesta);
			System.out.println("Producto guardado con éxito");
			
			
			
			
		
			
			
		 }catch(SQLException e) {
		 e.printStackTrace();
		}
	}

}
