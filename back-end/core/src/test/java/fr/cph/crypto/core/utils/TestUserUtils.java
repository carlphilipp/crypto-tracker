package fr.cph.crypto.core.utils;

import fr.cph.crypto.core.Utils;
import fr.cph.crypto.core.entity.Currency;
import fr.cph.crypto.core.entity.Position;
import fr.cph.crypto.core.entity.Ticker;
import fr.cph.crypto.core.entity.User;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class TestUserUtils {

	@Test
	void testEnrichUser() {
		// given
		Ticker tickerEth = new Ticker("ETH-USD", Currency.ETH, Currency.USD, 1000, "exchange", 0, 0, 0, 0, 0, 0L);
		Ticker tickerGrs = new Ticker("GRS-USD", Currency.GRS, Currency.USD, 5, "exchange", 0, 0, 0, 0, 0, 0L);
		User user = Utils.getUser();
		Position ethPosition = new Position("ETH-USD", Currency.ETH, Currency.USD, 2.0, 500.0, null, null, null, null, null);
		Position grsPosition = new Position("GRS-USD", Currency.GRS, Currency.USD, 200, 1, null, null, null, null, null);
		user.getPositions().add(ethPosition);
		user.getPositions().add(grsPosition);

		// when
		User actual = UserUtils.INSTANCE.enrichUser(user, Arrays.asList(tickerEth, tickerGrs));

		// then
		assertAll("Validate enrich user data",
			() -> assertNotNull(actual),
			() -> assertEquals(3000, actual.getValue().doubleValue()),
			() -> assertEquals(1200, actual.getOriginalValue().doubleValue()),
			() -> assertEquals(1800, actual.getGain().doubleValue()),
			() -> assertEquals(1.5, actual.getGainPercentage().doubleValue())
		);
		Position ethPositionModified = actual.getPositions().get(0);
		Position grsPositionModified = actual.getPositions().get(1);
		assertAll("Validate enrich user positions data",
			() -> assertEquals(1000, ethPositionModified.getOriginalValue().doubleValue()),
			() -> assertEquals(2000, ethPositionModified.getValue().doubleValue()),
			() -> assertEquals(1000, ethPositionModified.getGain().doubleValue()),
			() -> assertEquals(1, ethPositionModified.getGainPercentage().doubleValue()),
			() -> assertEquals(200, grsPositionModified.getOriginalValue().doubleValue()),
			() -> assertEquals(1000, grsPositionModified.getValue().doubleValue()),
			() -> assertEquals(800, grsPositionModified.getGain().doubleValue()),
			() -> assertEquals(4, grsPositionModified.getGainPercentage().doubleValue())
		);
	}
}
