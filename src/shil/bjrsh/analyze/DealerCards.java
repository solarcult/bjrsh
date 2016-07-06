package shil.bjrsh.analyze;

import java.util.Collection;

import shil.bjrsh.core.Card;
import shil.bjrsh.core.CardsPathValue;
import shil.bjrsh.core.GenerateCardsUtil;

public class DealerCards {
	
	public static Collection<CardsPathValue> StartOne = GenerateCardsUtil.generateDealerCards(new CardsPathValue(Card.One1));
	public static Collection<CardsPathValue> StartTwo = GenerateCardsUtil.generateDealerCards(new CardsPathValue(Card.Two2));
	public static Collection<CardsPathValue> StartThree = GenerateCardsUtil.generateDealerCards(new CardsPathValue(Card.Three3));
	public static Collection<CardsPathValue> StartFour = GenerateCardsUtil.generateDealerCards(new CardsPathValue(Card.Four4));
	public static Collection<CardsPathValue> StartFive = GenerateCardsUtil.generateDealerCards(new CardsPathValue(Card.Five5));
	public static Collection<CardsPathValue> StartSix = GenerateCardsUtil.generateDealerCards(new CardsPathValue(Card.Six6));
	public static Collection<CardsPathValue> StartSeven = GenerateCardsUtil.generateDealerCards(new CardsPathValue(Card.Seven7));
	public static Collection<CardsPathValue> StartEight = GenerateCardsUtil.generateDealerCards(new CardsPathValue(Card.Eight8));
	public static Collection<CardsPathValue> StartNine = GenerateCardsUtil.generateDealerCards(new CardsPathValue(Card.Nine9));
	public static Collection<CardsPathValue> StartTen = GenerateCardsUtil.generateDealerCards(new CardsPathValue(Card.Ten));
	public static Collection<CardsPathValue> StartJ = GenerateCardsUtil.generateDealerCards(new CardsPathValue(Card.JJJ));
	public static Collection<CardsPathValue> StartQ = GenerateCardsUtil.generateDealerCards(new CardsPathValue(Card.QQQ));
	public static Collection<CardsPathValue> StartK = GenerateCardsUtil.generateDealerCards(new CardsPathValue(Card.KKK));
	public static Collection<CardsPathValue> StartEleven = GenerateCardsUtil.generateDealerCards(new CardsPathValue(Card.Eleven));
	
	public static Collection<CardsPathValue> fetchDealerCards(Card card){
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
}
