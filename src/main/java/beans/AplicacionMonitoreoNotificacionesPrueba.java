package beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.TimeZone;

public class AplicacionMonitoreoNotificacionesPrueba {

	public static void main(String[] args) throws MalformedURLException {

		// TOKEN QUE NOS DA FACEBOOK
		String token = "EAADsQKlicEMBO1uDWPfe23iOZCAHXfc5I5TboqIWI3TVqIjuPr9jdJZALQubGAYk064GZBOrWBA5I4tyfQZAZCaSP9oesqA9IBTB4gkzhJNn1optP5z1PSIrzlJv5YuNdfPbxSGuZAQHU2eX80wufVON3AsZC26aZBpABZC8xsSvnhR0k7bdvjSGAnWitfjV6ZCEh8BLQeGyCkd1nVIhAtZCTWZCzoidiWKsW9oVZB4EZD";
		// NUESTRO TELEFONO
		String telefono = "529516470269";
		// IDENTIFICADOR DE NUMERO DE TELEFONO
		String idNumero = "356387480887111";

		// realizamos nuestras propies respuestas:
		String respuestaTexto = "Hola queridos amigos de Efectivale, nos complace comunicarles\n por este medio";

		// COLOCAMOS LA URL PARA ENVIAR EL MENSAJE
		URL urlFacebook = new URL("https://graph.facebook.com/v19.0/" + idNumero + "/messages");

		// INICIALIZAMOS EL CONTENEDOR DEL ENVIO
		HttpURLConnection httpConn = null;
		try {
			httpConn = (HttpURLConnection) urlFacebook.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// EL TIPO DE ENVIO DE DATOS VA A SER VIA POST
		try {
			httpConn.setRequestMethod("POST");
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// CODIGO DE AUTORIZACION DE JAVA
		httpConn.setRequestProperty("Authorization", "Bearer " + token);

		// DEFINIMOS QUE LOS DATOS SERAN TRATADOS COMO JSON
		httpConn.setRequestProperty("Content-Type",
				"application/json; application/x-www-form-urlencoded; charset=UTF-8");

		// httpConn.setRequestProperty("Content-type", "application/json");

		// PREPARAMOS Y ENVIAMOS EL JSON
		httpConn.setDoOutput(true);

		/* Parte del código para monitoreo */

		File fileUrls = new File(
				"C:/Users/eli.santiago/OneDrive - FleetCor/Documentos/Documentación-proyectosEfectivale/UrlSistemas.txt");

		// 1.-creando un objeto FileReader que contendra la direccion del archivo,
		// podremos leer un fichero de texto.
		try (FileReader fr = new FileReader(fileUrls)) {

			// 1.1-construimos un objeto BufferedReader
			BufferedReader in1 = new BufferedReader(new InputStreamReader(new FileInputStream(fileUrls)));

			/*
			 * 2.-Podemos construir un objeto BufferedReader a partir del FileReader de la
			 * siguiente forma:
			 */
			BufferedReader in = new BufferedReader(fr);
			String inputLine;
			StringBuffer response = new StringBuffer();
			StringBuffer responsability = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {

				responsability = response.append(inputLine);
				String resultRespons = responsability.toString();
				String data = "";

				// Metodos para leer el contenido del archivo txt, contiene información de URLS
				URL url = new URL(inputLine);

				System.out.println("resultado de url: " + url);

				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestProperty("Content-Type", "text/plain;utf-8");
				conn.setDoOutput(true);

				try (OutputStream os = conn.getOutputStream()) {
					byte[] input = data.getBytes("utf-8");
					os.write(input, 0, input.length);

					int code = conn.getResponseCode();

					/* Manejo de respuestas para monitoreo de Sistemas */

					String ResponseMessageOK = "OK";
					String ResponseMessageNotFound = "NOT_FOUND";
					String ResponseMessageServerError = "SERVER_ERROR";
					String ResponseMessageGatewayTimeout = "GATEWAY_TIMEOUT";
					int ResponseCode = conn.getResponseCode();
					String protocolo = url.getProtocol();
					String host = url.getHost();
					System.out.println("imprimiendo host: " + host);
					String authority = url.getAuthority();
					int port = url.getPort();
					String path = url.getPath();
					String contentType = conn.getContentType();

					long date = conn.getDate();
					Instant tempInstant = Instant.ofEpochMilli(date);
					LocalDateTime ldt = LocalDateTime.ofInstant(tempInstant, TimeZone.getDefault().toZoneId());
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
					String fecha = ldt.format(formatter);

					String respuestaOk = "";
					String respuestaNOT_FOUND = "";
					String respuestaSERVER_ERROR = "";
					String respuestaGATEWAYTIME_OUT = "";

					respuestaOk += "responseMessageEFV:" + ResponseMessageOK + "\n" + "ResponseCode: " + ResponseCode
							+ "\n" + "protocolo: " + protocolo + "\n" + "host: " + host + "authorithy: " + authority
							+ "\n" + "puerto: " + port + "\n" + "path: " + path + "\n" + "contentType: " + contentType
							+ "\n" + "fecha: " + fecha;

					respuestaNOT_FOUND += "responseMessageEFV:" + ResponseMessageNotFound + "\n" + "ResponseCode: "
							+ ResponseCode + "\n" + "protocolo: " + protocolo + "\n" + "host: " + host + "\n"
							+ "authorithy: " + authority + "\n" + "puerto: " + port + "\n" + "path: " + path + "\n"
							+ "contentType: " + contentType + "\n" + "fecha: " + fecha;

					respuestaSERVER_ERROR = "responseMessageEFV:" + ResponseMessageServerError + "\n" + "ResponseCode: "
							+ ResponseCode + "\n" + "protocolo: " + protocolo + "\n" + "host: " + host + "\n"
							+ "authorithy: " + authority + "\n" + "puerto: " + port + "\n" + "path: " + path + "\n"
							+ "contentType: " + contentType + "\n" + "fecha: " + fecha;

					respuestaGATEWAYTIME_OUT = "responseMessageEFV:" + ResponseMessageGatewayTimeout + "\n"
							+ "ResponseCode: " + ResponseCode + "\n" + "protocolo: " + protocolo + "\n" + "host: "
							+ host + "\n" + "authorithy: " + authority + "\n" + "puerto: " + port + "\n" + "path: "
							+ path + "\n" + "contentType: " + contentType + "\n" + "fecha: " + fecha;

					OutputStreamWriter writer = null;

					try {
						writer = new OutputStreamWriter(httpConn.getOutputStream());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					OutputStreamWriter writer_Not_Found = null;
					try {
						writer_Not_Found = new OutputStreamWriter(httpConn.getOutputStream());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					/* Plantilla 1 de meta-FACEBOOK; Error: 404_NOT_FOUND */

					try {
						if (code == conn.HTTP_OK) {
							System.out.println("respuesta fuera de estatus OK OK1.8:" + "\n" + respuestaOk);
						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// Construir el JSON de la solicitud
					/*
					 * StringBuilder jsonBody = new StringBuilder(); jsonBody.append("{")
					 * .append("\"messaging_product\": \"whatsapp\",")
					 * .append("\"recipient_type\": \"individual\",")
					 * .append("\"to\": \"").append(telefono).append("\",")
					 * .append("\"type\": \"template\",") .append("\"template\": {")
					 * .append("\"name\": \"notificaciones_incidencias_sistemas_efectivale\",")
					 * .append("\"language\": { \"code\": \"es\" },") .append("\"components\": [")
					 * .append("{") .append("\"type\": \"body\",") .append("\"parameters\": [")
					 * .append("{") .append("\"type\": \"text\",")
					 * .append("\"text\": \"").append(path).append("\"") .append("}") .append("]")
					 * .append("}") .append("]") .append("}") .append("}");
					 * 
					 * 
					 * System.out.println("leer jsonBody: "+ jsonBody);
					 * 
					 * 
					 * 
					 * //Enviar la solicitud try(OutputStream osOne= httpConn.getOutputStream()){
					 * osOne.write(jsonBody.toString().getBytes("UTF-8")); osOne.flush(); }
					 * 
					 * if(httpConn.getResponseCode()!=conn.HTTP_OK) {
					 * 
					 * System.out.println("Mensaje enviado correctamente."); } else {
					 * System.out.println("Error al enviar el mensaje: " +
					 * conn.getResponseMessage()); }
					 * 
					 * 
					 * 
					 * httpConn.disconnect();
					 */

					/*
					 * Plantilla que la api de facebook, para enviar notificación de que el algun
					 * servidor se encuentre con error: 404
					 */
					/*
					 * try { if(code != conn.HTTP_OK) {
					 * System.out.println("respuesta fuera de estatus 404 notfound1.8:" + "\n"
					 * +respuestaNOT_FOUND); writer.write("{" +
					 * "\"messaging_product\": \"whatsapp\"," + "\"to\": \"" + telefono + "\"," +
					 * "\"type\": \"template\"," + "\"template\": " +
					 * "{ \"name\": \"plantilla_not_found\"," + "\"language\": { \"code\": \"es\" }"
					 * + "}" + "}");
					 * 
					 * }
					 * 
					 * } catch (IOException e) { // TODO Auto-generated catch block
					 * e.printStackTrace(); }
					 */

					try {
						if (code != conn.HTTP_OK) {
							System.out.println(
									"Manejo de respuesta error cuatrocerocuatro con nueva plantilla version 1.TEEN<3 : "
											+ respuestaNOT_FOUND);

							writer.write("{" + "\"messaging_product\": \"whatsapp\","
									+ "\"recipient_type\": \"individual\"," + "\"to\": \"" + telefono + "\","
									+ "\"type\": \"template\"," + "\"template\": {"
									+ "\"name\": \"notificaciones_incidencias_sistemas_efectivale\","
									+ "\"language\": { \"code\": \"es\"}," + "\"components\":  [ " + "{"
									+ "\"type\": \"body\"," + "\"parameters\": [" + "{" + "\"type\": \"text\","
									+ "\"text\": \"" + path + "\" " + "}, " + "{" + "\"type\": \"text\","
									+ "\"text\": \"" + host + "\" " + "}, " + "{" + "\"type\": \"text\","
									+ "\"text\": \"" + ResponseCode + "\" " + "}, " + "{" + "\"type\": \"text\","
									+ "\"text\": \"" + fecha + "\" " + "}" + "]" + "}" + "]" + "}" + "}");
						}

					} catch (IOException e) {
						System.out.println("causa de error: " + e.getCause());
					}

					// LIMPIAMOS LOS DATOS
					try {
						writer.flush();

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// LIMPIAMOS LOS DATOS
					/*
					 * try { writer_Not_Found.flush();
					 * 
					 * } catch (IOException e) { // TODO Auto-generated catch block
					 * e.printStackTrace(); }
					 */
					// cerramos los datos
					try {
						writer.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// cerramos los datos
					/*
					 * try { writer_Not_Found.close(); } catch (IOException e) { // TODO
					 * Auto-generated catch block e.printStackTrace(); }
					 */

					// cerramos la conexion
					try {
						httpConn.getOutputStream().close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// Recibimos el resultado del envio
					InputStream responseStream = null;
					try {
						responseStream = httpConn.getResponseCode() / 100 == 2 ? httpConn.getInputStream()
								: httpConn.getErrorStream();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Scanner s = new Scanner(responseStream).useDelimiter("\\A");

					// OBTENEMOS LOS RESULTADOS
					String respuesta = s.hasNext() ? s.next() : "";

					System.out.println("Response aplicacion MonitoreosAppAndWeb3.9? : " + respuesta);

					// s.close();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("leyendo flujo: " + e.getMessage());
				}

			}
			in.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("leyendo flujo: " + e.getMessage());
		}

	}
}
