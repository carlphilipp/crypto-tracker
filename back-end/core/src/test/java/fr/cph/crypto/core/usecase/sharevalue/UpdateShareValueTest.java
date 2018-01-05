package fr.cph.crypto.core.usecase.sharevalue;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import fr.cph.crypto.core.Utils;
import fr.cph.crypto.core.entity.Currency;
import fr.cph.crypto.core.entity.Position;
import fr.cph.crypto.core.entity.ShareValue;
import fr.cph.crypto.core.entity.Ticker;
import fr.cph.crypto.core.entity.User;
import fr.cph.crypto.core.spi.ShareValueRepository;
import fr.cph.crypto.core.spi.TickerRepository;
import fr.cph.crypto.core.spi.UserRepository;

import static java.util.Collections.singletonList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

class UpdateShareValueTest {

	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private ShareValueRepository shareValueRepository = Mockito.mock(ShareValueRepository.class);
	private TickerRepository tickerRepository = Mockito.mock(TickerRepository.class);
	private UpdateShareValue updateShareValue = new UpdateShareValue(shareValueRepository, userRepository, tickerRepository);

	@Test
	void testUpdateAllUsersShareValue() {
		// given
		ArgumentCaptor<ShareValue> shareValueCaptor = ArgumentCaptor.forClass(ShareValue.class);
		User user = Utils.getUser();
		ShareValue shareValue = new ShareValue(null, 0L, user, 100, 100, 100);
		Ticker ticker = new Ticker("ETH-USD", Currency.ETH, Currency.USD, 1.0, "exchange", 0.0, 0.0, 0.0, 0.0, 0.0, 0L);
		Position ethPosition = new Position("ETH-USD", Currency.ETH, Currency.USD, 10.0, 200.0, null, null, null, null, null);
		user.getPositions().add(ethPosition);
		given(userRepository.findAllUsers()).willReturn(singletonList(user));
		given(tickerRepository.findAllById(singletonList("ETH-USD"))).willReturn(singletonList(ticker));
		given(shareValueRepository.findTop1ByUserOrderByTimestampDesc(user)).willReturn(shareValue);

		// when
		updateShareValue.updateAllUsersShareValue();

		// then
		then(userRepository).should().findAllUsers();
		then(tickerRepository).should().findAllById(singletonList("ETH-USD"));
		then(shareValueRepository).should().findTop1ByUserOrderByTimestampDesc(user);
		then(shareValueRepository).should().save(shareValueCaptor.capture());
		ShareValue shareValueActual = shareValueCaptor.getValue();

		// TODO to finish that test and test deeply
	}
}
