package com.bitpay.sdk.model.Bill;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {

    private String _id;
    private String _description;
    private Double _price;
    private Integer _quantity;

    public Item() {
    }

    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getId() {
        return _id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this._id = id;
    }

    @JsonProperty("description")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getDescription() {
        return _description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this._description = description;
    }

    @JsonProperty("price")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Double getPrice() {
        return _price;
    }

    @JsonProperty("price")
    public void setPrice(Double price) {
        this._price = price;
    }

    @JsonProperty("quantity")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Integer getQuantity() {
        return _quantity;
    }

    @JsonProperty("quantity")
    public void setQuantity(Integer quantity) {
        this._quantity = quantity;
    }
}
