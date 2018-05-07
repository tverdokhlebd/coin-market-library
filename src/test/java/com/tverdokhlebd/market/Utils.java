package com.tverdokhlebd.market;

import static com.tverdokhlebd.mining.http.ErrorCode.API_ERROR;
import static com.tverdokhlebd.mining.http.ErrorCode.HTTP_ERROR;
import static com.tverdokhlebd.mining.http.ErrorCode.PARSE_ERROR;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.tverdokhlebd.market.requestor.MarketRequestor;
import com.tverdokhlebd.market.requestor.MarketRequestorException;
import com.tverdokhlebd.market.requestor.MarketRequestorFactory;
import com.tverdokhlebd.mining.coin.CoinType;
import com.tverdokhlebd.mining.utils.HttpClientUtils;

import okhttp3.OkHttpClient;

/**
 * Utils for tests.
 *
 * @author Dmitry Tverdokhleb
 *
 */
public class Utils {

    /**
     * Tests coin market.
     *
     * @param httpClient HTTP client
     * @param marketType type of market
     * @param coinType type of coin
     * @param expectedPriceUsd expected price in USD
     * @throws MarketRequestorException if there is any error in market requesting
     */
    public static void testCoinMarket(OkHttpClient httpClient, MarketType marketType, CoinType coinType, BigDecimal expectedPriceUsd)
            throws MarketRequestorException {
        MarketRequestor marketRequestor = MarketRequestorFactory.create(marketType, httpClient, 0);
        CoinMarket coinMarket = marketRequestor.requestCoin(coinType);
        assertEquals(coinType, coinMarket.getCoin());
        assertEquals(expectedPriceUsd, coinMarket.getPrice());
    }

    /**
     * Tests API error.
     *
     * @param httpClient HTTP client
     * @param marketType type of market
     * @param coinType type of coin
     * @param expectedErrorMessage expected error message
     * @throws MarketRequestorException if there is any error in market requesting
     */
    public static void testApiError(OkHttpClient httpClient, MarketType marketType, CoinType coinType, String expectedErrorMessage)
            throws MarketRequestorException {
        MarketRequestor marketRequestor = MarketRequestorFactory.create(marketType, httpClient, 0);
        try {
            marketRequestor.requestCoin(coinType);
        } catch (MarketRequestorException e) {
            assertEquals(API_ERROR, e.getErrorCode());
            assertEquals(expectedErrorMessage, e.getMessage());
            throw e;
        }
    }

    /**
     * Tests internal server error.
     *
     * @param marketType type of market
     * @param coinType type of coin
     * @throws MarketRequestorException if there is any error in market requesting
     */
    public static void testInternalServerError(MarketType marketType, CoinType coinType) throws MarketRequestorException {
        OkHttpClient httpClient = HttpClientUtils.createHttpClient(new JSONObject().toString(), 500);
        MarketRequestor marketRequestor = MarketRequestorFactory.create(marketType, httpClient, 0);
        try {
            marketRequestor.requestCoin(coinType);
        } catch (MarketRequestorException e) {
            assertEquals(HTTP_ERROR, e.getErrorCode());
            throw e;
        }
    }

    /**
     * Tests empty response.
     *
     * @param marketType type of market
     * @param coinType type of coin
     * @throws MarketRequestorException if there is any error in market requesting
     */
    public static void testEmptyResponse(MarketType marketType, CoinType coinType) throws MarketRequestorException {
        OkHttpClient httpClient = HttpClientUtils.createHttpClient(new JSONObject().toString(), 200);
        MarketRequestor marketRequestor = MarketRequestorFactory.create(marketType, httpClient, 0);
        try {
            marketRequestor.requestCoin(coinType);
        } catch (MarketRequestorException e) {
            assertEquals(PARSE_ERROR, e.getErrorCode());
            throw e;
        }
    }

    /**
     * Tests unsupported coin.
     *
     * @param marketType type of market
     * @param coinType type of coin
     * @throws MarketRequestorException if there is any error in market requesting
     */
    public static void testUnsupportedCoin(MarketType marketType, CoinType coinType) throws MarketRequestorException {
        OkHttpClient httpClient = HttpClientUtils.createHttpClient(new JSONObject().toString(), 200);
        MarketRequestor marketRequestor = MarketRequestorFactory.create(marketType, httpClient, 0);
        try {
            marketRequestor.requestCoin(coinType);
        } catch (IllegalArgumentException e) {
            assertEquals(coinType.name() + " is not supported", e.getMessage());
            throw e;
        }
    }

}
