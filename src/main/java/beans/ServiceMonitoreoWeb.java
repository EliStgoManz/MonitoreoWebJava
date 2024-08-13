package beans;

/*@author: Elí Santiago Manzano*/
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class ServiceMonitoreoWeb {

	public static void main(String[] args) {

		try {
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

						respuestaOk += "responseMessageEFV:" + ResponseMessageOK + "\n" + "ResponseCode: "
								+ ResponseCode + "\n" + "protocolo: " + protocolo + "\n" + "host: " + host
								+ "authorithy: " + authority + "\n" + "puerto: " + port + "\n" + "path: " + path + "\n"
								+ "contentType: " + contentType + "\n" + "fecha: " + fecha;

						respuestaNOT_FOUND += "responseMessageEFV:" + ResponseMessageNotFound + "\n" + "ResponseCode: "
								+ ResponseCode + "\n" + "protocolo: " + protocolo + "\n" + "host: " + host + "\n"
								+ "authorithy: " + authority + "\n" + "puerto: " + port + "\n" + "path: " + path + "\n"
								+ "contentType: " + contentType + "\n" + "fecha: " + fecha;

						respuestaSERVER_ERROR = "responseMessageEFV:" + ResponseMessageServerError + "\n"
								+ "ResponseCode: " + ResponseCode + "\n" + "protocolo: " + protocolo + "\n" + "host: "
								+ host + "\n" + "authorithy: " + authority + "\n" + "puerto: " + port + "\n" + "path: "
								+ path + "\n" + "contentType: " + contentType + "\n" + "fecha: " + fecha;

						respuestaGATEWAYTIME_OUT = "responseMessageEFV:" + ResponseMessageGatewayTimeout + "\n"
								+ "ResponseCode: " + ResponseCode + "\n" + "protocolo: " + protocolo + "\n" + "host: "
								+ host + "\n" + "authorithy: " + authority + "\n" + "puerto: " + port + "\n" + "path: "
								+ path + "\n" + "contentType: " + contentType + "\n" + "fecha: " + fecha;

						if (code == conn.HTTP_OK) {

							System.out.println("respuesta fuera de estatus 200 OK:" + "\n" + respuestaOk);
						}

						if (code == conn.HTTP_NOT_FOUND) {
							System.out.println("respuesta estatus 404 NOT FOUND :" + "\n" + respuestaNOT_FOUND);
						}

						if (code == conn.HTTP_SERVER_ERROR) {
							System.out.println(
									"respuesta estatus 500 INTERNAL SERVER ERROR:" + "\n" + respuestaSERVER_ERROR);
						}

						if (code == conn.HTTP_UNAVAILABLE) {
							System.out.println("respuesta estatus 503 SERVICE UNAVAILABLE: " + "\n");
						}

						if (code == conn.HTTP_CLIENT_TIMEOUT) {
							System.out.println(
									"respuesta estatus 504: GATEWAY TIMEOUT" + "\n" + respuestaGATEWAYTIME_OUT);
						}

					}

				}
				in.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("leyendo flujo: " + e.getMessage());
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

}
