package com.tverdokhlebd.coin.market;

import static com.tverdokhlebd.mining.commons.http.ErrorCode.API_ERROR;
import static com.tverdokhlebd.mining.commons.http.ErrorCode.HTTP_ERROR;
import static com.tverdokhlebd.mining.commons.http.ErrorCode.PARSE_ERROR;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.tverdokhlebd.coin.market.CoinMarket;
import com.tverdokhlebd.coin.market.CoinMarketType;
import com.tverdokhlebd.coin.market.requestor.CoinMarketRequestor;
import com.tverdokhlebd.coin.market.requestor.CoinMarketRequestorException;
import com.tverdokhlebd.coin.market.requestor.CoinMarketRequestorFactory;
import com.tverdokhlebd.mining.commons.coin.CoinType;
import com.tverdokhlebd.mining.commons.utils.HttpClientUtils;

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
     * @throws CoinMarketRequestorException if there is any error in market requesting
     */
    public static void testCoinMarket(OkHttpClient httpClient, CoinMarketType marketType, CoinType coinType, BigDecimal expectedPriceUsd)
            throws CoinMarketRequestorException {
        CoinMarketRequestor marketRequestor = CoinMarketRequestorFactory.create(marketType, httpClient, 0);
        CoinMarket coinMarket = marketRequestor.requestCoinMarket(coinType);
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
     * @throws CoinMarketRequestorException if there is any error in market requesting
     */
    public static void testApiError(OkHttpClient httpClient, CoinMarketType marketType, CoinType coinType, String expectedErrorMessage)
            throws CoinMarketRequestorException {
        CoinMarketRequestor marketRequestor = CoinMarketRequestorFactory.create(marketType, httpClient, 0);
        try {
            marketRequestor.requestCoinMarket(coinType);
        } catch (CoinMarketRequestorException e) {
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
     * @throws CoinMarketRequestorException if there is any error in market requesting
     */
    public static void testInternalServerError(CoinMarketType marketType, CoinType coinType) throws CoinMarketRequestorException {
        OkHttpClient httpClient = HttpClientUtils.createHttpClient(new JSONObject().toString(), 500);
        CoinMarketRequestor marketRequestor = CoinMarketRequestorFactory.create(marketType, httpClient, 0);
        try {
            marketRequestor.requestCoinMarket(coinType);
        } catch (CoinMarketRequestorException e) {
            assertEquals(HTTP_ERROR, e.getErrorCode());
            throw e;
        }
    }

    /**
     * Tests empty response.
     *
     * @param marketType type of market
     * @param coinType type of coin
     * @throws CoinMarketRequestorException if there is any error in market requesting
     */
    public static void testEmptyResponse(CoinMarketType marketType, CoinType coinType) throws CoinMarketRequestorException {
        OkHttpClient httpClient = HttpClientUtils.createHttpClient(new JSONObject().toString(), 200);
        CoinMarketRequestor marketRequestor = CoinMarketRequestorFactory.create(marketType, httpClient, 0);
        try {
            marketRequestor.requestCoinMarket(coinType);
        } catch (CoinMarketRequestorException e) {
            assertEquals(PARSE_ERROR, e.getErrorCode());
            throw e;
        }
    }

    /**
     * Tests unsupported coin.
     *
     * @param marketType type of market
     * @param coinType type of coin
     * @throws CoinMarketRequestorException if there is any error in market requesting
     */
    public static void testUnsupportedCoin(CoinMarketType marketType, CoinType coinType) throws CoinMarketRequestorException {
        OkHttpClient httpClient = HttpClientUtils.createHttpClient(new JSONObject().toString(), 200);
        CoinMarketRequestor marketRequestor = CoinMarketRequestorFactory.create(marketType, httpClient, 0);
        try {
            marketRequestor.requestCoinMarket(coinType);
        } catch (IllegalArgumentException e) {
            assertEquals(coinType.name() + " is not supported", e.getMessage());
            throw e;
        }
    }

}
