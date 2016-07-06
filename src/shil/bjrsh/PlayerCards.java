package shil.bjrsh;

import java.util.Collection;

import shil.bjrsh.core.GenerateCardsUtil;
import shil.bjrsh.core.PlayerCardsPathValue;
import shil.bjrsh.core.StartValue;

public class PlayerCards {
	public static Collection<PlayerCardsPathValue> fetchPlayerCardsPathValues(StartValue startValue,int enableHitOneMoreCardNumber){
		return GenerateCardsUtil.generatePlayerCardsPaths(new PlayerCardsPathValue(startValue),enableHitOneMoreCardNumber);
	}
}
