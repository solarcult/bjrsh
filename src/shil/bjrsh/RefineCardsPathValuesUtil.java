package shil.bjrsh;

import java.util.Collection;
import java.util.HashSet;

import shil.bjrsh.analyze.DealerCards;

public class RefineCardsPathValuesUtil {

	public static <T> Collection<T> refineSameCardsPathValues(Collection<T> cardsPathValues){
		return new HashSet<T>(cardsPathValues);
	}
	
	public static void main(String[] args){
		System.out.println(DealerCards.StartTwo.size());
		System.out.println(refineSameCardsPathValues(DealerCards.StartTwo).size());
	}
}
