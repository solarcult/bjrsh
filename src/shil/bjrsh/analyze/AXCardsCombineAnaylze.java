package shil.bjrsh.analyze;

import java.util.Collection;
import java.util.HashSet;

import shil.bjrsh.HelloWorld;
import shil.bjrsh.core.BlackJackInfo;
import shil.bjrsh.core.Card;
import shil.bjrsh.core.GenerateCardsUtil;
import shil.bjrsh.core.PlayerCardsPathValue;

public class AXCardsCombineAnaylze {

	public static void main(String[] args) {
		analyzeAllAXCards();
	}

	/**
	 * FIXME 这个方法有问题,没有考虑playernotAcard是TJQK的情况,也没有考虑到如果再来一张A的情况,需要好好思考重写
	 * @param playernotACardvalue
	 * @param dealerCard
	 * @return
	 */
	public static double[] playerAX1moreCvsDealer(Card playernotACardvalue,Card dealerCard){
		Collection<PlayerCardsPathValue> oneHitCards = new HashSet<PlayerCardsPathValue>();
		
		//一下是正经的生成11的牌 ,A 2~9
		PlayerCardsPathValue ainstead11 = new PlayerCardsPathValue(Card.Eleven);
		ainstead11.addCard(playernotACardvalue);
		Collection<PlayerCardsPathValue>  a11all = GenerateCardsUtil.hitPlayerOneMoreCard(ainstead11);
		for(PlayerCardsPathValue one : a11all){
			if(one.getValue() <= BlackJackInfo.BlackJack){
				oneHitCards.add(one);
			}
		}
//		HelloWorld.print(oneHitCards);

//		System.out.println("s");
		
		PlayerCardsPathValue ainstead1 = new PlayerCardsPathValue(Card.One1);
		ainstead1.addCard(playernotACardvalue);
		Collection<PlayerCardsPathValue>  a1all = GenerateCardsUtil.hitPlayerOneMoreCardEvenIlleage(ainstead1);
		for(PlayerCardsPathValue one : a1all){
			if(one.getValue() > Card.Eleven.getValue()){
				oneHitCards.add(one);
			}
		}
		HelloWorld.print(oneHitCards);
		
//		return PlayerAnalyzeWithCardsProb.calcPlayerCollectionsProb(oneHitCards, dealerCard);
		return PlayersVSDealersResultChanceProb.calcPlayerdVSDealerProbs(oneHitCards, dealerCard);
	}
	
	public static void analyzeAllAXCards(){
		double aroi = 0d;
		for(Card playercard : Card.values()){
			//use should split A A
			if(playercard == Card.One1 || playercard == Card.JJJ || playercard == Card.QQQ || playercard == Card.KKK || playercard == Card.Eleven) continue;
			System.out.println("\n\r\t\t~~~~~~~~~~"+playercard.name()+"~~~~~~~~~~~~~");
			for(Card dealercard : Card.values()){
				//dealer one1 card including eleven cards
				if(dealercard == Card.One1 || dealercard == Card.JJJ || dealercard == Card.QQQ || dealercard == Card.KKK) continue;
				System.out.println("\nPlayer A+"+playercard.name() + "  vs  "+dealercard.name());
				double[] advanced = playerAX1moreCvsDealer(playercard,dealercard);
				
				//这里的目的是打印出AX组合原来最大的组合是多少,用()?:三元符更简单但不宜读
				int playervalue = playercard.getValue() + 11;
				System.out.println("decide value:"+playervalue);
				
				//这里打印出如果不多hit一张时,现有牌的胜率,不比不知道,一比吓一跳
//				double[] origin = PlayerAnalyzeWithCardsProb.playerNowVSDealerChance(StartValue.getOne(playervalue),dealercard);
				double[] origin = PlayersVSDealersResultChanceProb.calcPlayerdVSDealerProbs(new PlayerCardsPathValue(playercard,Card.Eleven),dealercard);
				HelloWorld.print2DoubleWDL(advanced,origin);
				
				int drate = 1;
				//hit
				if(playercard.getValue() <=6){
					//use advanced matrix
					if(dealercard.getValue()==5 || dealercard.getValue()==6){
						//double
						drate = 2;
					}else if(dealercard.getValue()==4 && playercard.getValue() ==6){
						drate = 2;
					}
					aroi += advanced[WinDrawLose.win]*drate;
					aroi -= advanced[WinDrawLose.lose]*drate;
				}else{
					//use origin matrix
					aroi += origin[WinDrawLose.win];
					aroi -= origin[WinDrawLose.lose];
				}
			}
		}
		System.out.println("aroi: "+aroi);
	}
}
