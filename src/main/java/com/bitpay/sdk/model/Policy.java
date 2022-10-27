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
 * @see <a href="https://bitpay.com/api/#rest-api-resources">Rest API Resources</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Policy {

    private String _policy;
    private String _method;
    private List<String> _params;

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
        return _policy;
    }

    /**
     * Sets policy. Can be "sin", "access", "events", "id", or "session".
     *
     * @param policy the policy
     */
    @JsonProperty("policy")
    public void setPolicy(String policy) {
        this._policy = policy;
    }

    /**
     * Gets method. Can be "requireSin", "requireFacadeAccess", "allowEventStream", "invalidated", "inactive",
     * "unclaimed", "requireSession".
     *
     * @return the method
     */
    @JsonIgnore
    public String getMethod() {
        return _method;
    }

    /**
     * Sets method. Can be "requireSin", "requireFacadeAccess", "allowEventStream", "invalidated", "inactive",
     * "unclaimed", "requireSession".
     *
     * @param method the method
     */
    @JsonProperty("method")
    public void setMethod(String method) {
        this._method = method;
    }

    /**
     * Gets params. Can be "support", SIN value, or null.
     *
     * @return the params
     */
    @JsonIgnore
    public List<String> getParams() {
        return _params;
    }

    /**
     * Sets params. Can be "support", SIN value, or null.
     *
     * @param params the params
     */
    @JsonProperty("params")
    public void setParams(List<String> params) {
        this._params = params;
    }
}
