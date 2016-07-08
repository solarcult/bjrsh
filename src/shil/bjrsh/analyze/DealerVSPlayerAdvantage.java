package shil.bjrsh.analyze;

import java.util.ArrayList;
import java.util.List;

import shil.bjrsh.HelloWorld;
import shil.bjrsh.core.BlackJackInfo;
import shil.bjrsh.core.Card;
import shil.bjrsh.core.PlayerCardsPathValue;
import shil.bjrsh.core.StartValue;

public class DealerVSPlayerAdvantage {
	
	public static List<DealerVSPlayerChance> makeDealerNow(){
		List<DealerVSPlayerChance> makeNow = new ArrayList<DealerVSPlayerChance>();
		for (StartValue startValue : StartValue.values()) {
			if (startValue == StartValue.One || startValue == StartValue.Two) {
				continue;
			}
			for (Card card : Card.values()) {
				double[] dealer = DealerAnalyzeWithCardsProb.dealerResultChance(card, startValue.getValue());
				DealerVSPlayerChance dealerVSPlayerChance = new DealerVSPlayerChance(DealerVSPlayerChance.Dealer, card, startValue, dealer);
				makeNow.add(dealerVSPlayerChance);
			}
		}
		return makeNow;
	}
	
	public static List<DealerVSPlayerChance> makePlayerNow(){
		List<DealerVSPlayerChance> makeNow = new ArrayList<DealerVSPlayerChance>();
		for (StartValue startValue : StartValue.values()) {
			if (startValue == StartValue.One || startValue == StartValue.Two) {
				continue;
			}
			for (Card card : Card.values()) {
				double[] player = PlayerAnalyzeWithCardsProb.playerNowVSDealerChance(startValue, card);
				DealerVSPlayerChance dealerVSPlayerChance = new DealerVSPlayerChance(DealerVSPlayerChance.Player, card, startValue, player);
				makeNow.add(dealerVSPlayerChance);
			}
		}
		return makeNow;
	}
	
	public static List<DealerVSPlayerChance> makePlayerOneMore(){
		List<DealerVSPlayerChance> makeNow = new ArrayList<DealerVSPlayerChance>();
		for (StartValue startValue : StartValue.values()) {
			if (startValue == StartValue.One || startValue == StartValue.Two) {
				continue;
			}
			for (Card card : Card.values()) {
				double[] playerWDL = PlayerAnalyzeWithCardsProb.playerChanceOneMoreCard(new PlayerCardsPathValue(startValue), card);
				DealerVSPlayerChance dealerVSPlayerChance = new DealerVSPlayerChance(DealerVSPlayerChance.Player, card, startValue, new double[]{playerWDL[WinDrawLose.win],playerWDL[WinDrawLose.draw],playerWDL[WinDrawLose.lose]});
				makeNow.add(dealerVSPlayerChance);
			}
		}
		return makeNow;
	}
	
	public static List<DealerVSPlayerChance> makePlayerOneMoreVSNow(){
		List<DealerVSPlayerChance> diff = new ArrayList<DealerVSPlayerChance>();
		for (StartValue startValue : StartValue.values()) {
			if (startValue.getValue() < StartValue.Twelve.getValue() || startValue.getValue() >= BlackJackInfo.DealerStop) {
//				continue;
			}
			for (Card card : Card.values()) {
				System.out.println("== Player:"+startValue.getValue() +"  vs  Dealer:"+ card +" ==");
				double[] playerOneMore = PlayerAnalyzeWithCardsProb.playerChanceOneMoreCard(new PlayerCardsPathValue(startValue), card);
				double[] playerNow = PlayerAnalyzeWithCardsProb.playerNowVSDealerChance(startValue, card);
				
				DealerVSPlayerChance dealerVSPlayerChance = new DealerVSPlayerChance(DealerVSPlayerChance.Player,card, startValue, new double[]{playerOneMore[WinDrawLose.win] - playerNow[WinDrawLose.win],playerOneMore[WinDrawLose.draw] - playerNow[WinDrawLose.draw],playerOneMore[WinDrawLose.lose] - playerNow[WinDrawLose.lose]});
				diff.add(dealerVSPlayerChance);
			}
		}
		return diff;
	}
	
	public static void main(String[] args){
		List<DealerVSPlayerChance> ao = makePlayerOneMoreVSNow();
//		List<DealerVSPlayerChance> ao = makeDealerNow();
//		List<DealerVSPlayerChance> ao = makePlayerNow();
//		Collections.sort(ao, new WinComparator());
//		Collections.sort(ao, new LoseComparator());
//		Collections.sort(ao, new Win4LoseComparator());
		HelloWorld.print(ao);
	}
}
