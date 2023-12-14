package game.utils;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

import game.components.Card;

public class HandRanker {

	private Card[] hand;

	public enum HandRank {
		ROYAL_FLUSH,
		STRAIGHT_FLUSH,
		FOUR_OF_A_KIND,
		FULL_HOUSE,
		FLUSH,
		STRAIGHT,
		THREE_OF_A_KIND,
		TWO_PAIR,
		PAIR,
		HIGH_CARD;
	}
	public HandRanker(Card[] hand) {
		this.hand = hand;
	}

	public Card[] getHand() {
		return hand;
	}

	public void setHand(Card[] hand) {
		this.hand = hand;
	}


	public HandRank determineHandRank(Card[] tableCards) {
		if (isRoyalFlush(tableCards)) {
			return HandRank.ROYAL_FLUSH;
		} else if (isStraightFlush(tableCards)) {
			return HandRank.STRAIGHT_FLUSH;
		} else if (isFourOfAKind(tableCards)) {
			return HandRank.FOUR_OF_A_KIND;
		} else if (isFullHouse(tableCards)) {
			return HandRank.FULL_HOUSE;
		} else if (isFlush(tableCards)) {
			return HandRank.FLUSH;
		} else if (isStraight(tableCards)) {
			return HandRank.STRAIGHT;
		} else if (isThreeOfKind(tableCards)) {
			return HandRank.THREE_OF_A_KIND;
		} else if (isTwoPair(tableCards)) {
			return HandRank.TWO_PAIR;
		} else if (isPair(tableCards)) {
			return HandRank.PAIR;
		} else {
			return HandRank.HIGH_CARD;
		}

	}

	public boolean isRoyalFlush(Card[] tableCards) {
		if (isStraight(tableCards) && isFlush(tableCards)) {
			Card[] allCards = getAllCards(hand, tableCards);
			boolean aceExists = false, kingExists = false, queenExists = false, jackExists = false, tenExists = false;
			for (Card c : allCards) {
				switch (c.getCardVal()) {
				case 14:
					aceExists = true;
					break;
				case 13:
					kingExists = true;
					break;
				case 12:
					queenExists = true;
					break;
				case 11:
					jackExists = true;
					break;
				case 10:
					tenExists = true;
					break;

				}
			}
			return (aceExists && kingExists && queenExists && jackExists && tenExists);
		} 
			return false;
	}

	public boolean isStraight(Card[] tableCards) {
		Card[] allCards = getAllCards(tableCards, hand);
		Arrays.sort(allCards, byRank);
		int CardsInARow = 0;
		for (int i=0;i < allCards.length - 1;i++ ) {
			if (allCards[i + 1].getCardVal() - allCards[i].getCardVal() == 1) {
				CardsInARow++;
				if (CardsInARow == 4) {
					return true;
				}
			} 
			else {
				CardsInARow = 0;
			}
		}
		return false;
	}

	public boolean isFlush(Card[] tableCards) {
		Card[] allCards = getAllCards(hand, tableCards);
		int numClubs = 0;
		int numSpades = 0;
		int numHearts = 0;
		int numDiamonds = 0;
		for (Card c : allCards) {
			switch (c.getSuit()) {
			case 0:
				numHearts++;
				break;
			case 1:
				numSpades++;
				break;
			case 2:
				numClubs++;
				break;
			case 3:
				numDiamonds++;
				break;
			}
		}
		return (numClubs >= 5 || numSpades >= 5 || numHearts >= 5 || numDiamonds >= 5);
	}

	private boolean isThreeOfKind(Card[] tableCards) {
		Card [] allCards= getAllCards(hand, tableCards);
		int cardRepeats = 1;
		
		for (int i=0;i < allCards.length ;i++) {
			cardRepeats = 1;
			for (int j=i+1; j< allCards.length; j++ ) {
				if (allCards[i].getCardVal() == allCards[j].getCardVal()) {
					cardRepeats++;
					if (cardRepeats == 3) {
						return true; 
					}
				}
			
			}
		
		}
		return false;
	}

	private boolean isTwoPair(Card[] tableCards) {
		Card [] allCards= getAllCards(hand, tableCards);
		int cardRepeats = 1;
		int numCardRepeats = 0;
		for (int i=0; i< allCards.length;i++) {
			cardRepeats = 1;
			for (int j=i+1;j < allCards.length;j++ ) {
				if (allCards[i].getCardVal() == allCards[j].getCardVal()) {
					cardRepeats++;
					if (cardRepeats == 2) {
						cardRepeats = 1;
						numCardRepeats++;
						if (numCardRepeats == 2) {
							return true;

						}
					}

				}
				
			}
		}
		return false;
	}

	private boolean isPair(Card[] tableCards) {
		Card [] allCards= getAllCards(hand, tableCards);
		int cardRepeats;
		for (int i=0; i< allCards.length;i++ ) {
			cardRepeats = 1;
			for (int j=i+1;j< allCards.length;j++) {
				if (allCards[i].getCardVal() == allCards[j].getCardVal()) {
					cardRepeats++;
					if (cardRepeats == 2) {
						return true;
					}
				}
				
			}
		}
		return false;
	}
	public Comparator<Card> byRank = (Card left, Card right) -> {
		if (left.getCardVal() < right.getCardVal()) {
			return -1;
		} else {
			return 1;
		}
	};

	private boolean isFullHouse(Card[] tableCards) {
		Card [] allCards= getAllCards(hand, tableCards);
		Arrays.sort(allCards, byRank);
		int noOfRepeats = 1;
		boolean isThreeOfAKind = false;
		boolean isTwoOfAKind = false;
		for (int i = 0; i < allCards.length - 1; i++) {
			if (allCards[i].getCardVal() == allCards[i + 1].getCardVal()) {
				noOfRepeats++;
				if (noOfRepeats == 3) {
					isThreeOfAKind = true;
					noOfRepeats = 1;
				} else if (noOfRepeats == 2) {
					isTwoOfAKind = true;
					noOfRepeats = 1;
				}
			} else {
				noOfRepeats = 1;
			}
		}
		return (isTwoOfAKind && isThreeOfAKind);

	}

	public boolean isFourOfAKind(Card[] tableCards) {
		Card [] allCards= getAllCards(hand, tableCards);
		int cardRepeats = 1;
		boolean isFourOfAKind = false;
		int i = 0;
		int k = i + 1;
		while (i < allCards.length && !isFourOfAKind) {
			cardRepeats = 1;
			while (k < allCards.length && !isFourOfAKind) {
				if (allCards[i].getCardVal() == allCards[k].getCardVal()) {
					cardRepeats++;
					if (cardRepeats == 4) {
						isFourOfAKind = true;
					}
				}
				k++;
			}
			i++;
		}
		return isFourOfAKind;
	}

	private boolean isStraightFlush(Card[] tableCards) {
		if (isFlush(getAllCards(tableCards, hand)) && isStraight(getAllCards(tableCards, hand))) {
			return true;
		} else {
			return false;
		}
	}

	public Card getHighCard(Card[] tableCards) {
		Card [] allCards= getAllCards(hand, tableCards);
		Arrays.sort(allCards, byRank);
		return allCards[0];
	}

	public Card getHandHighCard() {
		Arrays.sort(hand, byRank);
		return hand[0];
	}
	
	public Card [] getAllCards(Card [] hand, Card [] communityCards) {
		return Stream.concat(Arrays.stream(communityCards), Arrays.stream(hand)).toArray(Card[]::new);
	}

}