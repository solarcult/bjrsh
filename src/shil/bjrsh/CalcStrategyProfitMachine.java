package shil.bjrsh;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import shil.bjrsh.core.CalcROIMap;
import shil.bjrsh.core.Card;
import shil.bjrsh.core.PlayerCardsPathValue;
import shil.bjrsh.core.ProfitUtil;
import shil.bjrsh.core.StartValue;
import shil.bjrsh.strategy.Strategy;

/**
 * 并行计算接口,使用所有CPU计算
 * @author LiangJingJing
 * @date 2016年7月20日 上午12:53:23
 */
public class CalcStrategyProfitMachine {
	
	private static int cores = Runtime.getRuntime().availableProcessors();

	private ExecutorService machines ;
	private CompletionService<OnePackageResult> completionService;
	private Strategy strategy;
	
	public CalcStrategyProfitMachine (Strategy strategy){
		this.strategy = strategy;
		machines = Executors.newFixedThreadPool(cores);
		completionService = new ExecutorCompletionService<OnePackageResult>(machines);
	}
	
	public CalcROIMap calcROIofPlayerHands(Collection<OneCalcPackage> oneCalcPackages){
		CalcROIMap calcROIMap = new CalcROIMap();
		AtomicInteger playerPossibleHands = new AtomicInteger(0);
		for(OneCalcPackage oneCalcPackage : oneCalcPackages){
			this.completionService.submit(new Callable<OnePackageResult>() {
				@Override
				public OnePackageResult call() throws Exception {
					Collection<PlayerCardsPathValue> allPossibleHandsCombine = strategy.generatePlayerCardsPaths(oneCalcPackage.getPlayerStartTwoCards(), oneCalcPackage.getDealerCard());
					playerPossibleHands.addAndGet(allPossibleHandsCombine.size());
					double oneHandROI = ProfitUtil.calcStarthandPossibleFuturesVSDealerCardInReturn(allPossibleHandsCombine, oneCalcPackage.getDealerCard());
					return new OnePackageResult(oneCalcPackage.getPlayerStartTwoCards().getStartValue(),oneCalcPackage.getDealerCard(), oneHandROI);
				}
			});
		}
		
		System.out.println("da baba gan ma?");
		//this for just want to number of package
		for(int i=0; i<oneCalcPackages.size(); i++){
			try {
				OnePackageResult oneResult = completionService.take().get();
				System.out.println(i+"/"+oneCalcPackages.size()+" job done. "+oneResult);
				calcROIMap.addValue(oneResult.getStartValue(), oneResult.getRoi());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("how many :" + playerPossibleHands.get());
		machines.shutdown();
		return calcROIMap;
	}
}

class OnePackageResult{
	private StartValue startValue;
	private Card dealerCard;
	private Double roi;
	
	public OnePackageResult(StartValue startValue,Card dealercard, Double roi){
		this.startValue = startValue;
		this.dealerCard = dealercard;
		this.roi = roi;
	}
	
	public StartValue getStartValue() {
		return startValue;
	}
	public void setStartValue(StartValue startValue) {
		this.startValue = startValue;
	}
	public Double getRoi() {
		return roi;
	}
	public void setRoi(Double roi) {
		this.roi = roi;
	}

	public Card getDealerCard() {
		return dealerCard;
	}

	public void setDealerCard(Card dealerCard) {
		this.dealerCard = dealerCard;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dealerCard == null) ? 0 : dealerCard.hashCode());
		result = prime * result + ((roi == null) ? 0 : roi.hashCode());
		result = prime * result
				+ ((startValue == null) ? 0 : startValue.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OnePackageResult other = (OnePackageResult) obj;
		if (dealerCard != other.dealerCard)
			return false;
		if (roi == null) {
			if (other.roi != null)
				return false;
		} else if (!roi.equals(other.roi))
			return false;
		if (startValue != other.startValue)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OnePackageResult [startValue=" + startValue + ", dealerCard="
				+ dealerCard + ", roi=" + roi + "]";
	}

}