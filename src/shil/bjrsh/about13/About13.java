package shil.bjrsh.about13;

import org.apache.commons.math3.stat.Frequency;

import shil.bjrsh.core.CalcROIMap;
import shil.bjrsh.core.Card;
import shil.bjrsh.core.PlayerCardsPathValue;
import shil.bjrsh.core.StartValue;

public class About13 {

	public static void standardAnaylze13(){
		Frequency tfrequency = new Frequency();
		Frequency less13 = new Frequency();
		Frequency is13 = new Frequency();
		Frequency great13 = new Frequency();
		for(Card one: Card.values()){
			if(one == Card.Eleven) continue;
			for(Card two : Card.values()){
				if(two == Card.Eleven) continue;
				PlayerCardsPathValue cardsPathValue = new PlayerCardsPathValue(one,two);
				int value = one.getValue()+two.getValue();
				if(cardsPathValue.isValid()) {
					tfrequency.addValue(value);
					if(value < 13 ){
						less13.addValue(one.getValue());
						less13.addValue(two.getValue());
					}else if(value == 13){
						is13.addValue(one.getValue());
						is13.addValue(two.getValue());
					}else if(value > 13){
						great13.addValue(one.getValue());
						great13.addValue(two.getValue());
					}
				}else{
					System.out.println(one);
					System.out.println(two);
					throw new RuntimeException("why i am here");
				}
			}
		}
		System.out.println("less:\n"+less13);
		System.out.println("is:\n"+is13);
		System.out.println("great:\n"+great13);
		System.out.println("total:\n"+tfrequency);
	}
	
	public static void adjust13InDeckSet(DeckSet deckSet){
		CalcROIMap total = new CalcROIMap();
		CalcROIMap less13 = new CalcROIMap();
		CalcROIMap is13 = new CalcROIMap();
		CalcROIMap great13 = new CalcROIMap();
		for(Card one: Card.values()){
			if(one == Card.Eleven) continue;
			for(Card two : Card.values()){
				if(two == Card.Eleven) continue;
				PlayerCardsPathValue cardsPathValue = new PlayerCardsPathValue(one,two);
				int value = one.getValue()+two.getValue();
				if(cardsPathValue.isValid()) {
					total.addValue(StartValue.getOne(value), deckSet.getOneCardProb(one)*deckSet.getOneCardProb(two));
					if(value < 13 ){
						less13.addValue(StartValue.getOne(value), deckSet.getOneCardProb(one)*deckSet.getOneCardProb(two));
					}else if(value == 13){
						is13.addValue(StartValue.getOne(value), deckSet.getOneCardProb(one)*deckSet.getOneCardProb(two));
					}else if(value > 13){
						great13.addValue(StartValue.getOne(value), deckSet.getOneCardProb(one)*deckSet.getOneCardProb(two));
					}
				}else{
					System.out.println(one);
					System.out.println(two);
					throw new RuntimeException("why i am here");
				}
			}
		}
		System.out.println("\nless13");
		less13.print13Staff();
		System.out.println("\nis13");
		is13.print13Staff();
		System.out.println("\ngreat13");
		great13.print13Staff();
//		System.out.println("\ntotal");
//		total.print13Staff();
	}
	
	public static void missXSmallCard(int times){
		DeckSet deckSet = DeckSet.build6DeckSet();
		for(Card card : Card.values()){
			if(card.getValue()<=6 && card.getValue()>=2){
				deckSet.usedCards(card, times);
			}
		}
		adjust13InDeckSet(deckSet);
	}
	
	public static void missXBigCard(int times){
		DeckSet deckSet = DeckSet.build6DeckSet();
		for(Card card : Card.values()){
			if(card.getValue()==10){
				deckSet.usedCards(card, times);
			}
		}
		adjust13InDeckSet(deckSet);
	}
	
	public static void main(String[] args) {
//		standardAnaylze13();
		missXSmallCard(4);
//		missXBigCard(4);
	}

}
