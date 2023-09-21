package core.helpers;

import java.io.File;

public class RemoveDirectory {
  public static void execute(File pasta) {
      if (pasta.exists() && pasta.isDirectory()) {
        File[] arquivos = pasta.listFiles();
        if (arquivos != null) {
          for (File arquivo : arquivos) {
            if (arquivo.isDirectory()) {
              // Chama recursivamente a fun��o para apagar as subpastas
              execute(arquivo);
            } else {
              if (arquivo.delete()) {
                System.out.println("Arquivo exclu�do: " + arquivo.getAbsolutePath());
              } else {
                System.out.println("N�o foi poss�vel excluir o arquivo: " + arquivo.getAbsolutePath());
              }
            }
          }
        }

        if (pasta.delete()) {
          System.out.println("Diret�rio exclu�do: " + pasta.getAbsolutePath());
        } else {
          System.out.println("N�o foi poss�vel excluir o diret�rio: " + pasta.getAbsolutePath());
        }
      } else {
        System.out.println("O diret�rio n�o existe ou n�o � um diret�rio v�lida.");
      }
    }
}
