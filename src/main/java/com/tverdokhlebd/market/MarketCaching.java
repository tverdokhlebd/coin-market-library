package com.tverdokhlebd.market;

import java.util.Date;

import com.tverdokhlebd.mining.coin.CoinType;

/**
 * Interface for market caching.
 *
 * @author Dmitry Tverdokhleb
 *
 */
public interface MarketCaching {

    /**
     * Gets cached next update.
     *
     * @param coinType type of coin
     * @return cached next update
     */
    Date getCachedNextUpdate(CoinType coinType);

    /**
     * Gets cached coin market.
     *
     * @param coinType type of coin
     * @return cached coin info
     */
    CoinMarket getCachedCoinMarket(CoinType coinType);

    /**
     * Sets cached coin market.
     *
     * @param coinType type of coin
     * @param coinMarket coin market
     */
    void setCachedCoinMarket(CoinType coinType, CoinMarket coinMarket);

}
