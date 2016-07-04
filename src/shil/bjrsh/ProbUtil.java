package shil.bjrsh;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ProbUtil {
	
	public static int Ndeck = 6;
	public static int OneSameCardInOneDeck = 4;
	public static int DeckCards = 52;
	
	public static int TotalOneSameCards = Ndeck * OneSameCardInOneDeck;
	public static int TotalCards = Ndeck * DeckCards;

	public static double calcProb(CardsPathValue cardsPathValue){
		Map<Card,Integer> cardsMap = cardsPathValue.getCardsMap();
		for(Entry<Card, Integer> entry : cardsMap.entrySet()){
			
		}
		
		return 0;
	}
	
	public static Map<Card,Integer> convertList2Map(List<Card> cards){
		Map<Card,Integer> cardsMap = new HashMap<Card, Integer>();
		for(Card card : cards){
			//将11转换为 A
			if(Card.Eleven.equals(card)){
				card = Card.One;
			}
			Integer count = cardsMap.get(card);
			if(count == null){
				cardsMap.put(card, 1);
			}else{
				cardsMap.put(card, ++count);
			}
		}
		return cardsMap;
	}
	
	public static void main(String[] args){
		CardsPathValue cardsPathValue = new CardsPathValue(Card.One);
		cardsPathValue.addCard(Card.Eleven);
		cardsPathValue.addCard(Card.Eight);
		
		CardsPathValue cardsPathValue2 = new CardsPathValue(Card.One);
		cardsPathValue2.addCard(Card.One);
		cardsPathValue2.addCard(Card.Eight);
		
		Set<CardsPathValue> a = new HashSet<CardsPathValue>();
		a.add(cardsPathValue);
		a.add(cardsPathValue2);
		System.out.println(a.size());
		System.out.println(cardsPathValue.equals(cardsPathValue2));
		System.out.println(cardsPathValue);
		System.out.println(cardsPathValue2);
	}
	
}
