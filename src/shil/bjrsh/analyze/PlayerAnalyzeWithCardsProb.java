package shil.bjrsh.analyze;

import java.util.Collection;

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
	 * 用户再发一张牌的胜平负概率
	 * @param startValue
	 * @param dealerCard
	 * @return
	 */
	public static double[] playerChanceOneMoreCard(StartValue startValue,Card dealerCard){
		return playerChanceOneMoreCard(new PlayerCardsPathValue(startValue), dealerCard);
	}
	
	/**
	 * 用户再发一张牌的胜平负概率
	 * @param playerCardsPathValue
	 * @param dealerCard
	 * @return
	 */
	public static double[] playerChanceOneMoreCard(PlayerCardsPathValue playerCardsPathValue,Card dealerCard){
		Collection<PlayerCardsPathValue> oneHitCards = GenerateCardsUtil.hitPlayerOneMoreCard(playerCardsPathValue);
		if(oneHitCards.size()!=13){throw new RuntimeException("should 13 combine. "+oneHitCards.size());}
		return calcPlayerCollectionsProb(oneHitCards, dealerCard);
	}
	
	/**
	 * 计算用户的胜平负率 = 卡牌出现的概率*对阵的胜平负率
	 * @param playerCards
	 * @param dealerCard
	 * @return
	 */
	public static double[] calcPlayerCollectionsProb(Collection<PlayerCardsPathValue> playerCards,Card dealerCard){
		double winrate = 0;
		double drawrate = 0;
		double loserate = 0;
		for(PlayerCardsPathValue onehit : playerCards){
			if(onehit.getValue() > BlackJackInfo.BlackJack){
				loserate += onehit.prob();
			}else{
				double[] dealerwdl = DealerAnalyzeWithCardsProb.dealerResultChance(dealerCard, onehit.getValue());
				//dealer的负率就是用户的胜率
				winrate += onehit.prob() * dealerwdl[WinDrawLose.lose];
				drawrate += onehit.prob() * dealerwdl[WinDrawLose.draw];
				loserate += onehit.prob() * dealerwdl[WinDrawLose.win];
			}
		}
		
		double totalrate = winrate + drawrate + loserate;
		return new double[]{winrate/totalrate,drawrate/totalrate,loserate/totalrate};
	}
	
	
	public static void main(String[] args) {
		
//		anlayzeAvsDealerAllTypeCards();

		/*
		HelloWorld.printDoubleWDL(playerAX1moreCvsDealer(Card.Six6,Card.Seven7));
		for(StartValue startValue : StartValue.values()){
			System.out.println(" * Player:"+startValue +" *");
			for(Card card : Card.values()){
				System.out.println(" == Player:"+startValue+" vs Dealer:"+card+" == ");
//				HelloWorld.printDoubleWDL(playerChanceOneMoreCard(startValue,card));
				HelloWorld.printDoubleWDL(playerNowVSDealerChance(startValue,card));
			}
		}
		*/
		
//		HelloWorld.printDoubleWDL(playerAX1moreCvsDealer(Card.Two2,Card.Two2));
	}

}
