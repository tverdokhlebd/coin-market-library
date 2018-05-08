package com.tverdokhlebd.coin.market;

import static com.tverdokhlebd.mining.coin.CoinType.BTC;
import static com.tverdokhlebd.mining.coin.CoinType.ETC;
import static com.tverdokhlebd.mining.coin.CoinType.ETH;
import static com.tverdokhlebd.mining.coin.CoinType.XMR;
import static com.tverdokhlebd.mining.coin.CoinType.ZEC;

import java.util.Arrays;
import java.util.List;

import com.tverdokhlebd.mining.coin.CoinType;

/**
 * Enumerations of coin market types.
 *
 * @author Dmitry Tverdokhleb
 *
 */
public enum CoinMarketType {

    COIN_MARKET_CAP(Arrays.asList(BTC, ETH, ETC, XMR, ZEC));

    /** Supported list of coin types. */
    private final List<CoinType> coinTypeList;

    /**
     * Creates instance.
     *
     * @param coinTypeList supported list of coin types
     */
    private CoinMarketType(List<CoinType> coinTypeList) {
        this.coinTypeList = coinTypeList;
    }

    /**
     * Gets coin type list.
     *
     * @return coin type list
     */
    public List<CoinType> getCoinTypeList() {
        return coinTypeList;
    }

}
