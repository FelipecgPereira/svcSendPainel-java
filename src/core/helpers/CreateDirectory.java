package core.helpers;

import java.io.File;

public class CreateDirectory {
  public static String execute(){
    try {
            // Obt�m o caminho do diret�rio em que o JAR est� localizado
            String diretorioJar = CreateDirectory.class.getProtectionDomain()
                    .getCodeSource().getLocation().toURI().getPath();

            System.out.println(diretorioJar);
            // Remove o nome do arquivo do JAR, mantendo apenas o diret�rio
            File diretorio = new File(diretorioJar).getParentFile();

            // Nome da pasta a ser criada
            String nomePasta = "temp";

            // Cria a pasta
            File novaPasta = new File(diretorio, nomePasta);
            if (novaPasta.mkdir()) {
                System.out.println("Diret�rio criado com sucesso em: " + novaPasta.getAbsolutePath());
                return novaPasta.getAbsolutePath();
            } else {
                System.out.println("N�o foi poss�vel criar o diret�rio.");
                return "";
            }

        } catch (Exception e) {
            System.out.println("Erro ao criar a diret�rio: " + e.getMessage());
            return "";
        }
  }
}
