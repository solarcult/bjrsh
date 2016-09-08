package shil.bjrsh.about13;

import java.util.HashMap;
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
	
	public static DeckSet buildXDeckSet(int x){
		return new DeckSet(x);
	}
	
	public static DeckSet build6DeckSet(){
		return buildXDeckSet(6);
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
	
	public void usedCards(Card card,int times){
		if(Card.Eleven == card) card = Card.One1;
		int leftCardNumber = this.cardsLeftNumberMap.get(card) - times;
		if(leftCardNumber < 0) throw new RuntimeException(card +" Out of Cards in Decks");
		this.cardsLeftNumberMap.put(card, leftCardNumber);
		totalCards -= times;
	}
	
	public double getOneCardProb(Card card){
		return (double) 10 * cardsLeftNumberMap.get(card) / totalCards;
	}
	
	public static void main(String[] args){
		DeckSet decks = DeckSet.build6DeckSet();
		decks.usedCards(Card.Eight8, 2);
		System.out.println(decks.getOneCardProb(Card.Eight8));
		System.out.println((double)(4*6-2)/(52*6-2));
	}
}
