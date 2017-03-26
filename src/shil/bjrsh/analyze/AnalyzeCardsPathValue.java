package shil.bjrsh.analyze;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import shil.bjrsh.core.CardsPathValue;
import shil.bjrsh.core.DealerCardsPathValue;
import shil.bjrsh.core.PlayerCardsPathValue;

public class AnalyzeCardsPathValue {
	
	public static Map<Integer,Double> analyzeDealerCardsPathValue(Collection<DealerCardsPathValue> dealerCardsPathValues){
		return analyzeCardsPathValue(new ArrayList<CardsPathValue>(dealerCardsPathValues));
	}
	
	public static Map<Integer,Double> analyzePlayerCardsPathValue(Collection<PlayerCardsPathValue> playerCardsPathValues){
		return analyzeCardsPathValue(new ArrayList<CardsPathValue>(playerCardsPathValues));
	}
	
	public static Map<Integer,Double> analyzeCardsPathValue(Collection<CardsPathValue> cardsPathValues){
		double total = 0;
		Map<Integer,Double> valueMap = new HashMap<>();
		for(CardsPathValue cpv : cardsPathValues){
			double v = valueMap.getOrDefault(cpv.getValue(), 0d);
			valueMap.put(cpv.getValue(), v + cpv.prob());
			total += cpv.prob();
		}
		
		Map<Integer,Double> result = new HashMap<Integer, Double>();
		for(Entry<Integer, Double> e : valueMap.entrySet()){
			result.put(e.getKey(), e.getValue()/total);
		}
		
		return result;
	}
}
