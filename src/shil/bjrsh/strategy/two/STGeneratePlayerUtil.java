package shil.bjrsh.strategy.two;

import java.util.Collection;
import java.util.HashSet;

import shil.bjrsh.HelloWorld;
import shil.bjrsh.core.BlackJackInfo;
import shil.bjrsh.core.Card;
import shil.bjrsh.core.PlayerCardsPathValue;
import shil.bjrsh.core.StartValue;
import shil.bjrsh.strategy.PlayerAction;

public class STGeneratePlayerUtil {

	public static Collection<PlayerCardsPathValue> generatePlayerCardsPaths(PlayerCardsPathValue playerCardsPathValue,Card dealerCard){
		Collection<PlayerCardsPathValue> playerCardsPathValues = new HashSet<PlayerCardsPathValue>();
		
		if(!playerCardsPathValue.isValid()) return playerCardsPathValues;
		
		if(playerCardsPathValue.getResult() == PlayerAction.Double){
			playerCardsPathValues.add(playerCardsPathValue);
			return playerCardsPathValues;
		}
		
		if(playerCardsPathValue.getValue() > BlackJackInfo.BlackJack){
			playerCardsPathValues.add(playerCardsPathValue);
			return playerCardsPathValues;
		}
		
		//check strategy two matrix and decide action
		PlayerStrategy playerStrategy = PlayerStrategyMatrix.getPlayerAction(StartValue.getOne(playerCardsPathValue.getValue()),dealerCard);
		PlayerAction action = null;
		if(playerCardsPathValue.getCards().isEmpty()){
			action = playerStrategy.getStartAction();
		}else{
			action = playerStrategy.getThreeCardAction();
		}
		playerCardsPathValue.setResult(action);
		
		//if result allow conitnue;
		if(playerCardsPathValue.getResult() == PlayerAction.Giveup || playerCardsPathValue.getResult() == PlayerAction.Stand){
			playerCardsPathValues.add(playerCardsPathValue);
			return playerCardsPathValues;
		}
		
		
		for (Card card : Card.values()) {
			PlayerCardsPathValue aNewPath = new PlayerCardsPathValue(playerCardsPathValue);
			aNewPath.addCard(card);
			playerCardsPathValues.addAll(generatePlayerCardsPaths(aNewPath,dealerCard));
		}
		
		return playerCardsPathValues;
	}
	
	public static void main(String[] args){
		Collection<PlayerCardsPathValue> x = generatePlayerCardsPaths(new PlayerCardsPathValue(StartValue.Eight), Card.Seven7);
		HelloWorld.print(x) ;
	}
	
}
