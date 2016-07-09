package shil.bjrsh.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlayerCardsPathValue {

	public static int IllegalCards = -1;
	
	private int value;
	private List<Card> cards;
	private StartValue startValue;
	
	public PlayerCardsPathValue(StartValue startValue){
		this.startValue = startValue;
		value = startValue.getValue();
		cards = new ArrayList<Card>();
	}
	
	public PlayerCardsPathValue(Card one,Card two){
		this.startValue = StartValue.getOne(one.getValue()+two.getValue());
		this.value = 0;
		cards = new ArrayList<Card>();
		cards.add(one);
		cards.add(two);
	}
	
	public PlayerCardsPathValue(PlayerCardsPathValue playerCardsPathValue){
		value = playerCardsPathValue.getValue();
		cards = new ArrayList<Card>(playerCardsPathValue.getCards());
		startValue = playerCardsPathValue.getStartValue();
	}
	
	public void addCard(Card card){
		value += card.getValue();
		cards.add(card);
	}
	
	public int getValue(){
		if(value > BlackJackInfo.BlackJack){
			//如果A做为11超过了21点,则这个A会被看成1,这种情况,在其他A当1的组合里应该已经存在了,所以此情形忽略掉.
			if(cards.contains(Card.Eleven)){
				int reduceV = value - 10;
				if(reduceV <= BlackJackInfo.BlackJack){
					value = IllegalCards;
				}
			}
		}
		return value;
	}
	
	public boolean isValid(){
		boolean isElevenOk = getValue() != IllegalCards;
		boolean notOutofCards = notOutofCards();
		return isElevenOk && notOutofCards; 
	}
	
	private boolean notOutofCards(){
		boolean notOutofCards = true;
		Map<Card,Integer> cardsMap = getCardsMap();
		for(Integer cards : cardsMap.values()){
			if(cards > ProbUtil.TotalOneSameCards){
				notOutofCards = false;
				System.out.println("this is it, out of cards: "+ cards);
				break;
			}
		}
		return notOutofCards;
	}
	
	public StartValue getStartValue(){
		return this.startValue;
	}
	
	public List<Card> getCards(){
		return cards;
	}
	
	public Map<Card,Integer> getCardsMap(){
		return ProbUtil.convertList2Map(cards);
	}

	public double prob(){
		return ProbUtil.calcProb(cards);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + 
				//定制化的
				+ ((cards == null) ? 0 : replaceEleven2One().hashCode());
		result = prime * result
				+ ((startValue == null) ? 0 : startValue.hashCode());
		result = prime * result + value;
		return result;
	}
	
	protected List<Card> replaceEleven2One(){
		List<Card> replaceEvelen2One = new ArrayList<Card>();
		for(Card card : cards){
			if(Card.Eleven.equals(card)){
				replaceEvelen2One.add(Card.One1);
			}else{
				replaceEvelen2One.add(card);
			}
		}
		return replaceEvelen2One;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayerCardsPathValue other = (PlayerCardsPathValue) obj;
		//定制化的
		if (cards == null) {
			if (other.cards != null)
				return false;
		} else if (!replaceEleven2One().equals(other.replaceEleven2One()))
			return false;
		if(startValue != other.startValue)
			return false;
		if (value != other.value)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PlayerCardsPathValue [startValue=" + startValue + ", cards="
				+ cards + ", value=" + value + ", getCardsMap()="
				+ getCardsMap() + ", prob()=" + prob() + "]";
	}
	
}
