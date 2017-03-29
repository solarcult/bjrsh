package shil.bjrsh.analyze;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import shil.bjrsh.core.CardsPathValue;
import shil.bjrsh.core.DealerCardsPathValue;
import shil.bjrsh.core.PlayerCardsPathValue;

public class AnalyzeCardsPathValue {
	
	public static Map<Integer,AnalyzeStatus> analyzeDealerCardsPathValue(Collection<DealerCardsPathValue> dealerCardsPathValues){
		return analyzeCardsPathValue(new ArrayList<CardsPathValue>(dealerCardsPathValues));
	}
	
	public static Map<Integer,AnalyzeStatus> analyzePlayerCardsPathValue(Collection<PlayerCardsPathValue> playerCardsPathValues){
		return analyzeCardsPathValue(new ArrayList<CardsPathValue>(playerCardsPathValues));
	}
	
	public static Map<Integer,AnalyzeStatus> analyzeCardsPathValue(Collection<CardsPathValue> cardsPathValues){
		double totalProb = 0;
		Map<Integer,AnalyzeStatus> valueMap = new TreeMap<>();
		for(CardsPathValue cpv : cardsPathValues){
			totalProb += cpv.prob();
			AnalyzeStatus as = valueMap.getOrDefault(cpv.getValue(), new AnalyzeStatus(cpv.getValue(),0d,0d,0d,0d,0d));
			as.setProb(as.getProb()+cpv.prob());
			valueMap.put(cpv.getValue(), as);
		}
		
		double totalPct = 0d;
		double tillProb = 0d;
		for(Entry<Integer, AnalyzeStatus> e : valueMap.entrySet()){
			AnalyzeStatus as = e.getValue();
			tillProb += as.getProb();
			as.setTillProb(tillProb);
			as.setPrecent(as.getProb()/totalProb);
			totalPct+=as.getPrecent();
			as.setTillPct(totalPct);
			as.setTotalProb(totalProb);
		}
		
		return valueMap;
	}
}
