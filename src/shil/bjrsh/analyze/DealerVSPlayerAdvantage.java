package shil.bjrsh.analyze;

import java.util.ArrayList;
import java.util.List;

import shil.bjrsh.HelloWorld;
import shil.bjrsh.core.BlackJackInfo;
import shil.bjrsh.core.Card;
import shil.bjrsh.core.PlayerCardsPathValue;
import shil.bjrsh.core.StartValue;

public class DealerVSPlayerAdvantage {
	
	/**
	 * 以Dealer的角度展示对付用户牌的胜率.
	 * @return
	 */
	public static List<DealerVSPlayerChance> makeDealerNow(){
		List<DealerVSPlayerChance> makeNow = new ArrayList<DealerVSPlayerChance>();
		for (StartValue startValue : StartValue.values()) {
			if (startValue == StartValue.One || startValue == StartValue.Two) {
				continue;
			}
			for (Card card : Card.values()) {
				double[] dealer = DealerAnalyzeWithCardsProb.dealerResultChance(card, startValue.getValue());
				DealerVSPlayerChance dealerVSPlayerChance = new DealerVSPlayerChance(DealerVSPlayerChance.Dealer, card, startValue, dealer,null);
				makeNow.add(dealerVSPlayerChance);
			}
		}
		return makeNow;
	}
	
	/**
	 * 以Player的角度来看对Dealer的胜率
	 * @return
	 */
	public static List<DealerVSPlayerChance> makePlayerNow(){
		List<DealerVSPlayerChance> makeNow = new ArrayList<DealerVSPlayerChance>();
		for (StartValue startValue : StartValue.values()) {
			if (startValue == StartValue.One || startValue == StartValue.Two) {
				continue;
			}
			for (Card card : Card.values()) {
				double[] player = PlayerAnalyzeWithCardsProb.playerNowVSDealerChance(startValue, card);
				DealerVSPlayerChance dealerVSPlayerChance = new DealerVSPlayerChance(DealerVSPlayerChance.Player, card, startValue, player,null);
				makeNow.add(dealerVSPlayerChance);
			}
		}
		return makeNow;
	}
	
	/**
	 * 用户再来一张牌对付Dealer的胜率
	 * @return
	 */
	public static List<DealerVSPlayerChance> makePlayerOneMore(){
		List<DealerVSPlayerChance> makeNow = new ArrayList<DealerVSPlayerChance>();
		for (StartValue startValue : StartValue.values()) {
			if (startValue == StartValue.One || startValue == StartValue.Two) {
				continue;
			}
			for (Card card : Card.values()) {
				double[] playerWDL = PlayerAnalyzeWithCardsProb.playerChanceOneMoreCard(new PlayerCardsPathValue(startValue), card);
				DealerVSPlayerChance dealerVSPlayerChance = new DealerVSPlayerChance(DealerVSPlayerChance.Player, card, startValue, playerWDL,null);
				makeNow.add(dealerVSPlayerChance);
			}
		}
		return makeNow;
	}
	
	/**
	 * 用户所有牌+再来一张对Dealer的胜率有什么变化
	 * @return
	 */
	public static List<DealerVSPlayerChance> makePlayerOneMoreVSNow(){
		List<DealerVSPlayerChance> diff = new ArrayList<DealerVSPlayerChance>();
		for (Card card : Card.values()) {
			
			if(Card.One1.equals(card)) continue;
			for (StartValue startValue : StartValue.values()) {	
				if (startValue.getValue() < StartValue.Six.getValue() || startValue.getValue() >= BlackJackInfo.DealerStop) continue;
				
				double[] playerOneMore = PlayerAnalyzeWithCardsProb.playerChanceOneMoreCard(new PlayerCardsPathValue(startValue), card);
				double[] playerNow = PlayerAnalyzeWithCardsProb.playerNowVSDealerChance(startValue, card);
				DealerVSPlayerChance dealerVSPlayerChance = new DealerVSPlayerChance(DealerVSPlayerChance.Player,card, startValue, playerNow,playerOneMore);
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
