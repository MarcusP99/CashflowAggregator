import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

public class TradeBooking {

    public static void bulkBookTrades(TradeManager tradeMap, String csvFile) {
        boolean skipHeader = true;

        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }

                String tradeId = nextLine[0];
                String bbgCode = nextLine[1];
                String currency = nextLine[2];
                String side = nextLine[3]; // Consider creating an enum for Buy & Sell
                BigDecimal price = new BigDecimal(nextLine[4]);
                BigDecimal volume = new BigDecimal(nextLine[5]);
                String portfolio = nextLine[6];
                String action = nextLine[7];
                String account = nextLine[8];
                String strategy = nextLine[9];
                String user = nextLine[10];

                Trade trade = new Trade(tradeId, bbgCode, currency, side, price, volume, portfolio, action, account, strategy, user);
                tradeMap.addTrade(trade);
            }
        } catch (CsvValidationException | IOException e) {
            System.out.println("Invalid input. Please enter an valid CSV Path.");
        }
    }
}
