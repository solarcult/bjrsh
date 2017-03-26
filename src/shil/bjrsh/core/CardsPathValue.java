package shil.bjrsh.core;

import java.util.List;
import java.util.Map;

public interface CardsPathValue {
	public void addCard(Card card);
	public int getValue();
	public boolean isValid();
	public List<Card> getCards();
	public Map<Card,Integer> getCardsMap();
	public double prob();
}
