# Wallets Service
In Playtomic, we have a service to manage our wallets. Our players can recharge their wallets using a credit card and spend that money on the platform  (bookings, racket rentals, ...)

This service has the following operations:
- You can query your balance.
- You can recharge your wallet. In this case, we charge the amount using a third-party payments platform (stripe, paypal, redsys).
- You can spend your balance on purchases in Playtomic. 
- You can return these purchases, and your money is refunded.
- You can check your history of transactions.

This exercise consists of building a proof of concept of this wallet service.
You have to code endpoints for these operations:
1. Get a wallet using its identifier.
2. Recharge money in that wallet using a credit card number. It has to charge that amount internally using a third-party platform.
3. Subtract an amount from a wallet (that is, make a charge in that wallet).


## How Works? 🚀

First, run the SpringBoot app. The easy way to interact with the API is to use:

* POSTMAN
    ```
    There is a collection in /resources/PlayTonic.postman_collection.json for testing the Api Calls.
## Build with 🛠️

* [Maven](https://maven.apache.org/) - Manage Dependencies
* [SpringBoot]() - Java Framework
