package com.tverdokhlebd.coin.market.coinmarketcap;

import static com.tverdokhlebd.coin.market.CoinMarketType.COIN_MARKET_CAP;
import static com.tverdokhlebd.coin.market.coinmarketcap.UrlList.BTC_REQUEST_NAME;
import static com.tverdokhlebd.coin.market.coinmarketcap.UrlList.ETC_REQUEST_NAME;
import static com.tverdokhlebd.coin.market.coinmarketcap.UrlList.ETH_REQUEST_NAME;
import static com.tverdokhlebd.coin.market.coinmarketcap.UrlList.URL_MAP;
import static com.tverdokhlebd.coin.market.coinmarketcap.UrlList.XMR_REQUEST_NAME;
import static com.tverdokhlebd.coin.market.coinmarketcap.UrlList.ZEC_REQUEST_NAME;
import static com.tverdokhlebd.mining.commons.coin.CoinType.BTC;
import static com.tverdokhlebd.mining.commons.coin.CoinType.ETC;
import static com.tverdokhlebd.mining.commons.coin.CoinType.ETH;
import static com.tverdokhlebd.mining.commons.coin.CoinType.XMR;
import static com.tverdokhlebd.mining.commons.coin.CoinType.ZEC;
import static com.tverdokhlebd.mining.commons.http.ErrorCode.API_ERROR;
import static com.tverdokhlebd.mining.commons.http.ErrorCode.PARSE_ERROR;

import java.math.BigDecimal;
import java.util.AbstractMap.SimpleEntry;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.tverdokhlebd.coin.market.CoinMarket;
import com.tverdokhlebd.coin.market.CoinMarket.Builder;
import com.tverdokhlebd.coin.market.CoinMarketType;
import com.tverdokhlebd.coin.market.requestor.CoinMarketBaseRequestor;
import com.tverdokhlebd.mining.commons.coin.CoinType;
import com.tverdokhlebd.mining.commons.http.RequestException;
import com.tverdokhlebd.mining.commons.utils.TimeUtils;

import okhttp3.OkHttpClient;

/**
 * CoinMarketCap coin market requestor.
 *
 * @author Dmitry Tverdokhleb
 *
 */
public class CoinMarketCapRequestor extends CoinMarketBaseRequestor {

    /** Endpoints update. */
    private final int endpointsUpdate;
    /** Map of cached coin markets. */
    private static final Map<CoinType, SimpleEntry<CoinMarket, Date>> CACHED_COIN_MARKET_MAP = new ConcurrentHashMap<>();

    /**
     * Creates instance.
     *
     * @param httpClient HTTP client
     * @param endpointsUpdate endpoints update
     */
    public CoinMarketCapRequestor(OkHttpClient httpClient, int endpointsUpdate) {
        super(httpClient);
        this.endpointsUpdate = endpointsUpdate;
    }

    @Override
    public Date getCachedNextUpdate(CoinType coinType) {
        SimpleEntry<CoinMarket, Date> cachedCoinMarket = CACHED_COIN_MARKET_MAP.get(coinType);
        return cachedCoinMarket == null ? new Date(0) : cachedCoinMarket.getValue();
    }

    @Override
    public CoinMarket getCachedCoinMarket(CoinType coinType) {
        return CACHED_COIN_MARKET_MAP.get(coinType).getKey();
    }

    @Override
    public void setCachedCoinMarket(CoinType coinType, CoinMarket coinMarket) {
        SimpleEntry<CoinMarket, Date> cachedCoinMarket = CACHED_COIN_MARKET_MAP.get(coinType);
        CACHED_COIN_MARKET_MAP.replace(coinType, new SimpleEntry<CoinMarket, Date>(coinMarket, cachedCoinMarket.getValue()));
    }

    @Override
    protected CoinMarketType geMarketType() {
        return COIN_MARKET_CAP;
    }

    @Override
    protected List<SimpleEntry<String, String>> getUrlList(CoinType coinType) {
        return URL_MAP.get(coinType);
    }

    @Override
    protected void checkApiError(String responseBody, String requestName) throws RequestException {
        try {
            JSONObject jsonResponse = new JSONObject(responseBody);
            JSONObject metadata = jsonResponse.getJSONObject("metadata");
            String error = metadata.optString("error");
            if (!error.isEmpty()) {
                throw new RequestException(API_ERROR, error);
            }
        } catch (JSONException e) {
            throw new RequestException(PARSE_ERROR, e);
        }
    }

    @Override
    protected void parseResponse(String responseBody, String requestName, Builder result) throws RequestException {
        try {
            JSONObject jsonResponse = new JSONObject(responseBody);
            JSONObject data = jsonResponse.getJSONObject("data");
            BigDecimal price = BigDecimal.valueOf(data.getJSONObject("quotes").getJSONObject("USD").getDouble("price"));
            result.setPrice(price);
            Date lastUpdated = new Date(data.getLong("last_updated") * 1000);
            Date nextUpdate = TimeUtils.addMinutes(lastUpdated, endpointsUpdate);
            CoinType coinType = null;
            switch (requestName) {
            case BTC_REQUEST_NAME: {
                coinType = BTC;
                break;
            }
            case ETH_REQUEST_NAME: {
                coinType = ETH;
                break;
            }
            case ETC_REQUEST_NAME: {
                coinType = ETC;
                break;
            }
            case XMR_REQUEST_NAME: {
                coinType = XMR;
                break;
            }
            case ZEC_REQUEST_NAME: {
                coinType = ZEC;
                break;
            }
            }
            CACHED_COIN_MARKET_MAP.put(coinType, new SimpleEntry<CoinMarket, Date>(null, nextUpdate));
        } catch (JSONException e) {
            throw new RequestException(PARSE_ERROR, e);
        }
    }

}
