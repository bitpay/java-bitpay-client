package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InvoicePaymentUrls {

	private String _BIP21 = "";
	private String _BIP72 = "";
	private String _BIP72b = "";
	private String _BIP73 = "";
	
    public InvoicePaymentUrls() {}

    @JsonIgnore
	public String getBIP21() {
		return _BIP21;
	}
        
    @JsonProperty("BIP21")
	public void setBIP21(String _BIP21) {
		this._BIP21 = _BIP21;
	}

    @JsonIgnore
	public String getBIP72() {
		return _BIP72;
	}
        
    @JsonProperty("BIP72")
	public void setBIP72(String _BIP72) {
		this._BIP72 = _BIP72;
	}

    @JsonIgnore
	public String getBIP72b() {
		return _BIP72b;
	}
        
    @JsonProperty("BIP72b")
	public void setBIP72b(String _BIP72b) {
		this._BIP72b = _BIP72b;
	}

    @JsonIgnore
	public String getBIP73() {
		return _BIP73;
	}
        
    @JsonProperty("BIP73")
	public void setBIP73(String _BIP73) {
		this._BIP73 = _BIP73;
	}
}
