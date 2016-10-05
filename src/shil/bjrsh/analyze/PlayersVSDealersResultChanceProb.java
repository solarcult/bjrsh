package shil.bjrsh.analyze;

import java.util.Collection;
import java.util.HashSet;

import shil.bjrsh.HelloWorld;
import shil.bjrsh.core.BlackJackInfo;
import shil.bjrsh.core.Card;
import shil.bjrsh.core.DealerCardsPathValue;
import shil.bjrsh.core.GenerateCardsUtil;
import shil.bjrsh.core.PlayerCardsPathValue;
/**
 * This night we honored to introduce this class to new chance calculate, this class make this project to a high preciously rate and right
 * These methods are combine player chance and dealer chance together, better than last one, i think.
 * Thank you guys and it's too late , I need some sleep and rest.
 * See u soon.
 * @author LiangJingJing
 * @date 2016年10月6日 上午12:51:29
 */
public class PlayersVSDealersResultChanceProb {

	private static double multi = 1000000;
	
	public static double[] calcPlayerdVSDealerProbs(PlayerCardsPathValue playerCardsPathValue , Card dealerCard){
		Collection<PlayerCardsPathValue> players = new HashSet<PlayerCardsPathValue>();
		players.add(playerCardsPathValue);
		return calcPlayerdVSDealerProbs(players, DealerCards.fetchDealerCards(dealerCard));
	}
	
	public static double[] calcPlayerdVSDealerProbs(Collection<PlayerCardsPathValue> players, Card dealerCard){
		return calcPlayerdVSDealerProbs(players, DealerCards.fetchDealerCards(dealerCard));
	}
	
	public static double[] calcPlayerdVSDealerProbs(Collection<PlayerCardsPathValue> players , Collection<DealerCardsPathValue> dealers){
		double winrate = 0;
		double drawrate = 0;
		double loserate = 0;
		
		for(PlayerCardsPathValue player : players){
			for(DealerCardsPathValue dealer : dealers){
				double combineRate = player.prob()*dealer.prob()*multi;
				if(player.getValue() > BlackJackInfo.BlackJack){
					//player boost first
					loserate += combineRate;
				}else{
					if(dealer.getValue()>BlackJackInfo.BlackJack){
						//dealer boost
						winrate += combineRate;
					}else if(player.getValue() > dealer.getValue()){
						winrate += combineRate;
					}else if(player.getValue() == dealer.getValue()){
						drawrate += combineRate;
					}else if(player.getValue() < dealer.getValue()){
						loserate += combineRate;
					}
				}
			}
		}
		
		double totalrate = winrate + drawrate + loserate;
//		System.out.println(totalrate);
		return new double[]{winrate/totalrate,drawrate/totalrate,loserate/totalrate};
	}
	
	public static void main(String[] args){
//		HelloWorld.printDoubleWDL(calcPlayerdVSDealerProbs(new PlayerCardsPathValue(Card.Four4,Card.Six6), Card.Six6));
		HelloWorld.printDoubleWDL(calcPlayerdVSDealerProbs(GenerateCardsUtil.hitPlayerOneMoreCard(new PlayerCardsPathValue(Card.Four4,Card.Six6)), Card.Six6));
	}
}
