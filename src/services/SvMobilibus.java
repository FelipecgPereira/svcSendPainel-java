package services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SvMobilibus{
	
	public String getResponse(String endpoint) {

		System.out.println("Consultando url...");
		String endpointURL = endpoint;
        String r = null;
        try {
            URL url = new URL(endpointURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == 200) {

        		System.out.println("Serviço retornou sucesso na operação.");
            	BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),StandardCharsets.UTF_8));
                StringBuilder response = new StringBuilder();
                String line;

        		System.out.println("Registrando objetos encontrandos para posterior tratamento...");
                while ((line = br.readLine()) != null) {

            		System.out.println(line);
                	response.append(line);
                }
                br.close();

                // Parse JSON response
                String jsonResponse = response.toString();
                r = jsonResponse;
            } else {
                System.out.println("Failed to fetch data from the server. Response code: " + conn.getResponseCode());
            }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
		return r;
	}
	
}
