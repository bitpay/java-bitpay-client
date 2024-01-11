/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.util.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeToIso8601Serializer extends JsonSerializer<ZonedDateTime> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSX");

    /**
     * This method deserialize ZonedDateTime to format yyyy-mm-ddThh:mm:ssZ.
     *
     * @param zonedDateTime      ZonedDateTime
     * @param jsonGenerator      JsonGenerator
     * @param serializerProvider SerializerProvider
     * @throws IOException IOException
     */
    @Override
    public void serialize(
        ZonedDateTime zonedDateTime,
        JsonGenerator jsonGenerator,
        SerializerProvider serializerProvider
    ) throws IOException {
        jsonGenerator.writeString(zonedDateTime.format(DATE_TIME_FORMATTER));
    }
}
