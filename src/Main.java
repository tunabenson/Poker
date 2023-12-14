
import game.base.classes.Clickable;
import game.utils.ImageHandler;
import processing.core.PApplet;

public class Main extends PApplet{
	
	Table t;
	
	@Override
	public void setup() {
		ImageHandler.init("Images/cards", this);
		 t= new Table(2, this);
	}
	@Override
	public void settings() {
		size(800,650);
	}
	
	@Override
	public void draw() {
		background(ImageHandler.background);
		t.run();
	}
	@Override
	public void mouseReleased() {
		Clickable [] bList=t.getClickables();
		for (int i = 0; i < bList.length; i++) {
			if(bList[i].isPressed(mouseX, mouseY)) {
				bList[i].action();
			}
		}
	}
	
	
	public static void main(String[] args) {
		PApplet.main("Main");
	}
}
