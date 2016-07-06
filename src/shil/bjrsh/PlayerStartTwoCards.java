package shil.bjrsh;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.math3.stat.Frequency;

import shil.bjrsh.core.Card;
import shil.bjrsh.core.CardsPathValue;
import shil.bjrsh.core.PlayerCardsPathValue;
import shil.bjrsh.core.StartValue;

public class PlayerStartTwoCards {
	
	public static Collection<PlayerCardsPathValue> generatePlayerStartValues(){
		List<PlayerCardsPathValue> allType = new ArrayList<PlayerCardsPathValue>();
		for(StartValue startValue : StartValue.values()){
			allType.add(new PlayerCardsPathValue(startValue));
		}
		return allType;
	}
	
	public static Collection<CardsPathValue> generatePlayerTwoStartCards(){
		List<CardsPathValue> allCombination = new ArrayList<CardsPathValue>();
		for(Card one: Card.values()){
			for(Card two : Card.values()){
				CardsPathValue cardsPathValue = new CardsPathValue();
				cardsPathValue.addCard(one);
				cardsPathValue.addCard(two);
				if(cardsPathValue.isValid())
					allCombination.add(cardsPathValue);
			}
		}
		return allCombination;
	}
	
	public static void analyzeStartTwoCardsPercent(){
		Collection<CardsPathValue> twocards = generatePlayerTwoStartCards();
		Frequency frequency = new Frequency();
		for(CardsPathValue cardsPathValue : twocards){
			frequency.addValue(cardsPathValue.getValue());
		}
		System.out.println(frequency);
	}

	public static void main(String[] args) {
//		HelloWorld.printCollection(RefineCardsPathValuesUtil.refineSameCardsPathValues(generatePlayerStartValues()));
//		HelloWorld.printCollection(generatePlayerTwoStartCards());
		analyzeStartTwoCardsPercent();
	}

}
