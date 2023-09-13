package business;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import model.Partidas;
import services.SvMobilibus;


public class PartidasBusiness {
	
	private String ponto = "";
	

	
	@SuppressWarnings("null")
	public List<Partidas> getListPartidas(String url) throws JSONException{
		System.out.println("Obtendo lista de partidas para o endpoint informado: \n "+ url);
		SvMobilibus svcmobilibus;
		List<Partidas> partidasList = new ArrayList<>();

		System.out.println("Instanciando serviço para consulta à api da Mobilibus");
		svcmobilibus = new SvMobilibus();
		
		
		String response = svcmobilibus.getResponse(url);
		if (response != null) {
			
			JSONObject jsonResponse = new JSONObject(response.toString());
			this.ponto = (String) jsonResponse.get("ponto");
			JSONArray partidasArray = jsonResponse.getJSONArray("partidas");
			
			
			JSONObject p = null;
			
			String tripHeadsign = null;
	        String routeShortName = null;
	        String horarioPartida = null;
	        String routeColor = null;
	        String routeTextColor = null;
	        boolean wheelchairAccessible = false;
	        boolean online = false;
	        
	        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT-3"));
	        String dateTimeString = null;
            Date partidaDate = null;
            long timeLeftMillis = 0;
            int timeLeftMinutes = 0;
            System.out.println("*********************************");
			
            for (int i=0; i < partidasArray.length(); i++) 
			{ 
				p = partidasArray.getJSONObject(i);
				tripHeadsign = p.getString("tripHeadsign");
		        routeShortName = p.getString("routeShortName");
		        horarioPartida = p.getString("horarioPartida");
		        routeColor = p.getString("routeColor");
		        routeTextColor = p.getString("routeTextColor");
		        wheelchairAccessible = p.getBoolean("wheelchairAccessible");
		        online = p.getBoolean("online");
		        
		        String decodedTripHeadsign = null;
	            try {
					decodedTripHeadsign = URLDecoder.decode(tripHeadsign, "UTF-8");
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}            
	            
	            dateTimeString = currentDate + " " + horarioPartida;           
				try {
					partidaDate = sdf.parse(dateTimeString);
				} catch (ParseException e) {
					e.printStackTrace();
				}
	            
				timeLeftMillis = partidaDate.getTime() - System.currentTimeMillis();
	            timeLeftMinutes = (int) (timeLeftMillis / (1000 * 60));

	            // Create Partidas object and add it to the list
	            
	            if (timeLeftMinutes <= 20) {
	            
		            Partidas partida = new Partidas();
		            
		            
		            System.out.println("Rota: "+ routeShortName);
		            System.out.println("Destino: "+ decodedTripHeadsign);
		            System.out.println("Tempo Restante: "+ String.valueOf(timeLeftMinutes)+"m");
		            System.out.println("Online: " + String.valueOf(online));
		            System.out.println("Acessibilidade: " + String.valueOf(wheelchairAccessible));
		            System.out.println("");
		            
		            partida.setStopName(routeShortName);
		            partida.setDescription(decodedTripHeadsign);
		            partida.setTimeLeft(String.valueOf(timeLeftMinutes));
		            partida.setRouterColor(routeColor);
		            partida.setRouteTextColor(routeTextColor);
		            partida.setWheelchairAccessible(wheelchairAccessible);
		            partida.setOnline(online);
		            partidasList.add(partida);
	            }else {
	            	 System.out.println("Rota: "+ routeShortName + " [ignorada "+String.valueOf(timeLeftMinutes)+"  > 30m]");
	            }
	            
				
			}
            
            System.out.println("*********************************");
			
		/*
			
			
			JSONObject primeiraPartida = partidasArray.getJSONObject(0);
            String tripHeadsign = primeiraPartida.getString("tripHeadsign");
            String routeShortName = primeiraPartida.getString("routeShortName");
            String horarioPartida = primeiraPartida.getString("horarioPartida");
            String routeColor = primeiraPartida.getString("routeColor");
            String routeTextColor = primeiraPartida.getString("routeTextColor");
            
            
            
            String decodedTripHeadsign = null;
            try {
				decodedTripHeadsign = URLDecoder.decode(tripHeadsign, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            // Calculate time left
            
            
            String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String dateTimeString = currentDate + " " + horarioPartida;

            // Calculate time left
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT-3"));
            Date partidaDate = null;
            
			try {
				partidaDate = sdf.parse(dateTimeString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
            long timeLeftMillis = partidaDate.getTime() - System.currentTimeMillis();
            int timeLeftMinutes = (int) (timeLeftMillis / (1000 * 60));

            // Create Partidas object and add it to the list
            Partidas partida = new Partidas();
            
            System.out.println("*********************************");
            System.out.println("Linha superior");
            System.out.println("Rota: "+ routeShortName);
            System.out.println("Destino: "+ decodedTripHeadsign);
            System.out.println("Tempo Restante: "+ String.valueOf(timeLeftMinutes)+"m");
           
            partida.setStopName(routeShortName);
            partida.setDescription(decodedTripHeadsign);
            partida.setTimeLeft(String.valueOf(timeLeftMinutes)+"m");
            partida.setRouterColor(routeColor);
            partida.setRouteTextColor(routeTextColor);
            partidasList.add(partida);
            
            
            
            
            if (partidasArray.length() > 1) {
            	JSONObject segundaPartida = partidasArray.getJSONObject(1);
                tripHeadsign = segundaPartida.getString("tripHeadsign");
                routeShortName = segundaPartida.getString("routeShortName");
                horarioPartida = segundaPartida.getString("horarioPartida");
                routeColor = segundaPartida.getString("routeColor");
                routeTextColor = segundaPartida.getString("routeTextColor");
                
                
                
                
                
                try {
    				decodedTripHeadsign = URLDecoder.decode(tripHeadsign, "UTF-8");
    			} catch (UnsupportedEncodingException e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}
                // Calculate time left
                //SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                //sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                dateTimeString = currentDate + " " + horarioPartida;

                try {
    				partidaDate = sdf.parse(dateTimeString);
    			} catch (ParseException e) {
    				e.printStackTrace();
    			}
    			timeLeftMillis = partidaDate.getTime() - System.currentTimeMillis();
                timeLeftMinutes = (int) (timeLeftMillis / (1000 * 60));

                // Create Partidas object and add it to the list
                Partidas partida2 = new Partidas();
                
                System.out.println("");
                System.out.println("Linha inferior");
                System.out.println("Rota: "+ routeShortName);
                System.out.println("Destino: "+ decodedTripHeadsign);
                System.out.println("Tempo Restante: "+ String.valueOf(timeLeftMinutes)+"m");
                System.out.println("*********************************");
                
                
                partida2.setStopName(routeShortName);
                partida2.setDescription(decodedTripHeadsign);
                partida2.setTimeLeft(String.valueOf(timeLeftMinutes)+"m");
                partida2.setRouterColor(routeColor);
                partida2.setRouteTextColor(routeTextColor);
                partidasList.add(partida2);
                
            }else {
            	System.out.println("*********************************");
            }
            
            */
            
		}
		
		
		Collections.sort(partidasList, new Comparator<Partidas>() {
			@Override
			public int compare(Partidas partida1, Partidas partida2) {
				// Converte os tempos para valores numéricos (pode ser necessário tratar o formato do tempo)
				int tempo1 = Integer.parseInt(partida1.getTimeLeft());
				int tempo2 = Integer.parseInt(partida2.getTimeLeft());

				// Comparação com base no valor da propriedade timeLeft
				// Retorna um número negativo se tempo1 for menor que tempo2
				// Retorna um número positivo se tempo1 for maior que tempo2
				// Retorna 0 se tempo1 for igual a tempo2
				return tempo1 - tempo2;
			}
		});
		
		System.out.println("Ordem enviada ao painel...");
		for (int i=0; i < partidasList.size(); i++) {
			System.out.println("#"+Integer.toString(i));
			System.out.println("Rota: "+ partidasList.get(i).getStopName());
			System.out.println("Destino: "+ partidasList.get(i).getDescription());
			System.out.println("Tempo Restante: "+ partidasList.get(i).getTimeLeft());
			System.out.println("Online: "+ Boolean.toString(partidasList.get(i).isOnline()));
			System.out.println("Acessibilidade: "+ Boolean.toString(partidasList.get(i).getWheelchairAccessible()));
			
			System.out.println("");
		}
		
		return partidasList;
		
	
	}



	public String getPonto() {
		return ponto;
	}
}
