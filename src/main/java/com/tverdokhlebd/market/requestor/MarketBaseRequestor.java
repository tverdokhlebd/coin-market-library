package com.tverdokhlebd.market.requestor;

import java.util.AbstractMap.SimpleEntry;
import java.util.Date;
import java.util.List;

import com.tverdokhlebd.market.CoinMarket;
import com.tverdokhlebd.market.MarketCaching;
import com.tverdokhlebd.market.MarketType;
import com.tverdokhlebd.mining.coin.CoinType;
import com.tverdokhlebd.mining.http.BaseRequestor;
import com.tverdokhlebd.mining.http.RequestException;

import okhttp3.OkHttpClient;

/**
 * Coin market base HTTP requestor.
 *
 * @author Dmitry Tverdokhleb
 *
 */
public abstract class MarketBaseRequestor extends BaseRequestor<CoinMarket.Builder> implements MarketRequestor, MarketCaching {

    /**
     * Creates instance.
     *
     * @param httpClient HTTP client
     */
    protected MarketBaseRequestor(OkHttpClient httpClient) {
        super(httpClient);
    }

    @Override
    public CoinMarket requestCoin(CoinType coinType) throws MarketRequestorException {
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
            throw new MarketRequestorException(e);
        }
    }

    /**
     * Gets market type.
     *
     * @return market type
     */
    protected abstract MarketType geMarketType();

    /**
     * Gets list of urls.
     *
     * @param coinType type of coin
     * @return list of urls
     */
    protected abstract List<SimpleEntry<String, String>> getUrlList(CoinType coinType);

}
