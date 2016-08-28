package shil.bjrsh.analyze;

import java.util.Collection;
import java.util.HashSet;

import shil.bjrsh.HelloWorld;
import shil.bjrsh.core.BlackJackInfo;
import shil.bjrsh.core.Card;
import shil.bjrsh.core.GenerateCardsUtil;
import shil.bjrsh.core.PlayerCardsPathValue;
import shil.bjrsh.core.StartValue;

public class PlayerAnalyzeWithCardsProb {
	
	/**
	 * 用户现在的牌对Dealer的起始牌的概率
	 * @param startValue
	 * @param dealerValue
	 * @return
	 */
	public static double[] playerNowVSDealerChance(StartValue playerValue, Card dealerCard){
		double[] dealerWDL = DealerAnalyzeWithCardsProb.dealerResultChance(dealerCard, playerValue.getValue());
		return new double[]{dealerWDL[WinDrawLose.lose],dealerWDL[WinDrawLose.draw],dealerWDL[WinDrawLose.win]};
	}
	
	/**
	 * 用户再发一张牌的胜平负概率
	 * @param startValue
	 * @param dealerCard
	 * @return
	 */
	public static double[] playerChanceOneMoreCard(StartValue startValue,Card dealerCard){
		return playerChanceOneMoreCard(new PlayerCardsPathValue(startValue), dealerCard);
	}
	
	/**
	 * 用户再发一张牌的胜平负概率
	 * @param playerCardsPathValue
	 * @param dealerCard
	 * @return
	 */
	public static double[] playerChanceOneMoreCard(PlayerCardsPathValue playerCardsPathValue,Card dealerCard){
		Collection<PlayerCardsPathValue> oneHitCards = GenerateCardsUtil.hitPlayerOneMoreCard(playerCardsPathValue);
		if(oneHitCards.size()!=13){throw new RuntimeException("should 13 combine. "+oneHitCards.size());}
		return calcPlayerCollectionsProb(oneHitCards, dealerCard);
	}
	
	/**
	 * 计算用户的胜平负率 = 卡牌出现的概率*对阵的胜平负率
	 * @param playerCards
	 * @param dealerCard
	 * @return
	 */
	public static double[] calcPlayerCollectionsProb(Collection<PlayerCardsPathValue> playerCards,Card dealerCard){
		double winrate = 0;
		double drawrate = 0;
		double loserate = 0;
		for(PlayerCardsPathValue onehit : playerCards){
			if(onehit.getValue() > BlackJackInfo.BlackJack){
				loserate += onehit.prob();
			}else{
				double[] dealerwdl = DealerAnalyzeWithCardsProb.dealerResultChance(dealerCard, onehit.getValue());
				//dealer的负率就是用户的胜率
				winrate += onehit.prob() * dealerwdl[WinDrawLose.lose];
				drawrate += onehit.prob() * dealerwdl[WinDrawLose.draw];
				loserate += onehit.prob() * dealerwdl[WinDrawLose.win];
			}
		}
		
		double totalrate = winrate + drawrate + loserate;
		return new double[]{winrate/totalrate,drawrate/totalrate,loserate/totalrate};
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
		
		return calcPlayerCollectionsProb(oneHitCards, dealerCard);
	}
	
	public static void analyzeAllAXCards(){
		for(Card playercard : Card.values()){
			System.out.println("\n\r\t\t~~~~~~~~~~"+playercard.name()+"~~~~~~~~~~~~~");
			for(Card dealercard : Card.values()){
				System.out.println("\nPlayer A+"+playercard.name() + "  vs  "+dealercard.name());
				double[] advanced = playerAX1moreCvsDealer(playercard,dealercard);
//				HelloWorld.printDoubleWDL(playerAX1moreCvsDealer(playercard,dealercard));
				
				//这里的目的是打印出AX组合原来最大的组合是多少,用()?:三元符更简单但不宜读
				int playervalue = playercard.getValue();
				if((playercard.getValue()+11)<=BlackJackInfo.BlackJack){
					playervalue = (playercard.getValue()+11);
				}else{
					playervalue = (playercard.getValue()+1);
				}
				System.out.println("decide value:"+playervalue);
				
				//这里打印出如果不多hit一张时,现有牌的胜率,不比不知道,一比吓一跳
				double[] origin = playerNowVSDealerChance(StartValue.getOne(playervalue),dealercard);
//				HelloWorld.printDoubleWDL(playerNowVSDealerChance(StartValue.getOne(playervalue),dealercard));
				
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
	
	public static void main(String[] args) {
		
		analyzeAllAXCards();
		
//		anlayzeAvsDealerAllTypeCards();

		/*
		HelloWorld.printDoubleWDL(playerAX1moreCvsDealer(Card.Six6,Card.Seven7));
		for(StartValue startValue : StartValue.values()){
			System.out.println(" * Player:"+startValue +" *");
			for(Card card : Card.values()){
				System.out.println(" == Player:"+startValue+" vs Dealer:"+card+" == ");
//				HelloWorld.printDoubleWDL(playerChanceOneMoreCard(startValue,card));
				HelloWorld.printDoubleWDL(playerNowVSDealerChance(startValue,card));
			}
		}
		*/
		
//		HelloWorld.printDoubleWDL(playerAX1moreCvsDealer(Card.Two2,Card.Two2));
	}

}
