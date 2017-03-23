package shil.bjrsh.strategy.one;

import java.util.ArrayList;
import java.util.Collection;

import shil.bjrsh.analyze.PlayersVSDealersResultChanceProb;
import shil.bjrsh.analyze.WinDrawLose;
import shil.bjrsh.core.Card;
import shil.bjrsh.core.GenerateCardsUtil;
import shil.bjrsh.core.PlayerCardsPathValue;
import shil.bjrsh.core.StartValue;

public class PlayerStrategyOne {
	
	//根据多摸一张牌提高的好处来是否要牌的策略
	public static double benefit = 0.09; 

	public static void play(StartValue startValue, Card dealerCard){
		System.out.println("Player: " + startValue + " -vs- Dealer: " + dealerCard);
		PlayerCardsPathValue playerCardsPathValue = new PlayerCardsPathValue(startValue);
		hitMeOneMoreTillBad(playerCardsPathValue, dealerCard);
//		HelloWorld.print(hitMeOneMoreTillBad(playerCardsPathValue, dealerCard));
	}
	
	public static Collection<PlayerCardsPathValue> hitMeOneMoreTillBad(PlayerCardsPathValue playerCardsPathValue, Card dealerCard){
		Collection<PlayerCardsPathValue> decidePackage = new ArrayList<PlayerCardsPathValue>(); 
//		double[] nowChance = PlayerAnalyzeWithCardsProb.playerNowVSDealerChance(StartValue.getOne(playerCardsPathValue.getValue()), dealerCard);
//		double[] oneMoreCardChance = PlayerAnalyzeWithCardsProb.playerChanceOneMoreCard(playerCardsPathValue, dealerCard);
		double[] nowChance = PlayersVSDealersResultChanceProb.calcPlayerdVSDealerProbs(playerCardsPathValue,dealerCard);
		double[] oneMoreCardChance = PlayersVSDealersResultChanceProb.calcPlayerdVSDealerProbs(GenerateCardsUtil.hitPlayerOneMoreCard(playerCardsPathValue),dealerCard);
		//如果多发一张牌,输的可能性会更小,则发牌
		if(nowChance[WinDrawLose.lose] > oneMoreCardChance[WinDrawLose.lose] + benefit){
			System.out.println(playerCardsPathValue +" will hit one more.");
			System.out.println(nowChance[WinDrawLose.lose] + " > " + oneMoreCardChance[WinDrawLose.lose]);
			Collection<PlayerCardsPathValue> oneMoreCardPaths = GenerateCardsUtil.hitPlayerOneMoreCard(playerCardsPathValue);
			for(PlayerCardsPathValue oneMorePath : oneMoreCardPaths){
				decidePackage.addAll(hitMeOneMoreTillBad(oneMorePath, dealerCard));
			}
		}else{
			decidePackage.add(playerCardsPathValue);
		}
		return decidePackage;
	}
	
	public static void main(String[] args){
		for(Card card : Card.values()){
			play(StartValue.Ten, card);
		}
	}
}
