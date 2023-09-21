package domain.models;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class Departure {
  String stopName;
	String description;
	String timeLeft;
	String routerColor;
	String routeTextColor;
	boolean wheelchairAccessible;
	boolean online;
	
	
	public boolean isOnline() {
		return online;
	}
	public void setOnline(boolean online) {
		this.online = online;
	}
	
	public boolean getWheelchairAccessible() {
		return wheelchairAccessible;
	}
	public void setWheelchairAccessible(boolean wheelchairAccessible) {
		this.wheelchairAccessible = wheelchairAccessible;
	}
	public String getRouterColor() {
		return routerColor;
	}
	public void setRouterColor(String routerColor) {
		this.routerColor = routerColor;
	}
	public String getRouteTextColor() {
		return routeTextColor;
	}
	public void setRouteTextColor(String routeTextColor) {
		this.routeTextColor = routeTextColor;
	}
	public String getStopName() {
		return stopName;
	}
	public void setStopName(String stopName) {
		this.stopName = stopName;
	}
	public String getDescription() {
		String nfdNormalizedString = Normalizer.normalize(description, Normalizer.Form.NFD); 
	    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
	    return pattern.matcher(nfdNormalizedString).replaceAll("");		
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTimeLeft() {
		return timeLeft;
	}
	public String getTimeLeftWithSufix() {
		return timeLeft + "m";
	}
	public void setTimeLeft(String timeLeft) {
		this.timeLeft = timeLeft;
	}
	
	public int routerColorHex() {
		String hexString = routerColor.replace("#", "");
	    return Integer.parseInt(hexString, 16);
	}
	
	public int routerTextColorHex() {
		String hexString = routeTextColor.replace("#", "");
	    return Integer.parseInt(hexString, 16);
	}
	
	public static int convertColorStringToHex(String colorString) {
	    String hexString = colorString.replace("#", "");
	    return Integer.parseInt(hexString, 16);
	}
}
