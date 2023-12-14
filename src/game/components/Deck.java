package game.components;
import java.util.Stack;

import game.base.classes.Clickable;
import game.utils.ImageHandler;
import processing.core.PApplet;

import java.util.Collections;

public class Deck extends Clickable {

	private Stack<Card> cardList = new Stack<>();
	protected boolean messageDisplayable;
	public Deck(int numDecks, PApplet p) {
		super(p, 665, 250, 100, 150);
		for (int i = 0; i < numDecks; i++) {
			for (int suit = 0; suit <= 3; suit++) {
				for (int k = 2; k <= 14 ; k++) {
					cardList.add(new Card(suit, k, p));
				}
			}
		}
		shuffleCards();
		messageDisplayable=false;
	}

	public Card drawCard() {
		return cardList.pop();
	}

	private void shuffleCards() {
		for (int i = 0; i < 20; i++) {
			Collections.shuffle(cardList);
		}
	}

	@Override
	public void draw() {
		display.image(ImageHandler.backCard,x,y);
		displayText();
	}

	public void returnCard(Card card) {
		cardList.push(card);
	}
	
	public void action() {}
	
	public void displayText() {}

}