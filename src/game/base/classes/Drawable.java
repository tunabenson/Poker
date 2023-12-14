package game.base.classes;
import processing.core.PApplet;
import processing.core.PImage;

public abstract class Drawable {
	protected PApplet display;
	protected PImage image;
	protected int x;
	protected int y;
	protected int length;
	protected int height;
	protected boolean visible;
	public Drawable(PApplet display, int x, int y, int length, int height) {
		this.display=display;
		this.x=x;
		this.y=y;
		this.height=height;
		this.length=length;
		this.visible=true; 
	}
	
	public abstract void draw();
	
	
	public void setX(int x) {
		this.x=x;
	}
	public void setY(int y) {
		this.y=y;
	}
	
	public void delete() {
		this.visible=false;
	}
	
	public void setImage(PImage image) {
		this.image=image;
	}
	

	
}
