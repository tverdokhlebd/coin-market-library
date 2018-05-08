package com.tverdokhlebd.market.requestor;

import com.tverdokhlebd.market.CoinMarket;
import com.tverdokhlebd.mining.coin.CoinType;

/**
 * Interface for requesting coin market.
 *
 * @author Dmitry Tverdokhleb
 *
 */
public interface MarketRequestor {

    /**
     * Requests coin market.
     *
     * @param coinType type of coin
     * @return coin market
     * @throws MarketRequestorException if there is any error in market requesting
     */
    CoinMarket requestCoinMarket(CoinType coinType) throws MarketRequestorException;

}
