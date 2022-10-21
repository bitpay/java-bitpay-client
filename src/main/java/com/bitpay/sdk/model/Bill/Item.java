/*
 * Copyright (c) 2019 BitPay
 */
package com.bitpay.sdk.model.Bill;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type Item.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {

    private String _id;
    private String _description;
    private Double _price;
    private Integer _quantity;

    /**
     * Instantiates a new Item.
     */
    public Item() {
    }

    /**
     * Gets item resource Id.
     *
     * @return the id
     */
    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getId() {
        return _id;
    }

    /**
     * Sets item resource Id.
     *
     * @param id the id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this._id = id;
    }

    /**
     * Gets line item description.
     *
     * @return the description
     */
    @JsonProperty("description")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getDescription() {
        return _description;
    }

    /**
     * Sets line item description.
     *
     * @param description the description
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this._description = description;
    }

    /**
     * Gets line item unit price for the corresponding currency.
     *
     * @return the price
     */
    @JsonProperty("price")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Double getPrice() {
        return _price;
    }

    /**
     * Sets line item unit price for the corresponding currency.
     *
     * @param price the price
     */
    @JsonProperty("price")
    public void setPrice(Double price) {
        this._price = price;
    }

    /**
     * Gets line item number of units.
     *
     * @return the quantity
     */
    @JsonProperty("quantity")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Integer getQuantity() {
        return _quantity;
    }

    /**
     * Sets line item number of units.
     *
     * @param quantity the quantity
     */
    @JsonProperty("quantity")
    public void setQuantity(Integer quantity) {
        this._quantity = quantity;
    }
}
