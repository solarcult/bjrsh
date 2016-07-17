package shil.bjrsh.core;

import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CalcROIMap {
	
	private ConcurrentMap<StartValue, Double> calcROIMap;
	
	public CalcROIMap(){
		calcROIMap = new ConcurrentHashMap<StartValue, Double>();
	}
	
	public synchronized void addValue(StartValue startValue, Double value){
//		should use jdk8 calcROIMap.getOrDefault(startValue, 0d) method but for jdk7 i have to do it by myself
		Double x = calcROIMap.get(startValue);
		if(x == null){
			x = 0d;
		}
		calcROIMap.put(startValue, x+value);
	}
	
	public double get(StartValue startValue){
		Double x = calcROIMap.get(startValue);
		return (x==null) ? 0d : x;
	}
	
	public Set<Entry<StartValue, Double>> entrySet(){
		return calcROIMap.entrySet();
	}
	
	public void printSelf(){
		double tROI = 0d;
		for(Entry<StartValue, Double> e : calcROIMap.entrySet()){
			System.out.println("StartValue: \t"+e.getKey() +"\t roi: "+e.getValue());
			tROI += e.getValue();
		}
		System.out.println("tROI: \t"+tROI);
	}
}
