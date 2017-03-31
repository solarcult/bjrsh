package shil.bjrsh.strategy.two;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import shil.bjrsh.CalcStrategyProfitMachine;
import shil.bjrsh.HelloWorld;
import shil.bjrsh.OneCalcPackage;
import shil.bjrsh.PlayerStartTwoCards;
import shil.bjrsh.analyze.AnalyzeCardsPathValue;
import shil.bjrsh.analyze.PlayersVSDealersResultChanceProb;
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
			playerStrategyTwo = new PlayerStrategyTwo(new PSM14Hit15Hit16Stand());
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
				double result = ProfitUtil.calcStarthandPossibleFuturesVSDealerCardInReturn(PlayerStrategyTwo.getInstance().generatePlayerCardsPaths(new PlayerCardsPathValue(ptc), dealerCard), dealerCard);
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
				oneCalcPackages.add(new OneCalcPackage(new PlayerCardsPathValue(ptc), dealerCard));
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
				oneCalcPackages.add(new OneCalcPackage(new PlayerCardsPathValue(ptc), dealerCard));
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
				oneCalcPackages.add(new OneCalcPackage(new PlayerCardsPathValue(ptc), dealerCard));
			}
		}
		CalcStrategyProfitMachine calcStrategyProfitMachine = new CalcStrategyProfitMachine(PlayerStrategyTwo.getInstance());
		CalcROIMap calcROIMap = calcStrategyProfitMachine.calcROIofPlayerHands(oneCalcPackages);
		calcROIMap.printROI();
	}
	
	public static void thisIsIt(){
		Collection<PlayerCardsPathValue> allinone = new ArrayList<>();
		
		Collection<PlayerCardsPathValue> ptcs =  PlayerStartTwoCards.generatePlayerTwoStartCardsWithoutA();
		for (PlayerCardsPathValue ptc : ptcs) {
			for (Card dealerCard : Card.values()) {
				if(dealerCard == Card.One1) continue;
				Collection<PlayerCardsPathValue> pcs = PlayerStrategyTwo.getInstance().generatePlayerCardsPaths(new PlayerCardsPathValue(ptc), dealerCard);
				System.out.println(ptc.getCards().get(0)+" "+ptc.getCards().get(1)+" : " + dealerCard.getValue() + " = "+pcs.size());
//				HelloWorld.print(pcs);
				allinone.addAll(pcs);
			}
		}
		System.out.println(allinone.size());
		HelloWorld.printMap(AnalyzeCardsPathValue.analyzePlayerCardsPathValue(allinone));
	}
	
	public static void thatIsIt(){
		for (StartValue startValue : StartValue.values()) {
			if (startValue.getValue() < StartValue.Four.getValue()) continue;
			for (Card dealerCard : Card.values()) {
				if(Card.One1 == dealerCard || dealerCard == Card.JJJ || dealerCard == Card.QQQ || dealerCard == Card.KKK) continue;
				Collection<PlayerCardsPathValue> players = PlayerStrategyTwo.getInstance().generatePlayerCardsPaths(new PlayerCardsPathValue(startValue), dealerCard);
				double[] x = PlayersVSDealersResultChanceProb.calcPlayerVSDealerAnaylzeStatus(players, dealerCard);
//				if(startValue==StartValue.Nine && dealerCard==Card.Three3){
//					HelloWorld.print(players);
//				}
				System.out.println("Player : "+startValue +" Dealer : "+dealerCard+" = "+ (x[0]-x[2])*100);
//				HelloWorld.printDoubleWDL();
//				HelloWorld.print2DoubleWDL(PlayersVSDealersResultChanceProb.calcPlayerVSDealerAnaylzeStatus(players, dealerCard), PlayersVSDealersResultChanceProb.calcPlayerdVSDealerProbs(players, dealerCard));
			}
		}
	}
	
	public static void main(String[] args){
//		Collection<PlayerCardsPathValue> x = generatePlayerCardsPaths(new PlayerCardsPathValue(StartValue.Seventeen), Card.Seven7);
//		HelloWorld.print(x) ;
		
//		printAllStartValueVSDealer();
//		printReallyTwoCardsVSDealer();
		
//		printStrategyROI();
		
		printStrategyROIwithoutA();
//		printStrategyROIwithoutAStartingOn(StartValue.Six);
		
//		Collection<PlayerCardsPathValue> pcs = PlayerStrategyTwo.getInstance().generatePlayerCardsPaths(new PlayerCardsPathValue(Card.Eight8,Card.Two2), Card.Six6);
//		for(PlayerCardsPathValue p : pcs){
//			if(p.getValue() == 11){
//				System.out.println(p);
//			}
//		}
//		System.out.println(pcs.size());
		
//		HelloWorld.printMapPrecent(AnalyzeCardsPathValue.analyzePlayerCardsPathValue(pcs));
		
//		PlayerCardsPathValue x = new PlayerCardsPathValue(Card.Seven7,Card.Two2);
//		x.addCard(Card.One1);
//		x.setAction(PlayerAction.DoubleDone);
//		System.out.println(x.isValid());
		
		
//		Collection<PlayerCardsPathValue> t = PlayerStrategyTwo.getInstance().generatePlayerCardsPaths(new PlayerCardsPathValue(Card.Eight8,Card.Seven7), Card.Seven7);
//		HelloWorld.print(t);
//		HelloWorld.printMapPrecent(AnalyzeCardsPathValue.analyzePlayerCardsPathValue(t));
		
//		thisIsIt();
//		thatIsIt();
	}
	
}
