package shil.bjrsh.strategy;

import java.util.Map;

import shil.bjrsh.core.Card;
import shil.bjrsh.core.StartValue;

public abstract class PlayerStrategyMatrix {

	protected Map<PlayerStrategy,PlayerStrategy> strategyMatrix;
	
	public PlayerStrategy getPlayerAction(StartValue startValue,Card dealerCard){
		return strategyMatrix.get(PlayerStrategy.builderOne(startValue, dealerCard));
	}
	
	public PlayerStrategy getPlayerAction(PlayerStrategy playerStrategy){
		return strategyMatrix.get(playerStrategy);
	}
	
	public Map<PlayerStrategy,PlayerStrategy> getStrategyMatrix(){
		return strategyMatrix;
	}
	
	public void printStrategyMatrix(){
		for(PlayerStrategy playerStrategy : strategyMatrix.keySet()){
			System.out.println(playerStrategy);
		}
	}
}
