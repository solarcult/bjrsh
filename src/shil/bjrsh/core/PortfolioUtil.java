package shil.bjrsh.core;

import java.util.Collection;

import shil.bjrsh.analyze.PlayerAnalyzeWithCardsProb;
import shil.bjrsh.analyze.WinDrawLose;
import shil.bjrsh.strategy.PlayerAction;
import shil.bjrsh.strategy.two.STGeneratePlayerUtil;

public class PortfolioUtil {

	public static double baseMoney = 300d;
	
	public static double MoneyCalcInReturn(Collection<PlayerCardsPathValue> playerCardsPathValues,Card dealerCard){
		double ROI = 0;
		for(PlayerCardsPathValue oneWay : playerCardsPathValues){
			if(oneWay.getValue() > BlackJackInfo.BlackJack){
				ROI -= baseMoney * oneWay.prob();
				continue;
 			}
			double winrate = 1;
			double loserate = 1;
			if(oneWay.getResult() == PlayerAction.Double){
				winrate = 2;
				loserate = 2;
			}else if(oneWay.getResult() == PlayerAction.Giveup){
				ROI -= baseMoney * 0.5 * oneWay.prob();
				continue;
			}
			
			double[] playerchance = PlayerAnalyzeWithCardsProb.playerNowVSDealerChance(StartValue.getOne(oneWay.getValue()), dealerCard);
			
			ROI += baseMoney * playerchance[WinDrawLose.win] * winrate * oneWay.prob();
			ROI -= baseMoney * playerchance[WinDrawLose.lose] * loserate * oneWay.prob();
		}
		return ROI;
	}
	
	public static void printAllStartValueVSDealer(){

		double big = 0;
		for (StartValue startValue : StartValue.values()) {
			if(startValue.getValue() < 2) continue;
			double total = 0;
			for (Card card : Card.values()) {
				if(card == Card.One1) continue;
				double result = PortfolioUtil.MoneyCalcInReturn(STGeneratePlayerUtil.generatePlayerCardsPaths(new PlayerCardsPathValue(startValue), card), card);
				total +=result;
				System.out.println("StartValue :" + startValue +" vs DealerCard : "+card +"\tROI: "+ result);
			}
			System.out.println("T: " +total);
			big += total;
		}
		System.out.println(big);
	}
	
	public static void main(String[] args){
//		System.out.println(MoneyCalcInReturn(STGeneratePlayerUtil.generatePlayerCardsPaths(new PlayerCardsPathValue(StartValue.Eight), Card.Six6), Card.Six6));
		printAllStartValueVSDealer();
	}
}
