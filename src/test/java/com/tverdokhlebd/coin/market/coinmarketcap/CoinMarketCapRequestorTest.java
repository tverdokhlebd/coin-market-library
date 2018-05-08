package com.tverdokhlebd.coin.market.coinmarketcap;

import static com.tverdokhlebd.coin.market.CoinMarketType.COIN_MARKET_CAP;
import static com.tverdokhlebd.mining.coin.CoinType.BCH;
import static com.tverdokhlebd.mining.coin.CoinType.BTC;

import java.math.BigDecimal;

import org.json.JSONObject;
import org.junit.Test;

import com.tverdokhlebd.coin.market.Utils;
import com.tverdokhlebd.coin.market.requestor.CoinMarketRequestorException;
import com.tverdokhlebd.mining.utils.HttpClientUtils;

import okhttp3.OkHttpClient;

/**
 * Tests of CoinMarketCap market requestor.
 *
 * @author Dmitry Tverdokhleb
 *
 */
public class CoinMarketCapRequestorTest {

    @Test
    public void testCoinMarket() throws CoinMarketRequestorException {
        JSONObject response = new JSONObject("{  \n" +
                "  \"data\":{  \n" +
                "    \"id\":1,\n" +
                "    \"name\":\"Bitcoin\",\n" +
                "    \"symbol\":\"BTC\",\n" +
                "    \"website_slug\":\"bitcoin\",\n" +
                "    \"rank\":1,\n" +
                "    \"circulating_supply\":17020612.0,\n" +
                "    \"total_supply\":17020612.0,\n" +
                "    \"max_supply\":21000000.0,\n" +
                "    \"quotes\":{  \n" +
                "      \"USD\":{  \n" +
                "        \"price\":9398.58,\n" +
                "        \"volume_24h\":7196620000.0,\n" +
                "        \"market_cap\":159969583531.0,\n" +
                "        \"percent_change_1h\":0.2,\n" +
                "        \"percent_change_24h\":-1.8,\n" +
                "        \"percent_change_7d\":1.25\n" +
                "      }\n" +
                "    },\n" +
                "    \"last_updated\":1525721071\n" +
                "  },\n" +
                "  \"metadata\":{  \n" +
                "    \"timestamp\":1525721148,\n" +
                "    \"error\":null\n" +
                "  }\n" +
                "}");
        OkHttpClient httpClient = HttpClientUtils.createHttpClient(response.toString(), 200);
        BigDecimal expectedPriceUsd = BigDecimal.valueOf(9398.58);
        Utils.testCoinMarket(httpClient, COIN_MARKET_CAP, BTC, expectedPriceUsd);
    }

    @Test(expected = CoinMarketRequestorException.class)
    public void testApiError() throws CoinMarketRequestorException {
        JSONObject response = new JSONObject("{  \n" +
                "  \"data\":null,\n" +
                "  \"metadata\":{  \n" +
                "    \"timestamp\":1525722438,\n" +
                "    \"error\":\"id not found\"\n" +
                "  }\n" +
                "}");
        OkHttpClient httpClient = HttpClientUtils.createHttpClient(response.toString(), 200);
        Utils.testApiError(httpClient, COIN_MARKET_CAP, BTC, "id not found");
    }

    @Test(expected = CoinMarketRequestorException.class)
    public void testInternalServerError() throws CoinMarketRequestorException {
        Utils.testInternalServerError(COIN_MARKET_CAP, BTC);
    }

    @Test(expected = CoinMarketRequestorException.class)
    public void testEmptyResponse() throws CoinMarketRequestorException {
        Utils.testEmptyResponse(COIN_MARKET_CAP, BTC);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnsupportedCoin() throws CoinMarketRequestorException {
        Utils.testUnsupportedCoin(COIN_MARKET_CAP, BCH);
    }

}
