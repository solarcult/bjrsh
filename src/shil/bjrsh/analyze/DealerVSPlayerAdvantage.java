package shil.bjrsh.analyze;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import shil.bjrsh.HelloWorld;
import shil.bjrsh.core.Card;
import shil.bjrsh.core.GenerateCardsUtil;
import shil.bjrsh.core.PlayerCardsPathValue;
import shil.bjrsh.core.StartValue;
import shil.bjrsh.nouseful.DealerVSPlayerChance;

/**
 * 计算玩家和庄家对决胜率的类,输出结果.
 * @author LiangJingJing
 * @date 2016年8月30日 上午12:14:03
 */
public class DealerVSPlayerAdvantage {
	
	public static List<DealerVSPlayerChance> makePlayerOneMoreVSNowDealerChange(){
		List<DealerVSPlayerChance> diff = new ArrayList<DealerVSPlayerChance>();
		for (StartValue startValue : StartValue.values()) {
			if (startValue.getValue() < StartValue.Six.getValue() || startValue == StartValue.TwentyOne) continue;
			for (Card dealerCard : Card.values()) {
				if(Card.One1 == dealerCard || dealerCard == Card.JJJ || dealerCard == Card.QQQ || dealerCard == Card.KKK) continue;
				PlayerCardsPathValue playerCardsPathValue = new PlayerCardsPathValue(startValue); 
				Collection<PlayerCardsPathValue> playerOneMoreCards = GenerateCardsUtil.hitPlayerOneMoreCard(playerCardsPathValue);
//				double[] playerOneMore = PlayerAnalyzeWithCardsProb.playerChanceOneMoreCard(new PlayerCardsPathValue(startValue), dealerCard);
//				double[] playerNow = PlayerAnalyzeWithCardsProb.playerNowVSDealerChance(startValue, dealerCard);
				double[] playerOneMore = PlayersVSDealersResultChanceProb.calcPlayerdVSDealerProbs(playerOneMoreCards, dealerCard);
				double[] playerNow = PlayersVSDealersResultChanceProb.calcPlayerdVSDealerProbs(playerCardsPathValue, dealerCard);
				DealerVSPlayerChance dealerVSPlayerChance = new DealerVSPlayerChance(DealerVSPlayerChance.Player,dealerCard, startValue, playerNow,playerOneMore);
				diff.add(dealerVSPlayerChance);
			}
		}
		return diff;
	}
	
	public static void main(String[] args){
		List<DealerVSPlayerChance> ao = makePlayerOneMoreVSNowDealerChange();
//		List<DealerVSPlayerChance> ao = makePlayerOneMoreVSNowPlayerChange();
		HelloWorld.print(ao);
	}
}
