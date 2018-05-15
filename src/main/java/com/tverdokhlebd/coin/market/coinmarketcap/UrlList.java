package com.tverdokhlebd.coin.market.coinmarketcap;

import static com.tverdokhlebd.mining.commons.coin.CoinType.BTC;
import static com.tverdokhlebd.mining.commons.coin.CoinType.ETC;
import static com.tverdokhlebd.mining.commons.coin.CoinType.ETH;
import static com.tverdokhlebd.mining.commons.coin.CoinType.XMR;
import static com.tverdokhlebd.mining.commons.coin.CoinType.ZEC;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tverdokhlebd.mining.commons.coin.CoinType;

/**
 * List of urls for requestor.
 *
 * @author Dmitry Tverdokhleb
 *
 */
class UrlList {

    /** Map of urls. */
    static final Map<CoinType, List<SimpleEntry<String, String>>> URL_MAP = new HashMap<>();
    /** Request name of BTC coin market. */
    static final String BTC_REQUEST_NAME = "BTC_REQUEST_NAME";
    /** Request name of ETH coin market. */
    static final String ETH_REQUEST_NAME = "ETH_REQUEST_NAME";
    /** Request name of ETC coin market. */
    static final String ETC_REQUEST_NAME = "ETC_REQUEST_NAME";
    /** Request name of XMR coin market. */
    static final String XMR_REQUEST_NAME = "XMR_REQUEST_NAME";
    /** Request name of ZEC coin market. */
    static final String ZEC_REQUEST_NAME = "ZEC_REQUEST_NAME";
    /** Fills map of urls. */
    static {
        List<SimpleEntry<String, String>> btcUrlList = new ArrayList<>();
        btcUrlList.add(new SimpleEntry<String, String>(BTC_REQUEST_NAME,
                                                       "https://api.coinmarketcap.com/v2/ticker/1"));
        URL_MAP.put(BTC, btcUrlList);
        List<SimpleEntry<String, String>> ethUrlList = new ArrayList<>();
        ethUrlList.add(new SimpleEntry<String, String>(ETH_REQUEST_NAME,
                                                       "https://api.coinmarketcap.com/v2/ticker/1027"));
        URL_MAP.put(ETH, ethUrlList);
        List<SimpleEntry<String, String>> etcUrlList = new ArrayList<>();
        etcUrlList.add(new SimpleEntry<String, String>(ETC_REQUEST_NAME,
                                                       "https://api.coinmarketcap.com/v2/ticker/1321"));
        URL_MAP.put(ETC, etcUrlList);
        List<SimpleEntry<String, String>> xmrUrlList = new ArrayList<>();
        xmrUrlList.add(new SimpleEntry<String, String>(XMR_REQUEST_NAME,
                                                       "https://api.coinmarketcap.com/v2/ticker/328"));
        URL_MAP.put(XMR, xmrUrlList);
        List<SimpleEntry<String, String>> zecUrlList = new ArrayList<>();
        zecUrlList.add(new SimpleEntry<String, String>(ZEC_REQUEST_NAME,
                                                       "https://api.coinmarketcap.com/v2/ticker/1437"));
        URL_MAP.put(ZEC, zecUrlList);
    }

}
