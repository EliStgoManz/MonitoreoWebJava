
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.net.URL"%>
<%@page import="java.util.Scanner"%>
<%@page import="java.io.OutputStreamWriter"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.net.URL"%>
<%@page import="java.io.BufferedReader" %>
<%@page import="java.io.File" %>
<%@page import="java.io.FileReader" %>
<%@page import="java.io.IOException" %>
<%@page import="java.io.InputStream" %>
<%@page import="java.io.OutputStream" %>
<%@page import="java.io.OutputStreamWriter" %>
<%@page import="java.time.Instant" %>
<%@page import="java.time.LocalDateTime" %>
<%@page import="java.time.format.DateTimeFormatter" %>
<%@page import="java.util.TimeZone" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="Content-Type" content="text/html; charset=UTF-8">
<title>API URL-MESSAGE API WHATSAPP</title>
</head>
<body>
<%

try {

	/*
	 * 1.-Creamos un objeto tipo File, el cual leera la ruta de los sistemas web en
	 * efectivale, que se tienen que monitorear en caso de notificar algún problema
	 */
	File fileUrlsEfv = new File("C:/Users/eli.santiago/OneDrive - FleetCor/Documentos/Documentación-proyectosEfectivale/UrlSistemas.txt");
	

	/*
	 * 2.-Creando un objeto FileReader que contendra la dirección podremos leer un
	 * fichero de texto
	 */
	try (FileReader fr = new FileReader(fileUrlsEfv)) {
		
		
		/* Aquí incluimos también las variables de Facebook */

		// Token que nos proporciona Facebook
		String token = "EAADsQKlicEMBO2YrH7se8cnWJy88mbNrn8jSYVZAqGUBNeXjHVdHUZBndlcZCUBo3Nq1HKHpczgk88fzQc3nje80GZCpZCKphzBZBGeYVhOYYpb94H2E0DeMRAH0vowO7MO8e17qVUuKyAEh4iIJflFBgyTCxFZB8WZB0HEUlK9PpWSLkpFYTsDlg7tYyFIZCoP2XY6WT5BZAgZBqXlQ3iuYKYFhcBZAubzQzCDac0YZD";
		// NUESTRO TELEFONO QUIEN RECIBIRA EL MENSAJE O PLANTILLA
		String telefono = "529516470269";

		// IDENTIFICADOR DE NUMERO DE TELEFONO
		String idNumero = "356387480887111";

		/* 2.-Podemos construir un objeto BufferedReader */
		BufferedReader in = new BufferedReader(fr);
		String inputLine;
		StringBuffer respuest = new StringBuffer();
		StringBuffer responsability = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {

			responsability = respuest.append(inputLine);
			String resulResponse = responsability.toString();

			//System.out.println("resultResponse: " + resulResponse);

			String data = "";

			/*
			 * La clase URL contiene constructores y métodos para la manipulación de
			 * URL(Universal Resource Locator): un objeto servicio Internet
			 */
			URL url = new URL(inputLine);
			
			System.out.println("resultado de urlAplicativoMonitoreo: " + url);


		// INICIALIZAMOS EL CONTENEDOR DEL ENVIO para la url de los sistemas
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("Content-Type", "text/plain;utf-8");
			conn.setDoOutput(true);
			
			
			
			/* Colocamos la URL de la API GRAPH facebook */
			URL urlFacebook = new URL("https://graph.facebook.com/v19.0/" + idNumero + "/messages");
			
			// INICIALIZAMOS EL CONTENEDOR DE ENVIO PARA FACEBOOK
			HttpURLConnection httpConn = (HttpURLConnection) urlFacebook.openConnection();

			// EL TIPO DE ENVIO DE DATOS VA A SER VIA POST
			httpConn.setRequestMethod("POST");

			// CODIGO DE AUTORIZACION DE JAVA
			httpConn.setRequestProperty("Authorization", "Bearer" + token);

			// DEFINIMOS QUE LOS DATOS SERAN TRATADOS COMO JSON
			httpConn.setRequestProperty("Content-Type","application/json; application/x-www-form-urlencoded; charset=UTF-8");

			// PREPARAMOS Y ENVIAMOS LOS DATOS COMO JSON
			httpConn.setDoOutput(true);
			

		

			/*
			 * OutputStreamWriter: un OutputStreamer convierte caracteres a bytes usando una
			 * decodificacion de caracteres por defecto o una decodificacion de caracteres
			 * especificada por su nombre y luego escribe estos bytes en un OutputStream
			 */
			
			OutputStreamWriter writer_OK = new OutputStreamWriter(httpConn.getOutputStream());
			OutputStreamWriter writer_Not_Found = new OutputStreamWriter(httpConn.getOutputStream());
			OutputStreamWriter writer_Server_Error = new OutputStreamWriter(httpConn.getOutputStream());
			OutputStreamWriter writer_Service_Unavailable = new OutputStreamWriter(httpConn.getOutputStream());
			OutputStreamWriter writer_Gateway_TimeOut = new OutputStreamWriter(httpConn.getOutputStream());
			
			

			try (OutputStream os = conn.getOutputStream()) {

				byte[] input = data.getBytes("utf-8");
				os.write(input, 0, input.length);
				
				
				
				/*
				 * code: Es una variable de tipo entera que almacenará la información respecto
				 * al estatus del servidor
				 */
				int code = conn.getResponseCode();

				/* Manejo de respuestas por parte del servidor para monitoreo de sistemas */

				String ResponseMessageOk = "OK";
				String ResponseMessageNotFound = "NOT_FOUND";
				String ResponseMessageServerError = "SERVER_ERROR";
				String ResponseMessageServiceUnavailable = "SERVICE_UNAVAILABLE";
				String ResponseMessageGatewayTimeout = "GATEWAY_TIMEOUT";

				/*
				 * Estas variables,guardaran información necesaria sobre el host donde estan
				 * alojados los sistemas
				 */
				int ResponseCode = conn.getResponseCode();
				String protocolo = url.getProtocol();
				String host = url.getHost();
				String authority = url.getAuthority();
				int port = url.getPort();
				String path = url.getPath();
				String ContentType = conn.getContentType();

				/*
				 * en estas variables se manejara el dato de fecha para saber fecha y hora de
				 * análisis de los sistemas que llegarán a arrojar un problema
				 */
				long date = conn.getDate();
				Instant tempInstant = Instant.ofEpochMilli(date);
				LocalDateTime ldt = LocalDateTime.ofInstant(tempInstant, TimeZone.getDefault().toZoneId());
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
				String fecha = ldt.format(formatter);

				String respuestaOk = "";
				String respuestaNOT_FOUND = "";
				String respuestaSERVER_ERROR = "";
				String respuestaGATEWAYTIME_OUT = "";
				
				
				
				
				

				/*
				 * Variables que atraparan información sobre los datos que arrojara el servidor
				 * en caso de tener respuestas opuestas a un estatus OK
				 */

				respuestaOk += "responseMessageEFVMonitoreo:" + ResponseMessageOk + "\n" + "ResponseCode: "
						+ ResponseCode + "\n" + "protocolo: " + protocolo + "\n" + "host: " + host
						+ "authorithy: " + authority + "\n" + "puerto: " + port + "\n" + "path: " + path + "\n"
						+ "contentType: " + ContentType + "\n" + "fecha: " + fecha;

				respuestaNOT_FOUND += "responseMessageEFVMonitoreo:" + ResponseMessageNotFound + "\n" + "ResponseCode: "
						+ ResponseCode + "\n" + "protocolo: " + protocolo + "\n" + "host: " + host + "\n"
						+ "authorithy: " + authority + "\n" + "puerto: " + port + "\n" + "path: " + path + "\n"
						+ "contentType: " + ContentType + "\n" + "fecha: " + fecha;

				respuestaSERVER_ERROR = "responseMessageEFVMonitoreo:" + ResponseMessageServerError + "\n"
						+ "ResponseCode: " + ResponseCode + "\n" + "protocolo: " + protocolo + "\n" + "host: "
						+ host + "\n" + "authorithy: " + authority + "\n" + "puerto: " + port + "\n" + "path: "
						+ path + "\n" + "contentType: " + ContentType + "\n" + "fecha: " + fecha;

				respuestaGATEWAYTIME_OUT = "responseMessageEFVMonitoreo:" + ResponseMessageGatewayTimeout + "\n"
						+ "ResponseCode: " + ResponseCode + "\n" + "protocolo: " + protocolo + "\n" + "host: "
						+ host + "\n" + "authorithy: " + authority + "\n" + "puerto: " + port + "\n" + "path: "
						+ path + "\n" + "contentType: " + ContentType + "\n" + "fecha: " + fecha;
				
				
				

				if (code == conn.HTTP_OK) {
					System.out.println("respuesta fuera de estatus 200 OK1:" + "\n" + respuestaOk);
					
					 writer_OK.write("{" + "\"messaging_product\": \"whatsapp\"," + "\"to\": \"" + telefono + "\"," + "\"type\": \"template\","
								+ "\"template\": " + "{ \"name\": \"server_status_200ok\"," + "\"language\": { \"code\": \"es\" }" + "}"
								+ "}");
					 
					 
					// LIMPIAMOS LOS DATOS
					 writer_OK.flush();
						// cerramos LOS DATOS
					 writer_OK.close();
						// cerramos la conexión
						httpConn.getOutputStream().close();
						// RECIBIMOS EL RESULTADO DEL ENVIO
						InputStream responseStream = httpConn.getResponseCode() / 100 == 2
								? httpConn.getInputStream()
								: httpConn.getErrorStream();
						Scanner s = new Scanner(responseStream).useDelimiter("\\A");

						// OBTENEMOS LOS RESULTADOS
						String respuestaOKs = s.hasNext() ? s.next() : "";

						System.out.println("Response aplicativoMonitoreoNotificacionesOK2.66? : " + respuestaOKs);
                       
						//s.close();
				}

				if (code == conn.HTTP_NOT_FOUND) {
					
					System.out.println("respuesta estatus 404 NOT FOUND :" + "\n" +  respuestaNOT_FOUND);

					writer_Not_Found.write("{" + "\"messaging_product\": \"whatsapp\"," + "\"to\": \"" + telefono + "\"," + "\"type\": \"template\","
							+ "\"template\": " + "{ \"name\": \"plantilla_not_found\"," + "\"language\": { \"code\": \"es\" }" + "}"
							+ "}");

					// LIMPIAMOS LOS DATOS
					writer_Not_Found.flush();
					// cerramos LOS DATOS
					writer_Not_Found.close();
					// cerramos la conexión
					httpConn.getOutputStream().close();
					// RECIBIMOS EL RESULTADO DEL ENVIO
					InputStream responseStream = httpConn.getResponseCode() / 100 == 2
							? httpConn.getInputStream()
							: httpConn.getErrorStream();
					Scanner s = new Scanner(responseStream).useDelimiter("\\A");

					// OBTENEMOS LOS RESULTADOS
					String respuestaNTFD = s.hasNext() ? s.next() : "";

					System.out.println("Response aplicativoMonitoreoNotificacionesNot_Found2.6? : " + respuestaNTFD);
                   
					//s.close();
				}
				
				
				

			}

		}
		in.close();

	} catch (IOException io) {
		
		System.out.println("Exception IO2.1: " + io.getMessage());
	
	}

} catch (Exception e) {
	System.out.println("imprime el error: " + e.getMessage());
}




%>

</body>
</html>