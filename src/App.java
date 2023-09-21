import core.errors.InvalidParams;
import domain.services.SendPanelService;

public class App {

    public static void main(String[] args) throws Exception {
       try {
         if(args.length<1)   throw new InvalidParams("Parameters invalid");
        SendPanelService sendPanel = new SendPanelService();
        sendPanel.handler(args);
       } catch (Exception e) {
        System.err.println("Erro: " + e.getMessage());
       }
    }
}
