package shil.bjrsh.analyze;

import java.util.Collection;
import java.util.HashSet;

import shil.bjrsh.HelloWorld;
import shil.bjrsh.core.BlackJackInfo;
import shil.bjrsh.core.Card;
import shil.bjrsh.core.GenerateCardsUtil;
import shil.bjrsh.core.PlayerCardsPathValue;
import shil.bjrsh.core.StartValue;

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
		PlayerCardsPathValue ainstead1 = new PlayerCardsPathValue(StartValue.One);
		ainstead1.addCard(playernotACardvalue);
		Collection<PlayerCardsPathValue>  a1all = GenerateCardsUtil.hitPlayerOneMoreCard(ainstead1);
		for(PlayerCardsPathValue one : a1all){
			if(one.getValue() > Card.Eleven.getValue()){
				oneHitCards.add(one);
			}
		}
//		HelloWorld.print(oneHitCards);
//		System.out.println("s");
		//一下是正经的生成11的牌 ,A 2~9
		PlayerCardsPathValue ainstead11 = new PlayerCardsPathValue(StartValue.Eleven);
		ainstead11.addCard(playernotACardvalue);
		Collection<PlayerCardsPathValue>  a11all = GenerateCardsUtil.hitPlayerOneMoreCard(ainstead11);
		for(PlayerCardsPathValue one : a11all){
			if(one.getValue() <= BlackJackInfo.BlackJack){
				oneHitCards.add(one);
			}
		}
//		HelloWorld.print(oneHitCards);
		
		return PlayerAnalyzeWithCardsProb.calcPlayerCollectionsProb(oneHitCards, dealerCard);
	}
	
	public static void analyzeAllAXCards(){
		for(Card playercard : Card.values()){
			System.out.println("\n\r\t\t~~~~~~~~~~"+playercard.name()+"~~~~~~~~~~~~~");
			for(Card dealercard : Card.values()){
				System.out.println("\nPlayer A+"+playercard.name() + "  vs  "+dealercard.name());
				double[] advanced = playerAX1moreCvsDealer(playercard,dealercard);
				
				//这里的目的是打印出AX组合原来最大的组合是多少,用()?:三元符更简单但不宜读
				int playervalue = playercard.getValue();
				if((playercard.getValue()+11)<=BlackJackInfo.BlackJack){
					playervalue = (playercard.getValue()+11);
				}else{
					playervalue = (playercard.getValue()+1);
				}
				System.out.println("decide value:"+playervalue);
				
				//这里打印出如果不多hit一张时,现有牌的胜率,不比不知道,一比吓一跳
				double[] origin = PlayerAnalyzeWithCardsProb.playerNowVSDealerChance(StartValue.getOne(playervalue),dealercard);
				
				HelloWorld.print2DoubleWDL(advanced,origin);
			}
		}
	}
	
	/**
	 * 看分牌后的单A的概率方法
	 * 必须注释掉
	 * playerAX1moreCvsDealer ainstead1.addCard(playernotACardvalue) and ainstead11.addCard(playernotACardvalue);这两个方法
	 * @deprecated
	 */
	public static void anlayzeAvsDealerAllTypeCards(){
		//should comment ainstead1.addCard(playernotACardvalue) and ainstead11.addCard(playernotACardvalue);;
		for(Card dealercard : Card.values()){
			System.out.println("Player A Split" + "  vs  "+dealercard.name());
			HelloWorld.printDoubleWDL(playerAX1moreCvsDealer(Card.Eight8,dealercard));
//			HelloWorld.printDoubleWDL(playerNowVSDealerChance(StartValue.Twelve,dealercard));
		}
	}
}