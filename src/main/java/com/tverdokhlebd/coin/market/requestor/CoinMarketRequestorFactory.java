package com.tverdokhlebd.coin.market.requestor;

import com.tverdokhlebd.coin.market.CoinMarketType;
import com.tverdokhlebd.coin.market.coinmarketcap.CoinMarketCapRequestor;
import com.tverdokhlebd.mining.http.HttpClientFactory;

import okhttp3.OkHttpClient;

/**
 * Factory for creating coin market requestor.
 *
 * @author Dmitry Tverdokhleb
 *
 */
public class CoinMarketRequestorFactory {

    /** Endpoints update. */
    private static final int ENDPOINTS_UPDATE = 6;

    /**
     * Creates coin market requestor.
     *
     * @param coinMarketType coin market type
     * @return coin market requestor
     */
    public static CoinMarketRequestor create(CoinMarketType coinMarketType) {
        OkHttpClient httpClient = HttpClientFactory.create();
        return create(coinMarketType, httpClient);
    }

    /**
     * Creates coin market requestor.
     *
     * @param coinMarketType coin market type
     * @param httpClient HTTP client
     * @return coin market requestor
     */
    public static CoinMarketRequestor create(CoinMarketType coinMarketType, OkHttpClient httpClient) {
        return create(coinMarketType, httpClient, ENDPOINTS_UPDATE);
    }

    /**
     * Creates coin market requestor.
     *
     * @param coinMarketType coin market type
     * @param httpClient HTTP client
     * @param endpointsUpdate endpoints update
     * @return coin market requestor
     */
    public static CoinMarketRequestor create(CoinMarketType coinMarketType, OkHttpClient httpClient, int endpointsUpdate) {
        switch (coinMarketType) {
        case COIN_MARKET_CAP: {
            return new CoinMarketCapRequestor(httpClient, endpointsUpdate);
        }
        default:
            throw new IllegalArgumentException(coinMarketType.name());
        }
    }

}
