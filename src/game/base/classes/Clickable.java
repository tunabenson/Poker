package game.base.classes;

import processing.core.PApplet;

public abstract class Clickable extends Drawable {
	protected boolean messageDisplayable;
	public Clickable(PApplet display, int x, int y, int length, int height) {
		super(display, x, y, length, height);
	}
	
	
	public abstract void action();
	public abstract void displayText();
	
	public boolean isPressed(int mouseX, int mouseY) {
		if(mouseX>x && mouseX<x+length && mouseY>y && mouseY<y+height) {
			return true;
		}
		return false;
	}
}
