package domain.adapters;

import java.util.List;

import domain.models.Departure;
import domain.models.Panel;

public interface ISendPanelAdapter {
  void createStationSchema(String path, String station);
  void createDepartureSchema(String path, List<Departure> partidasList, int i);
  void createPlaybill(String path);
  void uploadFile(String path, Panel panel);
}
