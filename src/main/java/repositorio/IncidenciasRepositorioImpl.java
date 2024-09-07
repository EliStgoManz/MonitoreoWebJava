package repositorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import datosRecursos.ConexionPoolIncidencias;
import modelo.Respuestas;

//@author Elí Santiago Manzano

public class IncidenciasRepositorioImpl implements Repositorio<Respuestas> {
	
	
	private static Connection getConnection() throws SQLException{
		return ConexionPoolIncidencias.getConnection();
	}
	

	@Override
	public List<Respuestas> listar() {
		// TODO Auto-generated method stub
		List<Respuestas> respuestas=new ArrayList();
		try(Statement stmt=getConnection().createStatement();
			ResultSet rs= stmt.executeQuery("SELECT*FROM incidenciasservidores")	){
			
			
			while(rs.next()) {
				Respuestas rsp=crearRespuestas(rs);
				respuestas.add(rsp);
		}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return respuestas;
	}
	


	@Override
	public Respuestas porId(Long incidencias_id) {
		Respuestas resp=null;
		
		try(PreparedStatement stmt=getConnection().prepareStatement("select*from incidenciasservidores where incidencias_id=?")){
			stmt.setLong(1, incidencias_id);
		
			try(ResultSet rs= stmt.executeQuery()){
				if(rs.next()) {
			    resp=crearRespuestas(rs);
			}
					
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	public void guardar(Respuestas t) {
		

	String sql="";
	if (t.getIncidencias_id()!=null && t.getIncidencias_id()>0) {
		
	 sql="UPDATE incidenciasservidores SET nombrepath=?,nombrehost=?,responsecode=? WHERE incidencias_id=?";
	}else {
	 sql="INSERT INTO incidenciasservidores(nombrepath,nombrehost,responsecode,fecha)VALUES(?,?,?,?)";
	}
			
			
	try(PreparedStatement stmt=getConnection().prepareStatement(sql)){
	 stmt.setString(1,t.getPath());
	 stmt.setString(2,t.getHost());
	 stmt.setInt(3, t.getResponseCode());
	 
	 if  (t.getIncidencias_id()!=null && t.getIncidencias_id()>0) {
		 stmt.setString(4,t.getFecha());
		 
		 //stmt.setDate(4, (Date) t.getFecha());
	 }else {
		 stmt.setString(4,t.getFecha());
		 
		//stmt.setDate(4, (Date) t.getFecha());
	 }
	 
	 
	 
	 stmt.executeUpdate();
	}catch(SQLException e) {
	 e.printStackTrace();
	 System.out.println("incidencia al guardar en base datos incidencias"+ e.getMessage());
	}
	
		
	}

	@Override
	public void eliminar(long incidencias_id) {
	try(PreparedStatement stmt= getConnection().prepareStatement("DELETE FROM incidenciasservidores where incidencias_id")){
    	stmt.setLong(1, incidencias_id);	
    	stmt.executeUpdate();
	} catch (SQLException e) {

		e.printStackTrace();
	}
		
	}
	
	
	private Respuestas crearRespuestas(ResultSet rs)throws SQLException{
		Respuestas res=new Respuestas();
		res.setHost(rs.getString("nombreHost"));
		res.setPath(rs.getString("nombrePath"));
		res.setResponseCode(rs.getInt("responseCode"));
		res.setFecha(rs.getString("fecha"));
		return res;
		}
	

}
