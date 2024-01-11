package com.bitpay.sdk.t;

import com.bitpay.sdk.Client;
import com.bitpay.sdk.ConfigFilePath;
import com.bitpay.sdk.PosToken;
import com.bitpay.sdk.exceptions.BitPayGenericException;

public class ClientProvider {

    public static Client create() {
        try {
            return Client.createClientByConfigFilePath(new ConfigFilePath("some/config/path")); // use BitPaySetup
        } catch (BitPayGenericException e) {
            throw new RuntimeException(e);
        }
    }

    public static Client createPos() {
        try {
            return new Client(new PosToken("someToken"));
        } catch (BitPayGenericException e) {
            throw new RuntimeException(e);
        }
    }
}
