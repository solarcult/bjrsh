package shil.bjrsh.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import shil.bjrsh.about13.DeckSet;

public class ProbUtil {
	
	public static int Ndeck = 6;
	public static int TotalOneSameCards = Ndeck * DeckSet.OneSameCardInOneDeck;
	public static int TotalCards = Ndeck * DeckSet.DeckCards;

	//计算出现这副牌组合的概率
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
	
	//将1和11转换为相同的A(1),并映射成map
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
		DealerCardsPathValue cardsPathValue = new DealerCardsPathValue(Card.One1);
		cardsPathValue.addCard(Card.Eleven);
		cardsPathValue.addCard(Card.Eight8);
		
		DealerCardsPathValue cardsPathValue2 = new DealerCardsPathValue(Card.One1);
		cardsPathValue2.addCard(Card.One1);
		cardsPathValue2.addCard(Card.Eight8);
		
		DealerCardsPathValue cardsPathValue3 = new DealerCardsPathValue(Card.One1);
		cardsPathValue3.addCard(Card.Eight8);
		cardsPathValue3.addCard(Card.One1);
		
		PlayerCardsPathValue playerCardsPathValue = new PlayerCardsPathValue(StartValue.One);
		playerCardsPathValue.addCard(Card.One1);
		playerCardsPathValue.addCard(Card.Eleven);
		playerCardsPathValue.addCard(Card.Eight8);
		playerCardsPathValue.addCard(Card.Eleven);
		
		PlayerCardsPathValue playerCardsPathValue2 = new PlayerCardsPathValue(StartValue.One);
		playerCardsPathValue2.addCard(Card.One1);
		playerCardsPathValue2.addCard(Card.One1);
		playerCardsPathValue2.addCard(Card.Eight8);
		playerCardsPathValue2.addCard(Card.One1);
		
		PlayerCardsPathValue playerCardsPathValue3 = new PlayerCardsPathValue(StartValue.One);
		playerCardsPathValue3.addCard(Card.One1);
		playerCardsPathValue3.addCard(Card.Eight8);
		playerCardsPathValue3.addCard(Card.One1);
		
		Collection<DealerCardsPathValue> a = new HashSet<DealerCardsPathValue>();
		a.add(cardsPathValue);
		a.add(cardsPathValue2);
		a.add(cardsPathValue3);
		System.out.println(a.size());
		System.out.println(cardsPathValue.equals(cardsPathValue2));
		System.out.println(cardsPathValue.hashCode());
		System.out.println(cardsPathValue2.hashCode());
		System.out.println(cardsPathValue3.hashCode());
		System.out.println(cardsPathValue.isValid());
		System.out.println(cardsPathValue2.isValid());
		System.out.println(cardsPathValue3.isValid());
		
		System.out.println(calcProb(cardsPathValue.getCards()));
		System.out.println(calcProb(cardsPathValue2.getCards()));
		
		Collection<PlayerCardsPathValue> b = new HashSet<PlayerCardsPathValue>();
		b.add(playerCardsPathValue);
		b.add(playerCardsPathValue2);
		b.add(playerCardsPathValue3);
		System.out.println(playerCardsPathValue.hashCode());
		System.out.println(playerCardsPathValue2.hashCode());
		System.out.println(playerCardsPathValue3.hashCode());
		System.out.println(playerCardsPathValue.equals(playerCardsPathValue2));
		System.out.println(b.size());
	}
	
}
