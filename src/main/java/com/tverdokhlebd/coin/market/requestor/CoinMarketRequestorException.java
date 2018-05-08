package com.tverdokhlebd.coin.market.requestor;

import com.tverdokhlebd.mining.commons.http.ErrorCode;
import com.tverdokhlebd.mining.commons.http.RequestException;

/**
 * Exception for working with coin market requestor.
 *
 * @author Dmitry Tverdokhleb
 *
 */
public class CoinMarketRequestorException extends RequestException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 3067598387432040867L;

    /**
     * Creates instance.
     *
     * @param e request exception
     */
    public CoinMarketRequestorException(RequestException e) {
        super(e.getErrorCode(), e.getMessage());
    }

    /**
     * Creates the instance.
     *
     * @param errorCode error code
     * @param message the detail message
     */
    public CoinMarketRequestorException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    /**
     * Creates the instance.
     *
     * @param errorCode error code
     * @param cause the cause
     */
    public CoinMarketRequestorException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

}
