package com.bitpay.sdk.model;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class PolicyTest {
  @Test
  public void givenSetPolicyFields_thenReturnGetPolicyFields() {
    Policy policy = new Policy();
    List<String> params = new ArrayList<String>();
    
    String expectedSIN = "Tf1XQVBRPxVeQEQXUb7NTtcoB1qbAzGYBQ9";
    params.add(expectedSIN);

    String expectedPolicy = "sin";
    String expectedMethod = "requireSin";
    List<String> expectedParams = params;

    policy.setPolicy(expectedPolicy);
    policy.setMethod(expectedMethod);
    policy.setParams(expectedParams);

    String actualPolicy = policy.getPolicy();
    String actualMethod = policy.getMethod();
    List<String> actualParams = policy.getParams();

    assertEquals(expectedPolicy, actualPolicy);
    assertEquals(expectedMethod, actualMethod);
    assertEquals(expectedParams, actualParams);
  }
}
