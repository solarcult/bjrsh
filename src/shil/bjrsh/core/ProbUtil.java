package shil.bjrsh.core;

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

	public static double calcProb(List<Card> cards){
		double prob = 1, usedcard = 0;
		Map<Card,Integer> cardsMap = convertList2Map(cards);
		for(Entry<Card,Integer> entry : cardsMap.entrySet()){
			for(int i=0;i<entry.getValue();i++){
				prob *= (double)(TotalOneSameCards-i)/(TotalCards-usedcard);
				usedcard++;
			}
		}
		return prob;
	}
	
	public static Map<Card,Integer> convertList2Map(List<Card> cards){
		Map<Card,Integer> cardsMap = new HashMap<Card, Integer>();
		for(Card card : cards){
			//将11转换为 A
			if(Card.Eleven.equals(card)){
				card = Card.One1;
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
		CardsPathValue cardsPathValue = new CardsPathValue(Card.One1);
		cardsPathValue.addCard(Card.Eleven);
		cardsPathValue.addCard(Card.Eight8);
		
		CardsPathValue cardsPathValue2 = new CardsPathValue(Card.One1);
		cardsPathValue2.addCard(Card.One1);
		cardsPathValue2.addCard(Card.Eight8);
		
		Set<CardsPathValue> a = new HashSet<CardsPathValue>();
		a.add(cardsPathValue);
		a.add(cardsPathValue2);
		System.out.println(a.size());
		System.out.println(cardsPathValue.equals(cardsPathValue2));
		System.out.println(cardsPathValue);
		System.out.println(cardsPathValue2);
		
		System.out.println(calcProb(cardsPathValue.getCards()));
		System.out.println(calcProb(cardsPathValue2.getCards()));
	}
	
}
