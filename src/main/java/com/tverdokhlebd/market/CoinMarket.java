package com.tverdokhlebd.market;

import java.math.BigDecimal;

import com.tverdokhlebd.mining.coin.CoinType;

/**
 * Market information about coin (rank, price, market cap and etc.).
 *
 * @author Dmitry Tverdokhleb
 *
 */
public class CoinMarket {

    /** Type of coin. */
    private final CoinType coin;
    /** Price of coin. */
    private final BigDecimal price;

    /**
     * Creates instance.
     *
     * @param coin type of coin
     * @param price price of coin
     */
    private CoinMarket(CoinType coin, BigDecimal price) {
        super();
        this.coin = coin;
        this.price = price.stripTrailingZeros();
    }

    /**
     * Gets coin.
     *
     * @return coin
     */
    public CoinType getCoin() {
        return coin;
    }

    /**
     * Gets price.
     *
     * @return price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Builder of coin market.
     */
    public static class Builder {

        /** Type of coin. */
        private CoinType coin;
        /** Price of coin. */
        private BigDecimal price;

        /**
         * Creates instance.
         */
        public Builder() {
            super();
        }

        /**
         * Sets coin.
         *
         * @param coin new coin
         */
        public void setCoin(CoinType coin) {
            this.coin = coin;
        }

        /**
         * Sets price.
         *
         * @param price new price
         */
        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        /**
         * Builds coin market.
         *
         * @return coin market
         */
        public CoinMarket build() {
            return new CoinMarket(coin, price);
        }

    }

}
