package shil.bjrsh.analyze;

import java.util.Collection;

import shil.bjrsh.HelloWorld;
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
	public static Collection<DealerCardsPathValue> StartEleven = GenerateCardsUtil.generateDealerCards(new DealerCardsPathValue(Card.Eleven));
	
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
	
	public static void main(String[] args){
		HelloWorld.print(DealerCards.StartSix);
	}
}
