package domain.models;

import java.util.List;

public class Panel {
  String ip;
	int port;
	List<Screen> screens;
	
	public Panel(String ip, int port) {
		setIp(ip);
		setPort(port);
	}	
	
	public List<Screen> getScreens() {
		return screens;
	}
	public void setScreens(List<Screen> screens) {
		this.screens = screens;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
}
