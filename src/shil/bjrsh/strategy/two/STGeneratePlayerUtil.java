package shil.bjrsh.strategy.two;

import java.util.Collection;
import java.util.HashSet;

import shil.bjrsh.HelloWorld;
import shil.bjrsh.PlayerStartTwoCards;
import shil.bjrsh.core.BlackJackInfo;
import shil.bjrsh.core.Card;
import shil.bjrsh.core.PlayerCardsPathValue;
import shil.bjrsh.core.PortfolioUtil;
import shil.bjrsh.core.StartValue;
import shil.bjrsh.strategy.PlayerAction;

public class STGeneratePlayerUtil {

	public static Collection<PlayerCardsPathValue> generatePlayerCardsPaths(PlayerCardsPathValue playerCardsPathValue,Card dealerCard){
		Collection<PlayerCardsPathValue> playerCardsPathValues = new HashSet<PlayerCardsPathValue>();
		
		if(!playerCardsPathValue.isValid()) return playerCardsPathValues;
		
		if(playerCardsPathValue.getResult() == PlayerAction.Double){
			playerCardsPathValues.add(playerCardsPathValue);
			return playerCardsPathValues;
		}
		
		if(playerCardsPathValue.getValue() > BlackJackInfo.BlackJack){
			playerCardsPathValues.add(playerCardsPathValue);
			return playerCardsPathValues;
		}
		
		//check strategy two matrix and decide action
		PlayerStrategy playerStrategy = PlayerStrategyMatrix.getPlayerAction(StartValue.getOne(playerCardsPathValue.getValue()),dealerCard);
		PlayerAction action = null;
//		if(playerCardsPathValue.getCards().isEmpty()){
		if(playerCardsPathValue.getCards().size() == 2){
			action = playerStrategy.getStartAction();
		}else{
			action = playerStrategy.getThreeCardAction();
		}
		playerCardsPathValue.setResult(action);
		
		//if result allow conitnue;
		if(playerCardsPathValue.getResult() == PlayerAction.Giveup || playerCardsPathValue.getResult() == PlayerAction.Stand){
			playerCardsPathValues.add(playerCardsPathValue);
			return playerCardsPathValues;
		}
		
		
		for (Card card : Card.values()) {
			PlayerCardsPathValue aNewPath = new PlayerCardsPathValue(playerCardsPathValue);
			aNewPath.addCard(card);
			playerCardsPathValues.addAll(generatePlayerCardsPaths(aNewPath,dealerCard));
		}
		
		return playerCardsPathValues;
	}
	
	public static void printAllStartValueVSDealer(){
		double big = 0;
		for (StartValue startValue : StartValue.values()) {
			if(startValue.getValue() < 4) continue;
			double total = 0;
			for (Card card : Card.values()) {
				if(card == Card.One1) continue;
				double result = PortfolioUtil.MoneyCalcInReturn(STGeneratePlayerUtil.generatePlayerCardsPaths(new PlayerCardsPathValue(startValue), card), card);
				total +=result;
				System.out.println("StartValue :" + startValue +" vs DealerCard : "+card +"\tROI: "+ result);
			}
			System.out.println("T: " +total);
			big += total;
		}
		System.out.println(big);
	}
	
	public static void printReallyTwoCardsVSDealer(){
		double big = 0;
		Collection<PlayerCardsPathValue> ptcs =  PlayerStartTwoCards.generatePlayerTwoStartCards();
		for (PlayerCardsPathValue ptc : ptcs) {
//			System.out.println(ptc);
			double total = 0;
			for (Card dealerCard : Card.values()) {
				if(dealerCard == Card.One1) continue;				
				double result = PortfolioUtil.MoneyCalcInReturn(STGeneratePlayerUtil.generatePlayerCardsPaths(ptc, dealerCard), dealerCard);
				total +=result;
				System.out.println("StartValue :" + ptc.getValue() +" vs DealerCard : "+dealerCard +"\tROI: "+ result);
			}
			System.out.println("T: " +total);
			big += total;
		}
		System.out.println(big);
	}
	
	public static void main(String[] args){
//		Collection<PlayerCardsPathValue> x = generatePlayerCardsPaths(new PlayerCardsPathValue(StartValue.Seventeen), Card.Seven7);
//		HelloWorld.print(x) ;
		
		printAllStartValueVSDealer();
		
//		printReallyTwoCardsVSDealer();
	}
	
}
