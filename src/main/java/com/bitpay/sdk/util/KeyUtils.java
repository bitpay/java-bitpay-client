/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.util;

import com.bitpay.sdk.exceptions.BitPayExceptionProvider;
import com.bitpay.sdk.exceptions.BitPayGenericException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import org.bitcoinj.core.Base58;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.ECKey.ECDSASignature;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.crypto.KeyCrypterException;

/**
 * The type Key utils.
 */
public class KeyUtils {

    private static final char[] hexArray = "0123456789abcdef".toCharArray();
    private static String PrivateKeyFile;
    private static URI privateKey;

    /**
     * Instantiates a new Key utils.
     */
    public KeyUtils() {
    }

    /**
     * Check if private key exists.
     *
     * @param privateKeyFile the private key file
     * @return the boolean
     */
    public static boolean privateKeyExists(String privateKeyFile) {
        PrivateKeyFile = privateKeyFile;

        return new File(privateKeyFile).exists();
    }

    /**
     * Create EC key.
     *
     * @return the ec key
     */
    public static ECKey createEcKey() {
        //Default constructor uses SecureRandom numbers.
        return new ECKey();
    }

    /**
     * Create EC key from hex string.
     *
     * @param privateKey the private key
     * @return the ec key
     */
    public static ECKey createEcKeyFromHexString(String privateKey) throws BitPayGenericException {
        byte[] bytes = hexToBytes(privateKey);

        return ECKey.fromASN1(bytes);
    }

    /**
     * Create EC key from hex string private key file.
     *
     * @param privKeyFile the priv key file
     * @return the ec key
     * @throws IOException the io exception
     */
    public static ECKey createEcKeyFromHexStringFile(String privKeyFile) throws IOException, BitPayGenericException {
        return createEcKeyFromHexString(getKeyStringFromFile(privKeyFile));
    }

    /**
     * Load EC key.
     *
     * @return the ec key
     * @throws IOException the io exception
     */
    public static ECKey loadEcKey() throws IOException {
        FileInputStream fileInputStream;
        File file;

        if (KeyUtils.privateKey == null) {
            file = new File(PrivateKeyFile);
        } else {
            file = new File(KeyUtils.privateKey);
        }

        byte[] bytes = new byte[(int) file.length()];

        fileInputStream = new FileInputStream(file);
        int numBytesRead = fileInputStream.read(bytes);

        fileInputStream.close();

        if (numBytesRead == -1) {
            throw new IOException("read nothing from the file.");
        }
        return ECKey.fromASN1(bytes);
    }

    /**
     * Load EC key.
     *
     * @param privateKey the private key
     * @return the ec key
     * @throws IOException        the io exception
     * @throws URISyntaxException the uri syntax exception
     */
    public static ECKey loadEcKey(URI privateKey) throws IOException, URISyntaxException {
        KeyUtils.privateKey = privateKey;
        File file = new File(privateKey);
        if (!file.exists()) {
            ECKey key = createEcKey();
            saveEcKey(key, KeyUtils.privateKey);
            return key;
        }
        return loadEcKey();
    }

    /**
     * Gets key string from file.
     *
     * @param filename the filename
     * @return the key string from file
     * @throws IOException the io exception
     */
    public static String getKeyStringFromFile(String filename) throws IOException {
        BufferedReader br;

        br = new BufferedReader(new FileReader(filename));

        String line = br.readLine();

        br.close();

        return line;
    }

    /**
     * Save EC key.
     *
     * @param ecKey the ec key
     * @throws IOException the io exception
     */
    public static void saveEcKey(ECKey ecKey) throws IOException {
        byte[] bytes = ecKey.toASN1();
        File file;

        if (KeyUtils.privateKey == null) {
            file = new File(PrivateKeyFile);
        } else {
            file = new File(KeyUtils.privateKey);
        }

        FileOutputStream output = new FileOutputStream(file);

        output.write(bytes);
        output.close();
    }

    /**
     * Save EC key.
     *
     * @param ecKey      the ec key
     * @param privateKey the private key
     * @throws IOException        the io exception
     * @throws URISyntaxException the uri syntax exception
     */
    public static void saveEcKey(
        ECKey ecKey,
        URI privateKey
    ) throws IOException, URISyntaxException {
        File file = new File(privateKey);
        //we shan't overwrite an existing file

        if (file.exists()) {
            return;
        }
        KeyUtils.privateKey = privateKey;
        saveEcKey(ecKey);
    }

    /**
     * Save EC key as hex.
     *
     * @param ecKey the ec key
     * @throws IOException the io exception
     */
    public static void saveEcKeyAsHex(ECKey ecKey) throws IOException {
        byte[] bytes = ecKey.toASN1();
        PrintWriter file;

        if (KeyUtils.privateKey == null) {
            file = new PrintWriter(PrivateKeyFile);
        } else {
            file = new PrintWriter(String.valueOf(KeyUtils.privateKey));
        }

        String keyHex = bytesToHex(bytes);
        file.println(keyHex);
        file.close();
    }

    /**
     * Load EC key as hex string.
     *
     * @param ecKey the ec key
     * @return the string
     * @throws IOException the io exception
     */
    public static String loadEcKeyAsHex(ECKey ecKey) throws IOException {
        byte[] bytes = ecKey.toASN1();
        return bytesToHex(bytes);
    }

    /**
     * Derive sin string.
     *
     * @param ecKey the ec key
     * @return the string
     * @throws BitPayGenericException the illegal argument exception
     */
    public static String deriveSin(ECKey ecKey) throws BitPayGenericException {
        // Get sha256 hash and then the RIPEMD-160 hash of the public key (this call gets the result in one step).
        byte[] pubKeyHash = ecKey.getPubKeyHash();

        // Convert binary pubKeyHash, SINtype and version to Hex
        String version = "0F";
        String sinType = "02";
        String pubKeyHashHex = bytesToHex(pubKeyHash);

        // Concatenate all three elements
        String preSin = version + sinType + pubKeyHashHex;

        // Convert the hex string back to binary and double sha256 hash it leaving in binary both times
        byte[] preSinByte = hexToBytes(preSin);
        byte[] hash2Bytes = Sha256Hash.hashTwice(preSinByte);

        // Convert back to hex and take first four bytes
        String hashString = bytesToHex(hash2Bytes);
        String first4Bytes = hashString.substring(0, 8);

        // Append first four bytes to fully appended SIN string
        String unencoded = preSin + first4Bytes;
        byte[] unencodedBytes = new BigInteger(unencoded, 16).toByteArray();
        return Base58.encode(unencodedBytes);
    }

    /**
     * Sign EC Key.
     *
     * @param key   the key
     * @param input the input
     * @return the string
     * @throws BitPayGenericException BitPayGenericException class
     */
    public static String sign(
        ECKey key,
        String input
    ) throws BitPayGenericException {
        String result = null;

        try {
            byte[] data = input.getBytes(StandardCharsets.UTF_8);

            Sha256Hash hash = Sha256Hash.of(data);
            ECDSASignature sig = key.sign(hash, null);

            byte[] bytes = sig.encodeToDER();

            result = bytesToHex(bytes);
        } catch (KeyCrypterException e) {
            BitPayExceptionProvider.throwGenericExceptionWithMessage("Wrong ecKey. " + e.getMessage());
        }

        return result;
    }

    private static int getHexVal(char hex) {
        int val = hex;
        return val - (val < 58 ? 48 : (val < 97 ? 55 : 87));
    }

    /**
     * Convert hex to bytes.
     *
     * @param hex the hex
     * @return the byte []
     * @throws BitPayGenericException BitPayGenericException class
     */
    public static byte[] hexToBytes(String hex) throws BitPayGenericException {
        char[] hexArray = hex.toCharArray();

        if (hex.length() % 2 == 1) {
            BitPayExceptionProvider.throwGenericExceptionWithMessage(
                "Error: The binary key cannot have an odd number of digits");
        }

        byte[] arr = new byte[hex.length() >> 1];

        for (int i = 0; i < hex.length() >> 1; ++i) {
            arr[i] = (byte) ((getHexVal(hexArray[i << 1]) << 4) + (getHexVal(hexArray[(i << 1) + 1])));
        }

        return arr;
    }

    /**
     * Convert bytes to hex.
     *
     * @param bytes the bytes
     * @return the string
     */
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];

        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;

            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }

        return new String(hexChars);
    }
}
