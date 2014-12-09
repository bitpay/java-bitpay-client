package model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InvoiceBuyer {

	private String _name = "";
	private String _address1 = "";
	private String _address2 = "";
	private String _locality = "";
	private String _region = "";
	private String _postalCode = "";
	private String _country = "";
	private String _email = "";
	private String _phone = "";

    public InvoiceBuyer() {}    
    
    @JsonProperty("name")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public String getName() {
		return _name;
	}
	
    @JsonProperty("name")
	public void setName(String _name) {
		this._name = _name;
	}

    @JsonProperty("address1")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public String getAddress1() {
		return _address1;
	}
	
    @JsonProperty("address1")
	public void setAddress1(String _address1) {
		this._address1 = _address1;
	}

    @JsonProperty("address2")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public String getAddress2() {
		return _address2;
	}
	
    @JsonProperty("address2")
	public void setaddress2(String _address2) {
		this._address2 = _address2;
	}

    @JsonProperty("locality")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public String getLocality() {
		return _locality;
	}
	
    @JsonProperty("locality")
	public void setLocality(String _locality) {
		this._locality = _locality;
	}

    @JsonProperty("region")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public String getRegion() {
		return _region;
	}
	
    @JsonProperty("region")
	public void setRegion(String _region) {
		this._region = _region;
	}

    @JsonProperty("postalCode")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public String getPostalCode() {
		return _postalCode;
	}
	
    @JsonProperty("postalCode")
	public void setPostalCode(String _postalCode) {
		this._postalCode = _postalCode;
	}

    @JsonProperty("country")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public String getCountry() {
		return _country;
	}
	
    @JsonProperty("country")
	public void setCountry(String _country) {
		this._country = _country;
	}

    @JsonProperty("email")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public String getEmail() {
		return _email;
	}
	
    @JsonProperty("email")
	public void setEmail(String _email) {
		this._email = _email;
	}

    @JsonProperty("phone")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public String getPhone() {
		return _phone;
	}
	
    @JsonProperty("phone")
	public void setPhone(String _phone) {
		this._phone = _phone;
	}
   
}
