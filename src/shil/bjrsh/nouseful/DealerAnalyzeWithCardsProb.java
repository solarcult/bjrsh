package shil.bjrsh.nouseful;

import java.util.Collection;

import shil.bjrsh.HelloWorld;
import shil.bjrsh.analyze.DealerCards;
import shil.bjrsh.core.BlackJackInfo;
import shil.bjrsh.core.Card;
import shil.bjrsh.core.DealerCardsPathValue;

/**
 * this method calc chance has issue, had been replaced by PlayersVSDealersResultChanceProb
 * @author LiangJingJing
 * @date 2016年10月6日 上午12:49:13
 * @deprecated
 */
public class DealerAnalyzeWithCardsProb {
	
	/**
	 * 真.算Dealer概率方法,更科学.
	 * 玩家的总值是固定的,这时该Dealer发牌了,穷举所有Dealer发牌的情况
	 * @param dealerStartCard
	 * @param playerValue
	 * @return
	 */
	public static double[] dealerResultChance(Card dealerStartCard, int playerValue){
		if(playerValue > BlackJackInfo.BlackJack){
			throw new RuntimeException("should not any condiation be here");
//			return new double[]{1d,0d,0d};
		}
		
		//穷举所有情况
		Collection<DealerCardsPathValue> allCardsChances = DealerCards.fetchDealerCards(dealerStartCard);
		double winrate = 0;
		double drawrate = 0;
		double loserate = 0;
		double totalrate = 0;
		for(DealerCardsPathValue cardsPathValue : allCardsChances){
			if(!cardsPathValue.isValid()){throw new RuntimeException("should not any condiation be here");}
			//计算这一手牌产生的概率
			double rate = cardsPathValue.prob();
			if(cardsPathValue.getValue() > playerValue && cardsPathValue.getValue() <= BlackJackInfo.BlackJack){
				winrate += rate;
			}else if(cardsPathValue.getValue() == playerValue){
				drawrate += rate;
			}else{
				loserate += rate;
			}
			totalrate += rate;
		}
		
		return new double[]{winrate/totalrate,drawrate/totalrate,loserate/totalrate};
	}
	
	public static void main(String[] args){
		
//		for(Card card : Card.values()){
//			for(StartValue startValue : StartValue.values()){
//				System.out.println("== Dealer:"+ card +"  vs  " + "Player:"+startValue.getValue() +"==");
//				HelloWorld.printDoubleWDL(dealerResultChance(card, startValue.getValue()));
//			}
//		}
		
		HelloWorld.printDoubleWDL(dealerResultChance(Card.Ten, 12));
		HelloWorld.printDoubleWDL(dealerResultChance(Card.Ten, 19));
		HelloWorld.printDoubleWDL(dealerResultChance(Card.Six6, 12));
		HelloWorld.printDoubleWDL(dealerResultChance(Card.Six6, 5));
		HelloWorld.printDoubleWDL(dealerResultChance(Card.Five5, 12));
		HelloWorld.printDoubleWDL(dealerResultChance(Card.Five5, 18));
	}
}
