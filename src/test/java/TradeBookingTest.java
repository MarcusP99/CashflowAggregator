import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class TradeBookingTest {

    @Test
    public void testBulkBookNewTrade() {
        TradeManager tradeManager = new TradeManager();
        String inputCsv = "src/test/resources/csv/test_new_trade.csv";
        TradeBooking.bulkBookTrades(tradeManager, inputCsv);
        Trade trade = new Trade("f033fcaf0f164f99886a6bd6249a3b6a", "BRK.A US Equity", "USD",
                "B", new BigDecimal("1892.822443063602"), new BigDecimal("223698"), "portfolio5", "NEW",
                "Account1", "Strategy6", "User5");
        assertEquals(trade, tradeManager.getTradeById("f033fcaf0f164f99886a6bd6249a3b6a"));
    }

    @Test
    public void testBulkBookSampleTrades() {
        TradeManager tradeManager = new TradeManager();
        String inputCsv = "src/main/resources/csv/sample_trades.csv";
        TradeBooking.bulkBookTrades(tradeManager, inputCsv);
    }
}