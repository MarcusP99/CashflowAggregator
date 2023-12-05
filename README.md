# Cashflow Aggregator

## Table of Contents

- [About](#about)
- [Features](#features)
- [Usage](#usage)

## About

An application that processes simplified trade data from a CSV file to calculate and display intraday cash position aggregations (PnL) in USD.

## Features

Data Reading: Reads trade booking information from a provided CSV file.

Aggregation: Calculates the cash positions (Profit and Loss, PnL) in USD. Can be aggregated based on several criteria:
TradeID
BBGCode (Bloomberg Code)
Portfolio
Strategy
User
Currency
Account 

Handling Trade Types: Program can efficiently process new trades and handle amendments and cancellations.

Interactive Interface: An interactative interface to manually add new trades and cancel existing ones, in addition to those loaded from the CSV file.

## Usage

Explain how to use your project. Provide code examples, commands, or instructions to showcase its functionality.

Example:

```bash
$ java -jar target/cashflow-aggregator-1.0-SNAPSHOT-jar-with-dependencies.jar 

Choose an action
1. Book multiple trades via CSV file
2. Add/Amend/Cancel a single trade
3. Calculate Aggregated Cash Position
4. Calculate Aggregated Cash Position by Grouping
5. Restart Application
6. Exit
1
Enter CSV path: src/main/resources/csv/sample_trades.csv
Complete!
Choose an action
1. Book multiple trades via CSV file
2. Add/Amend/Cancel a single trade
3. Calculate Aggregated Cash Position
4. Calculate Aggregated Cash Position by Grouping
5. Restart Application
6. Exit
3
Total PnL: -119696568364.9281486941202883656
Complete!
Choose an action
1. Book multiple trades via CSV file
2. Add/Amend/Cancel a single trade
3. Calculate Aggregated Cash Position
4. Calculate Aggregated Cash Position by Grouping
5. Restart Application
6. Exit
4
Choose a grouping
1. By Trade ID
2. By BBG Code
3. By Currency
4. By Portfolio
5. By Account
6. By Strategy
7. By User
3
Currency:KRW PNL: -34161518.4151916909802837576
Currency:JPY PNL: 1603116269.832331309605283952
Currency:EUR PNL: -1631980894.93006338545733756
Currency:GBP PNL: -85521829779.6436786758375312
Currency:USD PNL: -42684424278.29163795171156
Currency:NOK PNL: 8572711836.52009170026114020
Complete!
6
Exiting application.


