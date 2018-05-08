package com.tverdokhlebd.coin.market.requestor;

import java.util.AbstractMap.SimpleEntry;
import java.util.Date;
import java.util.List;

import com.tverdokhlebd.coin.market.CoinMarket;
import com.tverdokhlebd.coin.market.CoinMarketCaching;
import com.tverdokhlebd.coin.market.CoinMarketType;
import com.tverdokhlebd.mining.commons.coin.CoinType;
import com.tverdokhlebd.mining.commons.http.BaseRequestor;
import com.tverdokhlebd.mining.commons.http.RequestException;

import okhttp3.OkHttpClient;

/**
 * Coin market base HTTP requestor.
 *
 * @author Dmitry Tverdokhleb
 *
 */
public abstract class CoinMarketBaseRequestor extends BaseRequestor<CoinMarket.Builder> implements CoinMarketRequestor, CoinMarketCaching {

    /**
     * Creates instance.
     *
     * @param httpClient HTTP client
     */
    protected CoinMarketBaseRequestor(OkHttpClient httpClient) {
        super(httpClient);
    }

    @Override
    public CoinMarket requestCoinMarket(CoinType coinType) throws CoinMarketRequestorException {
        if (geMarketType().getCoinTypeList().indexOf(coinType) == -1) {
            throw new IllegalArgumentException(coinType.name() + " is not supported");
        }
        try {
            if (new Date().after(getCachedNextUpdate(coinType))) {
                CoinMarket.Builder coinMarketBuilder = new CoinMarket.Builder();
                coinMarketBuilder.setCoin(coinType);
                List<SimpleEntry<String, String>> urlList = getUrlList(coinType);
                for (int i = 0; i < urlList.size(); i++) {
                    SimpleEntry<String, String> urlEntry = urlList.get(i);
                    String requestName = urlEntry.getKey();
                    String preparedUrl = urlEntry.getValue();
                    super.request(preparedUrl, requestName, coinMarketBuilder);
                }
                setCachedCoinMarket(coinType, coinMarketBuilder.build());
            }
            return getCachedCoinMarket(coinType);
        } catch (RequestException e) {
            throw new CoinMarketRequestorException(e);
        }
    }

    /**
     * Gets coin market type.
     *
     * @return coin market type
     */
    protected abstract CoinMarketType geMarketType();

    /**
     * Gets list of urls.
     *
     * @param coinType type of coin
     * @return list of urls
     */
    protected abstract List<SimpleEntry<String, String>> getUrlList(CoinType coinType);

}
