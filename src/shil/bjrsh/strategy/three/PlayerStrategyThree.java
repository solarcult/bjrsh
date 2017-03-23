package shil.bjrsh.strategy.three;

import java.util.Collection;
import java.util.HashSet;

import shil.bjrsh.HelloWorld;
import shil.bjrsh.core.BlackJackInfo;
import shil.bjrsh.core.Card;
import shil.bjrsh.core.PlayerCardsPathValue;
import shil.bjrsh.core.StartValue;

public class PlayerStrategyThree {

	/**
	 * 玩家的所有组合牌,根据enableHitOneMoreCardNumber决定还有几张出牌时进行抓牌
	 * @param playerCardsPathValue
	 * @param enableHitOneMoreCardNumber
	 * @return Collection<CardsPathValue> 未过滤,有重复cards数据,但顺序不同
	 */
	public static Collection<PlayerCardsPathValue> generatePlayerCardsPaths(PlayerCardsPathValue playerCardsPathValue,int enableHitOneMoreCardNumber){
		Collection<PlayerCardsPathValue> playerCardsPathValues = new HashSet<PlayerCardsPathValue>();
		
		if(!playerCardsPathValue.isValid()) return playerCardsPathValues;
		
		//出牌比较占优势,可以再要张牌看看
		if(playerLeftCard(playerCardsPathValue.getValue()) >= enableHitOneMoreCardNumber){
			for(Card card : Card.values()){
				PlayerCardsPathValue aNewPath = new PlayerCardsPathValue(playerCardsPathValue);
				aNewPath.addCard(card);
				playerCardsPathValues.addAll(generatePlayerCardsPaths(aNewPath,enableHitOneMoreCardNumber));
			}
		}else{
			//不要了直接返回
			playerCardsPathValues.add(playerCardsPathValue);
			return playerCardsPathValues;
		}
		
		return playerCardsPathValues;
	}
	
	/**
	 * 玩家还有几张牌可以抓
	 * @param value
	 * @return
	 */
	public static int playerLeftCard(int value){
		if(value >= 12) return BlackJackInfo.BlackJack - value;
		else return BlackJackInfo.CardsTypeNumber;
	}
	
	public static void main(String[] args){
		
		PlayerCardsPathValue playerCardsPathValue = new PlayerCardsPathValue(StartValue.Six);
		Collection<PlayerCardsPathValue> playerCardsPathValues = generatePlayerCardsPaths(playerCardsPathValue,BlackJackInfo.UserDecideHitCards);
		System.out.println(playerCardsPathValues.size());
		HelloWorld.print(playerCardsPathValues);
	}
}
