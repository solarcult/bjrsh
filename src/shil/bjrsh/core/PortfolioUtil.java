package shil.bjrsh.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import shil.bjrsh.analyze.PlayerAnalyzeWithCardsProb;
import shil.bjrsh.analyze.WinDrawLose;
import shil.bjrsh.strategy.PlayerAction;
import shil.bjrsh.strategy.two.STGeneratePlayerUtil;

public class PortfolioUtil {

	public static double baseMoney = 300d;
	
	public static double MoneyCalcInReturn(Collection<PlayerCardsPathValue> playerCardsPathValues,Card dealerCard){
		double ROI = 0;
		for(PlayerCardsPathValue oneWay : playerCardsPathValues){
//			System.out.println(oneWay);
			if(oneWay.getValue() > BlackJackInfo.BlackJack){
				ROI -= baseMoney * oneWay.prob();
//				System.out.println(-baseMoney * oneWay.prob());
				continue;
 			}
			double winrate = 1;
			double loserate = 1;
			if(oneWay.getResult() == PlayerAction.Double){
				winrate = 2;
				loserate = 2;
			}else if(oneWay.getResult() == PlayerAction.Giveup){
				ROI -= baseMoney * 0.5 * oneWay.prob();
//				System.out.println(-baseMoney * 0.5 * oneWay.prob());
				continue;
			}
			
			double[] playerchance = PlayerAnalyzeWithCardsProb.playerNowVSDealerChance(StartValue.getOne(oneWay.getValue()), dealerCard);
			ROI += baseMoney * playerchance[WinDrawLose.win] * winrate * oneWay.prob();
			ROI -= baseMoney * playerchance[WinDrawLose.lose] * loserate * oneWay.prob();
//			System.out.println("total: " + (double)((baseMoney * playerchance[WinDrawLose.win] * winrate * oneWay.prob()) + (-baseMoney * playerchance[WinDrawLose.lose] * loserate * oneWay.prob())));
		}
//		System.out.println(playerCardsPathValues.size());
		return ROI;
	}
	
	public static Map<StartValue,Double> getStartValueROI(){
		Map<StartValue,Double> roi = new HashMap<StartValue, Double>();
		return roi;
	}
	
	public static void main(String[] args){
//		System.out.println(MoneyCalcInReturn(STGeneratePlayerUtil.generatePlayerCardsPaths(new PlayerCardsPathValue(StartValue.Five), Card.Six6), Card.Six6));
//		System.out.println(MoneyCalcInReturn(STGeneratePlayerUtil.generatePlayerCardsPaths(new PlayerCardsPathValue(StartValue.Five), Card.Seven7), Card.Seven7));
		MoneyCalcInReturn(STGeneratePlayerUtil.generatePlayerCardsPaths(new PlayerCardsPathValue(StartValue.Eight), Card.Six6), Card.Six6);

	}
}
