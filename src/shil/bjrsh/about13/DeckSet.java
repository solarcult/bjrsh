package shil.bjrsh.about13;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shil.bjrsh.core.Card;

public class DeckSet {
	
	public static int OneSameCardInOneDeck = 4;
	public static int DeckCards = 52;
	
	private int ndeck;
	private int totalCards;
	private Map<Card,Integer> cardsLeftNumberMap;
	
	private DeckSet(int ndeck){
		this.ndeck = ndeck;
		totalCards = ndeck * DeckCards;
		int oneSameCards = ndeck * OneSameCardInOneDeck;
		cardsLeftNumberMap = new HashMap<Card, Integer>();
		for(Card card : Card.values()){
			if(Card.Eleven == card) continue;
			cardsLeftNumberMap.put(card, oneSameCards);
		}
	}

	public int getNdeck() {
		return ndeck;
	}

	public int getTotalCards() {
		return totalCards;
	}

	public Map<Card, Integer> getCardsLeftNumberMap() {
		return cardsLeftNumberMap;
	}
	
	public void used(Card card,int times){
		if(Card.Eleven == card) card = Card.One1;
		int leftCardNumber = this.cardsLeftNumberMap.get(card) - times;
		if(leftCardNumber < 0) throw new RuntimeException(card +" Out of Cards in Decks");
		this.cardsLeftNumberMap.put(card, leftCardNumber);
	}
	
	public double calcProb(List<Card> cards){
		return 0;
	}
}
