package shil.bjrsh.strategy.two;

import shil.bjrsh.core.Card;
import shil.bjrsh.core.StartValue;
import shil.bjrsh.strategy.PlayerAction;

public class PlayerStrategy implements Comparable<PlayerStrategy>{

	private StartValue startValue;
	private Card dealercard;
	private PlayerAction playerAction;
	
	protected PlayerStrategy(StartValue startValue,Card dealerCard,PlayerAction playerAction){
		this.startValue = startValue;
		this.dealercard = dealerCard;
		this.playerAction = playerAction;
	}
	
	protected PlayerStrategy(StartValue startValue,Card dealerCard){
		this.startValue = startValue;
		this.dealercard = dealerCard;
	}
	
	public static PlayerStrategy builderOne(StartValue startValue,Card dealerCard){
		return new PlayerStrategy(startValue, dealerCard);
	}

	public PlayerAction getPlayerAction() {
		return playerAction;
	}

	public void setPlayerAction(PlayerAction playerAction) {
		this.playerAction = playerAction;
	}

	public StartValue getStartValue() {
		return startValue;
	}

	public Card getDealercard() {
		return dealercard;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dealercard == null) ? 0 : dealercard.hashCode());
		result = prime * result
				+ ((startValue == null) ? 0 : startValue.hashCode());
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
		PlayerStrategy other = (PlayerStrategy) obj;
		if (dealercard != other.dealercard)
			return false;
		if (startValue != other.startValue)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PlayerStrategy [startValue=" + startValue + ", dealercard="
				+ dealercard + ", playerAction=" + playerAction + "]";
	}

	@Override
	public int compareTo(PlayerStrategy o) {
		if(this.startValue.getValue() < o.startValue.getValue()) 
			return -1;
		else if(this.startValue.getValue() > o.startValue.getValue()){
			return 1;
		}
		else{
			if(this.dealercard.getValue() < o.dealercard.getValue())
				return -1;
			else if(this.dealercard.getValue() > o.dealercard.getValue())
				return 1;
			return 0;
		}
	}
	
}
