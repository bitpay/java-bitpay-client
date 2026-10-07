/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

import com.bitpay.sdk.Client;
import com.bitpay.sdk.Config;
import com.bitpay.sdk.Environment;
import com.bitpay.sdk.PrivateKey;
import com.bitpay.sdk.model.Facade;
import com.bitpay.sdk.util.KeyUtils;
import com.bitpay.sdk.util.TokenContainer;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import org.bitcoinj.crypto.ECKey;

public class BitPaySetup {
    public static void main(final String[] args) {
        final Scanner scanner = new Scanner(System.in);
        String opt;
        final Environment env;
        String privateKeyPath = "";
        String privateKeyAsHex = "";
        final String pairingCodeMerchant;
        final String pairingCodePayout;
        String privateKey = "";
        final int keyType; // 1 = in file, 2 = as text
        final Client client;

        do {
            System.out.println("Select target environment:");
            System.out.println("Press T for testing or P for production:");
            opt = scanner.next();
        } while ("t".equals(opt.toLowerCase()) && "p".equals(opt.toLowerCase()));

        if ("t".equals(opt.toLowerCase())) {
            env = Environment.TEST;
        } else {
            env = Environment.PROD;
        }

        do {
            System.out.println("Select the way you want your private key:");
            System.out.println("Press F for binary in a text file or T for plain text in your config file:");
            opt = scanner.next();
        } while ("f".equals(opt.toLowerCase()) && "t".equals(opt.toLowerCase()));

        if ("f".equals(opt.toLowerCase())) {
            keyType = 1;
        } else {
            keyType = 2;
        }

        System.out.println("Generating private key... ");

        try {
            final File directory = new File(Paths.get(".").toAbsolutePath().normalize() + "/output");
            if (!directory.exists()) {
                directory.mkdir();
            }

            if (keyType == 1) {
                privateKeyPath = Paths.get(".").toAbsolutePath().normalize()
                    + "/output/bitpay_private_"
                    + env.toString().toLowerCase()
                    + ".key";
                if (!KeyUtils.privateKeyExists(privateKeyPath)) {
                    final ECKey ecKey = KeyUtils.createEcKey();
                    KeyUtils.saveEcKey(ecKey);
                    KeyUtils.createEcKey();
                    System.out.println("Private key generated successfully with public key:");
                    System.out.println(ecKey.getPublicKeyAsHex());
                } else {
                    KeyUtils.loadEcKey();
                }
                privateKey = privateKeyPath;
            } else {
                final ECKey ecKey = KeyUtils.createEcKey();
                privateKeyAsHex = KeyUtils.loadEcKeyAsHex(ecKey);
                System.out.println("Private key generated successfully with public key:");
                System.out.println(ecKey.getPublicKeyAsHex());
                privateKey = privateKeyAsHex;
            }
        } catch (final Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        System.out.println("Generating config file... ");
        try {
            final ObjectMapper mapper = new ObjectMapper();

            client = new Client(env, new PrivateKey(privateKey), new TokenContainer(), null, null);
            pairingCodeMerchant = client.authorizeClient(Facade.MERCHANT);
            pairingCodePayout = client.authorizeClient(Facade.PAYOUT);


            final HashMap<String, String> tokens = new HashMap<>();
            tokens.put("merchant", client.getAccessToken(Facade.MERCHANT));
            tokens.put("payout", client.getAccessToken(Facade.PAYOUT));
            final AtomicReference<JsonNode> ApiTokens = new AtomicReference<>(mapper.valueToTree(tokens));

            final ObjectNode envConfig = mapper.createObjectNode();
            envConfig.put("PrivateKeyPath", privateKeyPath);
            envConfig.put("PrivateKey", privateKeyAsHex);
            envConfig.put("ApiTokens", ApiTokens.get());

            final ObjectNode envTarget = mapper.createObjectNode();
            envTarget.put(env.toString(), envConfig);

            final ObjectNode bitPayConfiguration = mapper.createObjectNode();
            bitPayConfiguration.put("Environment", env.toString()).put("EnvConfig", envTarget);

            final ObjectNode configurationFile = mapper.createObjectNode();
            configurationFile.put("BitPayConfiguration", bitPayConfiguration);

            final String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(configurationFile);

            final ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
            writeOwnerOnlyFile(
                new File(Paths.get(".").toAbsolutePath().normalize() + "/output/BitPay.config.json"),
                writer.writeValueAsBytes(configurationFile));

            System.out.println("In location:");
            System.out.println(Paths.get(".").toAbsolutePath().normalize() + "/output/BitPay.config.json");
            System.out.println("With the following information:");
            System.out.println(jsonString);
            System.out.println();
            System.out.println(
                "To complete your setup, Go to "
                    + Config.TEST_URL
                    + "dashboard/merchant/api-tokens "
                    + "and pair this client with your merchant account using the pairing codes:"
            );
            System.out.println();
            System.out.println(pairingCodeMerchant + " for the Merchant facade.");
            System.out
                .println(pairingCodePayout + " for the Payout facade ONLY if you have requested access for this role.");

        } catch (final Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        System.out.println(
            "The private key and the config file is been generated in a directory called output"
                + "in the root of this package."
        );
        System.out.println(
            "Make sure you store this key in a secure location and update the PrivateKeyPath"
                + "in the generated config file."
        );
    }

    /**
     * Writes the config file so only its owner can read and write it, because it holds the API tokens.
     *
     * <p>On file systems with POSIX permissions, the file is created with mode 0600 (or set to 0600
     * if it already exists) before it is written. On other file systems, such as Windows, the file
     * keeps the default permissions.</p>
     *
     * @param file    the file to write
     * @param content the file content
     * @throws IOException the io exception
     */
    private static void writeOwnerOnlyFile(
        final File file,
        final byte[] content
    ) throws IOException {
        final Path path = file.toPath();
        final boolean isPosix = path.getFileSystem().supportedFileAttributeViews().contains("posix");
        final Set<PosixFilePermission> ownerOnly = PosixFilePermissions.fromString("rw-------");

        if (isPosix) {
            if (Files.exists(path)) {
                Files.setPosixFilePermissions(path, ownerOnly);
            } else {
                Files.createFile(path, PosixFilePermissions.asFileAttribute(ownerOnly));
            }
        }

        Files.write(path, content);

        if (isPosix) {
            Files.setPosixFilePermissions(path, ownerOnly);
        }
    }
}
