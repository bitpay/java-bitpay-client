package com.bitpay.sdk;

import com.bitpay.sdk.model.Facade;
import com.bitpay.sdk.util.AccessTokens;
import com.bitpay.sdk.util.KeyUtils;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;
import org.bitcoinj.core.ECKey;

class BitPaySetup {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String opt;
        Environment env;
        String privateKeyPath = "";
        String privateKeyAsHex = "";
        String pairingCodeMerchant;
        String pairingCodePayout;
        String privateKey = "";
        int keyType; // 1 = in file, 2 = as text
        Client client;

        do {
            System.out.println("Select target environment:");
            System.out.println("Press T for testing or P for production:");
            opt = scanner.next();
        } while (opt.toLowerCase().equals("t") && opt.toLowerCase().equals("p"));

        if (opt.toLowerCase().equals("t")) {
            env = Environment.TEST;
        } else {
            env = Environment.PROD;
        }

        do {
            System.out.println("Select the way you want your private key:");
            System.out.println("Press F for binary in a text file or T for plain text in your config file:");
            opt = scanner.next();
        } while (opt.toLowerCase().equals("f") && opt.toLowerCase().equals("t"));

        if (opt.toLowerCase().equals("f")) {
            keyType = 1;
        } else {
            keyType = 2;
        }

        System.out.println("Generating private key... ");

        try {
            File directory = new File(Paths.get(".").toAbsolutePath().normalize().toString() + "/output");
            if (!directory.exists()) {
                directory.mkdir();
            }

            if (keyType == 1) {
                privateKeyPath = Paths.get(".").toAbsolutePath().normalize().toString() + "/output/bitpay_private_" +
                    env.toString().toLowerCase() + ".key";
                if (!KeyUtils.privateKeyExists(privateKeyPath)) {
                    ECKey ecKey = KeyUtils.createEcKey();
                    KeyUtils.saveEcKey(ecKey);
                    KeyUtils.createEcKey();
                    System.out.println("Private key generated successfully with public key:");
                    System.out.println(ecKey.getPublicKeyAsHex());
                } else {
                    KeyUtils.loadEcKey();
                }
                privateKey = privateKeyPath;
            } else {
                ECKey ecKey = KeyUtils.createEcKey();
                privateKeyAsHex = KeyUtils.loadEcKeyAsHex(ecKey);
                System.out.println("Private key generated successfully with public key:");
                System.out.println(ecKey.getPublicKeyAsHex());
                privateKey = privateKeyAsHex;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        System.out.println("Generating config file... ");
        try {
            ObjectMapper mapper = new ObjectMapper();

            client = new Client(env, new PrivateKey(privateKey), new AccessTokens(), null, null);
            pairingCodeMerchant = client.authorizeClient(Facade.MERCHANT);
            pairingCodePayout = client.authorizeClient(Facade.PAYOUT);


            HashMap<String, String> tokens = new HashMap<>();
            tokens.put("merchant", client.getAccessToken(Facade.MERCHANT));
            tokens.put("payout", client.getAccessToken(Facade.PAYOUT));
            AtomicReference<JsonNode> ApiTokens = new AtomicReference<>(mapper.valueToTree(tokens));

            ObjectNode envConfig = mapper.createObjectNode();
            envConfig.put("PrivateKeyPath", privateKeyPath);
            envConfig.put("PrivateKey", privateKeyAsHex);
            envConfig.put("ApiTokens", ApiTokens.get());

            ObjectNode envTarget = mapper.createObjectNode();
            envTarget.put(env.toString(), envConfig);

            ObjectNode bitPayConfiguration = mapper.createObjectNode();
            bitPayConfiguration.put("Environment", env.toString()).put("EnvConfig", envTarget);

            ObjectNode configurationFile = mapper.createObjectNode();
            configurationFile.put("BitPayConfiguration", bitPayConfiguration);

            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(configurationFile);

            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
            writer.writeValue(
                new File(Paths.get(".").toAbsolutePath().normalize().toString() + "/output/BitPay.config.json"),
                configurationFile);

            System.out.println("In location:");
            System.out.println(Paths.get(".").toAbsolutePath().normalize().toString() + "/output/BitPay.config.json");
            System.out.println("With the following information:");
            System.out.println(jsonString);
            System.out.println();
            System.out.println("To complete your setup, Go to " + Config.TEST_URL +
                "dashboard/merchant/api-tokens and pair this client with your merchant account using the pairing codes:");
            System.out.println();
            System.out.println(pairingCodeMerchant + " for the Merchant facade.");
            System.out
                .println(pairingCodePayout + " for the Payout facade ONLY if you have requested access for this role.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        System.out.println(
            "The private key and the config file is been generated in a directory called output in the root of this package.");
        System.out.println(
            "Make sure you store this key in a secure location and update the PrivateKeyPath in the generated config file.");
    }
}