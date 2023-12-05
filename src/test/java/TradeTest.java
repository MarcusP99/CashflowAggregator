import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TradeTest {

    @Test
    public void testGBPPnl(){
        Trade tradeGBP = new Trade("94de9256c1444388a569e9a8f8cf3512", "AAPL US Equity", "GBP",
                "S", new BigDecimal(1989.731738365160), new BigDecimal(2570), "portfolio4", "NEW",
                "Account2", "Strategy5", "User4");
        BigDecimal volume = new BigDecimal(2570);
        BigDecimal price = new BigDecimal(1989.731738365160);
        BigDecimal fxRate = new BigDecimal("1.26");
        assertEquals(volume.multiply(price).multiply(fxRate), tradeGBP.getPnl());
    }

    @Test
    public void testCancelPnl(){
        TradeManager tradeManager = new TradeManager();
        String inputCsv = "src/test/resources/csv/test_cancel_trade.csv";
        TradeBooking.bulkBookTrades(tradeManager, inputCsv);
        assertEquals(BigDecimal.ZERO, tradeManager.getTradeById("f033fcaf0f164f99886a6bd6249a3b6a").getPnl());
    }

    @Test
    public void testAmendTrade() {
        TradeManager tradeManager = new TradeManager();
        String inputCsv = "src/test/resources/csv/test_amend_trade.csv";
        TradeBooking.bulkBookTrades(tradeManager, inputCsv);
        Trade amendTrade = new Trade("f033fcaf0f164f99886a6bd6249a3b6a", "BRK.A US Equity", "USD",
                "B", new BigDecimal("1893.822443063602"), new BigDecimal("223698"), "portfolio5", "AMEND",
                "Account1", "Strategy6", "User5");
        assertEquals(amendTrade, tradeManager.getTradeById("f033fcaf0f164f99886a6bd6249a3b6a"));
    }

    @Test
    public void getSellUSDPnl() {
        Trade tradeUSD = new Trade("94de9256c1444388a569e9a8f8cf3512", "AAPL US Equity", "USD",
                "S", new BigDecimal(1989.731738365160), new BigDecimal(2570), "portfolio4", "NEW",
                "Account2", "Strategy5", "User4");
        BigDecimal volume = new BigDecimal(2570);
        BigDecimal price = new BigDecimal(1989.731738365160);
        assertEquals(volume.multiply(price), tradeUSD.getPnl());
    }

    @Test
    public void getBuyUSDPnl() {
        Trade tradeUSD = new Trade("94de9256c1444388a569e9a8f8cf3512", "AAPL US Equity", "USD",
                "B", new BigDecimal(1989.731738365160), new BigDecimal(2570), "portfolio4", "NEW",
                "Account2", "Strategy5", "User4");
        BigDecimal volume = new BigDecimal(-2570);
        BigDecimal price = new BigDecimal(1989.731738365160);
        assertEquals(volume.multiply(price), tradeUSD.getPnl());
    }

    @Test
    public void getBbgCode() {
        Trade trade = new Trade("94de9256c1444388a569e9a8f8cf3512", "AAPL US Equity", "USD",
                "S", new BigDecimal(1989.731738365160), new BigDecimal(2570), "portfolio4", "NEW",
                "Account2", "Strategy5", "User4");
        assertEquals(trade.getBbgCode(), "AAPL US Equity");
        assertNotEquals("AAP US Equity", trade.getBbgCode());
    }

    @Test
    public void getSide() {
        Trade trade = new Trade("94de9256c1444388a569e9a8f8cf3512", "AAPL US Equity", "USD",
                "S", new BigDecimal(1989.731738365160), new BigDecimal(2570), "portfolio4", "NEW",
                "Account2", "Strategy5", "User4");
        assertEquals(trade.getSide(), Trade.Side.fromString("S"));
        assertNotEquals(Trade.Side.fromString("B"), trade.getSide());
    }

    @Test
    public void getPrice() {
        Trade trade = new Trade("94de9256c1444388a569e9a8f8cf3512", "AAPL US Equity", "USD",
                "S", new BigDecimal(1989.731738365160), new BigDecimal(2570), "portfolio4", "NEW",
                "Account2", "Strategy5", "User4");
        assertEquals(trade.getPrice(), new BigDecimal(1989.731738365160));
        assertNotEquals(new BigDecimal(1989.73), trade.getPrice());
    }

    @Test
    public void getVolume() {
        Trade trade = new Trade("94de9256c1444388a569e9a8f8cf3512", "AAPL US Equity", "USD",
                "S", new BigDecimal(1989.731738365160), new BigDecimal(2570), "portfolio4", "NEW",
                "Account2", "Strategy5", "User4");
        assertEquals(trade.getVolume(), new BigDecimal(2570));
        assertNotEquals(new BigDecimal(0), trade.getVolume());
    }

    @Test
    public void getTradeId() {
        Trade trade = new Trade("94de9256c1444388a569e9a8f8cf3512", "AAPL US Equity", "USD",
                "S", new BigDecimal(1989.731738365160), new BigDecimal(2570), "portfolio4", "NEW",
                "Account2", "Strategy5", "User4");
        assertEquals(trade.getTradeId(), "94de9256c1444388a569e9a8f8cf3512");
        assertNotEquals("94de9256c1444388a569e9a", trade.getTradeId());
    }
}