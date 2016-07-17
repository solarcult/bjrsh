package shil.bjrsh.strategy.two;

import java.util.Map;
import java.util.TreeMap;

import shil.bjrsh.core.Card;
import shil.bjrsh.core.StartValue;
import shil.bjrsh.strategy.PlayerAction;

public class PlayerStrategyMatrix {

	private static Map<PlayerStrategy,PlayerStrategy> strategyMatrix = new TreeMap<PlayerStrategy,PlayerStrategy>();
	static{
		
		for(StartValue startValue : StartValue.values()){
			if(startValue== StartValue.One) continue;
			//startvalue 2~9 hit
			else if(startValue.getValue() >=2 && startValue.getValue()<=8){
				for(Card dealerCard : Card.values()){
					if(dealerCard == Card.One1) continue;
					PlayerStrategy playerStrategy = new PlayerStrategy(startValue, dealerCard, PlayerAction.Hit,PlayerAction.Hit);
					strategyMatrix.put(playerStrategy,playerStrategy);
				}
			}
			else if(startValue == StartValue.Nine){
				for(Card dealerCard : Card.values()){
					if(dealerCard == Card.One1) continue;
					if(dealerCard == Card.Five5 || dealerCard == Card.Six6){
						PlayerStrategy playerStrategy = new PlayerStrategy(startValue, dealerCard, PlayerAction.Double,PlayerAction.Hit);
						strategyMatrix.put(playerStrategy,playerStrategy); 
					}else{
						PlayerStrategy playerStrategy = new PlayerStrategy(startValue, dealerCard, PlayerAction.Hit,PlayerAction.Hit);
						strategyMatrix.put(playerStrategy,playerStrategy);
					}
				}
			}
			else if(startValue == StartValue.Ten) {
				for(Card dealerCard : Card.values()){
					if(dealerCard == Card.One1) continue;
					if(dealerCard.getValue() <=8){
						PlayerStrategy playerStrategy = new PlayerStrategy(startValue, dealerCard, PlayerAction.Double,PlayerAction.Hit);
						strategyMatrix.put(playerStrategy,playerStrategy);
					}else{
						PlayerStrategy playerStrategy = new PlayerStrategy(startValue, dealerCard, PlayerAction.Hit,PlayerAction.Hit);
						strategyMatrix.put(playerStrategy,playerStrategy);
					}
				}
			}
			else if(startValue == StartValue.Eleven){
				for(Card dealerCard : Card.values()){
					if(dealerCard == Card.One1) continue;
					if(dealerCard.getValue() <=9){
						PlayerStrategy playerStrategy = new PlayerStrategy(startValue, dealerCard, PlayerAction.Double,PlayerAction.Hit);
						strategyMatrix.put(playerStrategy,playerStrategy);
					}else{
						PlayerStrategy playerStrategy = new PlayerStrategy(startValue, dealerCard, PlayerAction.Hit,PlayerAction.Hit);
						strategyMatrix.put(playerStrategy,playerStrategy);
					}
				}
			}
			else if(startValue == StartValue.Twelve){// && startValue.getValue()<=16){
				for(Card dealerCard : Card.values()){
					if(dealerCard == Card.One1) continue;
					if(dealerCard.getValue() <=6){
						PlayerStrategy playerStrategy = new PlayerStrategy(startValue, dealerCard, PlayerAction.Stand,PlayerAction.Stand);
						strategyMatrix.put(playerStrategy,playerStrategy);
					}else{
						PlayerStrategy playerStrategy = new PlayerStrategy(startValue, dealerCard, PlayerAction.Hit,PlayerAction.Hit);
						strategyMatrix.put(playerStrategy,playerStrategy);
					}
				}
			}else if(startValue==StartValue.Thirteen){
				for(Card dealerCard : Card.values()){
					if(dealerCard == Card.One1) continue;
					if(dealerCard.getValue() <=6){
						PlayerStrategy playerStrategy = new PlayerStrategy(startValue, dealerCard, PlayerAction.Stand,PlayerAction.Stand);
						strategyMatrix.put(playerStrategy,playerStrategy);
					}else if(dealerCard.getValue() == 7 || dealerCard.getValue() == 8){
						PlayerStrategy playerStrategy = new PlayerStrategy(startValue, dealerCard, PlayerAction.Hit,PlayerAction.Hit);
						strategyMatrix.put(playerStrategy,playerStrategy);
					}else{
						PlayerStrategy playerStrategy = new PlayerStrategy(startValue, dealerCard, PlayerAction.Giveup,PlayerAction.Hit);
						strategyMatrix.put(playerStrategy,playerStrategy);
					}
				}
			}else if(startValue==StartValue.Fourteen){
				//very hard choose
				for(Card dealerCard : Card.values()){
					if(dealerCard == Card.One1) continue;
					if(dealerCard.getValue() <=6){
						PlayerStrategy playerStrategy = new PlayerStrategy(startValue, dealerCard, PlayerAction.Stand,PlayerAction.Stand);
						strategyMatrix.put(playerStrategy,playerStrategy);
					}else if(dealerCard.getValue() == 7 ){
						PlayerStrategy playerStrategy = new PlayerStrategy(startValue, dealerCard, PlayerAction.Stand,PlayerAction.Stand);
						strategyMatrix.put(playerStrategy,playerStrategy);
					}else{
						PlayerStrategy playerStrategy = new PlayerStrategy(startValue, dealerCard, PlayerAction.Giveup,PlayerAction.Stand);
						strategyMatrix.put(playerStrategy,playerStrategy);
					}
				}
			}else if(startValue == StartValue.Fifteen || startValue == StartValue.Sixteen){
				//very hard choose
				for(Card dealerCard : Card.values()){
					if(dealerCard == Card.One1) continue;
					if(dealerCard.getValue() <=6){
						PlayerStrategy playerStrategy = new PlayerStrategy(startValue, dealerCard, PlayerAction.Stand,PlayerAction.Stand);
						strategyMatrix.put(playerStrategy,playerStrategy);
					}else if(dealerCard.getValue() == 7 ){
						PlayerStrategy playerStrategy = new PlayerStrategy(startValue, dealerCard, PlayerAction.Stand,PlayerAction.Stand);
						strategyMatrix.put(playerStrategy,playerStrategy);
					}else{
						PlayerStrategy playerStrategy = new PlayerStrategy(startValue, dealerCard, PlayerAction.Giveup,PlayerAction.Stand);
						strategyMatrix.put(playerStrategy,playerStrategy);
					}
				}
			}
			else{
				// start >= 17 just wait , watch and pray
				for(Card dealerCard : Card.values()){
					if(dealerCard == Card.One1) continue;
					PlayerStrategy playerStrategy = new PlayerStrategy(startValue, dealerCard, PlayerAction.Stand,PlayerAction.Stand);
					strategyMatrix.put(playerStrategy,playerStrategy);
				}
			}
		}
	}
	
	public static PlayerStrategy getPlayerAction(StartValue startValue,Card dealerCard){
		return strategyMatrix.get(PlayerStrategy.builderOne(startValue, dealerCard));
	}
	
	public static PlayerStrategy getPlayerAction(PlayerStrategy playerStrategy){
		return strategyMatrix.get(playerStrategy);
	}
	
	public static Map<PlayerStrategy,PlayerStrategy> getStrategyMatrix(){
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
