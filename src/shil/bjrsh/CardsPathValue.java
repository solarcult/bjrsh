package shil.bjrsh;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CardsPathValue{
	
	public static int IlleageCards = -1;
	
	private int value;
	private List<Card> cards;
	
	public CardsPathValue(){
		value = 0;
		cards = new ArrayList<Card>();
	}
	
	public CardsPathValue(Card card){
		this();
		addCard(card);
	}
	
	public CardsPathValue(CardsPathValue cardsPathValue){
		value = cardsPathValue.getValue();
		cards = new ArrayList<Card>(cardsPathValue.getCards());
	}
	
	
	public void addCard(Card card){
		value += card.getValue();
		cards.add(card);
	}
	
	public int getValue(){
		if(value > GenerateCardsUtil.BlackJack){
			//如果A做为11超过了21点,则这个A会被看成1,这种情况,在其他A当1的组合里应该已经存在了,所以此情形忽略掉.
			if(cards.contains(Card.Eleven)){
				int reduceV = value - 10;
				if(reduceV < GenerateCardsUtil.BlackJack){
					value = IlleageCards;
				}
			}
		}
		return value;
	}
	
	public Card getFirstInHand(){
		return cards.get(0);
	}
	
	public List<Card> getCards(){
		return cards;
	}
	
	public Map<Card,Integer> getCardsMap(){
		return ProbUtil.convertList2Map(cards);
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				//定制化的
				+ ((getCardsMap() == null) ? 0 : getCardsMap().hashCode());
		result = prime * result + value;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CardsPathValue other = (CardsPathValue) obj;
		//定制化的
		if (getCardsMap() == null) {
			if (other.getCardsMap() != null)
				return false;
		} else if (!getCardsMap().equals(other.getCardsMap()))
			return false;
		if (value != other.value)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CardsPathValue [firstInHand=" + getFirstInHand() + ", cards=" + cards + "]" + ", value=" + getValue() + ", cardsMap=" + getCardsMap();
	}
	
	
}
