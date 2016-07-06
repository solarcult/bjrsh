package shil.bjrsh.analyze;

import java.util.Comparator;

import shil.bjrsh.HelloWorld;
import shil.bjrsh.core.Card;
import shil.bjrsh.core.PlayerCardsPathValue;
import shil.bjrsh.core.StartValue;

public class DealerVSPlayerChance{

	public static String Dealer = "Dealer";
	public static String Player = "Player";
	
	private String who;
	private Card dealerCard;
	private StartValue playerStartValue;
	private double[] WDLMatrix;
	
	public DealerVSPlayerChance(String who, Card dealerCard,StartValue playerStartValue,double[] WDLMatrix){
		this.who = who;
		this.dealerCard = dealerCard;
		this.playerStartValue = playerStartValue;
		this.WDLMatrix = WDLMatrix;
	}
	
	public double winRate(){
		return WDLMatrix[WinDrawLose.win] * 100;
	}
	
	public double drawRate(){
		return WDLMatrix[WinDrawLose.draw] * 100;
	}
	
	public double loseRate(){
		return WDLMatrix[WinDrawLose.lose] * 100;
	}
	
	public double Win4LoseRate(){
		return winRate() - loseRate();
	}

	@Override
	public String toString() {
		double[] origin = PlayerAnalyzeWithCardsProb.playerNowVSDealerChance(new PlayerCardsPathValue(playerStartValue), dealerCard);
		return "DealerVSPlayerChance [who=" + who + ", \tdealerCard="
				+ dealerCard + ", \tplayerStartValue=" + playerStartValue.getValue()
				+ ", \twinRate()=" + winRate() + ", \tdrawRate()=" + drawRate()
				+ ", \tloseRate()=" + loseRate() + ", \tWin4LoseRate()="
				+ Win4LoseRate() + "] \t" + HelloWorld.builderDoubleWDL(origin) + "\t OriginLoseRate+@loseRate(onlyOneVsNow):" + (origin[2]*100+loseRate());
	}
}

class WinComparator implements Comparator<DealerVSPlayerChance>{

	@Override
	public int compare(DealerVSPlayerChance arg0, DealerVSPlayerChance arg1) {
		if (arg0.winRate() > arg1.winRate()) {
			return -1;
		} else if (arg0.winRate() < arg1.winRate()) {
			return 1;
		}
		return 0;
	}
}

class Win4LoseComparator implements Comparator<DealerVSPlayerChance>{

	@Override
	public int compare(DealerVSPlayerChance arg0, DealerVSPlayerChance arg1) {
		if (arg0.Win4LoseRate() > arg1.Win4LoseRate()) {
			return -1;
		} else if (arg0.Win4LoseRate() < arg1.Win4LoseRate()) {
			return 1;
		}
		return 0;
	}
}

class LoseComparator implements Comparator<DealerVSPlayerChance>{

	@Override
	public int compare(DealerVSPlayerChance arg0, DealerVSPlayerChance arg1) {
		if (arg0.loseRate() > arg1.loseRate()) {
			return -1;
		} else if (arg0.loseRate() < arg1.loseRate()) {
			return 1;
		}
		return 0;
	}
}
