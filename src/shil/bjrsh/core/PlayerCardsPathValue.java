package shil.bjrsh.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import shil.bjrsh.strategy.PlayerAction;

public class PlayerCardsPathValue {

	public static int IllegalCards = -1;
	
	private int value;
	private List<Card> cards;
	private StartValue startValue;
	private PlayerAction action;
	
	public PlayerCardsPathValue(StartValue startValue){
		this.startValue = startValue;
		value = startValue.getValue();
		cards = new ArrayList<Card>();
		action = PlayerAction.Init;
	}
	
	public PlayerCardsPathValue(Card ... _cards){
		int _startvalue = 0;
		cards = new ArrayList<Card>();
		for(Card card : _cards){
			_startvalue += card.getValue();
			addCard(card);
		}
		this.startValue = StartValue.getOne(_startvalue);
		action = PlayerAction.Init;
	}
	
	public PlayerCardsPathValue(PlayerCardsPathValue playerCardsPathValue){
		value = playerCardsPathValue.getValue();
		cards = new ArrayList<Card>(playerCardsPathValue.getCards());
		startValue = playerCardsPathValue.getStartValue();
		action = playerCardsPathValue.getAction();
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
		//这一条是为庄家制定的规则，为什么普通用户也需要？是不是之前的什么原因？过滤相同数据的原因吗？我在分析about13时，发现的这个bug? 暂时去掉试试。2016-Sep-06
		boolean isAbe11Conitnue = true;//isAbe11Conitnue();
		return isElevenOk && notOutofCards && isAbe11Conitnue; 
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
	
	private boolean isAbe11Conitnue(){
		//规定,庄家如果到17点必须停止,所以当A为11时,庄家必须停止.
		boolean isAbe11Conitnue = true;
		int tempValue = startValue.getValue();
		for(Card card : this.cards){
			if(Card.One1.equals(card)){
				int test = tempValue + Card.Eleven.getValue();
				if(test >= BlackJackInfo.DealerStop && test <= BlackJackInfo.BlackJack){
					isAbe11Conitnue = false;
					break;
				}
			}
			tempValue += card.getValue();
		}
		return isAbe11Conitnue;
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
		//特意处理AX类型的牌,去掉重复的情况
		StartValue tempStartValue = (StartValue.Eleven == startValue) ? StartValue.One : startValue;
		result = prime * result
				+ ((tempStartValue == null) ? 0 : tempStartValue.hashCode());
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
		//特意处理AX类型的牌,去掉重复的情况
		StartValue myStartValue = (StartValue.Eleven == startValue) ? StartValue.One : startValue;
		StartValue otherStartValue = (StartValue.Eleven == other.startValue) ? StartValue.One : other.startValue;
		if(myStartValue != otherStartValue)
			return false;
		if (value != other.value)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PlayerCardsPathValue [startValue=" + startValue + ", cards="
				+ cards + ", value=" + value + ", getCardsMap()="
				+ getCardsMap() + ", prob()=" + prob() + ", action=" + action + "]" ;
	}

	
	
	public PlayerAction getAction() {
		return action;
	}

	public void setAction(PlayerAction result) {
		this.action = result;
	}
}
