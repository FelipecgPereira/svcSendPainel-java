package domain.models;

public class Screen {
  ScreenLine topLine;
	ScreenLine bottomLine;

	public ScreenLine getTopLine() {
		return topLine;
	}
	public void setTopLine(ScreenLine topLine) {
		this.topLine = topLine;
	}
	public ScreenLine getBottomLine() {
		return bottomLine;
	}
	public void setBottomLine(ScreenLine bottomLine) {
		this.bottomLine = bottomLine;
	}
}
