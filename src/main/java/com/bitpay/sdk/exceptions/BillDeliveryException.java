package com.bitpay.sdk.exceptions;

public class BillDeliveryException extends BillException {
    /**
     * Construct the BillDeliveryException.
     *
     * @param status String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public BillDeliveryException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to deliver bill";
        String BitPayCode = "BITPAY-BILL-DELIVERY";

        message = BitPayCode + ": " + BitPayMessage + " -> " + message;

        return message;
    }
}