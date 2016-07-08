package shil.bjrsh.analyze;

import java.util.Collection;
import java.util.HashSet;

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
	public static double[] playerNowVSDealerChance(StartValue playerValue, Card dealerCard){
		double[] dealerWDL = DealerAnalyzeWithCardsProb.dealerResultChance(dealerCard, playerValue.getValue());
		return new double[]{dealerWDL[WinDrawLose.lose],dealerWDL[WinDrawLose.draw],dealerWDL[WinDrawLose.win]};
	}
	
	/**
	 * 用户在发一张牌的胜平负概率
	 * @param startValue
	 * @param dealerCard
	 * @return
	 */
	public static double[] playerChanceOneMoreCard(StartValue startValue,Card dealerCard){
		return playerChanceOneMoreCard(new PlayerCardsPathValue(startValue), dealerCard);
	}
	
	/**
	 * 用户在发一张牌的胜平负概率
	 * @param playerCardsPathValue
	 * @param dealerCard
	 * @return
	 */
	public static double[] playerChanceOneMoreCard(PlayerCardsPathValue playerCardsPathValue,Card dealerCard){
		Collection<PlayerCardsPathValue> oneHitCards = GenerateCardsUtil.hitPlayerOneMoreCard(playerCardsPathValue);
		return calcPlayerCollectionsProb(oneHitCards, dealerCard);
	}
	
	public static double[] calcPlayerCollectionsProb(Collection<PlayerCardsPathValue> playerCards,Card dealerCard){
		double winrate = 0;
		double drawrate = 0;
		double loserate = 0;
		for(PlayerCardsPathValue onehit : playerCards){
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
	
	public static double[] playerAX1moreCvsDealer(Card notACardvalue,Card dealerCard){
		Collection<PlayerCardsPathValue> oneHitCards = new HashSet<PlayerCardsPathValue>();
		PlayerCardsPathValue ainstead1 = new PlayerCardsPathValue(StartValue.One);
		ainstead1.addCard(notACardvalue);
		HelloWorld.print(GenerateCardsUtil.hitPlayerOneMoreCard(ainstead1));
		oneHitCards.addAll(GenerateCardsUtil.hitPlayerOneMoreCard(ainstead1));
		System.out.println("s");
		PlayerCardsPathValue ainstead11 = new PlayerCardsPathValue(StartValue.Eleven);
		ainstead11.addCard(notACardvalue);
		oneHitCards.addAll(GenerateCardsUtil.hitPlayerOneMoreCard(ainstead11));
		HelloWorld.print(GenerateCardsUtil.hitPlayerOneMoreCard(ainstead11));
		
		return calcPlayerCollectionsProb(oneHitCards, dealerCard);
	}
	
	public static void main(String[] args) {
		
		HelloWorld.printDoubleWDL(playerAX1moreCvsDealer(Card.Two2,Card.Six6));
		HelloWorld.printDoubleWDL(playerChanceOneMoreCard(StartValue.Two,Card.Six6));
		HelloWorld.printDoubleWDL(playerNowVSDealerChance(StartValue.Two,Card.Six6));
		
		
//		for(StartValue startValue : StartValue.values()){
//			System.out.println(" * Player:"+startValue +" *");
//			for(Card card : Card.values()){
//				System.out.println(" == Player:"+startValue+" vs Dealer:"+card+" == ");
////				HelloWorld.printDoubleWDL(playerChanceOneMoreCard(startValue,card));
//				HelloWorld.printDoubleWDL(playerNowVSDealerChance(new PlayerCardsPathValue(startValue),card));
//			}
//		}
	}

}
