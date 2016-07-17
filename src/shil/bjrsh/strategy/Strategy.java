package shil.bjrsh.strategy;

import java.util.Collection;

import shil.bjrsh.core.Card;
import shil.bjrsh.core.PlayerCardsPathValue;

public interface Strategy {
	Collection<PlayerCardsPathValue> generatePlayerCardsPaths(PlayerCardsPathValue playerCardsPathValue,Card dealerCard);
}
