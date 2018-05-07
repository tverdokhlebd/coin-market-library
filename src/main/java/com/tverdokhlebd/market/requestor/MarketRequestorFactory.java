package com.tverdokhlebd.market.requestor;

import com.tverdokhlebd.market.MarketType;
import com.tverdokhlebd.market.coinmarketcap.CoinMarketCapMarketRequestor;
import com.tverdokhlebd.mining.http.HttpClientFactory;

import okhttp3.OkHttpClient;

/**
 * Factory for creating market requestor.
 *
 * @author Dmitry Tverdokhleb
 *
 */
public class MarketRequestorFactory {

    /** Endpoints update. */
    private static final int ENDPOINTS_UPDATE = 6;

    /**
     * Creates market requestor.
     *
     * @param marketType market type
     * @return market requestor
     */
    public static MarketRequestor create(MarketType marketType) {
        OkHttpClient httpClient = HttpClientFactory.create();
        return create(marketType, httpClient);
    }

    /**
     * Creates market requestor.
     *
     * @param marketType market type
     * @param httpClient HTTP client
     * @return market requestor
     */
    public static MarketRequestor create(MarketType marketType, OkHttpClient httpClient) {
        return create(marketType, httpClient, ENDPOINTS_UPDATE);
    }

    /**
     * Creates market requestor.
     *
     * @param marketType market type
     * @param httpClient HTTP client
     * @param endpointsUpdate endpoints update
     * @return market requestor
     */
    public static MarketRequestor create(MarketType marketType, OkHttpClient httpClient, int endpointsUpdate) {
        switch (marketType) {
        case COIN_MARKET_CAP: {
            return new CoinMarketCapMarketRequestor(httpClient, endpointsUpdate);
        }
        default:
            throw new IllegalArgumentException(marketType.name());
        }
    }

}
