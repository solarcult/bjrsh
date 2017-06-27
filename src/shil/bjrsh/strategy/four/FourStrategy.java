package shil.bjrsh.strategy.four;

import java.util.Collection;
import java.util.HashSet;

import shil.bjrsh.core.BlackJackInfo;
import shil.bjrsh.core.Card;
import shil.bjrsh.core.PlayerCardsPathValue;
import shil.bjrsh.core.StartValue;
import shil.bjrsh.strategy.PlayerAction;
import shil.bjrsh.strategy.PlayerStrategyMatrix;

public abstract class FourStrategy {

	private PlayerStrategyMatrix nmSM;
	private PlayerStrategyMatrix scSM;
	private PlayerStrategyMatrix waSM;
	
	public FourStrategy(PlayerStrategyMatrix nmSM,PlayerStrategyMatrix scSM,PlayerStrategyMatrix waSM){
		this.nmSM = nmSM;
		this.scSM = scSM;
		this.waSM = waSM;
	}
	
	public Collection<PlayerCardsPathValue> generatePlayerCardsPaths(PlayerCardsPathValue playerCardsPathValue,Card dealerCard){
		
		Collection<PlayerCardsPathValue> playerCardsPathValues = new HashSet<PlayerCardsPathValue>();
		
		if(!playerCardsPathValue.isValid()) return playerCardsPathValues;
		
		if(playerCardsPathValue.getAction() == PlayerAction.Init){
			//the first time reset action
			PlayerAction playerAction = null; 
			//detect player cards type
			if(playerCardsPathValue.getCards().contains(Card.One1) || playerCardsPathValue.getCards().contains(Card.Eleven)){
				
				if(playerCardsPathValue.getCards().get(0).equals(Card.One1)||playerCardsPathValue.getCards().get(0).equals(Card.Eleven)){
					
				}
				playerAction = waSM.getPlayerAction(playerCardsPathValue.getStartValue(),dealerCard).getStartAction();
				
			}else if(playerCardsPathValue.getCards().get(0)==playerCardsPathValue.getCards().get(1)){
				
			}
			
			{
				playerCardsPathValue.setAction(nmSM.getPlayerAction(playerCardsPathValue.getStartValue(),dealerCard).getStartAction());
			}
		}else if(playerCardsPathValue.getAction() == PlayerAction.TestSecondChoice){
			playerCardsPathValue.setAction(nmSM.getPlayerAction(playerCardsPathValue.getStartValue(),dealerCard).getThreeCardAction());
		}
		
		if(playerCardsPathValue.getValue() > BlackJackInfo.BlackJack){
			//burst, bye~
			playerCardsPathValues.add(playerCardsPathValue);
		}else if(playerCardsPathValue.getAction() == PlayerAction.Giveup || playerCardsPathValue.getAction() == PlayerAction.Stand){
			// we finish and pray
			playerCardsPathValues.add(playerCardsPathValue);
		}else if(playerCardsPathValue.getAction() == PlayerAction.DoubleDone){
			// good luck
			playerCardsPathValues.add(playerCardsPathValue);
		}else if(playerCardsPathValue.getAction() == PlayerAction.Double){
			//let double me up
			for (Card card : Card.values()) {
				PlayerCardsPathValue aNewPath = new PlayerCardsPathValue(playerCardsPathValue);
				aNewPath.addCard(card);
				aNewPath.setAction(PlayerAction.DoubleDone);
				if(aNewPath.isValid()) playerCardsPathValues.addAll(generatePlayerCardsPaths(aNewPath,dealerCard));
			}
		}else if(playerCardsPathValue.getAction() == PlayerAction.Hit){
//			System.out.println(playerCardsPathValue);
			// hit me hardly
			for (Card card : Card.values()) {
				PlayerCardsPathValue aNewPath = new PlayerCardsPathValue(playerCardsPathValue);
				aNewPath.addCard(card);
				if(aNewPath.isValid()){
					if(aNewPath.getValue() <= BlackJackInfo.BlackJack){
//						aNewPath.setAction(playerStrategyMatrix.getPlayerAction(StartValue.getOne(aNewPath.getValue()),dealerCard).getThreeCardAction());
					}
					playerCardsPathValues.addAll(generatePlayerCardsPaths(aNewPath,dealerCard));
				}
			}
		}else{
			//wtf
			throw new RuntimeException("help, please help me, where am i?");
		}
		
		return playerCardsPathValues;
	}
}
