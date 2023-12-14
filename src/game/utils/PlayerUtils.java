package game.utils;

import java.util.ArrayList;

import game.components.Player;

public class PlayerUtils {
	
	public static int sumBets(ArrayList<Player> inGame) {
		int count=0;
		for(Player p: inGame) {
			count+=p.getCurrentBet();
		}
		
		return count;
	}
	
	public static int getCurrentGreatestBet(ArrayList<Player> inRound) {
		int max=0;
		for (Player p: inRound) {
			max= Math.max(max, p.getCurrentBet());
		}
		return max;
	}
	public static boolean betsMatch(ArrayList<Player> inRound) {
		boolean match=true;
		for (int i=0;i<inRound.size()-1;i++) {
			if(inRound.get(i).getCurrentBet()!=inRound.get(i+1).getCurrentBet()) {
				match=false;
			}
		}
		return match;
	}
}
