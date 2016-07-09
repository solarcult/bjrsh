package shil.bjrsh.analyze;

import java.util.Collection;
import java.util.List;

import org.apache.commons.math3.stat.Frequency;

import shil.bjrsh.core.BlackJackInfo;
import shil.bjrsh.core.Card;
import shil.bjrsh.core.DealerCardsPathValue;

@Deprecated
public class DealerAnalyzeWithOutCardsProb {
	
	/**
	 * 分析成牌后,最终分数的分布情况,未考虑组合出牌的概率
	 * @param cardsPathValues
	 * @return Frequency
	 */
	public static Frequency frequencyAnalyze(Collection<DealerCardsPathValue> cardsPathValues){
		System.out.println("Dealer Strat Card: " + ((List<DealerCardsPathValue>)cardsPathValues).get(0).getCards().get(0));
		Frequency frequency = new Frequency();
		for(DealerCardsPathValue cardsPathValue : cardsPathValues){
			frequency.addValue(cardsPathValue.getValue());
		}
		System.out.println(frequency);
		return frequency;
	}
	
	/**
	 * 分析Dealer起手牌和玩家最终分数时,庄家赢得概率
	 * @param dealerStartCard
	 * @param playerValue
	 * @return
	 */
	public static double dealerWinChance(Card dealerStartCard, int playerValue){
		Frequency dealerChance = frequencyAnalyze(DealerCards.fetchDealerCards(dealerStartCard));
		double winrate = 0;
		double drawrate = 0;
		winrate = dealerChance.getCumPct(BlackJackInfo.BlackJack);
		
		if(playerValue >= 17){
			drawrate = dealerChance.getCumPct(playerValue);
		}
		
		return winrate - drawrate;
	}
	
	/**
	 * 分析Dealer起手牌和玩家最终分数时,玩家不输的概率
	 * @param dealerStartCard
	 * @param playerValue
	 * @return
	 */
	public static double playerNotLoseChance(Card dealerStartCard, int playerValue){
		Frequency dealerChance = frequencyAnalyze(DealerCards.fetchDealerCards(dealerStartCard));
		double loserate = 0;
		for(int i = playerValue+1 ; i <= BlackJackInfo.BlackJack; i++){
			loserate += dealerChance.getPct(i);
		}
		
		return 1 - loserate;
	}
	
	public static void main(String[] args){
//		frequencyAnalyze(DealerCards.StartOne);
//		frequencyAnalyze(DealerCards.StartTwo);
		
		System.out.println(dealerWinChance(Card.Ten, 12));
		System.out.println(playerNotLoseChance(Card.Ten, 12));
	}
}
