package infra.adapters;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.lumen.ledcenter3.protocol.FileUploadProtocol;
import com.lumen.ledcenter3.protocol.PlaybillCreator;
import com.lumen.ledcenter3.protocol.ProgramCreator;
import com.lumen.ledcenter3.protocol.ProtocolConstant;
import com.lumen.ledcenter3.protocol.ShowEffect;
import com.lumen.ledcenter3.protocol.FileUploadProtocol.OnUploadListener;

import domain.adapters.ISendPanelAdapter;
import domain.models.Departure;
import domain.models.Panel;

public class SendPanelAdapter implements ISendPanelAdapter {

  @Override
  public void createStationSchema(String path, String station) {
    ProgramCreator createProgramaUtil = new ProgramCreator(192, 32, ProtocolConstant.COLOR_TYPE_RGB_COLOR);
        int winNo1 = createProgramaUtil.addWindow(0, 0, 192, 32);
        createProgramaUtil.addTextItem(winNo1, station, 0xFFFFFF, 16, 95, 0, ShowEffect.Scroll_left.getEffect(), 1, 1, 20, 0x000000);
        createProgramaUtil.createLpbFile(path);
  }

  @Override
  public void createDepartureSchema(String path, List<Departure> departures, int i) {
    int j = 0;
    String desc1 = "";
    String desc2 = "";
    int tam1 = 0;
    int tam2 = 0;
    boolean acessibilidade = false;

    if (i + 1 < departures.size()) {
        acessibilidade = departures.get(i).getWheelchairAccessible() || departures.get(i + 1).getWheelchairAccessible();
        desc1 = departures.get(i).getDescription();
        desc2 = departures.get(i + 1).getDescription();

        tam1 = departures.get(i).getWheelchairAccessible() ? departures.get(i).getDescription().length() + 2 : departures.get(i).getDescription().length();
        tam2 = departures.get(i + 1).getWheelchairAccessible() ? departures.get(i + 1).getDescription().length() + 2 : departures.get(i + 1).getDescription().length();

        if (tam1 > tam2) {
            for (j = 0; j < (tam1 - tam2); j++) {
                desc2 += "-";
            }
        } else {
            for (j = 0; j < (tam2 - tam1); j++) {
                desc1 += "-";
            }
        }
    } else {
        desc1 = departures.get(i).getDescription();
    }

    ProgramCreator createProgramaUtil = new ProgramCreator(192, 32, ProtocolConstant.COLOR_TYPE_RGB_COLOR);
    ClassLoader classLoader = getClass().getClassLoader();
    String picture = classLoader.getResource("svc-panel/assets/acc.jpg").getPath();

    if (!departures.get(i).getWheelchairAccessible()) {
        int winNo1 = createProgramaUtil.addWindow(0, 0, 32, 16);
        int winNo2 = createProgramaUtil.addWindow(32, 0, 136, 16);
        int winNo3 = createProgramaUtil.addWindow(168, 0, 24, 16);

        createProgramaUtil.addTextItem(winNo1, departures.get(i).getStopName(), departures.get(0).routerTextColorHex(), 12, 5, 3, ShowEffect.Instant.getEffect(), 1, 1, 20, departures.get(i).routerColorHex());
        createProgramaUtil.addTextItem(winNo2, desc1, 0xFFFFFF, 8, 95, 1, ShowEffect.Scroll_left.getEffect(), 1, 1, 20, 0x000000);

        int textColor = departures.get(i).isOnline() ? 0x24FF00 : 0xFF5C00;
        createProgramaUtil.addTextItem(winNo3, departures.get(i).getTimeLeftWithSufix(), textColor, 8, 5, 3, ShowEffect.Instant.getEffect(), 1, 1, 20, 0x000000);
    } else {
        int winNo1 = createProgramaUtil.addWindow(0, 0, 32, 16);
        int winNo2 = createProgramaUtil.addWindow(48, 0, 120, 16);
        int winNo3 = createProgramaUtil.addWindow(168, 0, 24, 16);
        int winNo7 = createProgramaUtil.addWindow(32, 0, 16, 16);
        
        createProgramaUtil.addTextItem(winNo1, departures.get(i).getStopName(), departures.get(0).routerTextColorHex(), 12, 5, 3, ShowEffect.Instant.getEffect(), 1, 1, 20, departures.get(i).routerColorHex());
        createProgramaUtil.addTextItem(winNo2, desc1, 0xFFFFFF, 8, 95, 1, ShowEffect.Scroll_left.getEffect(), 1, 1, 20, 0x000000);

        int textColor = departures.get(i).isOnline() ? 0x24FF00 : 0xFF5C00;
        createProgramaUtil.addTextItem(winNo3, departures.get(i).getTimeLeftWithSufix(), textColor, 8, 5, 3, ShowEffect.Instant.getEffect(), 1, 1, 20, 0x000000);
        createProgramaUtil.addPicture(winNo7,picture, ProtocolConstant.SHOWMODE_TILED, ShowEffect.Instant.getEffect(), 0, 2);
    }

    if (i + 1 < departures.size()) {
        if (!departures.get(i + 1).getWheelchairAccessible()) {
            int winNo4 = createProgramaUtil.addWindow(0, 16, 32, 16);
            int winNo5 = createProgramaUtil.addWindow(48, 16, 120, 16);
            int winNo6 = createProgramaUtil.addWindow(168, 16, 24, 16);

            createProgramaUtil.addTextItem(winNo4, departures.get(i + 1).getStopName(), departures.get(1).routerTextColorHex(), 12, 5, 3, ShowEffect.Instant.getEffect(), 1, 1, 20, departures.get(i + 1).routerColorHex());
            createProgramaUtil.addTextItem(winNo5, desc2, 0xFFFFFF, 8, 95, 1, ShowEffect.Scroll_left.getEffect(), 1, 1, 20, 0x000000);

            int textColor = departures.get(i + 1).isOnline() ? 0x24FF00 : 0xFF5C00;
            createProgramaUtil.addTextItem(winNo6, departures.get(i + 1).getTimeLeftWithSufix(), textColor, 8, 5, 3, ShowEffect.Instant.getEffect(), 1, 1, 20, 0x000000);
        } else {
            int winNo4 = createProgramaUtil.addWindow(0, 16, 32, 16);
            int winNo5 = createProgramaUtil.addWindow(32, 16, 136, 16);
            int winNo6 = createProgramaUtil.addWindow(168, 16, 24, 16);
            int winNo8 = createProgramaUtil.addWindow(32, 16, 16, 16);

            createProgramaUtil.addTextItem(winNo4, departures.get(i + 1).getStopName(), departures.get(1).routerTextColorHex(), 12, 5, 3, ShowEffect.Instant.getEffect(), 1, 1, 20, departures.get(i + 1).routerColorHex());
            createProgramaUtil.addTextItem(winNo5, desc2, 0xFFFFFF, 8, 95, 1, ShowEffect.Scroll_left.getEffect(), 1, 1, 20, 0x000000);

            int textColor = departures.get(i + 1).isOnline() ? 0x24FF00 : 0xFF5C00;
            createProgramaUtil.addTextItem(winNo6, departures.get(i + 1).getTimeLeftWithSufix(), textColor, 8, 5, 3, ShowEffect.Instant.getEffect(), 1, 1, 20, 0x000000);
            createProgramaUtil.addAnimator(winNo8, picture, ProtocolConstant.SHOWMODE_TILED, 1);
        }
    }

    createProgramaUtil.createLpbFile(path);
  }

  @Override
  public void createPlaybill(String path) {
    PlaybillCreator playbillCreator = new PlaybillCreator(192, 32, ProtocolConstant.COLOR_TYPE_RGB_COLOR);
        playbillCreator.addLpbFile(path);
        playbillCreator.createLppFile(path);
  }

  @Override
  public void uploadFile(String path, Panel panel) {
     OnUploadListener listener = null;
        FileUploadProtocol fu = new FileUploadProtocol();
        fu.setListener(listener);
        int status = 0;

        File diretorio = new File(path);
        ArrayList<String> files = new ArrayList<String>();

        if (diretorio.exists() && diretorio.isDirectory()) {
            File[] arquivos = diretorio.listFiles();

            Arrays.sort(arquivos, Comparator.comparingLong(File::lastModified));

            for (File arquivo : arquivos) {
                files.add(path + "\\" + arquivo.getName());
                System.out.println(path + "\\" + arquivo.getName());
            }

            try {
                fu.uploadFile(path, files, 192, 32, ProtocolConstant.COLOR_TYPE_FULL_COLOR, panel.getIp(), panel.getPort(), 1);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("O diretório não existe ou não é um diretório válido.");
        }
  }

  
  
}
