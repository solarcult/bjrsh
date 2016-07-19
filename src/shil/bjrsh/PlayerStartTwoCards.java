package shil.bjrsh;

import java.util.Collection;
import java.util.HashSet;

import org.apache.commons.math3.stat.Frequency;

import shil.bjrsh.core.Card;
import shil.bjrsh.core.PlayerCardsPathValue;
import shil.bjrsh.core.StartValue;

public class PlayerStartTwoCards {
	
	public static Collection<PlayerCardsPathValue> generatePlayerStartValues(){
		Collection<PlayerCardsPathValue> allType = new HashSet<PlayerCardsPathValue>();
		for(StartValue startValue : StartValue.values()){
			allType.add(new PlayerCardsPathValue(startValue));
		}
		return allType;
	}
	
	public static Collection<PlayerCardsPathValue> generatePlayerTwoStartCards(){
		Collection<PlayerCardsPathValue> allCombination = new HashSet<PlayerCardsPathValue>();
		for(Card one: Card.values()){
			//TODO 没有考虑AX牌的概率
			if(one == Card.One1 || one == Card.Eleven) continue;
			for(Card two : Card.values()){
				if(two == Card.One1 || two == Card.Eleven) continue;
				//TODO 没有考虑两张一样牌的概率
				if(one == two) continue;
//				if(one.getValue()+two.getValue()==10){
				PlayerCardsPathValue cardsPathValue = new PlayerCardsPathValue(one,two);
				if(cardsPathValue.isValid()) allCombination.add(cardsPathValue);
//				}
			}
		}
		return allCombination;
	}
	
	public static void analyzeStartTwoCardsPercent(){
		Collection<PlayerCardsPathValue> twocards = generatePlayerTwoStartCards();
		Frequency frequency = new Frequency();
		for(PlayerCardsPathValue cardsPathValue : twocards){
			frequency.addValue(cardsPathValue.getValue());
		}
		frequency.incrementValue(1, 20);
		System.out.println(frequency);
	}

	public static void main(String[] args) {
//		HelloWorld.printCollection(RefineCardsPathValuesUtil.refineSameCardsPathValues(generatePlayerStartValues()));
//		HelloWorld.printCollection(generatePlayerTwoStartCards());
		analyzeStartTwoCardsPercent();
	}

}
