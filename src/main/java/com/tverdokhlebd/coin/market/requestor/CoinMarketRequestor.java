package com.tverdokhlebd.coin.market.requestor;

import com.tverdokhlebd.coin.market.CoinMarket;
import com.tverdokhlebd.mining.coin.CoinType;

/**
 * Interface for requesting coin market.
 *
 * @author Dmitry Tverdokhleb
 *
 */
public interface CoinMarketRequestor {

    /**
     * Requests coin market.
     *
     * @param coinType type of coin
     * @return coin market
     * @throws CoinMarketRequestorException if there is any error in market requesting
     */
    CoinMarket requestCoinMarket(CoinType coinType) throws CoinMarketRequestorException;

}
