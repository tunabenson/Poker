package game.components;
import game.utils.HandRanker;
import game.utils.HandRanker.HandRank;
import processing.core.PApplet;

public class Player {
	private int id;
	private int money;
	private Card[] hand;
	private int currentBet;
	private int totalBet;
	HandRanker ranker;
	public static boolean gameOver;
	public Player(int id, Card c1, Card c2){
		this.id = id;
		this.money = 10_000;
		this.hand = new Card[2];
		c1.setX(283);
		c1.setY(480);
		c2.setX(416);
		c2.setY(480);
		hand[0]=c1;
		hand[1]=c2;
		currentBet=0;
		totalBet=0;
		ranker= new HandRanker(hand);
	}

	public int getMoney() {
		return money;
	}
	
	public void flipHand() {
		hand[0].flip();
		hand[1].flip();
	}
	
	public boolean isHandUp() {
		return hand[0].getPositionStatus();
	}
	public int getId() {
		return id;
	}
	


	public void draw(PApplet p, Card [] list) {
		hand[0].draw();
		hand[1].draw();
		if(gameOver) {
			p.textSize(20f);
			p.text(": "+this.playerRank(list), 425, 430);
			p.textSize(12f);
		}
	}

	public boolean setBet(int i) {
		if(money-i>=0) {
		currentBet+=i;
		money-=i;
		return true;
		}
		return false;
	}
		
	public void setWinner(int money) {
		this.money+=money;
	}
	
	
	public int getCurrentBet() {
		return currentBet;
	}
	
	public void resetBet() {
		currentBet=0;
	}
	
	public HandRank playerRank(Card [] tableCards) {
		HandRank r= ranker.determineHandRank(tableCards);
		return r;
	}
	
	@Override
	public String toString() {
		return "Player "+id+": [Card 1:"+hand[0].toString()+"; Card 2:"+hand[1].toString()+" ]";
	}
	
	public void addText(String str, int x, int y, PApplet p) {
		p.text(str, x, y);
	}
	
	


}