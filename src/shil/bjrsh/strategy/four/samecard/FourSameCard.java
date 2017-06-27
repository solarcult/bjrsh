package shil.bjrsh.strategy.four.samecard;

import java.util.TreeMap;

import shil.bjrsh.core.Card;
import shil.bjrsh.core.StartValue;
import shil.bjrsh.strategy.PlayerAction;
import shil.bjrsh.strategy.PlayerStrategy;
import shil.bjrsh.strategy.PlayerStrategyMatrix;

public class FourSameCard extends PlayerStrategyMatrix {
	public FourSameCard(){
		strategyMatrix = new TreeMap<PlayerStrategy,PlayerStrategy>();
		
		PlayerStrategy playerStrategy77vs6 = new PlayerStrategy(StartValue.getOne(14), Card.Six6, PlayerAction.Hit,PlayerAction.Hit, 2);
		strategyMatrix.put(playerStrategy77vs6,playerStrategy77vs6);
		
		PlayerStrategy playerStrategy88vs4 = new PlayerStrategy(StartValue.getOne(16), Card.Four4, PlayerAction.Hit,PlayerAction.Hit, 2);
		strategyMatrix.put(playerStrategy88vs4,playerStrategy88vs4);
		PlayerStrategy playerStrategy88vs5 = new PlayerStrategy(StartValue.getOne(16), Card.Five5, PlayerAction.Hit,PlayerAction.Hit, 2);
		strategyMatrix.put(playerStrategy88vs5,playerStrategy88vs5);
		PlayerStrategy playerStrategy88vs6 = new PlayerStrategy(StartValue.getOne(16), Card.Six6, PlayerAction.Hit,PlayerAction.Hit, 2);
		strategyMatrix.put(playerStrategy88vs6,playerStrategy88vs6);
		PlayerStrategy playerStrategy88vs7 = new PlayerStrategy(StartValue.getOne(16), Card.Seven7, PlayerAction.Hit,PlayerAction.Hit, 2);
		strategyMatrix.put(playerStrategy88vs7,playerStrategy88vs7);
		
	}
}
