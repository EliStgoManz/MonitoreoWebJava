
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.net.URL"%>
<%@page import="java.util.Scanner"%>
<%@page import="java.io.OutputStreamWriter"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.net.URL"%>



<!DOCTYPE html>
<html>
<head>
<meta charset="Content-Type" content="text/html; charset=UTF-8">
<title>WHATSAPP MESSAGE EFECTIVALE</title>
</head>
<body>

	<%
	//TOKEN QUE NOS DA FACEBOOK
	String token="EAADsQKlicEMBO2RlKEQ9iz0IbcooVwsGVw2MoiCl3BHqrkcxPaWUpsTf36jWuCk5saZBdzbzoDh0AEUinGNwF9KUcMLVBLUlBHiJvkjrfU0X9LETFztx6V1AChoH1NhDj8yeiFLL9v4ZCrbbRQbAaobASzxXMzQM6YeSqTatbW3jvekMWnG7SPB9yLl4czx2k0bye1gQZBl7DdyDM47AFW7oUf53bc63OMZD";
	//NUESTRO TELEFONO
	String telefono = "529516449114";
	//IDENTIFICADOR DE NUMERO DE TELEFONO
	String idNumero = "356387480887111";
	
	String nombre="Jazibe";

	//realizamos nuestras propies respuestas:
	String respuestaTexto = "Hola queridos amigos de Efectivale, nos complace comunicarles\n por este medio";

	//COLOCAMOS LA URL PARA ENVIAR EL MENSAJE
	URL url = new URL("https://graph.facebook.com/v19.0/" + idNumero + "/messages");

	//INICIALIZAMOS EL CONTENEDOR DEL ENVIO
	HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

	//EL TIPO DE ENVIO DE DATOS VA A SER VIA POST
	httpConn.setRequestMethod("POST");

	//CODIGO DE AUTORIZACION DE JAVA
	httpConn.setRequestProperty("Authorization", "Bearer " + token);

	//DEFINIMOS QUE LOS DATOS SERAN TRATADOS COMO JSON
	httpConn.setRequestProperty("Content-Type", "application/json; application/x-www-form-urlencoded; charset=UTF-8");

	//httpConn.setRequestProperty("Content-type", "application/json");

	//PREPARAMOS Y ENVIAMOS EL JSON
	httpConn.setDoOutput(true);

	OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());

	/*Plantilla 1 de meta-FACEBOOK; Error: 404_NOT_FOUND*/

	writer.write("{" + "\"messaging_product\": \"whatsapp\"," 
	            + "\"to\": \"" + telefono + "\"," + 
	            "\"type\": \"template\","
			    + "\"template\": " + "{ \"name\": \"saludos_not_j\"," 
	            + "\"language\": { \"code\": \"es\" },"
	            +"\"components\":  ["
	            +"{"
	            +"\"type\": \"body\","
	            +"\"parameters\": ["
	            +"{"
	            +"\"type\": \"text\","
	            +"\"text\": \""+nombre+"\" "
	            +"}"
	            +"]"
	            +"}"
	            +"]"
	         + "}"
			+ "}");
	
	
	/*plantilla 2 de facebook; Error: 505_INTERNAL_SERVER_ERROR*/
	/*writer.write("{" + "\"messaging_product\": \"whatsapp\"," + "\"to\": \"" + telefono + "\"," + "\"type\": \"template\","
	+ "\"template\": " + "{ \"name\": \"internal_server_error\"," + "\"language\": { \"code\": \"es\" }" + "}"
	+ "}");*/
	
	
			
	/*plantilla 3: Esta es la plantilla que ocuparemos para diseñar, nuestra propia plantilla
	esta plantilla no arroja algún resultado malo, pero no es aceptada por la api*/
			
	/* writer.write("{"
	 +"\"messaging_product\": \"whatsapp\","
	 +"\"recipient_type\": \"individual\","
	 +"\"to\": \""+telefono+"\","
	 +"\"type\": \"text\","
	 +"\"text\": "
	 +"{ \"preview_url\": \"false\","
	+"\"body\":  \"holaaaa que tal amigos\"," 
	+"}"
	+"}");*/
		

		//Esta es la plantilla que ocuparemos para diseñar, nuestra propia plantilla
		//esta plantilla no arroja algún resultado malo, pero no es aceptada por la api
		
		/*writer.write("{"
		+"\"messaging_product\": \"whatsapp\","
		+"\"recipient_type\": \"individual\","
		+"\"to\": \""+telefono+"\","
		+ "\"type\": \"template\","
		+ "\"template\": " 
	    + "{ \"name\": \"plantilla_not_found\"," 
		+ "\"language\": { \"code\": \"es\" }" 
		+"\"type\": \"text\","
		+"\"text\": "
		+"{ \"preview_url\": \"false\","
	    +"\"body\":  \"Hola Estimado(a) usuario(a),por medio de este medio notificamos un error de incidencia en nuestro servidor: 404\"," 
		+"}"
		+"}"
	    +"}");*/
			
/**/
  /*writer.write("{"
     + "\"messaging_product\": \"whatsapp\","
	 + "\"to\": \""+telefono+"\","
	 + "\"type\": \"template\","
	 + "\"template\": "
	 +"\"name\": \"notificacion_incidencia_efectivale\","
	 +"\"language\": {\"code\": \"en\"}\","
	 +"\"components\": "
	 +"["
	+"{"
	 +"\type\": \"body\","
	 +"\"parameters\": "
	 +"["
	 +"{"
	 +"\"type\": \"text\","
	 +"\"text\": \"Hola este mensaje, es para informar,la incidencia de un sistema\" "
	 +"}"
	 +","
	 +"{"
	 +"\"type\": \"currency\", "
	 +"\"currency\": "
	 +"{"
	 +"\"fallback_value\": \"$100.99\", "
	 +"\"code\": \"USD\" ,"
	 +"\"amount_1000\": \"100990\" "
	 +"}"
	 +"} "
     +"]"
	+"}"
	+"]"                    
	+"}"
	+"}");*/
  


	
   //LIMPIAMOS LOS DATOS
	writer.flush();
	//cerramos los datos
	writer.close();
	//cerramos la conexion
	httpConn.getOutputStream().close();
	//Recibimos el resultado del envio
	InputStream responseStream = httpConn.getResponseCode() / 100 == 2 ? httpConn.getInputStream()
			: httpConn.getErrorStream();
	Scanner s = new Scanner(responseStream).useDelimiter("\\A");

	//OBTENEMOS LOS RESULTADOS
	String respuesta = s.hasNext() ? s.next() : "";

	System.out.println("Response aplicacion MonitoreosAppAndWeb1? : " + respuesta);

	//s.close();
	%>

</body>
</html>