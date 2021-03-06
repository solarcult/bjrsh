package shil.bjrsh.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DealerCardsPathValue implements CardsPathValue{
	
	public static int IllegalCards = -1;
	
	private int value;
	private List<Card> cards;
	
	public DealerCardsPathValue(){
		value = 0;
		cards = new ArrayList<Card>();
	}
	
	public DealerCardsPathValue(Card card){
		this();
		addCard(card);
	}
	
	public DealerCardsPathValue(DealerCardsPathValue cardsPathValue){
		value = cardsPathValue.getValue();
		cards = new ArrayList<Card>(cardsPathValue.getCards());
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
				if(reduceV < BlackJackInfo.BlackJack){
					value = IllegalCards;
				}
			}
		}
		return value;
	}
	
	public boolean isValid(){
		//判断A当11是否会爆掉,爆掉这副牌无效,因为庄家会把A当1,则在其他组合里会有.
		boolean isElevenOk = getValue() != IllegalCards;
		//判断是否某一种类型的牌超出了范围.
		boolean notOutofCards = notOutofCards();
		//规定,庄家如果到17点必须停止,所以当A为11时,庄家必须停止.当A本来当做1但当做11时需停止时,这种情况在A=11时,枚举已经包含,所以这里表现为不合法.
		boolean isAbe11Conitnue = isAbe11Conitnue();
		return isElevenOk && notOutofCards && isAbe11Conitnue; 
	}
	
	/**
	 * 7,One1 代表8的时候必须停止,这个组合没有意义，因为另外的那个7,Eleven的组合会存在，代表18.
	 * @return
	 */
	private boolean isAbe11Conitnue(){
		//规定,庄家如果到17点必须停止,所以当A为11时,庄家必须停止.
		boolean isAbe11Conitnue = true;
		int tempValue = 0;
		for(Card card : this.cards){
			tempValue += card.getValue();
		}
		
		//check if One1 become 11 ,if reach the DealerStop ,if yes, should ignore because we have A is Eleven version. 
		if(this.cards.contains(Card.One1)){
			tempValue -= Card.One1.getValue();
			tempValue += Card.Eleven.getValue();
			if(tempValue >= BlackJackInfo.DealerStop && tempValue <= BlackJackInfo.BlackJack){
				isAbe11Conitnue = false;
			}
		}
			
		return isAbe11Conitnue;
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
		result = prime * result
		+ ((cards == null) ? 0 : replaceEleven2One().hashCode());
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
		DealerCardsPathValue other = (DealerCardsPathValue) obj;
		//定制化的
		if (cards == null) {
			if (other.cards != null)
				return false;
		} else if (!replaceEleven2One().equals(other.replaceEleven2One()))
			return false;
		if (value != other.value)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CardsPathValue [cards=" + cards + ", value=" + value
				+ ", isValid()=" + isValid() + ", getCardsMap()=" + getCardsMap() + ", prob()=" + prob()
				+ "]";
	}
	
}
