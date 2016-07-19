package shil.bjrsh.core;

import java.util.Collection;

import shil.bjrsh.analyze.PlayerAnalyzeWithCardsProb;
import shil.bjrsh.analyze.WinDrawLose;
import shil.bjrsh.strategy.PlayerAction;
import shil.bjrsh.strategy.two.PlayerStrategyTwo;

public class ProfitUtil {

	public static double baseMoney = 300d;
	
	//计算投资回报
	public static double calcStarthandPossibleFuturesVSDealerCardInReturn(Collection<PlayerCardsPathValue> playerCardsPathValues,Card dealerCard){
		double ROI = 0;
		for(PlayerCardsPathValue oneWay : playerCardsPathValues)
			ROI += moneyCalcOneHandInReturn(oneWay,dealerCard);
		return ROI;
	}
	
	//计算用户的本次组合与庄家起手牌的最终概率组合值,看看回报率是如何
	public static double moneyCalcOneHandInReturn(PlayerCardsPathValue playerCardsPathValue,Card dealerCard){
		double ROI = 0d;
		//自己爆了
		if(playerCardsPathValue.getValue() > BlackJackInfo.BlackJack){
			ROI -= baseMoney * playerCardsPathValue.prob();
			return ROI;
		}
		//基本的赔率都是1倍
		double winrate = 1;
		double loserate = 1;
		if(playerCardsPathValue.getAction() == PlayerAction.DoubleDone){
			//用户double
			winrate = 2;
			loserate = 2;
		}else if(playerCardsPathValue.getAction() == PlayerAction.Giveup){
			//用户放弃损失一半
			ROI -= baseMoney * 0.5 * playerCardsPathValue.prob();
			return ROI;
		}else if(playerCardsPathValue.getAction() == PlayerAction.Init || playerCardsPathValue.getAction() == PlayerAction.Double){
			//这两种状态不应该再次出现,一定是前面处理不对才会到这里
			throw new RuntimeException(playerCardsPathValue.getAction() + " ? wtf");
		}
		
		//最终计算投资收益率
		double[] playerchance = PlayerAnalyzeWithCardsProb.playerNowVSDealerChance(StartValue.getOne(playerCardsPathValue.getValue()), dealerCard);
		ROI += baseMoney * playerchance[WinDrawLose.win] * winrate * playerCardsPathValue.prob();
		ROI -= baseMoney * playerchance[WinDrawLose.lose] * loserate * playerCardsPathValue.prob();
		
		return ROI;
	}
	
	public static void main(String[] args){
//		System.out.println(MoneyCalcInReturn(STGeneratePlayerUtil.generatePlayerCardsPaths(new PlayerCardsPathValue(StartValue.Five), Card.Six6), Card.Six6));
//		System.out.println(MoneyCalcInReturn(STGeneratePlayerUtil.generatePlayerCardsPaths(new PlayerCardsPathValue(StartValue.Five), Card.Seven7), Card.Seven7));
		double x = calcStarthandPossibleFuturesVSDealerCardInReturn(PlayerStrategyTwo.getInstance().generatePlayerCardsPaths(new PlayerCardsPathValue(StartValue.Ten), Card.Six6), Card.Six6);
		System.out.println(x);
	}
}
