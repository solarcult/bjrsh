package shil.bjrsh.analyze;

import java.util.Collection;

import shil.bjrsh.HelloWorld;
import shil.bjrsh.core.BlackJackInfo;
import shil.bjrsh.core.Card;
import shil.bjrsh.core.GenerateCardsUtil;
import shil.bjrsh.core.PlayerCardsPathValue;
import shil.bjrsh.core.StartValue;

public class PlayerAnalyzeWithCardsProb {
	
	/**
	 * 用户现在的牌对Dealer的起始牌的概率
	 * @param startValue
	 * @param dealerValue
	 * @return
	 */
	public static double[] playerNowVSDealerChance(PlayerCardsPathValue playerCardsPathValue, Card dealerCard){
		double[] dealerWDL = DealerAnalyzeWithCardsProb.dealerResultChance(dealerCard, playerCardsPathValue.getValue());
		return new double[]{dealerWDL[WinDrawLose.lose],dealerWDL[WinDrawLose.draw],dealerWDL[WinDrawLose.win]};
	}
	
	/**
	 * 用户在发一张牌的胜平负概率
	 * @param startValue
	 * @param dealerCard
	 * @return
	 */
	public static double[] playerChanceOneMoreCard(StartValue startValue,Card dealerCard){
//		System.out.println("Player StartValue: "+startValue +" Dealer Card: "+dealerCard);
		PlayerCardsPathValue playerCardsPathValue = new PlayerCardsPathValue(startValue);
		return playerChanceOneMoreCard(playerCardsPathValue, dealerCard);
	}
	
	/**
	 * 用户在发一张牌的胜平负概率
	 * @param playerCardsPathValue
	 * @param dealerCard
	 * @return
	 */
	public static double[] playerChanceOneMoreCard(PlayerCardsPathValue playerCardsPathValue,Card dealerCard){
		Collection<PlayerCardsPathValue> oneHitCards = GenerateCardsUtil.hitPlayerOneMoreCard(playerCardsPathValue);
		double winrate = 0;
		double drawrate = 0;
		double loserate = 0;
		for(PlayerCardsPathValue onehit : oneHitCards){
			if(onehit.getValue() > BlackJackInfo.BlackJack){
				loserate += onehit.prob();
			}else{
				double[] dealerwdl = DealerAnalyzeWithCardsProb.dealerResultChance(dealerCard, onehit.getValue());
				winrate += onehit.prob() * dealerwdl[WinDrawLose.lose];
				drawrate += onehit.prob() * dealerwdl[WinDrawLose.draw];
				loserate += onehit.prob() * dealerwdl[WinDrawLose.win];
			}
		}
		
		double totalrate = winrate + drawrate + loserate;
		return new double[]{winrate/totalrate,drawrate/totalrate,loserate/totalrate};
	}
	
	public static void main(String[] args) {
		for(StartValue startValue : StartValue.values()){
			System.out.println(" * Player:"+startValue +" *");
			for(Card card : Card.values()){
				System.out.println(" == Player:"+startValue+" vs Dealer:"+card+" == ");
//				HelloWorld.printDoubleWDL(playerChanceOneMoreCard(startValue,card));
				HelloWorld.printDoubleWDL(playerNowVSDealerChance(new PlayerCardsPathValue(startValue),card));
			}
		}
	}

}
