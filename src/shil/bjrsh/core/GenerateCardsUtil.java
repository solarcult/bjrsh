package shil.bjrsh.core;

import java.util.Collection;
import java.util.HashSet;

import shil.bjrsh.HelloWorld;

public class GenerateCardsUtil {
	
	/**
	 * 产生Dealer的所有可能牌的组合
	 * @param cardsPathValue
	 * @return Collection<CardsPathValue> 未过滤,有重复cards数据,但顺序不同
	 */
	public static Collection<DealerCardsPathValue> generateDealerCards(DealerCardsPathValue cardsPathValue){
		Collection<DealerCardsPathValue> cardsPathValues = new HashSet<DealerCardsPathValue>();
		
		//这个组合不合理的,也就是A当做11爆掉,出口之一
		if(!cardsPathValue.isValid()) return cardsPathValues;
		
		//如果达到了Deal停止要牌的点数,返回,这是递归的出口之一
		if(cardsPathValue.getValue() >= BlackJackInfo.DealerStop){
			cardsPathValues.add(cardsPathValue);
			return cardsPathValues;
		}
		//如果没有达到点数,则再发一张牌,所有13中组合,继续递归
		if(cardsPathValue.getValue() < BlackJackInfo.DealerStop ){
			for(Card card : Card.values()){
				//这里要深拷贝一个副本,因为要产生不同的13条链路,不能用同一个实例
				DealerCardsPathValue aNewPath = new DealerCardsPathValue(cardsPathValue);
				aNewPath.addCard(card);
				cardsPathValues.addAll(generateDealerCards(aNewPath));
			}
		}
		
		return cardsPathValues;
	}
	
	public static Collection<DealerCardsPathValue> generateDealerACards(){
		Collection<DealerCardsPathValue> cardsPathValues = new HashSet<DealerCardsPathValue>();
		cardsPathValues.addAll(GenerateCardsUtil.generateDealerCards(new DealerCardsPathValue(Card.One1)));
		cardsPathValues.addAll(GenerateCardsUtil.generateDealerCards(new DealerCardsPathValue(Card.Eleven)));
		return cardsPathValues;
	}
	
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
	 * 再发一张牌,用户会面临的所有牌
	 * @param playerCardsPathValue
	 * @return
	 */
	public static Collection<PlayerCardsPathValue> hitPlayerOneMoreCard(PlayerCardsPathValue playerCardsPathValue){
		Collection<PlayerCardsPathValue> playerCardsPathValues = new HashSet<PlayerCardsPathValue>();
		
		if(!playerCardsPathValue.isValid()) return playerCardsPathValues;
		
		//Boss, give me one more cards
		for (Card card : Card.values()) {
			PlayerCardsPathValue aNewPath = new PlayerCardsPathValue(playerCardsPathValue);
			aNewPath.addCard(card);
			if(aNewPath.isValid()) playerCardsPathValues.add(aNewPath);
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
