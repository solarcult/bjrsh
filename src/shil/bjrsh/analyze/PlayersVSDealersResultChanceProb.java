package shil.bjrsh.analyze;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

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

	private static double multi = 1;
	
	/**
	 * 
	 * @param playerCardsPathValue
	 * @param dealerCard 此处DealerCard必须是原始第一张牌,否则出现问题
	 * @return
	 */
	public static double[] calcPlayerdVSDealerProbs(PlayerCardsPathValue playerCardsPathValue , Card dealerCard){
		Collection<PlayerCardsPathValue> players = new HashSet<PlayerCardsPathValue>();
		players.add(playerCardsPathValue);
		return calcPlayerdVSDealerProbs(players, DealerCards.fetchDealerCards(dealerCard));
	}
	
	/**
	 * 
	 * @param players
	 * @param dealerCard 此处DealerCard必须是原始第一张牌,否则出现问题
	 * @return
	 */
	public static double[] calcPlayerdVSDealerProbs(Collection<PlayerCardsPathValue> players, Card dealerCard){
		return calcPlayerdVSDealerProbs(players, DealerCards.fetchDealerCards(dealerCard));
	}
	
	
	public static double[] calcPlayerdVSDealerProbs(PlayerCardsPathValue playerCardsPathValue , Collection<DealerCardsPathValue> dealers){
		Collection<PlayerCardsPathValue> players = new HashSet<PlayerCardsPathValue>();
		players.add(playerCardsPathValue);
		return calcPlayerdVSDealerProbs(players, dealers);
	}
	
	
	public static double[] calcPlayerVSDealerAnaylzeStatus(Collection<PlayerCardsPathValue> players, Card dealerCard){
		Collection<DealerCardsPathValue> dealers = DealerCards.fetchDealerCards(dealerCard);
		Map<Integer,AnalyzeStatus> playermap = AnalyzeCardsPathValue.analyzePlayerCardsPathValue(players);
		Map<Integer,AnalyzeStatus> dealermap = AnalyzeCardsPathValue.analyzeDealerCardsPathValue(dealers);
		return calcPlayerVSDealerAnaylzeStatus(playermap, dealermap);
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
				}else if(dealer.getValue()>BlackJackInfo.BlackJack){
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
		
		double totalrate = winrate + drawrate + loserate;
		
		return new double[]{winrate/totalrate,drawrate/totalrate,loserate/totalrate};
	}
	
	public static double[] calcPlayerVSDealerAnaylzeStatus(Map<Integer,AnalyzeStatus> playermap,Map<Integer,AnalyzeStatus> dealermap){
		double winrate = 0;
		double drawrate = 0;
		double loserate = 0;
		
		for(Entry<Integer,AnalyzeStatus> pe : playermap.entrySet()){
			for(Entry<Integer,AnalyzeStatus> de : dealermap.entrySet()){
				AnalyzeStatus pa = pe.getValue();
				AnalyzeStatus da = de.getValue();
				if(pa.getValue() > BlackJackInfo.BlackJack){
					loserate += pa.getPrecent() * da.getPrecent();
				}else if(da.getValue() > BlackJackInfo.BlackJack){
					winrate += pa.getPrecent() * da.getPrecent();
				}else if(pa.getValue() > da.getValue()){
					winrate += pa.getPrecent() * da.getPrecent();
				}else if(pa.getValue() < da.getValue()){
					loserate += pa.getPrecent() * da.getPrecent();
				}else if(pa.getValue() == da.getValue()){
					drawrate += pa.getPrecent() * da.getPrecent();
				}
			}
		}
		
		double totalrate = winrate + drawrate + loserate;
		
		return new double[]{winrate/totalrate,drawrate/totalrate,loserate/totalrate};
	}
	
	public static double[] calcPlayerdVSDealerValues(PlayerCardsPathValue playerCardsPathValue , Collection<DealerCardsPathValue> dealers){
		Collection<PlayerCardsPathValue> players = new HashSet<PlayerCardsPathValue>();
		players.add(playerCardsPathValue);
		return calcPlayerdVSDealerValues(players, dealers);
	}
	
	/**
	 * 
	 * @param playerCardsPathValue
	 * @param dealerCard 此处DealerCard必须是原始第一张牌,否则出现问题
	 * @return
	 */
	public static double[] calcPlayerdVSDealerValues(PlayerCardsPathValue playerCardsPathValue , Card dealerCard){
		Collection<PlayerCardsPathValue> players = new HashSet<PlayerCardsPathValue>();
		players.add(playerCardsPathValue);
		return calcPlayerdVSDealerValues(players, DealerCards.fetchDealerCards(dealerCard));
	}
	
	public static double[] calcPlayerdVSDealerValues(Collection<PlayerCardsPathValue> players , Collection<DealerCardsPathValue> dealers){
		double winrate = 0;
		double drawrate = 0;
		double loserate = 0;
		
		for(PlayerCardsPathValue player : players){
			for(DealerCardsPathValue dealer : dealers){
				double combineRate = player.prob()*dealer.prob()*multi;
				if(player.getValue() > BlackJackInfo.BlackJack){
					//player boost first
					loserate += combineRate;
				}else if(dealer.getValue()>BlackJackInfo.BlackJack){
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
		
		return new double[]{winrate,drawrate,loserate};
	}
	
	public static double[] hitPlayerOneMore(PlayerCardsPathValue originPlayerCards, PlayerCardsPathValue hitPlayerCards, DealerCardsPathValue hitDealerCards){
		double win = 0;
		double draw = 0;
		double lose =0;
		double better = 0;
		double normal = 0;
		double worse = 0;
		
		if(hitPlayerCards.getValue() > BlackJackInfo.BlackJack){
			//player bust first
			lose ++;
			if(hitDealerCards.getValue() >= BlackJackInfo.DealerStop && hitDealerCards.getValue() <= BlackJackInfo.BlackJack){
				//如果我加了以后爆了,庄家成牌了,则算好牌,因为对结局没有影响.
				normal++;
				System.out.println(hitPlayerCards.getCards().get(hitPlayerCards.getCards().size()-1).getValue() +" normal (i bust and dealer make 17+) : " + hitPlayerCards.getValue()  + " dealer: " + hitDealerCards.getValue());
			}else if(hitDealerCards.getValue() > BlackJackInfo.BlackJack){
				//如果我加以后爆了,庄家也爆了,不算好牌,应该留给庄家.
				worse++;
				System.out.println(hitPlayerCards.getCards().get(hitPlayerCards.getCards().size()-1).getValue() +" worse (i bust dealer bust either, SHOULD NEVER HAPPEN): " + originPlayerCards.getValue() +"->" + hitPlayerCards.getValue() + " dealer: " +hitDealerCards.getValue());
			}else if(hitDealerCards.getValue() < BlackJackInfo.DealerStop){
				//如果我加了以后爆了,庄家没爆,应该留给庄家,让他有更多爆的几率
				worse++;
				System.out.println(hitPlayerCards.getCards().get(hitPlayerCards.getCards().size()-1).getValue() +" worse (i bust, dealer near bust): " + originPlayerCards.getValue() +"->"+ hitPlayerCards.getValue() + " dealer: " +hitDealerCards.getValue());

			}
		}else if(hitPlayerCards.getValue() >= BlackJackInfo.DealerStop){
			//player not bust
			if(hitDealerCards.getValue()>= BlackJackInfo.DealerStop){
				//如果玩家成了,对方也成了,这是一步好棋
				better++;
				System.out.println(hitPlayerCards.getCards().get(hitPlayerCards.getCards().size()-1).getValue() +" better (i make 17+, dealer either 17+): " + hitPlayerCards.getValue() + " dealer: " +hitDealerCards.getValue());
			}else{
				//如果玩家成了,对方没有成,算是一步good?
				better++;
				System.out.println(hitPlayerCards.getCards().get(hitPlayerCards.getCards().size()-1).getValue() +" better (i make 17+ , dealer near): " + hitPlayerCards.getValue() + " dealer: " +hitDealerCards.getValue());
			}
		}else{
			//not bust is good either
			if(hitDealerCards.getValue() >= BlackJackInfo.DealerStop && hitDealerCards.getValue() <= BlackJackInfo.BlackJack){
				better++;
				System.out.println(hitPlayerCards.getCards().get(hitPlayerCards.getCards().size()-1).getValue() +" better (i lived , dealer make 17+): " + hitPlayerCards.getValue() + " dealer: " +hitDealerCards.getValue());
			}else{
				normal++;
				System.out.println(hitPlayerCards.getCards().get(hitPlayerCards.getCards().size()-1).getValue() +" normal (i live here, nice): " + hitPlayerCards.getValue() + " dealer: " +hitDealerCards.getValue());
			}
		}
		
		if(hitDealerCards.getValue() >= BlackJackInfo.DealerStop){
			draw++;
		}
		
		return new double[]{win,draw,lose,better,normal,worse};
	}
	
	public static void main(String[] args){
//		HelloWorld.printDoubleWDL(calcPlayerdVSDealerProbs(new PlayerCardsPathValue(Card.Four4,Card.Six6), Card.Six6));
		HelloWorld.printDoubleWDL(calcPlayerdVSDealerProbs(GenerateCardsUtil.hitPlayerOneMoreCard(new PlayerCardsPathValue(Card.Four4,Card.Six6)), Card.Six6));
	}
}
