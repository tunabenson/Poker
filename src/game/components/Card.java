package game.components;


import game.base.classes.Drawable;
import game.utils.ImageHandler;
import processing.core.PApplet;
import processing.core.PImage;
public class Card extends Drawable {
	
	private int suit;
	
	private int cardVal;
	private PImage image;
	
	public Card(int suit, int cardVal, PApplet p) {
		super(p, 0, 0, 100, 150);
		this.suit = suit;
		this.cardVal = cardVal;
		this.visible=false;
		image=ImageHandler.getCardImage(cardVal,suit);
		image.resize(length, height);
		//image.copy(cardVal, cardVal, cardVal, cardVal, cardVal, cardVal, suit, cardVal)c
	}
	
	public int getCardVal() {
		return cardVal;
	}

	public int getSuit() {
		return suit;
	}

	

	@Override
	public void draw () {
		if(!visible) {
			display.image(image, x, y);
			display.image(ImageHandler.backCard,x, y);
		}
		else {
			display.image(image,x, y);
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void flip() {
		this.visible=!visible;
	}
	
	public boolean getPositionStatus() {
		return visible;
	}
	
	@Override
	public String toString() {
		return this.getCardVal()+" of "+getSuit();
	}
}
