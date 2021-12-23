# Wallet

Fetch the list of supported wallets and supported currency details

## Retrieve supported Wallets

`GET /supportedWallets`

Facades : **`PUBLIC`**

Note – this endpoint uses the “public” facade and does not require a token

```java

List<Wallet> wallets = bitpay.getSupportedWallets();

```

### HTTP Response

```json
{
  "data": [
    {
      "key": "bitpay",
      "displayName": "BitPay",
      "avatar": "bitpay-wallet.png",
      "payPro": true,
      "currencies": [
        {
          "payPro": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "BTC"
        },
        {
          "payPro": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "BCH"
        },
        {
          "payPro": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "XRP"
        },
        {
          "payPro": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "DOGE"
        },
        {
          "payPro": true,
          "qr": {
            "type": "EIP681"
          },
          "code": "ETH"
        },
        {
          "payPro": true,
          "qr": {
            "type": "EIP681b"
          },
          "code": "USDC"
        },
        {
          "payPro": true,
          "qr": {
            "type": "EIP681b"
          },
          "code": "PAX"
        },
        {
          "payPro": true,
          "qr": {
            "type": "EIP681b"
          },
          "code": "GUSD"
        },
        {
          "payPro": true,
          "qr": {
            "type": "EIP681b"
          },
          "code": "BUSD"
        },
        {
          "payPro": true,
          "qr": {
            "type": "EIP681b"
          },
          "code": "WBTC"
        },
        {
          "payPro": true,
          "qr": {
            "type": "EIP681b"
          },
          "code": "SHIB"
        },
        {
          "payPro": true,
          "qr": {
            "type": "EIP681b"
          },
          "code": "DAI"
        },
        {
          "payPro": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "LTC"
        }
      ]
    },
    {
      "key": "copay",
      "displayName": "Copay",
      "avatar": "copay-wallet.svg",
      "payPro": true,
      "currencies": [
        {
          "payPro": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "BTC"
        },
        {
          "payPro": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "BCH"
        },
        {
          "payPro": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "XRP"
        },
        {
          "payPro": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "DOGE"
        },
        {
          "payPro": true,
          "qr": {
            "type": "EIP681"
          },
          "code": "ETH"
        },
        {
          "payPro": true,
          "qr": {
            "type": "EIP681b"
          },
          "code": "USDC"
        },
        {
          "payPro": true,
          "qr": {
            "type": "EIP681b"
          },
          "code": "PAX"
        },
        {
          "payPro": true,
          "qr": {
            "type": "EIP681b"
          },
          "code": "GUSD"
        },
        {
          "payPro": true,
          "qr": {
            "type": "EIP681b"
          },
          "code": "BUSD"
        },
        {
          "payPro": true,
          "qr": {
            "type": "EIP681b"
          },
          "code": "WBTC"
        },
        {
          "payPro": true,
          "qr": {
            "type": "EIP681b"
          },
          "code": "SHIB"
        },
        {
          "payPro": true,
          "qr": {
            "type": "EIP681b"
          },
          "code": "DAI"
        },
        {
          "payPro": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "LTC"
        }
      ]
    },
    {
      "key": "blockchainWallet",
      "displayName": "Blockchain Wallet",
      "avatar": "blockchain-wallet.svg",
      "payPro": true,
      "invoiceDefault": "BTC",
      "uniCode": "bitcoin:?r=https://test.bitpay.com",
      "verificationLink": "https://test.bitpay.com/id/verify",
      "currencies": [
        {
          "payPro": true,
          "p2p": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "qr": {
            "type": "BIP21+"
          },
          "code": "BCH"
        }
      ]
    },
    {
      "key": "squareCash",
      "displayName": "Cash App",
      "avatar": "square-cash-app.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "type": "ADDRESS"
          },
          "code": "BTC"
        }
      ]
    },
    {
      "key": "coinbase",
      "displayName": "Coinbase",
      "avatar": "coinbase.svg",
      "offChainMode": true,
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "XRP"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "DOGE"
        }
      ]
    },
    {
      "key": "coinbaseP2P",
      "displayName": "Coinbase",
      "avatar": "coinbase.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "XRP"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "DOGE"
        }
      ]
    },
    {
      "key": "coinbasePro",
      "displayName": "Coinbase Pro",
      "avatar": "coinbase-pro.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "XRP"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "DOGE"
        }
      ]
    },
    {
      "key": "coinbaseWallet",
      "displayName": "Coinbase Wallet",
      "avatar": "coinbase-wallet.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "qr": {
            "type": "ADDRESS"
          },
          "code": "XRP"
        },
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "DOGE"
        },
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "LTC"
        }
      ]
    },
    {
      "key": "gemini",
      "displayName": "Gemini",
      "avatar": "gemini.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "LTC"
        }
      ]
    },
    {
      "key": "coinjar",
      "displayName": "CoinJar",
      "avatar": "coinjar.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "XRP"
        }
      ]
    },
    {
      "key": "shakepay",
      "displayName": "Shakepay",
      "avatar": "shakepay.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BTC"
        }
      ]
    },
    {
      "key": "paxful",
      "displayName": "Paxful",
      "avatar": "paxful.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "type": "ADDRESS"
          },
          "code": "BTC"
        }
      ]
    },
    {
      "key": "btcCom",
      "displayName": "BTC.com Wallet",
      "avatar": "btc-com-wallet.svg",
      "payPro": true,
      "currencies": [
        {
          "payPro": true,
          "p2p": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "BTC"
        },
        {
          "payPro": true,
          "p2p": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "BCH"
        }
      ]
    },
    {
      "key": "exodusWallet",
      "displayName": "Exodus Wallet",
      "avatar": "exodus-wallet.svg",
      "payPro": true,
      "currencies": [
        {
          "payPro": true,
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "BIP72b"
          },
          "code": "BTC"
        },
        {
          "payPro": true,
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "BIP72b"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "BIP21"
          },
          "code": "DOGE"
        },
        {
          "payPro": true,
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "EIP681"
          },
          "code": "ETH"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "XRP"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "BIP21"
          },
          "code": "LTC"
        }
      ]
    },
    {
      "key": "brdWallet",
      "displayName": "BRD Wallet",
      "avatar": "brd-wallet.svg",
      "payPro": true,
      "uniCode": "bitcoin:?r=https://test.bitpay.com",
      "verificationLink": "https://test.bitpay.com/id/verify",
      "invoiceDefault": "BTC",
      "currencies": [
        {
          "payPro": true,
          "p2p": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "BTC"
        },
        {
          "payPro": true,
          "p2p": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "qr": {
            "type": "ADDRESS"
          },
          "code": "XRP"
        }
      ]
    },
    {
      "key": "edgeWallet",
      "displayName": "Edge Wallet",
      "avatar": "edge-wallet.svg",
      "payPro": true,
      "currencies": [
        {
          "payPro": true,
          "p2p": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "BTC"
        },
        {
          "payPro": true,
          "p2p": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "qr": {
            "type": "ADDRESS"
          },
          "code": "XRP"
        },
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "LTC"
        }
      ]
    },
    {
      "key": "bitcoinCom",
      "displayName": "Bitcoin.com Wallet",
      "avatar": "bitcoin-com-wallet.svg",
      "payPro": true,
      "currencies": [
        {
          "payPro": true,
          "p2p": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "BTC"
        },
        {
          "payPro": true,
          "p2p": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "BCH"
        }
      ]
    },
    {
      "key": "trustWallet",
      "displayName": "Trust Wallet",
      "avatar": "trust-wallet.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "BIP21"
          },
          "code": "DOGE"
        },
        {
          "p2p": true,
          "qr": {
            "type": "ADDRESS"
          },
          "code": "XRP"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "BIP21"
          },
          "code": "LTC"
        }
      ]
    },
    {
      "key": "imToken",
      "displayName": "imToken",
      "avatar": "imtoken.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "BCH"
        }
      ]
    },
    {
      "key": "hodlWallet",
      "displayName": "HODL Wallet",
      "avatar": "hodl-wallet.svg",
      "payPro": true,
      "currencies": [
        {
          "payPro": true,
          "p2p": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "BTC"
        }
      ]
    },
    {
      "key": "metamask",
      "displayName": "MetaMask",
      "avatar": "metamask.svg",
      "uniCode": "https://metamask.app.link/dapp/test.bitpay.com",
      "verificationLink": "https://metamask.app.link/dapp/test.bitpay.com/id/verify",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "dappBrowser": true,
          "qr": {
            "collapsed": true,
            "type": "EIP681"
          },
          "code": "ETH"
        },
        {
          "p2p": true,
          "dappBrowser": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "GUSD"
        },
        {
          "p2p": true,
          "dappBrowser": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "PAX"
        },
        {
          "p2p": true,
          "dappBrowser": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "USDC"
        },
        {
          "p2p": true,
          "dappBrowser": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BUSD"
        },
        {
          "p2p": true,
          "dappBrowser": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "WBTC"
        },
        {
          "p2p": true,
          "dappBrowser": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "SHIB"
        },
        {
          "p2p": true,
          "dappBrowser": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "DAI"
        }
      ]
    },
    {
      "key": "braveBrowser",
      "displayName": "Brave Browser",
      "avatar": "brave-browser.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "dappBrowser": true,
          "qr": {
            "collapsed": true,
            "type": "EIP681"
          },
          "code": "ETH"
        },
        {
          "p2p": true,
          "dappBrowser": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "GUSD"
        },
        {
          "p2p": true,
          "dappBrowser": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "PAX"
        },
        {
          "p2p": true,
          "dappBrowser": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "USDC"
        },
        {
          "p2p": true,
          "dappBrowser": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BUSD"
        }
      ]
    },
    {
      "key": "operaBrowser",
      "displayName": "Opera Browser",
      "avatar": "opera-browser.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "dappBrowser": true,
          "qr": {
            "collapsed": true,
            "type": "EIP681"
          },
          "code": "ETH"
        },
        {
          "p2p": true,
          "dappBrowser": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "GUSD"
        },
        {
          "p2p": true,
          "dappBrowser": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "PAX"
        },
        {
          "p2p": true,
          "dappBrowser": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "USDC"
        },
        {
          "p2p": true,
          "dappBrowser": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BUSD"
        }
      ]
    },
    {
      "key": "ledgerWallet",
      "displayName": "Ledger Wallet",
      "avatar": "ledger-wallet.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "BIP21"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "legacyAddress": true,
          "qr": {
            "collapsed": true,
            "type": "BIP21"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "BIP21"
          },
          "code": "DOGE"
        },
        {
          "p2p": true,
          "qr": {
            "type": "ADDRESS"
          },
          "code": "XRP"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "BIP21"
          },
          "code": "LTC"
        }
      ]
    },
    {
      "key": "coolWallet",
      "displayName": "CoolWallet",
      "avatar": "coolwallet.svg",
      "payPro": true,
      "currencies": [
        {
          "payPro": true,
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "BIP72b"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "qr": {
            "type": "ADDRESS"
          },
          "code": "XRP"
        }
      ]
    },
    {
      "key": "trezorWallet",
      "displayName": "Trezor Wallet",
      "avatar": "trezor-wallet.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "BIP21"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "BIP21"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "BIP21"
          },
          "code": "DOGE"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "BIP21"
          },
          "code": "LTC"
        }
      ]
    },
    {
      "key": "bitcoinCore",
      "displayName": "Bitcoin Core",
      "avatar": "bitcoin-core.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "rbf": true,
          "qr": {
            "collapsed": true,
            "type": "BIP21+"
          },
          "code": "BTC"
        }
      ]
    },
    {
      "key": "dogecoinCore",
      "displayName": "Dogecoin Core",
      "avatar": "doge-logo.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "rbf": true,
          "qr": {
            "collapsed": true,
            "type": "BIP21+"
          },
          "code": "DOGE"
        }
      ]
    },
    {
      "key": "bitcoinABC",
      "displayName": "Bitcoin ABC",
      "avatar": "bitcoin-abc.svg",
      "payPro": true,
      "currencies": [
        {
          "payPro": true,
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "BIP72b"
          },
          "code": "BCH"
        }
      ]
    },
    {
      "key": "badgerWallet",
      "displayName": "Badger Wallet",
      "avatar": "badger-wallet.png",
      "payPro": true,
      "currencies": [
        {
          "payPro": true,
          "p2p": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "BCH"
        }
      ]
    },
    {
      "key": "greenWallet",
      "displayName": "Green Wallet",
      "avatar": "green-wallet.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "BTC"
        }
      ]
    },
    {
      "key": "zenGo",
      "displayName": "ZenGo Wallet",
      "avatar": "zengo-wallet.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "qr": {
            "type": "ADDRESS"
          },
          "code": "XRP"
        }
      ]
    },
    {
      "key": "abraWallet",
      "displayName": "Abra Wallet",
      "avatar": "abra-wallet.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "qr": {
            "type": "ADDRESS"
          },
          "code": "XRP"
        }
      ]
    },
    {
      "key": "samouraiWallet",
      "displayName": "Samourai Wallet",
      "avatar": "samourai-wallet.png",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "BTC"
        }
      ]
    },
    {
      "key": "wasabiWallet",
      "displayName": "Wasabi Wallet",
      "avatar": "wasabi-wallet.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "BTC"
        }
      ]
    },
    {
      "key": "guardaWallet",
      "displayName": "Guarda Wallet",
      "avatar": "guarda-wallet.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "BIP21"
          },
          "code": "DOGE"
        },
        {
          "p2p": true,
          "qr": {
            "type": "ADDRESS"
          },
          "code": "XRP"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "BIP21"
          },
          "code": "LTC"
        }
      ]
    },
    {
      "key": "zapWallet",
      "displayName": "Zap Wallet",
      "avatar": "zap-wallet.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "BTC"
        }
      ]
    },
    {
      "key": "binance",
      "displayName": "Binance",
      "avatar": "binance.svg",
      "payPro": false,
      "currencies": []
    },
    {
      "key": "kraken",
      "displayName": "Kraken",
      "avatar": "kraken.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "XRP"
        },
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "LTC"
        }
      ]
    },
    {
      "key": "poloniex",
      "displayName": "Poloniex",
      "avatar": "poloniex.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "BIP21"
          },
          "code": "DOGE"
        },
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "XRP"
        }
      ]
    },
    {
      "key": "bitfinex",
      "displayName": "Bitfinex",
      "avatar": "bitfinex.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "XRP"
        }
      ]
    },
    {
      "key": "okex",
      "displayName": "OKEx",
      "avatar": "okex.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "XRP"
        }
      ]
    },
    {
      "key": "bitgo",
      "displayName": "BitGo",
      "avatar": "bitgo.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "XRP"
        }
      ]
    },
    {
      "key": "bitso",
      "displayName": "Bitso",
      "avatar": "bitso.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "XRP"
        }
      ]
    },
    {
      "key": "bitpanda",
      "displayName": "Bitpanda",
      "avatar": "bitpanda.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "XRP"
        },
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "LTC"
        }
      ]
    },
    {
      "key": "coinsmart",
      "displayName": "CoinSmart",
      "avatar": "coinsmart.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "XRP"
        }
      ]
    },
    {
      "key": "kucoin",
      "displayName": "KuCoin",
      "avatar": "kucoin.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "XRP"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "LTC"
        }
      ]
    },
    {
      "key": "bittrex",
      "displayName": "Bittrex",
      "avatar": "bittrex.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "withdrawalFee": "0.0005",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "withdrawalFee": "0.001",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "withdrawalFee": "1",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "XRP"
        }
      ]
    },
    {
      "key": "bitstamp",
      "displayName": "Bitstamp",
      "avatar": "bitstamp.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "XRP"
        },
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "LTC"
        }
      ]
    },
    {
      "key": "luno",
      "displayName": "Luno",
      "avatar": "luno-exchange.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BCH"
        }
      ]
    },
    {
      "key": "coinfloor",
      "displayName": "Coinfloor",
      "avatar": "coinfloor.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BCH"
        }
      ]
    },
    {
      "key": "bitmex",
      "displayName": "BitMEX",
      "avatar": "bitmex.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "XRP"
        },
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "LTC"
        }
      ]
    },
    {
      "key": "cexIO",
      "displayName": "CEX.io",
      "avatar": "cex-io.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "XRP"
        }
      ]
    },
    {
      "key": "liquidExchange",
      "displayName": "Liquid",
      "avatar": "liquid-exchange.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "XRP"
        }
      ]
    },
    {
      "key": "shapeshift",
      "displayName": "ShapeShift",
      "avatar": "shapeshift.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "withdrawalFee": "dynamic",
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "XRP"
        }
      ]
    },
    {
      "key": "bitcoinWalletPolehin",
      "displayName": "Bitcoin Wallet by Polehin",
      "avatar": "bitcoin-wallet-polehin.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "qr": {
            "type": "ADDRESS"
          },
          "code": "XRP"
        }
      ]
    },
    {
      "key": "freewallet",
      "displayName": "Freewallet",
      "avatar": "freewallet.png",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "BIP21"
          },
          "code": "DOGE"
        },
        {
          "p2p": true,
          "qr": {
            "type": "ADDRESS"
          },
          "code": "XRP"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "BIP21"
          },
          "code": "LTC"
        }
      ]
    },
    {
      "key": "coinText",
      "displayName": "CoinText",
      "avatar": "cointext.png",
      "payPro": true,
      "currencies": [
        {
          "payPro": true,
          "p2p": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "BCH"
        }
      ]
    },
    {
      "key": "equalWallet",
      "displayName": "Equal Wallet",
      "avatar": "equal-wallet.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "dappBrowser": true,
          "walletConnect": true,
          "qr": {
            "collapsed": true,
            "type": "EIP681"
          },
          "code": "ETH"
        },
        {
          "p2p": true,
          "dappBrowser": true,
          "walletConnect": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "GUSD"
        },
        {
          "p2p": true,
          "dappBrowser": true,
          "walletConnect": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "PAX"
        },
        {
          "p2p": true,
          "dappBrowser": true,
          "walletConnect": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "USDC"
        },
        {
          "p2p": true,
          "dappBrowser": true,
          "walletConnect": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BUSD"
        }
      ]
    },
    {
      "key": "blueWallet",
      "displayName": "BlueWallet",
      "avatar": "bluewallet.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "BTC"
        }
      ]
    },
    {
      "key": "jaxxLiberty",
      "displayName": "Jaxx Liberty",
      "avatar": "jaxx-liberty-wallet.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "BIP21"
          },
          "code": "DOGE"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "BIP21"
          },
          "code": "LTC"
        }
      ]
    },
    {
      "key": "atomicWallet",
      "displayName": "Atomic Wallet",
      "avatar": "atomic-wallet.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "BIP21"
          },
          "code": "DOGE"
        },
        {
          "p2p": true,
          "qr": {
            "type": "ADDRESS"
          },
          "code": "XRP"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "BIP21"
          },
          "code": "LTC"
        }
      ]
    },
    {
      "key": "eidooWallet",
      "displayName": "Eidoo Wallet",
      "avatar": "eidoo-wallet.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "BCH"
        }
      ]
    },
    {
      "key": "enjinWallet",
      "displayName": "Enjin Wallet",
      "avatar": "enjin-wallet.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "BCH"
        }
      ]
    },
    {
      "key": "armoryWallet",
      "displayName": "Armory Wallet",
      "avatar": "armory-wallet.png",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "BIP21"
          },
          "code": "BTC"
        }
      ]
    },
    {
      "key": "coinomi",
      "displayName": "Coinomi Wallet",
      "avatar": "coinomi-wallet.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "BIP21"
          },
          "code": "DOGE"
        },
        {
          "p2p": true,
          "qr": {
            "type": "ADDRESS"
          },
          "code": "XRP"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "BIP21"
          },
          "code": "LTC"
        }
      ]
    },
    {
      "key": "myceliumWallet",
      "displayName": "Mycelium Wallet",
      "avatar": "mycelium-wallet.png",
      "payPro": true,
      "currencies": [
        {
          "payPro": true,
          "p2p": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "BTC"
        }
      ]
    },
    {
      "key": "electrumWallet",
      "displayName": "Electrum Wallet",
      "avatar": "electrum-wallet.png",
      "payPro": true,
      "currencies": [
        {
          "payPro": true,
          "p2p": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "BTC"
        }
      ]
    },
    {
      "key": "electronCash",
      "displayName": "Electron Cash",
      "avatar": "electron-cash-wallet.svg",
      "payPro": true,
      "currencies": [
        {
          "payPro": true,
          "p2p": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "BCH"
        }
      ]
    },
    {
      "key": "bitnovo",
      "displayName": "Bitnovo",
      "avatar": "bitnovo-wallet.svg",
      "payPro": true,
      "currencies": [
        {
          "payPro": true,
          "p2p": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "qr": {
            "type": "ADDRESS"
          },
          "code": "XRP"
        }
      ]
    },
    {
      "key": "argentWallet",
      "displayName": "Argent",
      "avatar": "argent-wallet.svg",
      "payPro": false,
      "currencies": []
    },
    {
      "key": "rainbowWallet",
      "displayName": "Rainbow Wallet",
      "avatar": "rainbow-wallet.png",
      "payPro": false,
      "currencies": []
    },
    {
      "key": "statusWallet",
      "displayName": "Status",
      "avatar": "status-wallet.svg",
      "payPro": false,
      "currencies": []
    },
    {
      "key": "tokenary",
      "displayName": "Tokenary",
      "avatar": "tokenary-wallet.svg",
      "payPro": false,
      "currencies": []
    },
    {
      "key": "alphaWallet",
      "displayName": "AlphaWallet",
      "avatar": "alphawallet.svg",
      "payPro": false,
      "currencies": []
    },
    {
      "key": "bancorWallet",
      "displayName": "Bancor Wallet",
      "avatar": "bancor-wallet.svg",
      "payPro": false,
      "currencies": []
    },
    {
      "key": "ibitcoinSE",
      "displayName": "iBitcoin.se",
      "avatar": "ibitcoin-se.png",
      "payPro": true,
      "currencies": [
        {
          "payPro": true,
          "p2p": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "BTC"
        },
        {
          "payPro": true,
          "p2p": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "BCH"
        }
      ]
    },
    {
      "key": "okcoin",
      "displayName": "OKCoin",
      "avatar": "okcoin.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "XRP"
        }
      ]
    },
    {
      "key": "huobi",
      "displayName": "Huobi",
      "avatar": "huobi.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "XRP"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "LTC"
        }
      ]
    },
    {
      "key": "xapo",
      "displayName": "Xapo",
      "avatar": "xapo.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "type": "ADDRESS"
          },
          "code": "BTC"
        }
      ]
    },
    {
      "key": "rain",
      "displayName": "Rain",
      "avatar": "rain.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "XRP"
        }
      ]
    },
    {
      "key": "bitwala",
      "displayName": "Bitwala",
      "avatar": "bitwala.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BTC"
        }
      ]
    },
    {
      "key": "changelly",
      "displayName": "Changelly",
      "avatar": "changelly.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "XRP"
        }
      ]
    },
    {
      "key": "dapper",
      "displayName": "Dapper",
      "avatar": "dapper-wallet.png",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "dappBrowser": true,
          "qr": {
            "collapsed": true,
            "type": "EIP681"
          },
          "code": "ETH"
        }
      ]
    },
    {
      "key": "lumiWallet",
      "displayName": "Lumi Wallet",
      "avatar": "lumi-wallet.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          },
          "code": "BTC"
        }
      ]
    },
    {
      "key": "balletWallet",
      "displayName": "Ballet Wallet",
      "avatar": "ballet-wallet.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "BCH"
        }
      ]
    },
    {
      "key": "zelcore",
      "displayName": "Zelcore",
      "avatar": "zelcore.svg",
      "payPro": true,
      "currencies": [
        {
          "payPro": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "BTC"
        },
        {
          "payPro": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "BCH"
        },
        {
          "payPro": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "XRP"
        },
        {
          "payPro": true,
          "qr": {
            "type": "EIP681"
          },
          "code": "ETH"
        }
      ]
    },
    {
      "key": "blockSettle",
      "displayName": "BlockSettle",
      "avatar": "block-settle.svg",
      "payPro": true,
      "currencies": [
        {
          "payPro": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "BTC"
        }
      ]
    },
    {
      "key": "localBitcoins",
      "displayName": "LocalBitcoins",
      "avatar": "local-bitcoins.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "qr": {
            "type": "BIP21"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "qr": {
            "type": "ADDRESS"
          },
          "code": "XRP"
        }
      ]
    },
    {
      "key": "uphold",
      "displayName": "Uphold",
      "avatar": "uphold.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "type": "ADDRESS"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "qr": {
            "type": "ADDRESS"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "qr": {
            "type": "ADDRESS"
          },
          "code": "XRP"
        }
      ]
    },
    {
      "key": "cryptoCom",
      "displayName": "Crypto.com",
      "avatar": "crypto-com.svg",
      "payPro": false,
      "currencies": []
    },
    {
      "key": "strike",
      "displayName": "Strike",
      "avatar": "strike.svg",
      "payPro": false,
      "currencies": [
        {
          "qr": {
            "type": "BIP21"
          },
          "code": "BTC",
          "p2p": true
        }
      ]
    },
    {
      "key": "litecoinCore",
      "displayName": "Litecoin Core",
      "avatar": "ltc-logo.svg",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "rbf": true,
          "qr": {
            "collapsed": true,
            "type": "BIP21+"
          },
          "code": "LTC"
        }
      ]
    },
    {
      "key": "walletConnect",
      "displayName": "WalletConnect",
      "payPro": false,
      "avatar": "wallet-connect.svg",
      "currencies": [
        {
          "code": "ETH",
          "p2p": true,
          "dappBrowser": true,
          "qr": {
            "collapsed": true,
            "type": "EIP681"
          }
        },
        {
          "code": "GUSD",
          "p2p": true,
          "dappBrowser": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          }
        },
        {
          "code": "PAX",
          "p2p": true,
          "dappBrowser": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          }
        },
        {
          "code": "USDC",
          "p2p": true,
          "dappBrowser": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          }
        },
        {
          "code": "BUSD",
          "p2p": true,
          "dappBrowser": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          }
        },
        {
          "code": "WBTC",
          "p2p": true,
          "dappBrowser": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          }
        },
        {
          "code": "SHIB",
          "p2p": true,
          "dappBrowser": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          }
        },
        {
          "code": "DAI",
          "p2p": true,
          "dappBrowser": true,
          "qr": {
            "collapsed": true,
            "type": "ADDRESS"
          }
        }
      ]
    },
    {
      "key": "legacyCopay",
      "payPro": true,
      "currencies": [
        {
          "payPro": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "BTC"
        },
        {
          "payPro": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "BCH"
        },
        {
          "payPro": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "XRP"
        },
        {
          "payPro": true,
          "qr": {
            "type": "EIP681"
          },
          "code": "ETH"
        },
        {
          "payPro": true,
          "qr": {
            "type": "EIP681b"
          },
          "code": "USDC"
        },
        {
          "payPro": true,
          "qr": {
            "type": "EIP681b"
          },
          "code": "PAX"
        },
        {
          "payPro": true,
          "qr": {
            "type": "EIP681b"
          },
          "code": "GUSD"
        }
      ]
    },
    {
      "key": "genericP2P",
      "payPro": false,
      "currencies": [
        {
          "p2p": true,
          "qr": {
            "type": "BIP21+"
          },
          "code": "BTC"
        },
        {
          "p2p": true,
          "qr": {
            "type": "BIP21+"
          },
          "code": "BCH"
        },
        {
          "p2p": true,
          "qr": {
            "type": "ADDRESS"
          },
          "code": "XRP"
        }
      ]
    },
    {
      "key": "genericPayPro",
      "payPro": true,
      "currencies": [
        {
          "payPro": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "BTC"
        },
        {
          "payPro": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "BCH"
        },
        {
          "payPro": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "XRP"
        },
        {
          "payPro": true,
          "dappBrowser": true,
          "qr": {
            "type": "EIP681"
          },
          "code": "ETH"
        },
        {
          "payPro": true,
          "dappBrowser": true,
          "qr": {
            "type": "EIP681b"
          },
          "code": "USDC"
        },
        {
          "payPro": true,
          "dappBrowser": true,
          "qr": {
            "type": "EIP681b"
          },
          "code": "PAX"
        },
        {
          "payPro": true,
          "dappBrowser": true,
          "qr": {
            "type": "EIP681b"
          },
          "code": "GUSD"
        },
        {
          "payPro": true,
          "dappBrowser": true,
          "qr": {
            "type": "EIP681b"
          },
          "code": "BUSD"
        }
      ]
    },
    {
      "key": "p2pDisabled",
      "payPro": true,
      "currencies": [
        {
          "payPro": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "BTC"
        },
        {
          "payPro": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "DOGE"
        },
        {
          "payPro": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "BCH"
        },
        {
          "payPro": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "XRP"
        },
        {
          "payPro": true,
          "qr": {
            "type": "BIP72b"
          },
          "code": "LTC"
        },
        {
          "payPro": true,
          "dappBrowser": true,
          "qr": {
            "type": "EIP681"
          },
          "code": "ETH"
        },
        {
          "payPro": true,
          "dappBrowser": true,
          "qr": {
            "type": "EIP681b"
          },
          "code": "USDC"
        },
        {
          "payPro": true,
          "dappBrowser": true,
          "qr": {
            "type": "EIP681b"
          },
          "code": "PAX"
        },
        {
          "payPro": true,
          "dappBrowser": true,
          "qr": {
            "type": "EIP681b"
          },
          "code": "GUSD"
        },
        {
          "payPro": true,
          "dappBrowser": true,
          "qr": {
            "type": "EIP681b"
          },
          "code": "DAI"
        },
        {
          "payPro": true,
          "dappBrowser": true,
          "qr": {
            "type": "EIP681b"
          },
          "code": "WBTC"
        },
        {
          "payPro": true,
          "dappBrowser": true,
          "qr": {
            "type": "EIP681b"
          },
          "code": "SHIB"
        },
        {
          "payPro": true,
          "dappBrowser": true,
          "qr": {
            "type": "EIP681b"
          },
          "code": "BUSD"
        }
      ]
    }
  ]
}
```


### [Back to guide index](../GUIDE.md)
