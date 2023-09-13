package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.json.JSONException;

import com.lumen.ledcenter3.protocol.ExtSendUtil;
import com.lumen.ledcenter3.protocol.ExternalNetworkSendProtocol.OnTcpNetWorkListener;
import com.lumen.ledcenter3.protocol.FileUploadProtocol;
import com.lumen.ledcenter3.protocol.FileUploadProtocol.OnUploadListener;
import com.sun.xml.internal.ws.api.pipe.ThrowableContainerPropertySet;
import com.lumen.ledcenter3.protocol.PlaybillCreator;
import com.lumen.ledcenter3.protocol.ProgramCreator;
import com.lumen.ledcenter3.protocol.ProtocolConstant;
import com.lumen.ledcenter3.protocol.ShowEffect;

import business.PartidasBusiness;
import model.Panel;
import model.Partidas;
import sun.font.FontFamily;

import com.lumen.ledcenter3.protocol.FileUploadProtocol;



public class Main {

	
	@SuppressWarnings("null")
	public static void main(String[] args){
		
		String ponto = "";
		
		
		if (args.length < 3) {
			 System.out.println("Quantidade de parâmetros inváidos");
		} else {
				
			List<Partidas> partidasList = null;
			System.out.println("Instanciando objeto PartidasBusiness...");
			PartidasBusiness pb = new PartidasBusiness();
			System.out.println("Objeto PartidasBusiness instanciado");
			try {
				partidasList =  pb.getListPartidas(args[2]);
				ponto = pb.getPonto();
				System.out.println("Ponto: " + ponto);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
			if (partidasList != null) {
				
				
				
				Panel panel = new Panel(args[0],Integer.parseInt(args[1]));
				
				
				ExtSendUtil ex = new ExtSendUtil();
				
				ex.initNetwork(panel.getIp(), panel.getPort(), "255.255.255.0");
				
							
				ex.clearSavedDataFromFlash();
				ex.exitSelectPlay();
				ex.disconnectNetwork();
				
				
				
				
				//String ip = "192.168.1.222";
				//int porta = 5200;
				//String mask = "255.255.255.255";
				//String ls = "Linha superior";
				
				String path = criarPastaNoDiretorioDoJar();
				ProgramCreator createProgramaUtil;// new ProgramCreator(192, 32,ProtocolConstant.COLOR_TYPE_RGB_COLOR);
				
				int winNo1;// = createProgramaUtil.addWindow(0, 0, 32, 16);
				int winNo2;// = createProgramaUtil.addWindow(32, 0, 136, 16);
				int winNo3;// = createProgramaUtil.addWindow(168,0,24,16);
				int winNo4;// = createProgramaUtil.addWindow(0, 16, 32, 16);
				int winNo5;// = createProgramaUtil.addWindow(32, 16, 136, 16);
				int winNo6;// = createProgramaUtil.addWindow(168,16,24,16);
				int winNo7;// = createProgramaUtil.addWindow(168,16,24,16);
				int winNo8;// = createProgramaUtil.addWindow(168,16,24,16);
				
				PlaybillCreator playbillCreator;
				
				if (ponto != "") {
					createProgramaUtil =  new ProgramCreator(192, 32,ProtocolConstant.COLOR_TYPE_RGB_COLOR);
					winNo1 = createProgramaUtil.addWindow(0, 0, 192, 32);
					createProgramaUtil.addTextItem(winNo1, ponto, 0xFFFFFF, 16, 95, 0, ShowEffect.Scroll_left.getEffect(), 1, 1, 20, 0x000000);
					
					//createProgramaUtil.addExtraTextItem(winNo1, ponto, 0xFFFFFF, 16, "Sego", 95, 0, ShowEffect.Scroll_left.getEffect(), 2, 2, 0);
					/*
					createProgramaUtil.addFormatTextItem(
							winNo1, 
							"<p style=\"font-family: Arial, sans-serif; font-size: 14px; font-weight: bold;\">"+ponto+"</p>",
							95,
							0,
							ShowEffect.Scroll_left.getEffect());
					*/
					createProgramaUtil.createLpbFile(path);					
					createProgramaUtil = null;
				}
				
				
				for (int i=0; i < partidasList.size(); i = i+2) {
					
					int j = 0;
					String desc1 = "";
					String desc2 = "";
					int tam1 = 0;
					int tam2 = 0;
					boolean acessibilidade = false;
					
					if (i+1 < partidasList.size()){
						
						acessibilidade = partidasList.get(i).getWheelchairAccessible() || partidasList.get(i+1).getWheelchairAccessible();
						desc1 = partidasList.get(i).getDescription();
						desc2 = partidasList.get(i+1).getDescription();
						
						tam1 = partidasList.get(i).getWheelchairAccessible() ? partidasList.get(i).getDescription().length() + 2 : partidasList.get(i).getDescription().length();
						tam2 = partidasList.get(i+1).getWheelchairAccessible() ? partidasList.get(i+1).getDescription().length() + 2 : partidasList.get(i+1).getDescription().length();
						
						
						if (tam1 >tam2) {	
							for (j = 0; j < (tam1 - tam2) ; j++) {
								desc2 += "-";
						    }
						}else {
							for (j = 0; j < (tam2 - tam1) ; j++) {
								desc1 += "-";
						    }
						}
					}else {
						desc1 = partidasList.get(i).getDescription();
					}
					
					createProgramaUtil =  new ProgramCreator(192, 32,ProtocolConstant.COLOR_TYPE_RGB_COLOR);
					 
					if (!partidasList.get(i).getWheelchairAccessible()){
						winNo1 = createProgramaUtil.addWindow(0, 0, 32, 16);
						winNo2 = createProgramaUtil.addWindow(32, 0, 136, 16);
						winNo3 = createProgramaUtil.addWindow(168,0,24,16);
					 
						createProgramaUtil.addTextItem(winNo1, partidasList.get(i).getStopName(),partidasList.get(0).routerTextColorHex(),12, 5, 3, ShowEffect.Instant.getEffect(), 1, 1, 20, partidasList.get(i).routerColorHex());
						createProgramaUtil.addTextItem(winNo2, desc1, 0xFFFFFF, 8, 95, 1, ShowEffect.Scroll_left.getEffect(), 1, 1, 20, 0x000000);
						
						if(!partidasList.get(i).isOnline()) {
							createProgramaUtil.addTextItem(winNo3, partidasList.get(i).getTimeLeftWithSufix(), 0xFF5C00, 8, 5, 3, ShowEffect.Instant.getEffect(), 1, 1, 20, 0x000000);
						}else {
							createProgramaUtil.addTextItem(winNo3, partidasList.get(i).getTimeLeftWithSufix(), 0x24FF00, 8, 5, 3, ShowEffect.Instant.getEffect(), 1, 1, 20, 0x000000);
						}
					}else {
						winNo1 = createProgramaUtil.addWindow(0, 0, 32, 16);
						winNo2 = createProgramaUtil.addWindow(48, 0, 120, 16);
						winNo3 = createProgramaUtil.addWindow(168,0,24,16);
						winNo7 = createProgramaUtil.addWindow (32,0,16,16);
						createProgramaUtil.addTextItem(winNo1, partidasList.get(i).getStopName(),partidasList.get(0).routerTextColorHex(),12, 5, 3, ShowEffect.Instant.getEffect(), 1, 1, 20, partidasList.get(i).routerColorHex());
						createProgramaUtil.addTextItem(winNo2, desc1, 0xFFFFFF, 8, 95, 1, ShowEffect.Scroll_left.getEffect(), 1, 1, 20, 0x000000);
						
						if(!partidasList.get(i).isOnline()) {
							createProgramaUtil.addTextItem(winNo3, partidasList.get(i).getTimeLeftWithSufix(), 0xFF5C00, 8, 5, 3, ShowEffect.Instant.getEffect(), 1, 1, 20, 0x000000);
						}else {
							createProgramaUtil.addTextItem(winNo3, partidasList.get(i).getTimeLeftWithSufix(), 0x24FF00, 8, 5, 3, ShowEffect.Instant.getEffect(), 1, 1, 20, 0x000000);
						}
						createProgramaUtil.addPicture(winNo7,"C:\\Users\\digivox\\citrus\\svcSendPanel\\rersources\\img\\acc.jpg",ProtocolConstant.SHOWMODE_TILED, ShowEffect.Instant.getEffect(),0, 2);
					}
					
					if (i+1 < partidasList.size()){
						 
						if (!partidasList.get(i+1).getWheelchairAccessible()){
						
							winNo4 = createProgramaUtil.addWindow(0, 16, 32, 16);
							winNo5 = createProgramaUtil.addWindow(48, 16, 120, 16);
							winNo6 = createProgramaUtil.addWindow(168,16,24,16);
							
							
							createProgramaUtil.addTextItem(winNo4, partidasList.get(i+1).getStopName(), partidasList.get(1).routerTextColorHex(), 12, 5, 3, ShowEffect.Instant.getEffect(), 1, 1, 20, partidasList.get(i+1).routerColorHex());
							createProgramaUtil.addTextItem(winNo5, desc2, 0xFFFFFF, 8, 95, 1, ShowEffect.Scroll_left.getEffect(), 1, 1, 20, 0x000000);
							if(!partidasList.get(i+1).isOnline()) {
								createProgramaUtil.addTextItem(winNo6, partidasList.get(i+1).getTimeLeftWithSufix(),  0xFF5C00, 8, 5, 3, ShowEffect.Instant.getEffect(), 1, 1, 20, 0x00000);
							}else {
								createProgramaUtil.addTextItem(winNo6, partidasList.get(i+1).getTimeLeftWithSufix(),  0x24FF00, 8, 5, 3, ShowEffect.Instant.getEffect(), 1, 1, 20, 0x00000);
							}
						
						
						}else {
							
							winNo4 = createProgramaUtil.addWindow(0, 16, 32, 16);
							winNo5 = createProgramaUtil.addWindow(32, 16, 136, 16);
							winNo6 = createProgramaUtil.addWindow(168,16,24,16);
							winNo8 = createProgramaUtil.addWindow(32,16,16,16);
							
							createProgramaUtil.addTextItem(winNo4, partidasList.get(i+1).getStopName(), partidasList.get(1).routerTextColorHex(), 12, 5, 3, ShowEffect.Instant.getEffect(), 1, 1, 20, partidasList.get(i+1).routerColorHex());
							createProgramaUtil.addTextItem(winNo5, desc2, 0xFFFFFF, 8, 95, 1, ShowEffect.Scroll_left.getEffect(), 1, 1, 20, 0x000000);
							
							if(!partidasList.get(i+1).isOnline()) {
								createProgramaUtil.addTextItem(winNo6, partidasList.get(i+1).getTimeLeftWithSufix(),  0xFF5C00, 8, 5, 3, ShowEffect.Instant.getEffect(), 1, 1, 20, 0x00000);
							}else {
								createProgramaUtil.addTextItem(winNo6, partidasList.get(i+1).getTimeLeftWithSufix(),  0x24FF00, 8, 5, 3, ShowEffect.Instant.getEffect(), 1, 1, 20, 0x00000);
							}
							createProgramaUtil.addAnimator(winNo8, "C:\\Users\\digivox\\citrus\\svcSendPanel\\rersources\\img\\acc.jpg", ProtocolConstant.SHOWMODE_TILED, 1);// addPicture(winNo8,"C:\\Users\\digivox\\citrus\\svcSendPanel\\rersources\\img\\acc.jpg", ProtocolConstant.SHOWMODE_TILED, ShowEffect.Instant.getEffect(),0, 2);
						}
						
					 }
					
					createProgramaUtil.createLpbFile(path);
					
					
					
					
					createProgramaUtil = null;
						
				 }
				
								
				
				
				//createProgramaUtil.addFormatTextItem(winNo, "<span style=\"color:#FF0000; font-size:12px;\">01<span>", 5, 3, ShowEffect.Instant.getEffect());
				
				
				//createProgramaUtil.addTextItem(winNo, "Teste", 0x57899C, ProtocolConstant.FONTSIZE_16, 100, 3, ShowEffect.Random.getEffect());
				
				
				
				
				
				//createProgramaUtil.addTextItem(winNo1, "222", 0xFFFFFF, 8, 5, 3, ShowEffect.Instant.getEffect(), 1, 1, 20, 0x580166);
				//createProgramaUtil.addTextItem(winNo2, "Linha superior - x", 0xFFFFFF, 8, 95, 0, ShowEffect.Scroll_left.getEffect(), 1, 1, 20, 0x000000);
				//createProgramaUtil.addTextItem(winNo3, "05m", 0xFFFFFF, 8, 5, 3, ShowEffect.Instant.getEffect(), 1, 1, 20, 0x2E1280);
				//createProgramaUtil.addTextItem(winNo4, "202", 0xFFFFFF, 8, 5, 3, ShowEffect.Instant.getEffect(), 1, 1, 20, 0x044930);
				//createProgramaUtil.addTextItem(winNo5, "Linha inferior - x", 0xFFFFFF, 8, 95, 0, ShowEffect.Scroll_left.getEffect(), 1, 1, 20, 0x000000);
				//createProgramaUtil.addTextItem(winNo6, "2m", 0xFFFFFF, 8, 5, 3, ShowEffect.Instant.getEffect(), 1, 1, 20, 0x6C2202);
				
				//createProgramaUtil.createLpbFile("C:\\testes");
				
				
			
				playbillCreator = new PlaybillCreator(192, 32, ProtocolConstant.COLOR_TYPE_RGB_COLOR);
				playbillCreator.addLpbFile(path);
				playbillCreator.createLppFile(path);
				
				OnUploadListener listener = null;
				FileUploadProtocol fu = new FileUploadProtocol( );
				fu.setListener(listener);
				
				int status = 0;
				
				
				//String diretorioPath = "C:\\testes";
		
		        // Criar um objeto File com o caminho do diretório
					
				File diretorio = new File(path);
		        ArrayList<String> files = new ArrayList<String>();
		        // Verificar se o diretório existe
		        if (diretorio.exists() && diretorio.isDirectory()) {
		            // Obter a lista de arquivos do diretório
		            File[] arquivos = diretorio.listFiles();
		
		            Arrays.sort(arquivos, Comparator.comparingLong(File::lastModified));
		            // Iterar pelos arquivos e imprimir seus nomes
		            for (File arquivo : arquivos) {
		            	files.add(path+ "\\"+arquivo.getName());
		            	System.out.println(path+ "\\"+arquivo.getName());
		            }
		            
		            
		            try {
		    			fu.uploadFile(path,files, 192, 32, ProtocolConstant.COLOR_TYPE_FULL_COLOR, panel.getIp(), panel.getPort(), 1);
		    			
		    			
		    			apagarPasta(new File(path));
		    			
		    			
		    		
		    		} catch (Exception e) {
		    			System.out.println(e.getMessage());
		    		}
		        } else {
		            System.out.println("O diretório não existe ou não é um diretório válido.");
		        }
				
				
		       /*
		        
				
				
				
				int [][] winRects = new int [][] {
					{0,0,32,16},{32,0,136,16},{168,0,24,16},
					{0,16,32,16},{32,16,136,16},{168,16,24,16}
				};
				
				
				ExtSendUtil ex = new ExtSendUtil();
		
				ex.initNetwork(ip, porta, mask);
				ex.splitScreen(6, winRects);
				ex.setListener(new OnTcpNetWorkListener() {
					
					@Override
					public void onTcpProcess(long arg0, long arg1, int arg2) {
						System.out.println("Processamento "+ Long.toString(arg0) + " " + Long.toString(arg1) + " "+ Long.toString(arg2));
						
					}
					
					@Override
					public void onStatus(int arg0, int arg1) {
						System.out.println("onStatus "+ Integer.toString(arg0) + " " + Integer.toString(arg1));
						
					}
					
					@Override
					public void onSocketInit(int arg0) {
						System.out.println("Socket iniciado: "+ Integer.toString(arg0));
						
					}
					
					@Override
					public void breakSocket(int arg0) {
						System.out.println("Socket desconectado "+ Integer.toString(arg0));
						
					}
				});
		
				
				
				
				
				
				
				
				
						
				
				Thread thread = new Thread (new Runnable() {
		
					@Override
					public void run() {
						boolean r;
						
						
						try {
						
						ex.sendPicture(1, 2, ShowEffect.Instant.getEffect(), 1, "C:\\Users\\digivox\\citrus\\svcSendPanel\\rersources\\img\\acc.jpg");	
						
						r = ex.sendText(0, "01", 1, 1, 5, ShowEffect.Instant.getEffect(), 3, 1,1);
						Thread.sleep(100);
						r = ex.sendText(1, "JANELA 02", 2, 1, 5, ShowEffect.Scrollleft_continuously .getEffect(), 3, 1,1);
						Thread.sleep(100);
						r = ex.sendText(2, "03", 1, 1, 5, ShowEffect.Instant.getEffect(), 3, 1,1);
						Thread.sleep(100);
						r = ex.sendText(3, "04", 1, 1, 5, ShowEffect.Instant.getEffect(), 3, 1,1);
						Thread.sleep(100);
						r = ex.sendText(4, "JANELA 05", 2, 1, 5, ShowEffect.Scrollleft_continuously.getEffect(), 3, 1,1);
						Thread.sleep(100);
						r = ex.sendText(5, "06", 1, 1, 5, ShowEffect.Instant.getEffect(), 3, 1,1);
						
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				});
				thread.start();
				
			*/  
	        
			
			}else {
	        	System.out.println("Nenhuma partida encontrada para a url informada");
	        }
		}
	}
	
	
	public static void apagarPasta(File pasta) {
        if (pasta.exists() && pasta.isDirectory()) {
            File[] arquivos = pasta.listFiles();

            if (arquivos != null) {
                for (File arquivo : arquivos) {
                    if (arquivo.isDirectory()) {
                        // Chama recursivamente a função para apagar as subpastas
                        apagarPasta(arquivo);
                    } else {
                        if (arquivo.delete()) {
                            System.out.println("Arquivo excluído: " + arquivo.getAbsolutePath());
                        } else {
                            System.out.println("Não foi possível excluir o arquivo: " + arquivo.getAbsolutePath());
                        }
                    }
                }
            }

            if (pasta.delete()) {
                System.out.println("Diretório excluído: " + pasta.getAbsolutePath());
            } else {
                System.out.println("Não foi possível excluir o diretório: " + pasta.getAbsolutePath());
            }
        } else {
            System.out.println("O diretório não existe ou não é um diretório válida.");
        }
        
        
    }
	
	public static String criarPastaNoDiretorioDoJar() {
        try {
            // Obtém o caminho do diretório em que o JAR está localizado
            String diretorioJar = Main.class.getProtectionDomain()
                    .getCodeSource().getLocation().toURI().getPath();

            System.out.println(diretorioJar);
            // Remove o nome do arquivo do JAR, mantendo apenas o diretório
            File diretorio = new File(diretorioJar).getParentFile();

            // Nome da pasta a ser criada
            String nomePasta = "temp";

            // Cria a pasta
            File novaPasta = new File(diretorio, nomePasta);
            if (novaPasta.mkdir()) {
                System.out.println("Diretório criado com sucesso em: " + novaPasta.getAbsolutePath());
                return novaPasta.getAbsolutePath();
            } else {
                System.out.println("Não foi possível criar o diretório.");
                return "";
            }

        } catch (Exception e) {
            System.out.println("Erro ao criar a diretório: " + e.getMessage());
            return "";
        }
  
	}
}



