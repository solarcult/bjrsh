package shil.bjrsh.strategy;

public enum PlayerAction {
	//TODO Split还没有写入代码
	Init,Stand,Hit,Double,Giveup,DoubleDone,Split,TestSecondChoice;
	
	public static boolean isStopAction(PlayerAction playerAction){
		if(playerAction == Stand || playerAction == Giveup || playerAction == DoubleDone){
			return true;
		}
		return false;
	}
}
