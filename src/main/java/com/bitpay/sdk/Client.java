package com.bitpay.sdk;

import com.bitpay.sdk.exceptions.*;
import com.bitpay.sdk.model.Bill.Bill;
import com.bitpay.sdk.model.Facade;
import com.bitpay.sdk.model.Invoice.Invoice;
import com.bitpay.sdk.model.Invoice.PaymentTotal;
import com.bitpay.sdk.model.Ledger.Ledger;
import com.bitpay.sdk.model.Ledger.LedgerEntry;
import com.bitpay.sdk.model.Payout.PayoutBatch;
import com.bitpay.sdk.model.Rate.Rate;
import com.bitpay.sdk.model.Rate.Rates;
import com.bitpay.sdk.model.Refund;
import com.bitpay.sdk.model.RefundHelper;
import com.bitpay.sdk.model.Settlement.Settlement;
import com.bitpay.sdk.model.Token;
import com.bitpay.sdk.util.BitPayLogger;
import com.bitpay.sdk.util.KeyUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.bitcoinj.core.ECKey;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author Antonio Buedo
 * @version 3.1.1910
 * See bitpay.com/api for more information.
 * date 16.10.2019
 */

public class Client {

    private static final BitPayLogger _log = new BitPayLogger(BitPayLogger.DEBUG);
    private Config _configuration;
    private String _env;
    private Hashtable<String, String> _tokenCache; // {facade, token}
    private String _configFilePath;
    private String _baseUrl;
    private ECKey _ecKey;
    private HttpClient _httpClient = null;

    /**
     * Return the identity of this client (i.e. the public key).
     */
    private String _identity;

    /**
     * Constructor for use if the keys and SIN are managed by this library.
     *
     * @param environment    Target environment. Options: Env.Test / Env.Prod
     * @param privateKeyPath Private Key file path.
     * @param tokens         Env.Tokens containing the available tokens.
     * @throws BitPayException BitPayException class
     */
    public Client(String environment, String privateKeyPath, Env.Tokens tokens) throws BitPayException {
        try {
            this._env = environment;
            this.BuildConfig(privateKeyPath, tokens);
            this.initKeys();
            this.init();
        } catch (JsonProcessingException e) {
            throw new BitPayException("failed to deserialize BitPay server response (Config) : " + e.getMessage());
        } catch (URISyntaxException e) {
            throw new BitPayException("failed to deserialize BitPay server response (Config) : " + e.getMessage());
        } catch (Exception e) {
            throw new BitPayException("failed to deserialize BitPay server response (Config) : " + e.getMessage());
        }
    }

    /**
     * Constructor for use if the keys and SIN are managed by this library.
     *
     * @param configFilePath The path to the configuration file.
     * @throws BitPayException BitPayException class
     */
    public Client(String configFilePath) throws BitPayException {
        try {
            this._configFilePath = configFilePath;
            this.GetConfig();
            this.initKeys();
            this.init();
        } catch (JsonProcessingException e) {
            throw new BitPayException("failed to deserialize BitPay server response (Config) : " + e.getMessage());
        } catch (URISyntaxException e) {
            throw new BitPayException("failed to deserialize BitPay server response (Config) : " + e.getMessage());
        } catch (Exception e) {
            throw new BitPayException("failed to deserialize BitPay server response (Config) : " + e.getMessage());
        }
    }

    /**
     * Authorize (pair) this client with the server using the specified pairing code.
     *
     * @param pairingCode A code obtained from the server; typically from bitpay.com/api-tokens.
     * @throws BitPayException BitPayException class
     */
    public void authorizeClient(String pairingCode) throws BitPayException {
        Token token = new Token();
        token.setId(_identity);
        token.setGuid(this.getGuid());
        token.setPairingCode(pairingCode);

        ObjectMapper mapper = new ObjectMapper();

        String json;

        try {
            json = mapper.writeValueAsString(token);
        } catch (JsonProcessingException e) {
            throw new BitPayException("failed to serialize Token object : " + e.getMessage());
        }

        HttpResponse response = this.post("tokens", json);

        List<Token> tokens;

        try {
            tokens = Arrays.asList(mapper.readValue(this.responseToJsonString(response), Token[].class));
        } catch (JsonProcessingException e) {
            throw new BitPayException("failed to deserialize BitPay server response (Tokens) : " + e.getMessage());
        } catch (Exception e) {
            throw new BitPayException("failed to deserialize BitPay server response (Tokens) : " + e.getMessage());
        }

        for (Token t : tokens) {
            _tokenCache.put(t.getFacade(), t.getValue());
        }
    }

    /**
     * Request a pairing code from the BitPay server.
     *
     * @param facade Defines the level of API access being requested
     * @return A pairing code for claim at https://bitpay.com/dashboard/merchant/api-tokens.
     * @throws BitPayException BitPayException class
     */
    public String requestClientAuthorization(String facade) throws BitPayException {
        Token token = new Token();
        token.setId(_identity);
        token.setGuid(this.getGuid());
        token.setFacade(facade);
        token.setCount(1);

        ObjectMapper mapper = new ObjectMapper();

        String json;

        try {
            json = mapper.writeValueAsString(token);
        } catch (JsonProcessingException e) {
            throw new BitPayException("failed to serialize Token object : " + e.getMessage());
        }

        HttpResponse response = this.post("tokens", json);

        List<Token> tokens;

        try {
            tokens = Arrays.asList(mapper.readValue(this.responseToJsonString(response), Token[].class));

            // Expecting a single token resource.
            if (tokens.size() != 1) {
                throw new BitPayException("failed to get token resource; expected 1 token, got " + tokens.size());
            }

        } catch (JsonProcessingException e) {
            throw new BitPayException("failed to deserialize BitPay server response (Tokens) : " + e.getMessage());
        } catch (Exception e) {
            throw new BitPayException("failed to deserialize BitPay server response (Tokens) : " + e.getMessage());
        }

        _tokenCache.put(tokens.get(0).getFacade(), tokens.get(0).getValue());

        return tokens.get(0).getPairingCode();
    }

    /**
     * Specified whether the client has authorization (a token) for the specified facade.
     *
     * @param facade The facade name for which authorization is tested.
     * @return True if this client is authorized, false otherwise.
     */
    public boolean tokenExist(String facade) {
        return _tokenCache.containsKey(facade);
    }

    /**
     * Returns the token for the specified facade.
     *
     * @param facade The facade name for which the token is requested.
     * @return The token for the given facade.
     */
    public String GetTokenByFacade(String facade) {
        if (!_tokenCache.containsKey(facade))
            return "";

        return _tokenCache.get(facade);
    }

    /**
     * Create a BitPay invoice using the Merchant facade.
     *
     * @param invoice An Invoice object with request parameters defined.
     * @return A BitPay generated Invoice object.
     * @throws InvoiceCreationException InvoiceCreationException class
     */
    public Invoice createInvoice(Invoice invoice) throws InvoiceCreationException {
        try {
            return this.createInvoice(invoice, Facade.Merchant, true);
        } catch (Exception e) {
            throw new InvoiceCreationException(e.getMessage());
        }
    }

    /**
     * Create a BitPay invoice.
     *
     * @param invoice     An Invoice object with request parameters defined.
     * @param facade      The facade used to create it.
     * @param signRequest Signed request.
     * @return A BitPay generated Invoice object.
     * @throws BitPayException          BitPayException class
     * @throws InvoiceCreationException InvoiceCreationException class
     */
    public Invoice createInvoice(Invoice invoice, String facade, Boolean signRequest) throws BitPayException, InvoiceCreationException {
        invoice.setToken(this.getAccessToken(facade));
        invoice.setGuid(this.getGuid());
        ObjectMapper mapper = new ObjectMapper();
        String json;

        try {
            json = mapper.writeValueAsString(invoice);
        } catch (JsonProcessingException e) {
            throw new InvoiceCreationException("failed to serialize Invoice object : " + e.getMessage());
        }

        try {
            HttpResponse response = this.post("invoices", json, signRequest);
            invoice = mapper.readerForUpdating(invoice).readValue(this.responseToJsonString(response));
        } catch (Exception e) {
            throw new InvoiceCreationException("failed to deserialize BitPay server response (Invoice) : " + e.getMessage());
        }

        this.cacheToken(invoice.getId(), invoice.getToken());
        return invoice;
    }

    /**
     * Retrieve a BitPay invoice by invoice id using the public facade.
     *
     * @param invoiceId The id of the invoice to retrieve.
     * @return A BitPay Invoice object.
     * @throws InvoiceQueryException InvoiceQueryException class
     */
    public Invoice getInvoice(String invoiceId) throws InvoiceQueryException {
        try {
            return this.getInvoice(invoiceId, Facade.Merchant, true);
        } catch (Exception e) {
            throw new InvoiceQueryException(e.getMessage());
        }
    }

    /**
     * Retrieve a BitPay invoice by invoice id using the specified facade.  The client must have been previously authorized for the specified facade (the public facade requires no authorization).
     *
     * @param invoiceId   The id of the invoice to retrieve.
     * @param facade      The facade used to create it.
     * @param signRequest Signed request.
     * @return A BitPay Invoice object.
     * @throws BitPayException       BitPayException class
     * @throws InvoiceQueryException InvoiceQueryException class
     */
    public Invoice getInvoice(String invoiceId, String facade, Boolean signRequest) throws BitPayException, InvoiceQueryException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.getAccessToken(facade)));

        Invoice invoice;

        try {
            HttpResponse response = this.get("invoices/" + invoiceId, params, signRequest);
            invoice = new ObjectMapper().readValue(this.responseToJsonString(response), Invoice.class);
        } catch (JsonProcessingException e) {
            throw new InvoiceQueryException("failed to deserialize BitPay server response (Invoice) : " + e.getMessage());
        } catch (Exception e) {
            throw new InvoiceQueryException("failed to deserialize BitPay server response (Invoice) : " + e.getMessage());
        }

        return invoice;
    }

    /**
     * Retrieve a collection of BitPay invoices.
     *
     * @param dateStart The first date for the query filter.
     * @param dateEnd   The last date for the query filter.
     * @return A list of BitPay Invoice objects.
     * @throws BitPayException       BitPayException class
     * @throws InvoiceQueryException InvoiceQueryException class
     */
    public List<Invoice> getInvoices(String dateStart, String dateEnd) throws BitPayException, InvoiceQueryException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.getAccessToken(Facade.Merchant)));
        params.add(new BasicNameValuePair("dateStart", dateStart));
        params.add(new BasicNameValuePair("dateEnd", dateEnd));

        List<Invoice> invoices;

        try {
            HttpResponse response = this.get("invoices", params);
            invoices = Arrays.asList(new ObjectMapper().readValue(this.responseToJsonString(response), Invoice[].class));
        } catch (JsonProcessingException e) {
            throw new InvoiceQueryException("failed to deserialize BitPay server response (Invoices) : " + e.getMessage());
        } catch (Exception e) {
            throw new InvoiceQueryException("failed to deserialize BitPay server response (Invoices) : " + e.getMessage());
        }

        return invoices;
    }

    /**
     * Create a BitPay bill using the POS facade.
     *
     * @param bill An Bill object with request parameters defined.
     * @return A BitPay generated Bill object.
     * @throws BillCreationException BillCreationException class
     */
    public Bill createBill(Bill bill) throws BillCreationException {
        try {
            return this.createBill(bill, Facade.Merchant, true);
        } catch (Exception e) {
            throw new BillCreationException(e.getMessage());
        }
    }

    /**
     * Create a BitPay Bill.
     *
     * @param bill        A Bill object with request parameters defined.
     * @param facade      The facade used to create it.
     * @param signRequest Signed request.
     * @return A BitPay generated Bill object.
     * @throws BitPayException       BitPayException class
     * @throws BillCreationException BillCreationException class
     */
    public Bill createBill(Bill bill, String facade, boolean signRequest) throws BitPayException, BillCreationException {
        String token = this.getAccessToken(facade);
        bill.setToken(token);
        ObjectMapper mapper = new ObjectMapper();
        String json;

        try {
            json = mapper.writeValueAsString(bill);
        } catch (JsonProcessingException e) {
            throw new BillCreationException("failed to serialize Bill object : " + e.getMessage());
        }

        try {
            HttpResponse response = this.post("bills", json, signRequest);
            bill = mapper.readerForUpdating(bill).readValue(this.responseToJsonString(response));
        } catch (Exception e) {
            throw new BillCreationException("failed to deserialize BitPay server response (Bill) : " + e.getMessage());
        }

        this.cacheToken(bill.getId(), bill.getToken());
        return bill;
    }

    /**
     * Retrieve a BitPay bill by bill id using the public facade.
     *
     * @param billId The id of the bill to retrieve.
     * @return A BitPay Bill object.
     * @throws BillQueryException BillQueryException class
     */
    public Bill getBill(String billId) throws BillQueryException {
        try {
            return this.getBill(billId, Facade.Merchant, true);
        } catch (Exception e) {
            throw new BillQueryException(e.getMessage());
        }
    }

    /**
     * Retrieve a BitPay bill by bill id using the specified facade.
     *
     * @param billId      The id of the bill to retrieve.
     * @param facade      The facade used to retrieve it.
     * @param signRequest Signed request.
     * @return A BitPay Bill object.
     * @throws BitPayException    BitPayException class
     * @throws BillQueryException BillQueryException class
     */
    public Bill getBill(String billId, String facade, boolean signRequest) throws BitPayException, BillQueryException {
        String token = this.getAccessToken(facade);
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", token));

        Bill bill;

        try {
            HttpResponse response = this.get("bills/" + billId, params, signRequest);
            bill = new ObjectMapper().readValue(this.responseToJsonString(response), Bill.class);
        } catch (JsonProcessingException e) {
            throw new BillQueryException("failed to deserialize BitPay server response (Bill) : " + e.getMessage());
        } catch (Exception e) {
            throw new BillQueryException("failed to deserialize BitPay server response (Bill) : " + e.getMessage());
        }

        return bill;
    }

    /**
     * Retrieve a collection of BitPay bills.
     *
     * @param status The status to filter the bills.
     * @return A list of BitPay Bill objects.
     * @throws BitPayException    BitPayException class
     * @throws BillQueryException BillQueryException class
     */
    public List<Bill> getBills(String status) throws BitPayException, BillQueryException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.getAccessToken(Facade.Merchant)));
        params.add(new BasicNameValuePair("status", status));

        List<Bill> bills;

        try {
            HttpResponse response = this.get("bills", params);
            bills = Arrays.asList(new ObjectMapper().readValue(this.responseToJsonString(response), Bill[].class));
        } catch (JsonProcessingException e) {
            throw new BillQueryException("failed to deserialize BitPay server response (Bills) : " + e.getMessage());
        } catch (Exception e) {
            throw new BillQueryException("failed to deserialize BitPay server response (Bills) : " + e.getMessage());
        }

        return bills;
    }

    /**
     * Retrieve a collection of BitPay bills.
     *
     * @return A list of BitPay Bill objects.
     * @throws BitPayException    BitPayException class
     * @throws BillQueryException BillQueryException class
     */
    public List<Bill> getBills() throws BitPayException, BillQueryException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.getAccessToken(Facade.Merchant)));

        List<Bill> bills;

        try {
            HttpResponse response = this.get("bills", params);
            bills = Arrays.asList(new ObjectMapper().readValue(this.responseToJsonString(response), Bill[].class));
        } catch (JsonProcessingException e) {
            throw new BillQueryException("failed to deserialize BitPay server response (Bills) : " + e.getMessage());
        } catch (Exception e) {
            throw new BillQueryException("failed to deserialize BitPay server response (Bills) : " + e.getMessage());
        }

        return bills;
    }

    /**
     * Update a BitPay Bill.
     *
     * @param bill   A Bill object with the parameters to update defined.
     * @param billId The Id of the Bill to udpate.
     * @return An updated Bill object.
     * @throws BitPayException     BitPayException class
     * @throws BillUpdateException BillUpdateException class
     */
    public Bill updateBill(Bill bill, String billId) throws BitPayException, BillUpdateException {
        ObjectMapper mapper = new ObjectMapper();
        String json;
        try {
            json = mapper.writeValueAsString(bill);
        } catch (JsonProcessingException e) {
            throw new BillUpdateException("failed to serialize Bill object : " + e.getMessage());
        }

        try {
            HttpResponse response = this.update("bills/" + billId, json);
            bill = mapper.readerForUpdating(bill).readValue(this.responseToJsonString(response));
        } catch (Exception e) {
            throw new BillUpdateException("failed to deserialize BitPay server response (Bill) : " + e.getMessage());
        }

        this.cacheToken(bill.getId(), bill.getToken());
        return bill;
    }

    /**
     * Deliver a BitPay Bill.
     *
     * @param billId    The id of the requested bill.
     * @param billToken The token of the requested bill.
     * @return A response status returned from the API.
     * @throws BillDeliveryException BillDeliveryException class
     */
    public String deliverBill(String billId, String billToken) throws BillDeliveryException {
        try {
            return this.deliverBill(billId, billToken, true);
        } catch (Exception e) {
            throw new BillDeliveryException(e.getMessage());
        }
    }

    /**
     * Deliver a BitPay Bill.
     *
     * @param billId      The id of the requested bill.
     * @param billToken   The token of the requested bill.
     * @param signRequest Allow unsigned request
     * @return A response status returned from the API.
     * @throws BillDeliveryException BillDeliveryException class
     */
    public String deliverBill(String billId, String billToken, boolean signRequest) throws BillDeliveryException {
        Map<String, String> map = new HashMap<>();
        map.put("token", billToken);
        ObjectMapper mapper = new ObjectMapper();
        String json;
        String result;
        try {
            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new BillDeliveryException("failed to serialize Bill object : " + e.getMessage());
        }

        try {
            HttpResponse response = this.post("bills/" + billId + "/deliveries", json, signRequest);
            result = this.responseToJsonString(response).replace("\"", "");
        } catch (Exception e) {
            throw new BillDeliveryException("failed to deserialize BitPay server response (Bill) : " + e.getMessage());
        }

        return result;
    }

    /**
     * Retrieve the exchange rate table maintained by BitPay.  See https://bitpay.com/bitcoin-exchange-rates.
     *
     * @return A Rates object populated with the BitPay exchange rate table.
     * @throws RateQueryException RateQueryException class
     */
    public Rates getRates() throws RateQueryException {
        List<Rate> rates;

        try {
            HttpResponse response = this.get("rates");
            rates = Arrays.asList(new ObjectMapper().readValue(this.responseToJsonString(response), Rate[].class));
        } catch (JsonProcessingException e) {
            throw new RateQueryException("failed to deserialize BitPay server response (Rates) : " + e.getMessage());
        } catch (Exception e) {
            throw new RateQueryException("failed to deserialize BitPay server response (Rates) : " + e.getMessage());
        }

        return new Rates(rates, this);
    }

    /**
     * Retrieve a list of ledgers by date range using the merchant facade.
     *
     * @param currency  The three digit currency string for the ledger to retrieve.
     * @param dateStart The first date for the query filter.
     * @param dateEnd   The last date for the query filter.
     * @return A Ledger object populated with the BitPay ledger entries list.
     * @throws BitPayException      BitPayException class
     * @throws LedgerQueryException LedgerQueryException class
     */
    public Ledger getLedger(String currency, String dateStart, String dateEnd) throws BitPayException, LedgerQueryException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.getAccessToken(Facade.Merchant)));
        params.add(new BasicNameValuePair("startDate", dateStart));
        params.add(new BasicNameValuePair("endDate", dateEnd));

        Ledger ledger = new Ledger();

        try {
            HttpResponse response = this.get("ledgers/" + currency, params);
            List<LedgerEntry> ledgerEntries;
            ledgerEntries = Arrays.asList(new ObjectMapper().readValue(this.responseToJsonString(response), LedgerEntry[].class));
            ledgerEntries.remove(null);
            ledgerEntries.remove("");
            ledger.setEntries(ledgerEntries);
        } catch (JsonProcessingException e) {
            throw new LedgerQueryException("failed to deserialize BitPay server response (Ledger) : " + e.getMessage());
        } catch (Exception e) {
            throw new LedgerQueryException("failed to deserialize BitPay server response (Ledger) : " + e.getMessage());
        }

        return ledger;
    }

    /**
     * Retrieve a list of ledgers using the merchant facade.
     *
     * @return A list of Ledger objects populated with the currency and current balance of each one.
     * @throws BitPayException      BitPayException class
     * @throws LedgerQueryException LedgerQueryException class
     */
    public List<Ledger> getLedgers() throws BitPayException, LedgerQueryException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.getAccessToken(Facade.Merchant)));

        List<Ledger> ledgers;

        try {
            HttpResponse response = this.get("ledgers", params);
            ledgers = Arrays.asList(new ObjectMapper().readValue(this.responseToJsonString(response), Ledger[].class));
        } catch (JsonProcessingException e) {
            throw new LedgerQueryException("failed to deserialize BitPay server response (Ledger) : " + e.getMessage());
        } catch (Exception e) {
            throw new LedgerQueryException("failed to deserialize BitPay server response (Ledger) : " + e.getMessage());
        }

        return ledgers;
    }

    /**
     * Submit a BitPay Payout batch.
     *
     * @param batch A PayoutBatch object with request parameters defined.
     * @return A BitPay generated PayoutBatch object.
     * @throws BitPayException         BitPayException class
     * @throws PayoutCreationException PayoutCreationException class
     */
    public PayoutBatch submitPayoutBatch(PayoutBatch batch) throws BitPayException, PayoutCreationException {
        String token = this.getAccessToken(Facade.Payroll);
        batch.setToken(token);
        batch.setGuid(this.getGuid());

        ObjectMapper mapper = new ObjectMapper();
        String json;
        try {
            json = mapper.writeValueAsString(batch);
        } catch (JsonProcessingException e) {
            throw new PayoutCreationException("failed to serialize PayoutBatch object : " + e.getMessage());
        }

        try {
            HttpResponse response = this.post("payouts", json, true);
            batch = mapper.readerForUpdating(batch).readValue(this.responseToJsonString(response));
        } catch (Exception e) {
            throw new PayoutCreationException("failed to deserialize BitPay server response (PayoutBatch) : " + e.getMessage());
        }

        this.cacheToken(batch.getId(), batch.getToken());

        return batch;
    }

    /**
     * Retrieve a collection of BitPay payout batches.
     *
     * @return A list of BitPay PayoutBatch objects.
     * @throws BitPayException      BitPayException class
     * @throws PayoutQueryException PayoutQueryException class
     */
    public List<PayoutBatch> getPayoutBatches() throws BitPayException, PayoutQueryException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.getAccessToken(Facade.Payroll)));

        List<PayoutBatch> batches;

        try {
            HttpResponse response = this.get("payouts", params);
            batches = Arrays.asList(new ObjectMapper().readValue(this.responseToJsonString(response), PayoutBatch[].class));
        } catch (JsonProcessingException e) {
            throw new PayoutQueryException("failed to deserialize BitPay server response (PayoutBatch) : " + e.getMessage());
        } catch (Exception e) {
            throw new PayoutQueryException("failed to deserialize BitPay server response (PayoutBatch) : " + e.getMessage());
        }

        return batches;
    }

    /**
     * Retrieve a collection of BitPay payout batches.
     *
     * @param status The status to filter the Payout Batches.
     * @return A list of BitPay PayoutBatch objects.
     * @throws BitPayException      BitPayException class
     * @throws PayoutQueryException PayoutQueryException class
     */
    public List<PayoutBatch> getPayoutBatches(String status) throws BitPayException, PayoutQueryException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.getAccessToken(Facade.Payroll)));
        params.add(new BasicNameValuePair("status", status));

        List<PayoutBatch> batches;

        try {
            HttpResponse response = this.get("payouts", params);
            batches = Arrays.asList(new ObjectMapper().readValue(this.responseToJsonString(response), PayoutBatch[].class));
        } catch (JsonProcessingException e) {
            throw new PayoutQueryException("failed to deserialize BitPay server response (PayoutBatch) : " + e.getMessage());
        } catch (Exception e) {
            throw new PayoutQueryException("failed to deserialize BitPay server response (PayoutBatch) : " + e.getMessage());
        }

        return batches;
    }

    /**
     * Retrieve a BitPay payout batch by batch id using.  The client must have been previously authorized for the payroll facade.
     *
     * @param batchId The id of the batch to retrieve.
     * @return A BitPay PayoutBatch object.
     * @throws BitPayException      BitPayException class
     * @throws PayoutQueryException PayoutQueryException class
     */
    public PayoutBatch getPayoutBatch(String batchId) throws BitPayException, PayoutQueryException {
        String token = this.getAccessToken(Facade.Payroll);
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", token));

        PayoutBatch batch;

        try {
            HttpResponse response = this.get("payouts/" + batchId, params, true);
            batch = new ObjectMapper().readValue(this.responseToJsonString(response), PayoutBatch.class);
        } catch (JsonProcessingException e) {
            throw new PayoutQueryException("failed to deserialize BitPay server response (PayoutBatch) : " + e.getMessage());
        } catch (Exception e) {
            throw new PayoutQueryException("failed to deserialize BitPay server response (PayoutBatch) : " + e.getMessage());
        }

        return batch;
    }

    /**
     * Cancel a BitPay Payout batch.
     *
     * @param batchId The id of the batch to cancel.
     * @return A BitPay generated PayoutBatch object.
     * @throws PayoutCancellationException PayoutCancellationException class
     */
    public PayoutBatch cancelPayoutBatch(String batchId) throws PayoutCancellationException {
        PayoutBatch batch;

        try {
            batch = getPayoutBatch(batchId);
        } catch (Exception e) {
            throw new PayoutCancellationException("failed to serialize PayoutBatch object : " + e.getMessage());
        }

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", batch.getToken()));

        try {
            HttpResponse response = this.delete("payouts/" + batchId, params);
            batch = new ObjectMapper().readValue(this.responseToJsonString(response), PayoutBatch.class);
        } catch (JsonProcessingException e) {
            throw new PayoutCancellationException("failed to deserialize BitPay server response (PayoutBatch) : " + e.getMessage());
        } catch (Exception e) {
            throw new PayoutCancellationException("failed to deserialize BitPay server response (PayoutBatch) : " + e.getMessage());
        }

        return batch;
    }

    /**
     * Retrieves settlement reports for the calling merchant filtered by query.
     * The `limit` and `offset` parameters
     * specify pages for large query sets.
     *
     * @param currency  The three digit currency string for the ledger to retrieve.
     * @param dateStart The start date for the query.
     * @param dateEnd   The end date for the query.
     * @param status    Can be `processing`, `completed`, or `failed`.
     * @param limit     Maximum number of settlements to retrieve.
     * @param offset    Offset for paging.
     * @return A list of BitPay Settlement objects.
     * @throws BitPayException          BitPayException class
     * @throws SettlementQueryException SettlementQueryException class
     */
    public List<Settlement> getSettlements(String currency, String dateStart, String dateEnd, String status, Integer limit, Integer offset) throws BitPayException, SettlementQueryException {
        status = status != null ? status : "";
        limit = limit != null ? limit : 100;
        offset = offset != null ? offset : 0;

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.getAccessToken(Facade.Merchant)));
        params.add(new BasicNameValuePair("dateStart", dateStart));
        params.add(new BasicNameValuePair("dateEnd", dateEnd));
        params.add(new BasicNameValuePair("currency", currency));
        params.add(new BasicNameValuePair("status", status));
        params.add(new BasicNameValuePair("limit", limit.toString()));
        params.add(new BasicNameValuePair("offset", offset.toString()));

        List<Settlement> settlements;

        try {
            HttpResponse response = this.get("settlements", params);
            settlements = Arrays.asList(new ObjectMapper().readValue(this.responseToJsonString(response), Settlement[].class));
        } catch (JsonProcessingException e) {
            throw new SettlementQueryException("failed to deserialize BitPay server response (Settlement) : " + e.getMessage());
        } catch (Exception e) {
            throw new SettlementQueryException("failed to deserialize BitPay server response (Settlement) : " + e.getMessage());
        }

        return settlements;
    }

    /**
     * Retrieves a summary of the specified settlement.
     *
     * @param settlementId Settlement Id.
     * @return A BitPay Settlement object.
     * @throws BitPayException          BitPayException class
     * @throws SettlementQueryException SettlementQueryException class
     */
    public Settlement getSettlement(String settlementId) throws BitPayException, SettlementQueryException {
        String token = this.getAccessToken(Facade.Merchant);
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", token));

        Settlement settlement;

        try {
            HttpResponse response = this.get("settlements/" + settlementId, params);
            settlement = new ObjectMapper().readValue(this.responseToJsonString(response), Settlement.class);
        } catch (JsonProcessingException e) {
            throw new SettlementQueryException("failed to deserialize BitPay server response (Settlement) : " + e.getMessage());
        } catch (Exception e) {
            throw new SettlementQueryException("failed to deserialize BitPay server response (Settlement) : " + e.getMessage());
        }

        return settlement;
    }

    /**
     * Gets a detailed reconciliation report of the activity within the settlement period.
     *
     * @param settlement Settlement to generate report for.
     * @return A detailed BitPay Settlement object.
     * @throws SettlementQueryException SettlementQueryException class
     */
    public Settlement getSettlementReconciliationReport(Settlement settlement) throws SettlementQueryException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", settlement.getToken()));

        Settlement reconciliationReport;

        try {
            HttpResponse response = this.get("settlements/" + settlement.getId() + "/reconciliationReport", params);
            reconciliationReport = new ObjectMapper().readValue(this.responseToJsonString(response), Settlement.class);
        } catch (JsonProcessingException e) {
            throw new SettlementQueryException("failed to deserialize BitPay server response (ReconciliationReport) : " + e.getMessage());
        } catch (Exception e) {
            throw new SettlementQueryException("failed to deserialize BitPay server response (ReconciliationReport) : " + e.getMessage());
        }

        return reconciliationReport;
    }

    /**
     * Checks whether a BitPay invoice has been paid in full.
     * Returns true if the amountPaid higher or equal to paymentTotals, returns false otherwise
     *
     * @param invoice A Bitpay invoice object
     * @return true if the amountPaid higher or equal to paymentTotals, returns false otherwise
     * @throws BitPayException BitPayException class
     */
    public boolean isFullyPaid(Invoice invoice) throws BitPayException {
        try {
            long amountPaid = invoice.getAmountPaid();
            String transactionCurrency = invoice.getTransactionCurrency();
            // first check if invoice has a transactionCurrency. If not, this means the invoice has not been paid
            if (transactionCurrency == null) {
                return false;
            }
            PaymentTotal paymentTotals = invoice.getPaymentTotals();
            if (transactionCurrency.equals("BTC")) {
                return amountPaid >= paymentTotals.getBTC();
            } else if (transactionCurrency.equals("BCH")) {
                return amountPaid >= paymentTotals.getBCH();
            }
        } catch (Exception e) {
            throw new BitPayException("failed to check whether a BitPay invoice has been paid in full : " + e.getMessage());
        }

        return true;
    }

    // TODO refactor when resource is available

    /**
     * Request a full refund for a BitPay invoice.  The invoice full price and currency type are used in the request.
     *
     * @param invoiceId   The id of the BitPay invoice for which a refund request should be made.
     * @param refundEmail The email of the buyer to which the refund email will be sent
     * @return A BitPay RefundRequest object with the new Refund object.
     * @throws RefundCreationException RefundCreationException class
     */
    public RefundHelper requestRefund(String invoiceId, String refundEmail) throws RefundCreationException {
        try {
            Invoice invoice = this.getInvoice(invoiceId);
            return this.requestRefund(invoice, refundEmail, invoice.getPrice(), invoice.getCurrency());
        } catch (Exception e) {
            throw new RefundCreationException(e.getMessage());
        }
    }

    /**
     * Request a refund for a BitPay invoice.
     *
     * @param invoice     A BitPay invoice object for which a refund request should be made.  Must have been obtained using the merchant facade.
     * @param refundEmail The email of the buyer to which the refund email will be sent
     * @param amount      The amount of money to refund. If zero then a request for 100% of the invoice value is created.
     * @param currency    The three digit currency code specifying the exchange rate to use when calculating the refund bitcoin amount. If this value is "BTC" then no exchange rate calculation is performed.
     * @return A BitPay RefundRequest object with the new Refund object.
     * @throws BitPayException BitPayException class
     */
    public RefundHelper requestRefund(Invoice invoice, String refundEmail, Double amount, String currency) throws BitPayException {

        Refund refund = new Refund();
        refund.setToken(invoice.getToken());
        refund.setGuid(this.getGuid());
        refund.setAmount(amount);
        refund.setRefundEmail(refundEmail);
        refund.setCurrency(currency);

        ObjectMapper mapper = new ObjectMapper();

        String json;

        try {
            json = mapper.writeValueAsString(refund);
        } catch (JsonProcessingException e) {
            throw new BitPayException("failed to serialize Refund object : " + e.getMessage());
        }

        HttpResponse response = this.post("invoices/" + invoice.getId() + "/refunds", json, true);

        try {
            refund = mapper.readerForUpdating(refund).readValue(this.responseToJsonString(response));
        } catch (JsonProcessingException e) {
            throw new BitPayException("failed to deserialize BitPay server response (Refund) : " + e.getMessage());
        } catch (Exception e) {
            throw new BitPayException("failed to deserialize BitPay server response (Refund) : " + e.getMessage());
        }

        try {
            this.cacheToken(refund.getId(), refund.getToken());
        } catch (java.lang.NullPointerException e) {
            // When using the email address to refund, BitPay does not instantly return a request id
            // Instead BitPay returns 'success':'true'.
            // BitPay only returns a supportRequestId when the customer has entered his BTC/BCH refund address
        }
        return new RefundHelper(refund, invoice);
    }

    /**
     * Cancel a previously submitted refund request on a BitPay invoice.
     *
     * @param invoiceId The BitPay invoiceId having the associated refund to be canceled.
     * @param refundId  The refund id for the refund to be canceled.
     * @return True if the refund was successfully canceled, false otherwise.
     * @throws RefundCancellationException RefundCancellationException class
     */
    public boolean cancelRefundRequest(String invoiceId, String refundId) throws RefundCancellationException {
        try {
            Invoice invoice = this.getInvoice(invoiceId);
            return this.cancelRefundRequest(invoice, refundId);
        } catch (Exception e) {
            throw new RefundCancellationException(e.getMessage());
        }
    }

    /**
     * Cancel a previously submitted refund request on a BitPay invoice.
     *
     * @param invoice  The BitPay invoice having the associated refund to be canceled. Must have been obtained using the merchant facade.
     * @param refundId The refund id for the refund to be canceled.
     * @return True if the refund was successfully canceled, false otherwise.
     * @throws BitPayException BitPayException class
     */
    public boolean cancelRefundRequest(Invoice invoice, String refundId) throws BitPayException {
        Refund refund = this.getRefund(invoice, refundId);
        if (refund == null) {
            throw new BitPayException("refundId is not associated with specified invoice");
        }

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", refund.getToken()));

        HttpResponse response = this.delete("invoices/" + invoice.getId() + "/refunds/" + refundId, params);
        String result = this.responseToJsonString(response);

        return (result.equals("\"Success\""));
    }

    /**
     * Retrieve a previously made refund request on a BitPay invoice.
     *
     * @param invoice  The BitPay invoice having the associated refund.
     * @param refundId The refund id for the refund to be updated with new status.
     * @return A BitPay invoice object with the associated Refund object updated.
     * @throws BitPayException BitPayException class
     */
    public Refund getRefund(Invoice invoice, String refundId) throws BitPayException {
        Refund refund = new Refund();

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", invoice.getToken()));
        HttpResponse response = this.get("invoices/" + invoice.getId() + "/refunds/" + refundId, params);

        ObjectMapper mapper = new ObjectMapper();

        try {
            refund = mapper.readerForUpdating(refund).readValue(this.responseToJsonString(response));
        } catch (JsonProcessingException e) {
            throw new BitPayException("failed to deserialize BitPay server response (Refund) : " + e.getMessage());
        } catch (Exception e) {
            throw new BitPayException("failed to deserialize BitPay server response (Refund) : " + e.getMessage());
        }

        return refund;
    }

    /**
     * Retrieve all refund requests on a BitPay invoice.
     *
     * @param invoice The BitPay invoice object having the associated refunds.
     * @return A BitPay invoice object with the associated Refund objects updated.
     * @throws BitPayException BitPayException class
     */
    public List<Refund> getAllRefunds(Invoice invoice) throws BitPayException {
        List<Refund> refunds;
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", invoice.getToken()));

        HttpResponse response = this.get("invoices/" + invoice.getId() + "/refunds", params);

        try {
            refunds = Arrays.asList(new ObjectMapper().readValue(this.responseToJsonString(response), Refund[].class));
        } catch (JsonProcessingException e) {
            throw new BitPayException("failed to deserialize BitPay server response (Refund) : " + e.getMessage());
        } catch (Exception e) {
            throw new BitPayException("failed to deserialize BitPay server response (Refund) : " + e.getMessage());
        }

        return refunds;
    }
    // TODO refactor the above when resource is available

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Initialize this object with the client name and the environment Url.
     *
     * @throws Exception
     * @throws URISyntaxException
     */
    private void init() throws BitPayException {
        try {
            this._baseUrl = this._env.equals(Env.Test) ? Env.TestUrl : Env.ProdUrl;
            _httpClient = HttpClientBuilder.create().build();
            deriveIdentity();
            LoadAccessTokens();
        } catch (Exception e) {
            throw new BitPayException("failed to deserialize BitPay server response (Token array) : " + e.getMessage());
        }
    }

    /**
     * Initialize the public/private key pair by either loading the existing one or by creating a new one.
     *
     * @throws Exception
     * @throws URISyntaxException
     */
    private void initKeys() throws Exception, URISyntaxException {
        if (KeyUtils.privateKeyExists(this._configuration.getEnvConfig(this._env).path("PrivateKeyPath").toString().replace("\"", ""))) {
            _ecKey = KeyUtils.loadEcKey();
        } else {
            _ecKey = KeyUtils.createEcKey();
            KeyUtils.saveEcKey(_ecKey);
        }
    }

    private void deriveIdentity() throws IllegalArgumentException {
        // Identity in this implementation is defined to be the SIN.
        _identity = KeyUtils.deriveSIN(_ecKey);
    }

    private void clearAccessTokenCache() {
        _tokenCache = new Hashtable<String, String>();
    }

    /**
     * Add this token to the token cache.
     *
     * @param key   The token type.
     * @param token The token value.
     * @return The token associated with resource.
     */
    private void cacheToken(String key, String token) throws BitPayException {
        // we add the token to the runtime dictionary
        if (tokenExist(key)) {
            _tokenCache.put(key, token);
        }

        // we also persist the token
        WriteTokenCache();
    }

    /**
     * Persist the token cache to disk.
     *
     * @throws BitPayException BitPayException class
     */
    private void WriteTokenCache() throws BitPayException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode tokens = mapper.valueToTree(this._tokenCache);
            ((ObjectNode) this._configuration.getEnvConfig(this._env)).put("ApiTokens", tokens);
        } catch (Exception e) {
            throw new BitPayException("When trying to write the tokens : " + e.getMessage());
        }
    }

    /**
     * Load tokens from configuration.
     *
     * @throws BitPayException BitPayException class
     */
    private void LoadAccessTokens() throws BitPayException {
        try {
            this.clearAccessTokenCache();

            Iterator<Map.Entry<String, JsonNode>> tokens = this._configuration.getEnvConfig(this._env).path("ApiTokens").fields();
            while (tokens.hasNext()) {
                Map.Entry<String, JsonNode> next = tokens.next();
                if (!next.getValue().asText().isEmpty()) {
                    _tokenCache.put(next.getKey(), next.getValue().asText());
                }
            }
        } catch (Exception e) {
            throw new BitPayException("When trying to load the tokens : " + e.getMessage());
        }
    }

    /**
     * Retrieve a token associated with a known resource. The token is used to access other related resources.
     *
     * @param key The identifier for the desired resource.
     * @return The token associated with resource.
     * @throws BitPayException BitPayException class
     */
    public String getAccessToken(String key) throws BitPayException {
        try {
            return _tokenCache.get(key);
        } catch (Exception e) {
            throw new BitPayException("There is no token for the specified key : " + e.getMessage());
        }
    }


    public HttpResponse get(String uri, List<BasicNameValuePair> parameters) throws BitPayException {
        return get(uri, parameters, true);
    }

    public HttpResponse get(String uri, List<BasicNameValuePair> parameters, boolean signatureRequired) throws BitPayException {
        try {

            String fullURL = _baseUrl + uri;
            HttpGet get = new HttpGet(fullURL);

            if (parameters != null) {

                fullURL += "?" + URLEncodedUtils.format(parameters, "UTF-8");
                get.setURI(new URI(fullURL));
            }
            if (signatureRequired) {
                get.addHeader("x-signature", KeyUtils.sign(_ecKey, fullURL));
                get.addHeader("x-identity", KeyUtils.bytesToHex(_ecKey.getPubKey()));
            }
            get.addHeader("x-bitpay-plugin-info", Env.BitpayPluginInfo);
            get.addHeader("x-accept-version", Env.BitpayApiVersion);


            _log.info(get.toString());
            return _httpClient.execute(get);

        } catch (URISyntaxException e) {
            throw new BitPayException("Error: GET failed\n" + e.getMessage());
        } catch (ClientProtocolException e) {
            throw new BitPayException("Error: GET failed\n" + e.getMessage());
        } catch (Exception e) {
            throw new BitPayException("Error: GET failed\n" + e.getMessage());
        }
    }

    public HttpResponse get(String uri) throws BitPayException {
        return this.get(uri, null, false);
    }

    private HttpResponse delete(String uri, List<BasicNameValuePair> parameters) throws BitPayException {
        try {

            String fullURL = _baseUrl + uri;
            HttpDelete delete = new HttpDelete(fullURL);

            if (parameters != null) {

                fullURL += "?" + URLEncodedUtils.format(parameters, "UTF-8");

                delete.setURI(new URI(fullURL));

                delete.addHeader("x-bitpay-plugin-info", Env.BitpayPluginInfo);
                delete.addHeader("x-accept-version", Env.BitpayApiVersion);
                delete.addHeader("x-signature", KeyUtils.sign(_ecKey, fullURL));
                delete.addHeader("x-identity", KeyUtils.bytesToHex(_ecKey.getPubKey()));
            }

            _log.info(delete.toString());
            return _httpClient.execute(delete);

        } catch (URISyntaxException e) {
            throw new BitPayException("Error: DELETE failed\n" + e.getMessage());
        } catch (ClientProtocolException e) {
            throw new BitPayException("Error: DELETE failed\n" + e.getMessage());
        } catch (Exception e) {
            throw new BitPayException("Error: DELETE failed\n" + e.getMessage());
        }
    }

    public HttpResponse post(String uri, String json) throws BitPayException {
        return this.post(uri, json, false);
    }

    public HttpResponse post(String uri, String json, boolean signatureRequired) throws BitPayException {
        try {
            HttpPost post = new HttpPost(_baseUrl + uri);

            post.setEntity(new ByteArrayEntity(json.getBytes(StandardCharsets.UTF_8)));

            if (signatureRequired) {
                post.addHeader("x-signature", KeyUtils.sign(_ecKey, _baseUrl + uri + json));
                post.addHeader("x-identity", KeyUtils.bytesToHex(_ecKey.getPubKey()));
            }

            post.addHeader("x-accept-version", Env.BitpayApiVersion);
            post.addHeader("x-bitpay-plugin-info", Env.BitpayPluginInfo);
            post.addHeader("Content-Type", "application/json");

            _log.info(post.toString());
            return _httpClient.execute(post);

        } catch (UnsupportedEncodingException e) {
            throw new BitPayException("Error: POST failed\n" + e.getMessage());
        } catch (ClientProtocolException e) {
            throw new BitPayException("Error: POST failed\n" + e.getMessage());
        } catch (Exception e) {
            throw new BitPayException("Error: POST failed\n" + e.getMessage());
        }
    }

    public HttpResponse update(String uri, String json) throws BitPayException {
        try {
            HttpPut put = new HttpPut(_baseUrl + uri);

            put.setEntity(new ByteArrayEntity(json.getBytes(StandardCharsets.UTF_8)));

            put.addHeader("x-signature", KeyUtils.sign(_ecKey, _baseUrl + uri + json));
            put.addHeader("x-identity", KeyUtils.bytesToHex(_ecKey.getPubKey()));
            put.addHeader("x-accept-version", Env.BitpayApiVersion);
            put.addHeader("x-bitpay-plugin-info", Env.BitpayPluginInfo);
            put.addHeader("Content-Type", "application/json");

            _log.info(put.toString());
            return _httpClient.execute(put);

        } catch (UnsupportedEncodingException e) {
            throw new BitPayException("Error: PUT failed\n" + e.getMessage());
        } catch (ClientProtocolException e) {
            throw new BitPayException("Error: PUT failed\n" + e.getMessage());
        } catch (Exception e) {
            throw new BitPayException("Error: PUT failed\n" + e.getMessage());
        }
    }

    public String responseToJsonString(HttpResponse response) throws BitPayException {
        if (response == null) {
            throw new BitPayException("Error: HTTP response is null");
        }

        try {
            // Get the JSON string from the response.
            HttpEntity entity = response.getEntity();

            String jsonString;

            jsonString = EntityUtils.toString(entity, "UTF-8");
            _log.info("RESPONSE: " + jsonString);

            ObjectMapper mapper = new ObjectMapper();

            JsonNode rootNode = mapper.readTree(jsonString);
            JsonNode node = rootNode.get("error");

            if (node != null) {
                throw new BitPayException("Error: " + node.asText());
            }

            node = rootNode.get("errors");

            if (node != null) {
                String message = "Multiple errors:";

                if (node.isArray()) {
                    for (final JsonNode errorNode : node) {
                        message += "\n" + errorNode.asText();
                    }

                    throw new BitPayException(message);
                }
            }

            node = rootNode.get("data");

            if (node != null) {
                jsonString = node.toString();
            }

            return jsonString;

        } catch (ParseException e) {
            throw new BitPayException("failed to retrieve HTTP response body : " + e.getMessage());
        } catch (JsonMappingException e) {
            throw new BitPayException("failed to parse json response to map : " + e.getMessage());
        } catch (Exception e) {
            throw new BitPayException("failed to retrieve HTTP response body : " + e.getMessage());
        }
    }

    private String getGuid() {
        int Min = 0;
        int Max = 99999999;

        return Min + (int) (Math.random() * ((Max - Min) + 1)) + "";
    }

    /**
     * Loads the configuration file (JSON)
     *
     * @throws BitPayException BitPayException class
     */
    public void GetConfig() throws BitPayException {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(this._configFilePath));
            //create ObjectMapper instance
            ObjectMapper mapper = new ObjectMapper();
            //read JSON like DOM Parser
            JsonNode rootNode = mapper.readTree(jsonData);
            JsonNode bitPayConfiguration = rootNode.path("BitPayConfiguration");
            this._configuration = new ObjectMapper().readValue(bitPayConfiguration.toString(), Config.class);
            this._env = this._configuration.getEnvironment();
        } catch (JsonProcessingException e) {
            throw new BitPayException("failed to read configuration file : " + e.getMessage());
        } catch (Exception e) {
            throw new BitPayException("failed to read configuration file : " + e.getMessage());
        }
    }

    /**
     * Builds the configuration object
     *
     * @param privateKeyPath The full path to the securely located private key.
     * @param tokens         Env.Tokens object containing the BitPay's API tokens.
     * @throws BitPayException BitPayException class
     */
    public void BuildConfig(String privateKeyPath, Env.Tokens tokens) throws BitPayException {
        try {
            File privateKeyFile = new File(privateKeyPath);
            if (!privateKeyFile.exists()) {
                throw new BitPayException("Private Key file not found");
            }
            Config config = new Config();
            config.setEnvironment(this._env);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode ApiTokens = mapper.valueToTree(tokens);

            ObjectNode envConfig = mapper.createObjectNode();
            envConfig.put("PrivateKeyPath", privateKeyPath).put("ApiTokens", ApiTokens);

            ObjectNode envTarget = mapper.createObjectNode();
            envTarget.put(this._env, envConfig);

            config.setEnvConfig(envTarget);
            this._configuration = config;
        } catch (Exception e) {
            throw new BitPayException("failed to process configuration : " + e.getMessage());
        }
    }
}
