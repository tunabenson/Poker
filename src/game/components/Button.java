package game.components;
import game.base.classes.Clickable;
import processing.core.PApplet;
import processing.core.PImage;

public abstract class Button extends Clickable{

	
	public Button(int x, int y, int height, int len, PApplet p) {
		super(p,x,y,height,len);
		this.visible=true;
		this.display=p;
	}
	@Override
	public void draw() {
		if(visible) {
			display.image(image, x, y);
			displayText();
		}
	}

	public boolean isPressed(int mouseX, int mouseY) {
		if(mouseX>x && mouseX<x+length && mouseY>y && mouseY<y+height) {
			return true;
		}
		return false;
	}


	public void displayText() {}
		
	
	public abstract void action();
}
