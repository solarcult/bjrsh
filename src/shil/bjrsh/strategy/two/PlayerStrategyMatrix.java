package shil.bjrsh.strategy.two;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import shil.bjrsh.core.Card;
import shil.bjrsh.core.StartValue;
import shil.bjrsh.strategy.PlayerAction;

public class PlayerStrategyMatrix {

	private static Map<PlayerStrategy,PlayerAction> strategyMatrix = new TreeMap<PlayerStrategy,PlayerAction>();
	static{
		
		for(StartValue startValue : StartValue.values()){
			if(startValue== StartValue.One) continue;
			//startvalue 2~9 hit
			else if(startValue.getValue() >=2 && startValue.getValue()<=9){
				for(Card dealerCard : Card.values()){
					if(dealerCard == Card.One1) continue;
					if(dealerCard == Card.Five5 || dealerCard == Card.Six6)
						strategyMatrix.put(new PlayerStrategy(startValue, dealerCard, PlayerAction.Double),PlayerAction.Double); 
					else 
						strategyMatrix.put(new PlayerStrategy(startValue, dealerCard, PlayerAction.Hit),PlayerAction.Hit);
				}
			}
			else if(startValue == StartValue.Ten) {
				for(Card dealerCard : Card.values()){
					if(dealerCard == Card.One1) continue;
					if(dealerCard.getValue() <=8)
						strategyMatrix.put(new PlayerStrategy(startValue, dealerCard, PlayerAction.Double),PlayerAction.Double);
					else 
						strategyMatrix.put(new PlayerStrategy(startValue, dealerCard, PlayerAction.Hit),PlayerAction.Hit);
				}
			}
			else if(startValue == StartValue.Eleven){
				for(Card dealerCard : Card.values()){
					if(dealerCard == Card.One1) continue;
					if(dealerCard.getValue() <=9)
						strategyMatrix.put(new PlayerStrategy(startValue, dealerCard, PlayerAction.Double),PlayerAction.Double);
					else 
						strategyMatrix.put(new PlayerStrategy(startValue, dealerCard, PlayerAction.Hit),PlayerAction.Hit);
				}
			}
			else if(startValue.getValue()>=12 && startValue.getValue()<=16){
				for(Card dealerCard : Card.values()){
					if(dealerCard == Card.One1) continue;
					if(dealerCard.getValue() <=6)
						strategyMatrix.put(new PlayerStrategy(startValue, dealerCard, PlayerAction.Stand),PlayerAction.Stand);
					else{
						//this is complex part
						
					}
				}
			}
			else{
				// start >= 17 just wait and watch and pray
				for(Card dealerCard : Card.values()){
					if(dealerCard == Card.One1) continue;
					strategyMatrix.put(new PlayerStrategy(startValue, dealerCard, PlayerAction.Stand),PlayerAction.Stand);
				}
			}
		}
	}
	
	public static PlayerAction getPlayerAction(StartValue startValue,Card dealerCard){
		return strategyMatrix.get(PlayerStrategy.builderOne(startValue, dealerCard));
	}
	
	public static PlayerAction getPlayerAction(PlayerStrategy playerStrategy){
		return strategyMatrix.get(playerStrategy);
	}
	
	public static Map<PlayerStrategy,PlayerAction> getStrategyMatrix(){
		return strategyMatrix;
	}
	
	public static void printStrategyMatrix(){
		for(PlayerStrategy playerStrategy : strategyMatrix.keySet()){
			System.out.println(playerStrategy);
		}
	}
	
	public static void main(String[] args){
		printStrategyMatrix();
	}
}
