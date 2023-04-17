/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * The type Policy.
 *
 * @see <a href="https://bitpay.readme.io/reference/tokens">Tokens concept</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Policy {

    private String policy;
    private String method;
    private List<String> params;

    /**
     * Instantiates a new Policy.
     */
    public Policy() {
    }

    /**
     * Gets policy. Can be "sin", "access", "events", "id", or "session".
     *
     * @return the policy
     */
    @JsonIgnore
    public String getPolicy() {
        return this.policy;
    }

    /**
     * Sets policy. Can be "sin", "access", "events", "id", or "session".
     *
     * @param policy the policy
     */
    @JsonProperty("policy")
    public void setPolicy(String policy) {
        this.policy = policy;
    }

    /**
     * Gets method. Can be "requireSin", "requireFacadeAccess", "allowEventStream", "invalidated", "inactive",
     * "unclaimed", "requireSession".
     *
     * @return the method
     */
    @JsonIgnore
    public String getMethod() {
        return method;
    }

    /**
     * Sets method. Can be "requireSin", "requireFacadeAccess", "allowEventStream", "invalidated", "inactive",
     * "unclaimed", "requireSession".
     *
     * @param method the method
     */
    @JsonProperty("method")
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * Gets params. Can be "support", SIN value, or null.
     *
     * @return the params
     */
    @JsonIgnore
    public List<String> getParams() {
        return params;
    }

    /**
     * Sets params. Can be "support", SIN value, or null.
     *
     * @param params the params
     */
    @JsonProperty("params")
    public void setParams(List<String> params) {
        this.params = params;
    }
}
