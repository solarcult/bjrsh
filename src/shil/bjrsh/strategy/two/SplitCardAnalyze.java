package shil.bjrsh.strategy.two;

import java.util.Collection;

import shil.bjrsh.analyze.PlayersVSDealersResultChanceProb;
import shil.bjrsh.core.Card;
import shil.bjrsh.core.PlayerCardsPathValue;
import shil.bjrsh.core.ProfitUtil;
import shil.bjrsh.core.StartValue;
import shil.bjrsh.nouseful.DealerVSPlayerChance;

public class SplitCardAnalyze {

	public static void main(String[] args) {
		splitCardChanceCompare();
	}

	public static void splitCardChanceCompare(){
		for(Card playercard: Card.values()){
			if(playercard.getValue() >= 2 && playercard.getValue()<=10 && playercard != Card.JJJ && playercard != Card.QQQ && playercard != Card.KKK){
				for(Card dealerCard : Card.values()){
					if(dealerCard.getValue() >= 2 && dealerCard.getValue()<=10 && dealerCard != Card.JJJ && dealerCard != Card.QQQ && dealerCard != Card.KKK){
						Collection<PlayerCardsPathValue> origin =  PlayerStrategyTwo.getInstance().generatePlayerCardsPaths(new PlayerCardsPathValue(playercard), dealerCard);
						Collection<PlayerCardsPathValue> doubleOrigin =  PlayerStrategyTwo.getInstance().generatePlayerCardsPaths(new PlayerCardsPathValue(playercard,playercard),dealerCard);
//						double[] splitafter = PlayerAnalyzeWithCardsProb.calcPlayerCollectionsProb(origin, dealerCard);
//						double[] duborg = PlayerAnalyzeWithCardsProb.calcPlayerCollectionsProb(doubleOrigin, dealerCard);
						double[] splitafter = PlayersVSDealersResultChanceProb.calcPlayerdVSDealerProbs(origin,dealerCard);
						double[] duborg = PlayersVSDealersResultChanceProb.calcPlayerdVSDealerProbs(doubleOrigin,dealerCard);
						DealerVSPlayerChance dealerVSPlayerChance = new DealerVSPlayerChance(DealerVSPlayerChance.Player, dealerCard, StartValue.getOne(playercard.getValue()*2),duborg,splitafter);
						System.out.println(dealerVSPlayerChance);
					}
				}
			}
		}
	}
	
	@Deprecated
	//被上面的方法替代了,无用
	public static void printPairSplitROI(){
		double roi = 0d;
		for(Card splitCard : Card.values()){
			if(splitCard.getValue()>=2 && splitCard.getValue()<=11){
				for(Card dealerCard : Card.values()){
					if(dealerCard == Card.One1) continue;
					double result = ProfitUtil.calcStarthandPossibleFuturesVSDealerCardInReturn(PlayerStrategyTwo.getInstance().generatePlayerCardsPaths(new PlayerCardsPathValue(splitCard), dealerCard), dealerCard);
					roi +=result;
					System.out.println("playercard :" + splitCard +" vs DealerCard : "+dealerCard +"\tROI: "+ result);
				}
			}
		}
		System.out.println("ROI: " + roi);
	}
}
