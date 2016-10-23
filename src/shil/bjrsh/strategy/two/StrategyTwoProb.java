package shil.bjrsh.strategy.two;

import java.util.ArrayList;
import java.util.List;

import shil.bjrsh.analyze.PlayersVSDealersResultChanceProb;
import shil.bjrsh.core.Card;
import shil.bjrsh.core.GenerateCardsUtil;
import shil.bjrsh.core.PlayerCardsPathValue;
import shil.bjrsh.core.StartValue;
import shil.bjrsh.nouseful.DealerVSPlayerChance;
import shil.bjrsh.strategy.PlayerAction;

public class StrategyTwoProb {

	
	public static List<DealerVSPlayerChance> view9andAbove(){
		List<DealerVSPlayerChance> diff = new ArrayList<DealerVSPlayerChance>();
		for (StartValue startValue : StartValue.values()) {
			if(startValue.getValue() < 4 || startValue.getValue()>=22) continue;
			for (Card dealerCard : Card.values()) {
				if(Card.One1 == dealerCard || dealerCard == Card.JJJ || dealerCard == Card.QQQ || dealerCard == Card.KKK) continue;
				PlayerCardsPathValue adang = new PlayerCardsPathValue(startValue);
				adang.setAction(PlayerAction.TestSecondChoice);
				double[] advanced = PlayersVSDealersResultChanceProb.calcPlayerdVSDealerProbs(PlayerStrategyTwo.getInstance().generatePlayerCardsPaths(adang, dealerCard), dealerCard);
				double [] origin = PlayersVSDealersResultChanceProb.calcPlayerdVSDealerProbs(GenerateCardsUtil.hitPlayerOneMoreCard(adang), dealerCard);
				DealerVSPlayerChance dealerVSPlayerChance = new DealerVSPlayerChance(DealerVSPlayerChance.Player,dealerCard, startValue, origin,advanced);
				diff.add(dealerVSPlayerChance);
				System.out.println(dealerVSPlayerChance);
			}
		}
		return diff;
	}
	
	public static void main(String[] args) {
		view9andAbove();
//		HelloWorld.print(view9andAbove());
	}

}
