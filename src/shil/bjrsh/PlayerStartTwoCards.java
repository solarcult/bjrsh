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
//			if(one == Card.One1) continue;
			for(Card two : Card.values()){
				if(two == Card.One1 || two == Card.Eleven) continue;
//				if(two == Card.Eleven) continue;
				//TODO 没有考虑两张一样牌的概率
				if(one == two) {
//					System.out.println(one+" "+two);
					continue;
				}
				PlayerCardsPathValue cardsPathValue = new PlayerCardsPathValue(one,two);
				if(cardsPathValue.isValid()) {
					System.out.println(one+" "+two);
					allCombination.add(cardsPathValue);
				}
			}
		}
		System.out.println(allCombination.size());
		return allCombination;
	}
	
	public static Collection<PlayerCardsPathValue> generatePlayerTwoStartCardsWithoutA(){
		Collection<PlayerCardsPathValue> allCombination = new HashSet<PlayerCardsPathValue>();
		for(Card one: Card.values()){
			//TODO 没有考虑AX牌的概率
			if(one == Card.One1 || one == Card.Eleven) continue;
			for(Card two : Card.values()){
				if(two == Card.One1 || two == Card.Eleven) continue;
				PlayerCardsPathValue cardsPathValue = new PlayerCardsPathValue(one,two);
				if(cardsPathValue.isValid()) {
					allCombination.add(cardsPathValue);
				}
			}
		}
		System.out.println(allCombination.size());
		return allCombination;
	}
	
	public static void analyzeStartTwoCardsPercent(){
		Collection<PlayerCardsPathValue> twocards = generatePlayerTwoStartCards();
		Frequency frequency = new Frequency();
		for(PlayerCardsPathValue cardsPathValue : twocards){
			frequency.addValue(cardsPathValue.getValue());
		}
		frequency.incrementValue(1, 20);//AX牌
		frequency.incrementValue(0, 12);//成对出现
		System.out.println(frequency);
	}
	
	public static void main(String[] args) {
//		HelloWorld.printCollection(RefineCardsPathValuesUtil.refineSameCardsPathValues(generatePlayerStartValues()));
//		HelloWorld.printCollection(generatePlayerTwoStartCards());
		analyzeStartTwoCardsPercent();
//		System.out.println(generatePlayerTwoStartCards().size());
	}

}
