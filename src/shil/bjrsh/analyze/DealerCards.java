package shil.bjrsh.analyze;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.commons.math3.stat.Frequency;

import shil.bjrsh.core.Card;
import shil.bjrsh.core.DealerCardsPathValue;
import shil.bjrsh.core.GenerateCardsUtil;

public class DealerCards {
	
	public static Collection<DealerCardsPathValue> StartOne = GenerateCardsUtil.generateDealerCards(new DealerCardsPathValue(Card.One1));
	public static Collection<DealerCardsPathValue> StartTwo = GenerateCardsUtil.generateDealerCards(new DealerCardsPathValue(Card.Two2));
	public static Collection<DealerCardsPathValue> StartThree = GenerateCardsUtil.generateDealerCards(new DealerCardsPathValue(Card.Three3));
	public static Collection<DealerCardsPathValue> StartFour = GenerateCardsUtil.generateDealerCards(new DealerCardsPathValue(Card.Four4));
	public static Collection<DealerCardsPathValue> StartFive = GenerateCardsUtil.generateDealerCards(new DealerCardsPathValue(Card.Five5));
	public static Collection<DealerCardsPathValue> StartSix = GenerateCardsUtil.generateDealerCards(new DealerCardsPathValue(Card.Six6));
	public static Collection<DealerCardsPathValue> StartSeven = GenerateCardsUtil.generateDealerCards(new DealerCardsPathValue(Card.Seven7));
	public static Collection<DealerCardsPathValue> StartEight = GenerateCardsUtil.generateDealerCards(new DealerCardsPathValue(Card.Eight8));
	public static Collection<DealerCardsPathValue> StartNine = GenerateCardsUtil.generateDealerCards(new DealerCardsPathValue(Card.Nine9));
	public static Collection<DealerCardsPathValue> StartTen = GenerateCardsUtil.generateDealerCards(new DealerCardsPathValue(Card.Ten));
	public static Collection<DealerCardsPathValue> StartJ = GenerateCardsUtil.generateDealerCards(new DealerCardsPathValue(Card.JJJ));
	public static Collection<DealerCardsPathValue> StartQ = GenerateCardsUtil.generateDealerCards(new DealerCardsPathValue(Card.QQQ));
	public static Collection<DealerCardsPathValue> StartK = GenerateCardsUtil.generateDealerCards(new DealerCardsPathValue(Card.KKK));
	public static Collection<DealerCardsPathValue> StartEleven = GenerateCardsUtil.generateDealerACards();
	
	public static Collection<DealerCardsPathValue> fetchDealerCards(Card card){
		switch(card){
			case One1:
				return StartOne;
			case Two2:
				return StartTwo;
			case Three3:
				return StartThree;
			case Four4:
				return StartFour;
			case Five5:
				return StartFive;
			case Six6:
				return StartSix;
			case Seven7:
				return StartSeven;
			case Eight8:
				return StartEight;
			case Nine9: 
				return StartNine;
			case Ten:
				return StartTen;
			case JJJ:
				return StartJ;
			case QQQ:
				return StartQ;
			case KKK:
				return StartK;
			case Eleven:
				return StartEleven;
			default:
				return null;
		}
	}
	
	@Deprecated 
	// replace by moreAccurateRate
	public static void printFrequency(Collection<DealerCardsPathValue> dealerPackage){
		Frequency frequency = new Frequency();
		for(DealerCardsPathValue onepath : dealerPackage){
			frequency.addValue(onepath.getValue());
		}
		System.out.println(frequency);
	}
	
	public static void moreAccurateRate(Collection<DealerCardsPathValue> dealerPackage){
		HashMap<Integer, Double> amap = new HashMap<Integer, Double>();
		for(DealerCardsPathValue onepath : dealerPackage){
			Double t = amap.get(onepath.getValue());
			if(t!=null){
				t+=onepath.prob();
				amap.put(onepath.getValue(), t);
			}else{
				amap.put(onepath.getValue(), onepath.prob());
			}
		}
		
		Frequency frequency = new Frequency();
		for(Entry<Integer, Double> e : amap.entrySet()){
			String x = String.valueOf(e.getValue()*100000);
			x = x.substring(0, x.indexOf("."));
			frequency.incrementValue(e.getKey(), Long.parseLong(x));
		}
		
		System.out.println(frequency);
	}
	
	public static void printAll(){
		for(Card card: Card.values()){
			if(card.getValue()>=2 && card.getValue()<=11 && card!=Card.JJJ && card!=Card.QQQ && card!=Card.KKK){
				System.out.println("Card:" + card.getValue());
				moreAccurateRate(fetchDealerCards(card));
			}
		}
	}
	
	public static void main(String[] args){
//		HelloWorld.print(DealerCards.StartSix);
//		printFrequency(StartTwo);
//		printAll();
		
		System.out.println(StartTwo.size());
		System.out.println(StartThree.size());
		System.out.println(StartFour.size());
		System.out.println(StartFive.size());
		System.out.println(StartSix.size());
		System.out.println(StartSeven.size());
		System.out.println(StartEight.size());
		System.out.println(StartNine.size());
		System.out.println(StartTen.size());
		System.out.println(StartJ.size());
		System.out.println(StartQ.size());
		System.out.println(StartK.size());
		System.out.println(StartEleven.size());

		
	}
}
