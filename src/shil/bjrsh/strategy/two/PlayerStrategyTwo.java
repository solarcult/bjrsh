package shil.bjrsh.strategy.two;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import shil.bjrsh.CalcStrategyProfitMachine;
import shil.bjrsh.OneCalcPackage;
import shil.bjrsh.PlayerStartTwoCards;
import shil.bjrsh.core.BlackJackInfo;
import shil.bjrsh.core.CalcROIMap;
import shil.bjrsh.core.Card;
import shil.bjrsh.core.PlayerCardsPathValue;
import shil.bjrsh.core.ProfitUtil;
import shil.bjrsh.core.StartValue;
import shil.bjrsh.strategy.PlayerAction;
import shil.bjrsh.strategy.Strategy;

public class PlayerStrategyTwo implements Strategy{

	private static PlayerStrategyTwo playerStrategyTwo = new PlayerStrategyTwo();
	
	public static PlayerStrategyTwo getInstance() {
		return playerStrategyTwo;
	}
	
	@Override
	public Collection<PlayerCardsPathValue> generatePlayerCardsPaths(PlayerCardsPathValue playerCardsPathValue,Card dealerCard){
		Collection<PlayerCardsPathValue> playerCardsPathValues = new HashSet<PlayerCardsPathValue>();
		
		if(!playerCardsPathValue.isValid()) return playerCardsPathValues;
		
		if(playerCardsPathValue.getAction() == PlayerAction.Init){
			//the first time reset action
			playerCardsPathValue.setAction(PlayerStrategyMatrix.getPlayerAction(playerCardsPathValue.getStartValue(),dealerCard).getStartAction());
		}
		
		if(playerCardsPathValue.getValue() > BlackJackInfo.BlackJack){
			//burst, bye~
			playerCardsPathValues.add(playerCardsPathValue);
		}else if(playerCardsPathValue.getAction() == PlayerAction.Giveup || playerCardsPathValue.getAction() == PlayerAction.Stand){
			// we finish and pray
			playerCardsPathValues.add(playerCardsPathValue);
		}else if(playerCardsPathValue.getAction() == PlayerAction.DoubleDone){
			// good luck
			playerCardsPathValues.add(playerCardsPathValue);
		}else if(playerCardsPathValue.getAction() == PlayerAction.Double){
			//let double me up
			for (Card card : Card.values()) {
				PlayerCardsPathValue aNewPath = new PlayerCardsPathValue(playerCardsPathValue);
				aNewPath.addCard(card);
				aNewPath.setAction(PlayerAction.DoubleDone);
				if(aNewPath.isValid()) playerCardsPathValues.addAll(generatePlayerCardsPaths(aNewPath,dealerCard));
			}
		}else if(playerCardsPathValue.getAction() == PlayerAction.Hit){
			System.out.println(playerCardsPathValue);
			// hit me hardly
			for (Card card : Card.values()) {
				PlayerCardsPathValue aNewPath = new PlayerCardsPathValue(playerCardsPathValue);
				aNewPath.addCard(card);
				if(aNewPath.isValid()){
					if(aNewPath.getValue() <= BlackJackInfo.BlackJack){
						aNewPath.setAction(PlayerStrategyMatrix.getPlayerAction(StartValue.getOne(aNewPath.getValue()),dealerCard).getThreeCardAction());
					}
					playerCardsPathValues.addAll(generatePlayerCardsPaths(aNewPath,dealerCard));
				}
			}
		}else{
			//wtf
			throw new RuntimeException("help, please help me, where am i?");
		}
		
		return playerCardsPathValues;
}
	
	@Deprecated
	public static void printAllStartValueVSDealer(){
		double big = 0;
		for (StartValue startValue : StartValue.values()) {
			if(startValue.getValue() < 4) continue;
			double total = 0;
			for (Card card : Card.values()) {
				if(card == Card.One1) continue;
				double result = ProfitUtil.calcStarthandPossibleFuturesVSDealerCardInReturn(PlayerStrategyTwo.getInstance().generatePlayerCardsPaths(new PlayerCardsPathValue(startValue), card), card);
				total +=result;
				System.out.println("StartValue :" + startValue +" vs DealerCard : "+card +"\tROI: "+ result);
			}
			System.out.println("T: " +total);
			big += total;
		}
		System.out.println(big);
	}
	
	public static void printReallyTwoCardsVSDealer(){
		CalcROIMap calcROIMap = new CalcROIMap();
		double big = 0;
		Collection<PlayerCardsPathValue> ptcs =  PlayerStartTwoCards.generatePlayerTwoStartCards();
		for (PlayerCardsPathValue ptc : ptcs) {
			double total = 0;
			for (Card dealerCard : Card.values()) {
				if(dealerCard == Card.One1) continue;
				double result = ProfitUtil.calcStarthandPossibleFuturesVSDealerCardInReturn(PlayerStrategyTwo.getInstance().generatePlayerCardsPaths(ptc, dealerCard), dealerCard);
				total +=result;
//				System.out.println("StartValue :" + ptc.getValue() +" vs DealerCard : "+dealerCard +"\tROI: "+ result);
				System.out.println(ptc +" # "+ dealerCard + " : "+result);
			}
			System.out.println("T: " +total);
			
			big += total;
			calcROIMap.addValue(ptc.getStartValue(), total);
		}
		System.out.println(big);
		calcROIMap.printSelf();
	}
	
	public static void printStrategyROI(){
		List<OneCalcPackage> oneCalcPackages = new ArrayList<OneCalcPackage>();
		Collection<PlayerCardsPathValue> ptcs =  PlayerStartTwoCards.generatePlayerTwoStartCards();
		for (PlayerCardsPathValue ptc : ptcs) {
			for (Card dealerCard : Card.values()) {
				if(dealerCard == Card.One1) continue;
				oneCalcPackages.add(new OneCalcPackage(ptc, dealerCard));
			}
		}
		CalcStrategyProfitMachine calcStrategyProfitMachine = new CalcStrategyProfitMachine(PlayerStrategyTwo.getInstance());
		CalcROIMap calcROIMap = calcStrategyProfitMachine.calcROIofPlayerHands(oneCalcPackages);
		calcROIMap.printSelf();
	}
	
	public static void main(String[] args){
//		Collection<PlayerCardsPathValue> x = generatePlayerCardsPaths(new PlayerCardsPathValue(StartValue.Seventeen), Card.Seven7);
//		HelloWorld.print(x) ;
		
//		printAllStartValueVSDealer();
//		printReallyTwoCardsVSDealer();
		
		printStrategyROI();
	}
	
}
