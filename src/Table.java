
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

import game.base.classes.Clickable;
import game.components.*;
import game.utils.HandRanker.HandRank;
import game.utils.ImageHandler;
import game.utils.PlayerUtils;
import processing.core.PApplet;

public class Table {

	public static NumberFormat nf = NumberFormat.getInstance(Locale.US);

	private PApplet p;

	private Slot bettingSlot;
	private Slot cardSlot;	
	private Slot currentBets; 
	private Slot prizePool; 

	private Deck deck;

	private Button nextPlayerButton;
	private Button foldButton;
	private Button flipCardsButton;
	private Button placeTen;
	private Button placeHundred;
	private Button placeThousand;

	private int pool;

	private ArrayList<Card> communityCards;

	private ArrayList<Player> playersInRound;

	private ArrayList<Player> playersInGame;

	private int currentPlayer;


	public Table( int numPlayers, PApplet p){
		this.p=p;
		this.initializeSlotsNButtons();
		this.deck=new Deck((numPlayers/3)+1, p) {
			@Override
			public void displayText() {
				if(messageDisplayable) {
					this.display.fill(255,0,0);
					this.display.text("All Players Must Match or Raise \n           Before Drawing", 350, 140);
					this.display.fill(0,0,0);
				}
			}
			@Override
			public void action() {
				if(!PlayerUtils.betsMatch(playersInRound)) {
					messageDisplayable=true;
					return;
				}

				messageDisplayable=false;

				if (communityCards.size()==0) {
					addCommunityCard(3);
					setCardCordinates();
				}

				else if(communityCards.size()<5) {
					Card c= deck.drawCard();
					setCordinatesOfConsequentCard(c);
					addCommunityCard(c);
				}
				else {
					Player.gameOver=true;
				}
				for (Player p: playersInRound) {
					p.resetBet();
				}
			}
		};


		communityCards= new ArrayList<>();


		playersInRound= new ArrayList<>();

		playersInGame= new ArrayList<>();

		for (int i = 0; i < numPlayers; i++) {
			Player temp=new Player(i+1, deck.drawCard(), deck.drawCard());
			System.out.println(temp);
			playersInRound.add(temp);
			playersInGame.add(temp);
		}


		currentPlayer=0;

		for (Card c: communityCards) {
			System.out.println(c.toString());
		}
	}


private void initializeSlotsNButtons() {

	nextPlayerButton= new Button(120, 500, 100, 30,p){
		@Override
		public void displayText() {
			if(this.messageDisplayable) {
				this.display.fill(255,0,0);
				this.display.text("Player Must Match", 390, 150);
				this.display.fill(0,0,0);
			}
		}

		@Override
		public void action() {
			if((getCurrentPlayer().isHandUp()))getCurrentPlayer().flipHand();
			if(PlayerUtils.getCurrentGreatestBet(playersInRound)>getCurrentPlayer().getCurrentBet()) {
				messageDisplayable=true;
				return;
			}
			messageDisplayable=false;
			currentPlayer++;
			currentPlayer%=getNumPlayers();

		}
	};
	nextPlayerButton.setImage(ImageHandler.nextButton);

	flipCardsButton= new Button(120, 550, 100, 30,p) {
		@Override
		public void action() {
			getCurrentPlayer().flipHand();

		}
	};
	flipCardsButton.setImage(ImageHandler.flipButton);

	foldButton= new Button(120, 600, 100, 30,p) {
		@Override
		public void action() {
			if(playersInRound.size()>1) {
				playersInRound.remove(currentPlayer);
				return;
			}
			endRound(0);

		}
	};
	foldButton.setImage(ImageHandler.foldButton);

	placeTen= new Button(700, 500, 50,30,p) {

		@Override
		public void action() {
			if(getCurrentPlayer().setBet(10))pool+=10;

		}

	};
	placeTen.setImage(ImageHandler.tenButton);

	placeHundred= new Button(700, 540, 50,30,p) {

		@Override
		public void action() {
			if(getCurrentPlayer().setBet(100))pool+=100;


		}
	};
	placeHundred.setImage(ImageHandler.hundredButton);

	placeThousand= new Button(700, 580, 50,30,p) {

		@Override
		public void action() {
			if(getCurrentPlayer().setBet(1000))pool+=1000;

		}
	};
	placeThousand.setImage(ImageHandler.thousandButton);

	cardSlot= new Slot(250,450,300,200,p) {
		@Override
		public void draw() {
			this.drawSlot();
			this.setTextSize(20f);
			this.addText("Player "+ getCurrentPlayer().getId(), 350, 430);

		}
	};
	bettingSlot= new Slot(570,500,120,100,p) {
		@Override
		public void draw() {
			this.drawSlot();
			this.setTextSize(15f);
			this.addText("Money", 600, 490);
			this.addText("$"+nf.format(getCurrentPlayer().getMoney()), 610, 560);
		}
	};
	currentBets= new Slot(350,35,100,80,p) {
		@Override
		public void draw() {
			this.drawSlot();
			this.setTextSize(15f);
			this.addText("Bets Must match $"+PlayerUtils.getCurrentGreatestBet(playersInRound), this.x-25 , this.y-10);
			this.setTextSize(15f);
			this.addText("$"+nf.format(getCurrentPlayer().getCurrentBet()), x+40, y+45);
		}

	};
	prizePool= new Slot(50,35,120,80,p) {

		@Override
		public void draw() {
			this.drawSlot();
			this.setTextSize(15f);
			this.addText("Prize Pool", x+30, y-10);
			this.setTextSize(15f);
			this.addText("$"+(nf.format(pool)), x+50, y+45);

		}
	};
}

public void run() {

	nextPlayerButton.draw();
	flipCardsButton.draw();
	foldButton.draw();
	deck.draw();
	placeTen.draw();
	placeHundred.draw();
	placeThousand.draw();
	currentBets.draw();
	prizePool.draw();
	bettingSlot.draw();
	cardSlot.draw();

	for (Card c: communityCards) {
		c.draw();
	}
	currentPlayer%=playersInRound.size();
	getCurrentPlayer().draw(p, toArray());

	if(playersInRound.size()==1) {
		endRound(0);
	}
}

public int getNumPlayers() {
	return playersInRound.size();
}

public Clickable [] getClickables(){
	return new Clickable [] {deck, nextPlayerButton, flipCardsButton,foldButton, placeTen, placeHundred, placeThousand};
}

private void addCommunityCard(int numCards){
	for (int i = 0; i < numCards ; i++) {
		Card c=deck.drawCard();
		c.flip();
		this.communityCards.add(c);
	}

}

private void addCommunityCard(Card c){
	c.flip();
	this.communityCards.add(c);
	System.out.println(c.toString());
}



private void setCardCordinates() {
	int x=545;
	int y=250;
	for (int i = 0; i < communityCards.size(); i++) {
		Card c= communityCards.get(i);
		c.setX(x);
		c.setY(y);
		x-=120;
	}

}

private void setCordinatesOfConsequentCard(Card c) {
	Card conseq=communityCards.get(communityCards.size()-1);
	c.setX(conseq.getX()-120);
	c.setY(conseq.getY());
}





private void endRound(int winner) {
	playersInRound.get(winner).setWinner(PlayerUtils.sumBets(playersInGame));

	playersInRound.clear();
	for (int i = 0; i < playersInGame.size(); i++) {
		Player p=playersInGame.get(i);
		p.resetBet();
		playersInRound.add(p);
	}


}

private Player getCurrentPlayer() {
	return playersInRound.get(currentPlayer);
}



private Card[] toArray() {
	Card [] cList=new Card[5];
	for(int i=0;i<communityCards.size();i++) {
		cList[i]=communityCards.get(i);
	}
	return cList;
}

}