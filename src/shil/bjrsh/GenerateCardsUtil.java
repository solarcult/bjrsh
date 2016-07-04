package shil.bjrsh;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GenerateCardsUtil {

	public static int BlackJack = 21;
	
	private static int DealerStop = 17;
	
	// 7/13 = 53.8% 8/13 = 61.5% 9/13 = 69.2% 1/13= 7.7%
	private static int UserDecideHitCards = 7; 
	
	private static int CardsTypeNumber = 13;
	
	/**
	 * 产生Dealer的所有可能牌的组合
	 * @param cardsPathValue
	 * @return Collection<CardsPathValue> 未过滤,有重复cards数据,但顺序不同
	 */
	public static Collection<CardsPathValue> generateDealerCards(CardsPathValue cardsPathValue){
		List<CardsPathValue> cardsPathValues = new ArrayList<CardsPathValue>(); 
		
		//这个组合是合理的,也就是A当做11没有爆掉
		if(cardsPathValue.getValue() == CardsPathValue.IlleageCards) return cardsPathValues;
		
		//如果达到了Deal停止要牌的点数,返回,这是递归的出口
		if(cardsPathValue.getValue() >= DealerStop){
			cardsPathValues.add(cardsPathValue);
			return cardsPathValues;
		}
		//如果没有达到点数,则再发一张牌,所有13中组合,继续递归
		if(cardsPathValue.getValue() < DealerStop ){
			for(Card card : Card.values()){
				//这里要深拷贝一个副本,因为要产生不同的13条链路,不能用同一个实例
				CardsPathValue aNewPath = new CardsPathValue(cardsPathValue);
				aNewPath.addCard(card);
				if(aNewPath.getValue() == CardsPathValue.IlleageCards) {
					//如果A做为11爆掉了,则这个组合不会存在,庄家会把这个A当做1,这种情况会包含在A=1的组合里,所以这个组合不合理,忽略.
					continue;
				}
				//一切没爆,继续递归
				cardsPathValues.addAll(generateDealerCards(aNewPath));
			}
		}
		
		return cardsPathValues;
	}
	
	/**
	 * 玩家的所有组合牌,根据UserDecideHitCards决定还有几张出牌时进行抓牌
	 * @param cardsPathValue
	 * @return Collection<CardsPathValue> 未过滤,有重复cards数据,但顺序不同
	 */
	public static Collection<CardsPathValue> generateUserCards(CardsPathValue cardsPathValue){
		List<CardsPathValue> cardsPathValues = new ArrayList<CardsPathValue>(); 
		
		if(cardsPathValue.getValue() == CardsPathValue.IlleageCards) return cardsPathValues;
		
		//出牌比较占优势,可以再要张牌看看
		if(playerLeftCard(cardsPathValue.getValue()) >= UserDecideHitCards){
			for(Card card : Card.values()){
				CardsPathValue aNewPath = new CardsPathValue(cardsPathValue);
				aNewPath.addCard(card);
				
				if(aNewPath.getValue() == CardsPathValue.IlleageCards) {
					//如果A做为11爆掉了,则这个组合不会存在,玩家会把这个A当做1,这种情况会包含在A=1的组合里,所以这个组合不合理,忽略.
					continue;
				}
				cardsPathValues.addAll(generateUserCards(aNewPath));
			}
		}else{
			//不要了直接返回
			cardsPathValues.add(cardsPathValue);
			return cardsPathValues;
		}
		
		return cardsPathValues;
	}
	
	/**
	 * 玩家还有几张牌可以抓
	 * @param value
	 * @return
	 */
	public static int playerLeftCard(int value){
		if(value >= 12) return BlackJack - value;
		else return CardsTypeNumber;
	}
	
	public static void main(String[] args){
		CardsPathValue cardsPathValue = new CardsPathValue(Card.K);
		
		Collection<CardsPathValue> dealerCardsPathValues = 
				generateUserCards(cardsPathValue); 
//				GenerateCardsUtil.generateDealerCards(cardsPathValue);

		System.out.println(dealerCardsPathValues.size());
		
		for(CardsPathValue aCardsPathValue : dealerCardsPathValues){
			System.out.println(aCardsPathValue);
		}
		
	}
}
