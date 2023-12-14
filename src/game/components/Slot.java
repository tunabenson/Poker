package game.components;
import game.base.classes.Drawable;
import game.utils.ImageHandler;
import processing.core.PApplet;

public abstract class Slot extends Drawable{
	private int color;
	private float fontSize;
	public Slot(int x, int y, int length, int height, PApplet p) {
		super(p, x, y, length, height);
		this.image=ImageHandler.slot.copy();
		image.resize(length, height);
		color=0;
	}
	
	public void setTextSize(float fontSize) {
		this.fontSize=fontSize;
	}
	
	public void setTextColor(int red, int green, int blue) {
		color=display.color(red,green, blue);
	}
	
	public void drawSlot() {
		display.image(image, x,y);
	}

	public void addText( String text, int textX, int textY) {
		display.fill(color);
		display.textSize(fontSize);
		display.text(text, textX, textY);
		display.textSize(12f);
	}
	
	
}
