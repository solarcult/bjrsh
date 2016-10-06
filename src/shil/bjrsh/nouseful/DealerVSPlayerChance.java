package shil.bjrsh.nouseful;

import shil.bjrsh.HelloWorld;
import shil.bjrsh.core.Card;
import shil.bjrsh.core.StartValue;

public class DealerVSPlayerChance{

	public static String Dealer = "Dealer";
	public static String Player = "Player";
	
	private String who;
	private Card dealerCard;
	private StartValue playerStartValue;
	private double[] origin;
	private double[] advanced;
	
	public DealerVSPlayerChance(String who, Card dealerCard,StartValue playerStartValue,double[] origin,double[] advanced){
		this.who = who;
		this.dealerCard = dealerCard;
		this.playerStartValue = playerStartValue;
		this.origin = origin;
		this.advanced = advanced;
	}

	@Override
	public String toString() {
		return "\nDealerVSPlayerChance [who=" + who + ", \tdealerCard="
				+ dealerCard + ", \tplayerStartValue=" + playerStartValue.getValue() +"\t"
				+ HelloWorld.builder2DoubleWDL(advanced, origin);
	}
}