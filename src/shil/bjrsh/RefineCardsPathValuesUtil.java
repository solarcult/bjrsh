package shil.bjrsh;

import java.util.Collection;
import java.util.HashSet;

public class RefineCardsPathValuesUtil {

	public static Collection<CardsPathValue> refineSameCardsPathValues(Collection<CardsPathValue> cardsPathValues){
		return new HashSet<CardsPathValue>(cardsPathValues);
	}
	
	public static void main(String[] args){
		System.out.println(DealerCards.StartTwo.size());
		System.out.println(refineSameCardsPathValues(DealerCards.StartTwo).size());
	}
}
