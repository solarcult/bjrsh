package shil.bjrsh.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import shil.bjrsh.analyze.PlayerAnalyzeWithCardsProb;
import shil.bjrsh.analyze.WinDrawLose;
import shil.bjrsh.strategy.PlayerAction;
import shil.bjrsh.strategy.two.PlayerStrategyTwo;

public class ProfitUtil {

	public static double baseMoney = 300d;
	
	public static double calcStarthandPossibleFuturesVSDealerCardInReturn(Collection<PlayerCardsPathValue> playerCardsPathValues,Card dealerCard){
		double ROI = 0;
		for(PlayerCardsPathValue oneWay : playerCardsPathValues)
			ROI += moneyCalcOneHandInReturn(oneWay,dealerCard);
		return ROI;
	}
	
	public static double moneyCalcOneHandInReturn(PlayerCardsPathValue playerCardsPathValue,Card dealerCard){
		double ROI = 0d;
		if(playerCardsPathValue.getValue() > BlackJackInfo.BlackJack){
			ROI -= baseMoney * playerCardsPathValue.prob();
			return ROI;
		}
		double winrate = 1;
		double loserate = 1;
		if(playerCardsPathValue.getAction() == PlayerAction.DoubleDone){
			winrate = 2;
			loserate = 2;
		}else if(playerCardsPathValue.getAction() == PlayerAction.Giveup){
			ROI -= baseMoney * 0.5 * playerCardsPathValue.prob();
			return ROI;
		}else if(playerCardsPathValue.getAction() == PlayerAction.Init || playerCardsPathValue.getAction() == PlayerAction.Double){
			throw new RuntimeException(playerCardsPathValue.getAction() + " ? wtf");
		}
		
		double[] playerchance = PlayerAnalyzeWithCardsProb.playerNowVSDealerChance(StartValue.getOne(playerCardsPathValue.getValue()), dealerCard);
		ROI += baseMoney * playerchance[WinDrawLose.win] * winrate * playerCardsPathValue.prob();
		ROI -= baseMoney * playerchance[WinDrawLose.lose] * loserate * playerCardsPathValue.prob();
		
		return ROI;
	}
	
	public static Map<StartValue,Double> getStartValueROI(){
		Map<StartValue,Double> roi = new HashMap<StartValue, Double>();
		return roi;
	}
	
	public static void main(String[] args){
//		System.out.println(MoneyCalcInReturn(STGeneratePlayerUtil.generatePlayerCardsPaths(new PlayerCardsPathValue(StartValue.Five), Card.Six6), Card.Six6));
//		System.out.println(MoneyCalcInReturn(STGeneratePlayerUtil.generatePlayerCardsPaths(new PlayerCardsPathValue(StartValue.Five), Card.Seven7), Card.Seven7));
		double x = calcStarthandPossibleFuturesVSDealerCardInReturn(PlayerStrategyTwo.getInstance().generatePlayerCardsPaths(new PlayerCardsPathValue(StartValue.Ten), Card.Six6), Card.Six6);
		System.out.println(x);
	}
}
