package shil.bjrsh;

import java.util.Collection;
import java.util.List;

import shil.bjrsh.analyze.DealerCards;

public class HelloWorld {

	public static void main(String[] args){
//		for(CardsPathValue cardsPathValue : DealerCards.StartTwo)
//		{
//			System.out.println(cardsPathValue);
//		}
		System.out.println(DealerCards.StartTwo.size());
	}
	
	public static <T> void print(Collection<T> cs){
		for(T o : cs){
			System.out.println(o);
		}
		System.out.println(cs.size());
	}
	
	public static <T> void print(List<T> cs){
		for(T o : cs){
			System.out.println(o);
		}
		System.out.println(cs.size());
	}
	
	public static void printDoubleWDL(double[] result){
		System.out.println("w:"+result[0]+"  $d:"+result[1]+"  $l:"+result[2]);
	}
	
	public static String builderDoubleWDL(double[] result){
		return "w:"+result[0]*100+"  $d:"+result[1]*100+"  $l:"+result[2]*100;
	}
}
