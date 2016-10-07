package shil.bjrsh.analyze;

import shil.bjrsh.HelloWorld;
import shil.bjrsh.core.Card;
import shil.bjrsh.core.DealerCardsPathValue;
import shil.bjrsh.core.GenerateCardsUtil;
import shil.bjrsh.core.PlayerCardsPathValue;
import shil.bjrsh.core.StartValue;

public class HardCardDetect {
	
	private static double[] detectNextCardAdvProb(StartValue startValue,Card dealerCard,Card playerNextCard,Card dealerNextCard){
		PlayerCardsPathValue originPlayerCards = new PlayerCardsPathValue(startValue);
		PlayerCardsPathValue hitPlayerCards = new PlayerCardsPathValue(startValue);
		hitPlayerCards.addCard(playerNextCard);
		DealerCardsPathValue hitDealerCards = new DealerCardsPathValue(dealerCard);
		hitDealerCards.addCard(dealerNextCard);
		if(!hitPlayerCards.isValid() || !hitDealerCards.isValid()){
//			System.out.println("detect cards has error: "+hitPlayerCards +"<-p , d->"+hitDealerCards);
			return new double[]{0,0,0,0,0};
		}
		return PlayersVSDealersResultChanceProb.hitPlayerOneMore(originPlayerCards,hitPlayerCards, hitDealerCards);
	}
	
	public static double[] detectNextCardsAdvProb(StartValue startValue,Card dealerCard){
		double[] result = new double[6];
		for(Card nextCard : Card.values()){
			double[] t = detectNextCardAdvProb(startValue, dealerCard, nextCard, nextCard);
			result = WinDrawLose.addMatrix(result, t);
		}
		double[] a1 = detectNextCardAdvProb(startValue, dealerCard, Card.One1, Card.Eleven);
		result = WinDrawLose.addMatrix(result, a1);
		double[] a2 = detectNextCardAdvProb(startValue, dealerCard, Card.Eleven, Card.One1);
		result = WinDrawLose.addMatrix(result, a2);
		return result;
	}
	
	//用户没有拿下一张牌,vs庄家拿了下一张牌的胜率 origin
	//用户拿了下一张牌,vs,庄家的原始牌胜率  advanced
	//比较结果
	public static void according2Prob(StartValue startValue,Card dealerCard,Card playerNextCard,Card dealerNextCard){
		PlayerCardsPathValue originPlayerCards = new PlayerCardsPathValue(startValue);
		PlayerCardsPathValue hitPlayerCards = new PlayerCardsPathValue(startValue);
		hitPlayerCards.addCard(playerNextCard);
		DealerCardsPathValue hitDealerCards = new DealerCardsPathValue(dealerCard);
		hitDealerCards.addCard(dealerNextCard);
		if(!hitPlayerCards.isValid() || !hitDealerCards.isValid()){
//			System.out.println("detect cards has error: "+hitPlayerCards +"<-p , d->"+hitDealerCards);
//			return new double[]{0,0,0,0,0};
			return;
		}
		//用户没有拿下一张牌,vs庄家拿了下一张牌的胜率 origin
		double origin[] = PlayersVSDealersResultChanceProb.calcPlayerdVSDealerProbs(originPlayerCards,GenerateCardsUtil.generateDealerCards(hitDealerCards));
		//用户拿了下一张牌,vs,庄家的原始牌胜率  advanced
		double advanced[] = PlayersVSDealersResultChanceProb.calcPlayerdVSDealerProbs(hitPlayerCards,dealerCard);
		
		HelloWorld.print2DoubleWDL(advanced, origin);
	}
	
	//拿到用户没有拿下一张牌,vs庄家拿了下一张牌的胜率 origin
	public static double[] orginMatrixValue(StartValue startValue,Card dealerCard,Card dealerNextCard){
		PlayerCardsPathValue originPlayerCards = new PlayerCardsPathValue(startValue);
		DealerCardsPathValue hitDealerCards = new DealerCardsPathValue(dealerCard);
		hitDealerCards.addCard(dealerNextCard);
		return PlayersVSDealersResultChanceProb.calcPlayerdVSDealerValues(originPlayerCards,GenerateCardsUtil.generateDealerCards(hitDealerCards));
	}
	
	//用户拿了下一张牌,vs,庄家的原始牌胜率  advanced
	public static double[] advancedMatrixValue(StartValue startValue,Card dealerCard,Card playerNextCard){
		PlayerCardsPathValue hitPlayerCards = new PlayerCardsPathValue(startValue);
		hitPlayerCards.addCard(playerNextCard);
		return PlayersVSDealersResultChanceProb.calcPlayerdVSDealerValues(hitPlayerCards, dealerCard);
	}
	
	public static void according2FullProb(StartValue startValue,Card dealerCard){
		double[] origin = new double[3];
		double[] advanced = new double[3];
		for(Card card : Card.values()){
			if(!checkValid(startValue, dealerCard, card, card)) continue;
			double[] ot = orginMatrixValue(startValue, dealerCard, card);
			origin = WinDrawLose.addMatrix(origin, ot);
			double[] at = advancedMatrixValue(startValue, dealerCard, card);
			advanced = WinDrawLose.addMatrix(advanced, at);
		}
		
		if(checkValid(startValue, dealerCard, Card.One1, Card.Eleven)){
			double[] ot = orginMatrixValue(startValue, dealerCard, Card.Eleven);
			origin = WinDrawLose.addMatrix(origin, ot);
			double[] at = advancedMatrixValue(startValue, dealerCard, Card.One1);
			advanced = WinDrawLose.addMatrix(advanced, at);
		}
		if(checkValid(startValue, dealerCard, Card.Eleven, Card.One1)){
			double[] ot = orginMatrixValue(startValue, dealerCard, Card.One1);
			origin = WinDrawLose.addMatrix(origin, ot);
			double[] at = advancedMatrixValue(startValue, dealerCard, Card.Eleven);
			advanced = WinDrawLose.addMatrix(advanced, at);
		}
		
		double origintotal = origin[WinDrawLose.win] + origin[WinDrawLose.draw] + origin[WinDrawLose.lose];
		double advancedtotal = advanced[WinDrawLose.win] + advanced[WinDrawLose.draw] + advanced[WinDrawLose.lose];
		
		HelloWorld.print2DoubleWDL(
				new double[]{advanced[WinDrawLose.win]/advancedtotal,advanced[WinDrawLose.draw]/advancedtotal, advanced[WinDrawLose.lose]/advancedtotal}, 
				new double[]{origin[WinDrawLose.win]/origintotal,origin[WinDrawLose.draw]/origintotal,origin[WinDrawLose.lose]/origintotal});
	}
	
	public static boolean checkValid(StartValue startValue,Card dealerCard,Card playerNextCard,Card dealerNextCard){
		PlayerCardsPathValue hitPlayerCards = new PlayerCardsPathValue(startValue);
		hitPlayerCards.addCard(playerNextCard);
		DealerCardsPathValue hitDealerCards = new DealerCardsPathValue(dealerCard);
		hitDealerCards.addCard(dealerNextCard);
		if(!hitPlayerCards.isValid() || !hitDealerCards.isValid()){
			System.out.println("detect cards has error: "+hitPlayerCards +"<-p , d->"+hitDealerCards);
			return false;
		}
		return true;
	}
	
	public static void showHardDecideAllHands(){
		for(StartValue startValue : StartValue.values()){
			if(startValue.getValue()>=12 && startValue.getValue() <20){
				System.out.println("\n \t\t-------- "+startValue.getValue()+" --------");
				for(Card dealerCard : Card.values()){
					if(dealerCard == Card.One1 || dealerCard == Card.JJJ || dealerCard == Card.QQQ || dealerCard == Card.KKK) continue;
					System.out.println("\n \tplayer value: "+startValue.getValue() +" dealerCard: "+ dealerCard +":");
					HelloWorld.printDoubleMatrix(detectNextCardsAdvProb(startValue, dealerCard));
				}
			}
		}
	}
	
	
	public static void main(String[] args) {
//		HelloWorld.printDoubleMatrix(detectNextCardsAdvProb(StartValue.Fifteen, Card.Two2));
//		HelloWorld.printDoubleMatrix(detectNextCardsAdvProb(StartValue.Twelve, Card.Two2));
//		according2Prob(StartValue.Fourteen, Card.Ten, Card.Six6, Card.Six6);
//		according2FullProb(StartValue.Fourteen, Card.Eight8);
		
		showHardDecideAllHands();
	}

}
