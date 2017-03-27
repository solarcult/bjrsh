package shil.bjrsh.core;

import java.util.Collection;
import java.util.HashSet;

import shil.bjrsh.HelloWorld;

public class GenerateCardsUtil {
	
	/**
	 * 产生Dealer的所有可能牌的组合
	 * @param dealerCardsPathValue
	 * @return Collection<CardsPathValue> 未过滤,有重复cards数据,但顺序不同
	 */
	public static Collection<DealerCardsPathValue> generateDealerCards(DealerCardsPathValue dealerCardsPathValue){
		Collection<DealerCardsPathValue> dealerCardsPathValues = new HashSet<DealerCardsPathValue>();
		
		//这个组合不合理的,也就是A当做11爆掉,出口之一
		if(!dealerCardsPathValue.isValid()) return dealerCardsPathValues;
		
		//如果达到了Deal停止要牌的点数,返回,这是递归的出口之一
		if(dealerCardsPathValue.getValue() >= BlackJackInfo.DealerStop){
			dealerCardsPathValues.add(dealerCardsPathValue);
			return dealerCardsPathValues;
		}
		//如果没有达到点数,则再发一张牌,所有13中组合,继续递归
		if(dealerCardsPathValue.getValue() < BlackJackInfo.DealerStop ){
			for(Card card : Card.values()){
				//这里要深拷贝一个副本,因为要产生不同的13条链路,不能用同一个实例
				DealerCardsPathValue aNewPath = new DealerCardsPathValue(dealerCardsPathValue);
				aNewPath.addCard(card);
				dealerCardsPathValues.addAll(generateDealerCards(aNewPath));
			}
		}
		
		return dealerCardsPathValues;
	}
	
	//生成所有A代表1和代表11的组合,将重复的数据过滤掉,如果A在相同位置,并且值相同,则过滤掉比如说11,8,1代表20点,则1,8,11也是相同的组合,则过滤掉
	public static Collection<DealerCardsPathValue> generateDealerACards(){
		Collection<DealerCardsPathValue> cardsPathValues = new HashSet<DealerCardsPathValue>();
		cardsPathValues.addAll(GenerateCardsUtil.generateDealerCards(new DealerCardsPathValue(Card.One1)));
		cardsPathValues.addAll(GenerateCardsUtil.generateDealerCards(new DealerCardsPathValue(Card.Eleven)));
		return cardsPathValues;
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
	 * 专门为AX牌做的再来一张,其他逻辑不要用
	 * @param playerCardsPathValue
	 * @return
	 */
	@Deprecated
	public static Collection<PlayerCardsPathValue> hitPlayerOneMoreCardEvenIlleage(PlayerCardsPathValue playerCardsPathValue){
		Collection<PlayerCardsPathValue> playerCardsPathValues = new HashSet<PlayerCardsPathValue>();
				
		//Boss, give me one more cards
		for (Card card : Card.values()) {
			PlayerCardsPathValue aNewPath = new PlayerCardsPathValue(playerCardsPathValue);
			aNewPath.addCard(card);
			playerCardsPathValues.add(aNewPath);
		}
		
		return playerCardsPathValues;
	}
	
	public static void main(String[] args){
		PlayerCardsPathValue x = new PlayerCardsPathValue(Card.Nine9,Card.Four4,Card.Five5);
		HelloWorld.print(hitPlayerOneMoreCard(x));
	}
	
}
