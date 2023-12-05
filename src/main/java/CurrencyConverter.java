import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

// Initially planned to use the Javax Money API to get the exchange rate
public class CurrencyConverter {
    private static Map<String, BigDecimal> buildExchangeRatesFromCSV(String csvFilePath) {
        boolean skipHeader = true;

        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }
                String currencyCode = nextLine[0];
                BigDecimal rate = new BigDecimal(nextLine[1]);

                exchangeRates.put(currencyCode, rate);
            }
        } catch (IOException | CsvException | NumberFormatException e) {
            e.printStackTrace();
        }
        return exchangeRates;
    }

    public static BigDecimal getExchangeRate(String currencyCode) {
        return exchangeRates.get(currencyCode);
    }

    private static final Map<String, BigDecimal> exchangeRates = new HashMap<>();
    static {
        buildExchangeRatesFromCSV("src/main/resources/csv/usd_exchange_rate.csv");
    }
}