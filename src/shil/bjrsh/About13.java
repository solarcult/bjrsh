package shil.bjrsh;

import org.apache.commons.math3.stat.Frequency;

import shil.bjrsh.core.Card;
import shil.bjrsh.core.PlayerCardsPathValue;

public class About13 {

	public static void about13(){
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
	
	public static void main(String[] args) {
		about13();
	}

}
