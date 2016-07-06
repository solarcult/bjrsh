package shil.bjrsh.analyze;

import java.util.Collection;

import shil.bjrsh.HelloWorld;
import shil.bjrsh.core.BlackJackInfo;
import shil.bjrsh.core.Card;
import shil.bjrsh.core.CardsPathValue;

public class DealerAnalyzeWithCardsProb {
	
	/**
	 * 真.算Dealer概率方法,更科学.
	 * @param dealerStartCard
	 * @param playerValue
	 * @return
	 */
	public static double[] dealerResultChance(Card dealerStartCard, int playerValue){
		Collection<CardsPathValue> allCardsChances = DealerCards.fetchDealerCards(dealerStartCard);
		double winrate = 0;
		double drawrate = 0;
		double loserate = 0;
		double totalrate = 0;
		for(CardsPathValue cardsPathValue : allCardsChances){
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
		HelloWorld.printDoubleWDL(dealerResultChance(Card.Ten, 12));
		HelloWorld.printDoubleWDL(dealerResultChance(Card.Ten, 19));
		HelloWorld.printDoubleWDL(dealerResultChance(Card.Six6, 12));
		HelloWorld.printDoubleWDL(dealerResultChance(Card.Five5, 12));
		HelloWorld.printDoubleWDL(dealerResultChance(Card.Five5, 18));
	}
}
