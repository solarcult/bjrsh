package shil.bjrsh.core;

public enum Card {
	One1(1),Two2(2),Three3(3),Four4(4),Five5(5),Six6(6),Seven7(7),Eight8(8),Nine9(9),Ten(10),JJJ(10),QQQ(10),KKK(10),Eleven(11);
	
	private int value;
	Card(int _value){
		value = _value;
	}
	
	public int getValue(){
		return value;
	}
}
