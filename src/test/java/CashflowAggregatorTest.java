import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CashflowAggregatorTest {

    @Test
    public void calculateGBPPnLTest() {
        TradeManager tradeManager = new TradeManager();
        String inputCsv = "src/test/resources/csv/test_gbp_pnl.csv";
        TradeBooking.bulkBookTrades(tradeManager, inputCsv);
        BigDecimal volume = new BigDecimal(-100);
        BigDecimal price = new BigDecimal(100);
        BigDecimal fxRate = new BigDecimal("1.26");
        assertEquals(volume.multiply(price).multiply(fxRate), CashflowAggregator.calculatePnL(tradeManager));
    }

    @Test
    public void calculatePnLTest() {
        TradeManager tradeManager = new TradeManager();
        String inputCsv = "src/test/resources/csv/test_pnl.csv";
        TradeBooking.bulkBookTrades(tradeManager, inputCsv);
        assertEquals(new BigDecimal(331500), CashflowAggregator.calculatePnL(tradeManager));
    }

    @Test
    public void calculatePnLSampleTrades() {
        TradeManager tradeManager = new TradeManager();
        String inputCsv = "src/main/resources/csv/sample_trades.csv";
        TradeBooking.bulkBookTrades(tradeManager, inputCsv);
        Map<String, BigDecimal> resultMap = new HashMap<>();
        CashflowAggregator.calculatePnLByGrouping(tradeManager, trade -> "All Trades");
    }

    @Test
    public void calculateCancelPnLTest() {
        TradeManager tradeManager = new TradeManager();
        String inputCsv = "src/test/resources/csv/test_cancel_trades_pnl.csv";
        TradeBooking.bulkBookTrades(tradeManager, inputCsv);
        assertEquals(new BigDecimal(334500), CashflowAggregator.calculatePnL(tradeManager));
    }

    @Test
    public void calculatePnLByGrouping() {
        TradeManager tradeManager = new TradeManager();
        String inputCsv = "src/test/resources/csv/test_pnl.csv";
        TradeBooking.bulkBookTrades(tradeManager, inputCsv);
        Map<String, BigDecimal> testMap = new HashMap<>();
        testMap.put("All Trades", new BigDecimal(331500));
        assertEquals(testMap, CashflowAggregator.calculatePnLByGrouping(tradeManager, trade -> "All Trades"));
    }

    @Test
    public void calculatePnLByBBGCode() {
        TradeManager tradeManager = new TradeManager();
        String inputCsv = "src/test/resources/csv/test_pnl.csv";
        TradeBooking.bulkBookTrades(tradeManager, inputCsv);
        Map<String, BigDecimal> testMap = new HashMap<>();
        testMap.put("QQQ US ETF", new BigDecimal(176500));
        testMap.put("BC94 JPY Equity", new BigDecimal(55000));
        testMap.put("MSFT US Equity", new BigDecimal(2000));
        testMap.put("GOOG US Equity", new BigDecimal(15000));
        testMap.put("AAPL US Equity", new BigDecimal(65000));
        testMap.put("V LN Equity", new BigDecimal(20000));
        testMap.put("BRK.A US Equity", new BigDecimal(-2000));
        assertEquals(testMap, CashflowAggregator.calculatePnLByGrouping(tradeManager, Trade::getBbgCode));
    }

    @Test
    public void calculatePnLByTradeID() {
        TradeManager tradeManager = new TradeManager();
        String inputCsv = "src/test/resources/csv/test_pnl.csv";
        TradeBooking.bulkBookTrades(tradeManager, inputCsv);
        CashflowAggregator.calculatePnLByGrouping(tradeManager, Trade::getTradeId);
    }
}