package shil.bjrsh.strategy;

import java.util.Collection;
import java.util.HashSet;

import shil.bjrsh.core.BlackJackInfo;
import shil.bjrsh.core.Card;
import shil.bjrsh.core.PlayerCardsPathValue;
import shil.bjrsh.core.StartValue;

public abstract class Strategy {
	
	private PlayerStrategyMatrix playerStrategyMatrix;
	
	public Strategy(PlayerStrategyMatrix playerStrategyMatrix){
		this.playerStrategyMatrix = playerStrategyMatrix;
	}

	/**
	 * 根据双方的牌情况来从PlayerStrategyMatrix中取得所要做的动作,从而得到这个决定的牌结果
	 * @param playerCardsPathValue 传入玩家的牌情况
	 * @param dealerCard 庄家起始牌情况
	 * @return 玩家牌结果
	 */
	public Collection<PlayerCardsPathValue> generatePlayerCardsPaths(PlayerCardsPathValue playerCardsPathValue,Card dealerCard){
		
		Collection<PlayerCardsPathValue> playerCardsPathValues = new HashSet<PlayerCardsPathValue>();
		
		if(!playerCardsPathValue.isValid()) return playerCardsPathValues;
		
		if(playerCardsPathValue.getAction() == PlayerAction.Init){
			//the first time reset action
			playerCardsPathValue.setAction(playerStrategyMatrix.getPlayerAction(playerCardsPathValue.getStartValue(),dealerCard).getStartAction());
		}else if(playerCardsPathValue.getAction() == PlayerAction.TestSecondChoice){
			playerCardsPathValue.setAction(playerStrategyMatrix.getPlayerAction(playerCardsPathValue.getStartValue(),dealerCard).getThreeCardAction());
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
						aNewPath.setAction(playerStrategyMatrix.getPlayerAction(StartValue.getOne(aNewPath.getValue()),dealerCard).getThreeCardAction());
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
