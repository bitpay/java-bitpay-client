/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GuidGeneratorTest {

    @Test
    public void it_should_generate_uuid() {
        // given
        final int uuid4length = 36;
        GuidGenerator testedClass = this.getTestedClass();

        String uuid1 = testedClass.execute();
        String uuid2 = testedClass.execute();

        Assertions.assertNotEquals(uuid1, uuid2);
        Assertions.assertEquals(uuid4length, uuid1.length());
        Assertions.assertEquals(uuid4length, uuid2.length());
    }

    private GuidGenerator getTestedClass() {
        return new GuidGenerator();
    }
}
