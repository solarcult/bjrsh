package shil.bjrsh.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import shil.bjrsh.strategy.PlayerAction;

public class PlayerCardsPathValue implements CardsPathValue{

	public static int IllegalCards = -1;
	
	private int value;
	private List<Card> cards;
	private StartValue startValue;
	private PlayerAction action;
	private int betMutiV = 1;
	
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
		boolean isAbe11Conitune = isAbe11Conitune();
		return isElevenOk && notOutofCards && isAbe11Conitune; 
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
	
	/**
	 * 此处的意思为，如果用户为8,2,One1,则不把它当做合法的，因为另一个组合会有8,2,Eleven，所以忽略掉821这个组合.
	 * 如果用户把A当做11能达到17，则停止要牌，这是正常的选择。
	 * @return 这个组合是否合法继续
	 */
	private boolean isAbe11Conitune(){
		boolean isAbe11Conitnue = true;
		//此处没有使用tempValue=startvalue是因为如果用card...的初始函数会造成开始的cards会被加两遍,所以自己造数据测试,初始化时,需要注意.
		int tempValue = 0;
		for(Card card : this.cards){
			tempValue += card.getValue();
		}
		//如果A变成11可以达到17到21,则抛弃这个组合,因为另外一个11的组合会替代这个.
		if(this.cards.contains(Card.One1)){
			tempValue -= Card.One1.getValue();
			tempValue += Card.Eleven.getValue();
			if(tempValue >= BlackJackInfo.DealerStop && tempValue <= BlackJackInfo.BlackJack){
				isAbe11Conitnue = false;
			}
		}
			
		return isAbe11Conitnue;
	}
	
	/* 这个方法会造成颠覆性的代码重构,因为穷举时,我们不用再考虑A当11的情况了,自适应.
	private boolean isAbe11Conitune(){
		boolean isAbe11Conitnue = true;
		//此处没有使用tempValue=startvalue是因为如果用card...的初始函数会造成开始的cards会被加两遍,所以自己造数据测试,初始化时,需要注意.
		int tempValue = 0;
		for(Card card : this.cards){
			tempValue += card.getValue();
		}
		List<Card> nCards = new ArrayList<>(this.cards.size());
		//如果A变成11可以达到17到21,则抛弃这个组合,因为另外一个11的组合会替代这个.
		if(this.cards.contains(Card.One1)){
			tempValue -= Card.One1.getValue();
			tempValue += Card.Eleven.getValue();
			if(tempValue >= BlackJackInfo.DealerStop && tempValue <= BlackJackInfo.BlackJack){
				for(Card card : this.cards){
					if(card == Card.One1){
						nCards.add(Card.Eleven);
					}else{
						nCards.add(card);
					}
				}
			}
		}
			
		return isAbe11Conitnue;
	}
	*/
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
		//wrong understand, 原因如下行。原注释：[如果牌的列表顺序一致,则不用比value,一定一致的.但如果包含A的情况,value就会不一样,所以没有必要存在],注释存在 if只用在运算DealerVSPlayerAdvantage.makePlayerOneMoreVSNowDealerChange(),时,因为这是22,A会被解释成15而不是5. 26A为19而不是9
		//玩家用2,2起手hitnextOnecard时，第一局会出现2,2,1,和2,2,11并存的情况,实际现实中会用最大的做为起手,但如果算strategy时,必须把这行注释去掉,因为当2,2,A,7出现时,如果第一步只留下2,2,11,会造成2,2,A,7这个组合不会出现.
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
		//wrong understand, 原因如下行。原注释：[如果牌的列表顺序一致,则不用比value,一定一致的.但如果包含A的情况,value就会不一样,所以没有必要存在],注释存在 if只用在运算DealerVSPlayerAdvantage.makePlayerOneMoreVSNowDealerChange(),时,因为这是22,A会被解释成15而不是5. 26A为19而不是9
		//玩家用2,2起手hitnextOnecard时，第一局会出现2,2,1,和2,2,11并存的情况,实际现实中会用最大的做为起手,但如果算strategy时,必须把这行注释去掉,因为当2,2,A,7出现时,如果第一步只留下2,2,11,会造成2,2,A,7这个组合不会出现.
		if (value != other.value) return false;
		return true;
	}

	@Override
	public String toString() {
		return "PlayerCardsPathValue [startValue=" + startValue + ", cards="
				+ cards + ", value=" + value  + ", isValid()=" + isValid() + ", getCardsMap()="
				+ getCardsMap() + ", prob()=" + prob() + ", action=" + action + "]" ;
	}

	
	
	public PlayerAction getAction() {
		return action;
	}

	public void setAction(PlayerAction result) {
		this.action = result;
	}

	public int getBetMutiV() {
		return betMutiV;
	}

	public void setBetMutiV(int betMutiV) {
		this.betMutiV = betMutiV;
	}
	
}
