package com.bitpay.sdk.model.Bill;

import com.bitpay.sdk.BitPayException;
import com.bitpay.sdk.model.Currency;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Bill {

    private String _currency;
    private String _token = "";
    private String _email;
    private List<Item> _items;
    private String _number;
    private String _name;
    private String _address1;
    private String _address2;
    private String _city;
    private String _state;
    private String _zip;
    private String _country;
    private List<String> _cc;
    private String _phone;
    private String _dueDate;
    private boolean _passProcessingFee;
    private String _status;
    private String _url;
    private String _createDate;
    private String _id;
    private String _merchant;

    /**
     * Constructor, create an empty Bill object.
     */
    public Bill() {
    }

    /**
     * Constructor, create a minimal request Bill object.
     *
     * @param number   A string for tracking purposes.
     * @param currency The three digit currency type used to compute the bill's amount.
     * @param email    The email address of the receiver for this bill.
     * @param items    The list of itens to add to this bill.
     */
    public Bill(String number, String currency, String email, List<Item> items) {
        this._number = number;
        this._currency = currency;
        this._email = email;
        this._items = items;
    }

    // API fields
    //

    @JsonProperty("token")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getToken() {
        return _token;
    }

    @JsonProperty("token")
    public void setToken(String _token) {
        this._token = _token;
    }

    // Required fields
    //
    @JsonProperty("currency")
    public String getCurrency() {
        return _currency;
    }

    @JsonProperty("currency")
    public void setCurrency(String _currency) throws BitPayException {
        if (!Currency.isValid(_currency))
            throw new BitPayException("Error: currency code must be a type of Model.Currency");

        this._currency = _currency;
    }

    @JsonProperty("email")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getEmail() {
        return _email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this._email = email;
    }

    @JsonProperty("items")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public List<Item> getItems() {
        return _items;
    }

    @JsonProperty("items")
    public void setItems(List<Item> items) {
        this._items = items;
    }

    // Optional fields
    //

    @JsonProperty("number")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getNumber() {
        return _number;
    }

    @JsonProperty("number")
    public void setNumber(String number) {
        this._number = number;
    }

    @JsonProperty("name")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getName() {
        return _name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this._name = name;
    }

    @JsonProperty("address1")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getAddress1() {
        return _address1;
    }

    @JsonProperty("address1")
    public void setAddress1(String address1) {
        this._address1 = address1;
    }

    @JsonProperty("address2")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getAddress2() {
        return _address2;
    }

    @JsonProperty("address2")
    public void setAddress2(String address2) {
        this._address2 = address2;
    }

    @JsonProperty("city")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getCity() {
        return _city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this._city = city;
    }

    @JsonProperty("state")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getState() {
        return _state;
    }

    @JsonProperty("state")
    public void setState(String state) {
        this._state = state;
    }

    @JsonProperty("zip")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getZip() {
        return _zip;
    }

    @JsonProperty("zip")
    public void setZip(String zip) {
        this._zip = zip;
    }

    @JsonProperty("country")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getCountry() {
        return _country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this._country = country;
    }

    @JsonProperty("cc")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public List<String> getCc() {
        return _cc;
    }

    @JsonProperty("cc")
    public void setCc(List<String> cc) {
        this._cc = cc;
    }

    @JsonProperty("phone")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getPhone() {
        return _phone;
    }

    @JsonProperty("phone")
    public void setPhone(String phone) {
        this._phone = phone;
    }

    @JsonProperty("dueDate")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getDueDate() {
        return _dueDate;
    }

    @JsonProperty("dueDate")
    public void setDueDate(String dueDate) {
        this._dueDate = dueDate;
    }

    @JsonProperty("passProcessingFee")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public boolean getPassProcessingFee() {
        return _passProcessingFee;
    }

    @JsonProperty("passProcessingFee")
    public void setPassProcessingFee(boolean passProcessingFee) {
        this._passProcessingFee = passProcessingFee;
    }

    // Response fields
    //

    @JsonIgnore
    public String getStatus() {
        return _status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this._status = status;
    }

    @JsonIgnore
    public String getUrl() {
        return _url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this._url = url;
    }

    @JsonIgnore
    public String getCreateDate() {
        return _createDate;
    }

    @JsonProperty("createDate")
    public void setCreateDate(String createDate) {
        this._createDate = createDate;
    }

    @JsonIgnore
    public String getId() {
        return _id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this._id = id;
    }

    @JsonIgnore
    public String getMerchant() {
        return _merchant;
    }

    @JsonProperty("merchant")
    public void setMerchant(String merchant) {
        this._merchant = merchant;
    }
}
