package shil.bjrsh.strategy.two;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import shil.bjrsh.CalcStrategyProfitMachine;
import shil.bjrsh.OneCalcPackage;
import shil.bjrsh.PlayerStartTwoCards;
import shil.bjrsh.core.CalcROIMap;
import shil.bjrsh.core.Card;
import shil.bjrsh.core.PlayerCardsPathValue;
import shil.bjrsh.core.ProfitUtil;
import shil.bjrsh.core.StartValue;
import shil.bjrsh.strategy.PlayerStrategyMatrix;
import shil.bjrsh.strategy.Strategy;

public class PlayerStrategyTwo extends Strategy{

	private static Strategy playerStrategyTwo;
	
	public PlayerStrategyTwo(PlayerStrategyMatrix playerStrategyMatrix) {
		super(playerStrategyMatrix);
	}	

	public static Strategy getInstance() {
		if(playerStrategyTwo == null){
			playerStrategyTwo = new PlayerStrategyTwo(new PlayerStrategyMatrixTwo());
		}
		return playerStrategyTwo;
	}
	
	@Deprecated 
	//replace by printReallyTwoCardsVSDealer
	public static void printAllStartValueVSDealer(){
		double big = 0;
		for (StartValue startValue : StartValue.values()) {
			if(startValue.getValue() < 4) continue;
			double total = 0;
			for (Card dealerCard : Card.values()) {
				if(dealerCard == Card.One1) continue;
				double result = ProfitUtil.calcStarthandPossibleFuturesVSDealerCardInReturn(PlayerStrategyTwo.getInstance().generatePlayerCardsPaths(new PlayerCardsPathValue(startValue), dealerCard), dealerCard);
				total +=result;
				System.out.println("StartValue :" + startValue +" vs DealerCard : "+dealerCard +"\tROI: "+ result);
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
		calcROIMap.printROI();
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
		calcROIMap.printROI();
	}
	
	public static void printStrategyROIwithoutA(){
		List<OneCalcPackage> oneCalcPackages = new ArrayList<OneCalcPackage>();
		Collection<PlayerCardsPathValue> ptcs =  PlayerStartTwoCards.generatePlayerTwoStartCardsWithoutA();
		for (PlayerCardsPathValue ptc : ptcs) {
			for (Card dealerCard : Card.values()) {
				if(dealerCard == Card.One1) continue;
				oneCalcPackages.add(new OneCalcPackage(ptc, dealerCard));
			}
		}
		CalcStrategyProfitMachine calcStrategyProfitMachine = new CalcStrategyProfitMachine(PlayerStrategyTwo.getInstance());
		CalcROIMap calcROIMap = calcStrategyProfitMachine.calcROIofPlayerHands(oneCalcPackages);
		calcROIMap.printROI();
	}
	
	public static void printStrategyROIwithoutAStartingOn(StartValue startValue){
		List<OneCalcPackage> oneCalcPackages = new ArrayList<OneCalcPackage>();
		Collection<PlayerCardsPathValue> ptcs =  PlayerStartTwoCards.generatePlayerTwoStartCardsWithoutA();
		for (PlayerCardsPathValue ptc : ptcs) {
			if(startValue.getValue() != ptc.getValue()) continue;
			for (Card dealerCard : Card.values()) {
				if(dealerCard == Card.One1) continue;
				oneCalcPackages.add(new OneCalcPackage(ptc, dealerCard));
			}
		}
		CalcStrategyProfitMachine calcStrategyProfitMachine = new CalcStrategyProfitMachine(PlayerStrategyTwo.getInstance());
		CalcROIMap calcROIMap = calcStrategyProfitMachine.calcROIofPlayerHands(oneCalcPackages);
		calcROIMap.printROI();
	}
	
	public static void main(String[] args){
//		Collection<PlayerCardsPathValue> x = generatePlayerCardsPaths(new PlayerCardsPathValue(StartValue.Seventeen), Card.Seven7);
//		HelloWorld.print(x) ;
		
//		printAllStartValueVSDealer();
//		printReallyTwoCardsVSDealer();
		
//		printStrategyROI();
		
		printStrategyROIwithoutA();
//		printStrategyROIwithoutAStartingOn(StartValue.Six);
	}
	
}
