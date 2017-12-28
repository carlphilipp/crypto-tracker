package fr.cph.crypto.core;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.cph.crypto.core.api.entity.Currency;
import fr.cph.crypto.core.api.entity.Email;
import fr.cph.crypto.core.api.entity.Position;
import fr.cph.crypto.core.api.entity.Role;
import fr.cph.crypto.core.api.entity.Ticker;
import fr.cph.crypto.core.api.entity.User;
import fr.cph.crypto.core.core.UserServiceImpl;
import fr.cph.crypto.core.spi.ContextService;
import fr.cph.crypto.core.spi.EmailService;
import fr.cph.crypto.core.spi.IdGenerator;
import fr.cph.crypto.core.spi.PasswordEncoder;
import fr.cph.crypto.core.spi.ShareValueRepository;
import fr.cph.crypto.core.spi.TemplateService;
import fr.cph.crypto.core.spi.TickerRepository;
import fr.cph.crypto.core.spi.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

public class UserServiceImplTest {
	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private ShareValueRepository shareValueRepository = Mockito.mock(ShareValueRepository.class);
	private TickerRepository tickerRepository = Mockito.mock(TickerRepository.class);
	private IdGenerator idGenerator = Mockito.mock(IdGenerator.class);
	private TemplateService templateService = Mockito.mock(TemplateService.class);
	private ContextService contextService = Mockito.mock(ContextService.class);
	private EmailService emailService = Mockito.mock(EmailService.class);
	private PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
	private UserServiceImpl tickerService = new UserServiceImpl(userRepository, shareValueRepository, tickerRepository, idGenerator, passwordEncoder,
			templateService, contextService, emailService);

	@Test
	public void testCreate() {
		// given
		User user = getUser();
		Email email = new Email("email", "Welcome to crypto tracker!", "email content");
		given(idGenerator.getNewId()).willReturn("ID");
		given(passwordEncoder.encode(user.getPassword())).willReturn("encodedPassword");
		given(passwordEncoder.encode("IDencodedPassword")).willReturn("key");
		given(userRepository.saveUser(user)).willReturn(user);
		given(contextService.getBaseUrl()).willReturn("localhost");
		given(templateService.welcomeContentEmail("localhost", "ID", "key")).willReturn("email content");

		// when
		User actual = tickerService.create(user);

		// then
		assertNotNull(actual);
		then(idGenerator).should().getNewId();
		then(passwordEncoder).should().encode("password");
		then(passwordEncoder).should().encode("IDencodedPassword");
		then(userRepository).should().saveUser(user);
		then(emailService).should().sendWelcomeEmail(email);
		then(contextService).should().getBaseUrl();
		then(templateService).should().welcomeContentEmail("localhost", "ID", "key");
	}

	@Test
	public void testFindOneUserWithPositions() {
		// given
		Ticker btcTicker = new Ticker("BTC-USD", Currency.BTC, Currency.USD, 10000.0, "whatever", 0.0, 0.0, 0.0, 0.0, 0.0, 1L);
		Ticker ethTicker = new Ticker("ETH-USD", Currency.ETH, Currency.USD, 100.0, "whatever", 0.0, 0.0, 0.0, 0.0, 0.0, 2L);
		Position btcPosition = new Position("BTC-USD", Currency.BTC, Currency.USD, 1.0, 5000.0, null, null, null, null, null);
		Position ethPosition = new Position("ETH-USD", Currency.ETH, Currency.USD, 10.0, 200.0, null, null, null, null, null);
		List<Position> positions = Arrays.asList(btcPosition, ethPosition);
		User user = getUser();
		user.setPositions(positions);
		given(userRepository.findOneUserById("id")).willReturn(user);
		given(tickerRepository.findAllById(Arrays.asList("BTC-USD", "ETH-USD"))).willReturn(Arrays.asList(btcTicker, ethTicker));

		// when
		User actual = tickerService.findOne("id");

		// then
		then(userRepository).should().findOneUserById("id");
		then(tickerRepository).should().findAllById(Arrays.asList("BTC-USD", "ETH-USD"));
		assertEquals(user, actual);
		assertEquals(11000.0, actual.getValue().doubleValue());
		assertEquals(7000.0, actual.getOriginalValue().doubleValue());
		assertEquals(4000.0, actual.getGain().doubleValue());
		assertEquals(0.5714285714285714, actual.getGainPercentage().doubleValue());
		assertEquals(10000.0, actual.getPositions().get(0).getValue().doubleValue());
		assertEquals(5000.0, actual.getPositions().get(0).getOriginalValue().doubleValue());
		assertEquals(5000.0, actual.getPositions().get(0).getGain().doubleValue());
		assertEquals(1.0, actual.getPositions().get(0).getGainPercentage().doubleValue());
		assertEquals(1L, actual.getPositions().get(0).getLastUpdated().longValue());
		assertEquals(1000.0, actual.getPositions().get(1).getValue().doubleValue());
		assertEquals(2000.0, actual.getPositions().get(1).getOriginalValue().doubleValue());
		assertEquals(-1000.0, actual.getPositions().get(1).getGain().doubleValue());
		assertEquals(-0.5, actual.getPositions().get(1).getGainPercentage().doubleValue());
		assertEquals(2L, actual.getPositions().get(1).getLastUpdated().longValue());
	}

	@Test
	public void testFindOneUserNoPositions() {
		// given
		User user = getUser();
		given(userRepository.findOneUserById("id")).willReturn(user);

		// when
		User actual = tickerService.findOne("id");

		// then
		then(userRepository).should().findOneUserById("id");
		assertEquals(user, actual);
		assertEquals(0.0, actual.getValue().doubleValue());
		assertEquals(0.0, actual.getOriginalValue().doubleValue());
		assertEquals(0.0, actual.getGain().doubleValue());
		assertEquals(0.0, actual.getGainPercentage().doubleValue());
	}

	@Test
	public void testFindAll() {
		// given
		User user = getUser();
		given(userRepository.findAllUsers()).willReturn(Arrays.asList(user));

		// when
		List<User> actual = tickerService.findAll();

		// then
		then(userRepository).should().findAllUsers();
		assertEquals(user, actual.get(0));
		assertEquals(0.0, actual.get(0).getValue().doubleValue());
		assertEquals(0.0, actual.get(0).getOriginalValue().doubleValue());
		assertEquals(0.0, actual.get(0).getGain().doubleValue());
		assertEquals(0.0, actual.get(0).getGainPercentage().doubleValue());
	}

	@Test
	public void testAddPosition() {
		// given
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		User user = getUser();
		Position ethPosition = new Position("ETH-USD", Currency.ETH, Currency.USD, 10.0, 200.0, null, null, null, null, null);
		user.getPositions().add(ethPosition);

		Position btcPosition = new Position("BTC-USD", Currency.BTC, Currency.USD, 1.0, 5000.0, null, null, null, null, null);
		given(userRepository.findOneUserById("id")).willReturn((user));
		given(idGenerator.getNewId()).willReturn(("positionId"));

		// when
		tickerService.addPosition("id", btcPosition);

		// then
		then(userRepository).should().findOneUserById("id");
		then(idGenerator).should().getNewId();
		then(userRepository).should().savePosition(userCaptor.capture(), eq(btcPosition));
		User actualUser = userCaptor.getValue();
		assertEquals(5000.0, actualUser.getLiquidityMovement());
		assertEquals(Currency.BTC, actualUser.getPositions().get(0).getCurrency1());
		assertEquals("positionId", actualUser.getPositions().get(0).getId());
		assertEquals(Currency.ETH, actualUser.getPositions().get(1).getCurrency1());
	}

	private User getUser() {
		return new User(null, "email", "password", Role.USER, Currency.USD, 0.0, true, null, null, null, null, new ArrayList<>());
	}
}
