package fr.cph.crypto.core

import fr.cph.crypto.core.api.entity.Currency
import fr.cph.crypto.core.api.entity.Ticker
import fr.cph.crypto.core.core.TickerServiceImpl
import fr.cph.crypto.core.spi.TickerClient
import fr.cph.crypto.core.spi.TickerRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mockito

class TickerServiceImplTest {

    private val tickerClient = Mockito.mock(TickerClient::class.java)
    private val tickerRepository = Mockito.mock(TickerRepository::class.java)

    private val tickerService = TickerServiceImpl(tickerClient, tickerRepository)

    @Test
    fun testFindOne() {
        // given
        val tickerId = "BTC-USD"
        val ticker = Ticker(null, Currency.BTC, Currency.USD, 1.0, "exchange", 0.0, 0.0, 0.0, 0.0, 0.0, 0L)
        given(tickerRepository.findOne(tickerId)).willReturn(ticker)

        // when
        val actual = tickerService.findOne(tickerId)

        // then
        then(tickerRepository).should().findOne(tickerId)
        assertEquals(actual, ticker)
    }

    @Test
    fun testFindAllById() {
        // given
        val tickerIds = listOf("BTC-USD")
        val ticker = Ticker(null, Currency.BTC, Currency.USD, 1.0, "exchange", 0.0, 0.0, 0.0, 0.0, 0.0, 0L)
        given(tickerRepository.findAllById(tickerIds)).willReturn(listOf(ticker))

        // when
        val actual = tickerService.findAllById(tickerIds)

        // then
        then(tickerRepository).should().findAllById(tickerIds)
        assertEquals(actual[0], ticker)
    }

    @Test
    fun testFindAll() {
        // given
        val ticker = Ticker(null, Currency.BTC, Currency.USD, 1.0, "exchange", 0.0, 0.0, 0.0, 0.0, 0.0, 0L)
        given(tickerRepository.findAll()).willReturn(listOf(ticker))

        // when
        val actual = tickerService.findAll()

        // then
        then(tickerRepository).should().findAll()
        assertEquals(actual[0], ticker)
    }

    @Test
    fun testUpdateAll() {
        // given
        val ticker = Ticker(null, Currency.BTC, Currency.USD, 1.0, "exchange", 0.0, 0.0, 0.0, 0.0, 0.0, 0L)
        given(tickerClient.getTickers(Currency.USD, Currency.cryptoCurrenciesAsListOfString())).willReturn(listOf(ticker))

        // when
        tickerService.updateAll()

        // then
        then(tickerClient).should().getTickers(Currency.USD, Currency.cryptoCurrenciesAsListOfString())
        then(tickerRepository).should().save(ticker)
    }
}