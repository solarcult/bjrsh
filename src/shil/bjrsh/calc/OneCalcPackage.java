package shil.bjrsh.calc;

import shil.bjrsh.core.Card;
import shil.bjrsh.core.PlayerCardsPathValue;

public class OneCalcPackage {
	private PlayerCardsPathValue playerStartTwoCards;
	private Card dealerCard;
	
	public OneCalcPackage(PlayerCardsPathValue playerStartTwoCards, Card dealerCard){
		this.playerStartTwoCards = playerStartTwoCards;
		this.dealerCard = dealerCard;
	}

	public PlayerCardsPathValue getPlayerStartTwoCards() {
		return playerStartTwoCards;
	}

	public Card getDealerCard() {
		return dealerCard;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dealerCard == null) ? 0 : dealerCard.hashCode());
		result = prime
				* result
				+ ((playerStartTwoCards == null) ? 0 : playerStartTwoCards
						.hashCode());
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
		OneCalcPackage other = (OneCalcPackage) obj;
		if (dealerCard != other.dealerCard)
			return false;
		if (playerStartTwoCards == null) {
			if (other.playerStartTwoCards != null)
				return false;
		} else if (!playerStartTwoCards.equals(other.playerStartTwoCards))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OneCalcPackage [playerStartTwoCards=" + playerStartTwoCards
				+ ", dealerCard=" + dealerCard + "]";
	}

}
