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
		System.out.println("w:"+result[0]*100+"  $d:"+result[1]*100+"  $l:"+result[2]*100);
	}
	
	public static String builderDoubleWDL(double[] result){
		return "w:"+result[0]*100+"  $d:"+result[1]*100+"  $l:"+result[2]*100;
	}
	
	public static void print2DoubleWDL(double[] advanced , double[] origin){
		System.out.println("w:"+advanced[0]*100 + "->" + origin[0]*100+"  \t$d:"+advanced[1]*100 + "->" + origin[1]*100+"  \t$l:"+advanced[2]*100 + "->" + origin[2]*100);
		System.out.println(" \timprove value x$w(high is good):"+(advanced[0]*100 - origin[0]*100)+"  \tx$d:"+(advanced[1]*100 - origin[1]*100)+"  \t x$l(negative is good):"+(advanced[2]*100 - origin[2]*100));

	}
}
