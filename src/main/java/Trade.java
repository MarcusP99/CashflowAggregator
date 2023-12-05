import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Objects;

public class Trade implements Cloneable {

    public Trade(String tradeId, String bbgCode, String currency, String side, BigDecimal price, BigDecimal volume, String portfolio, String action, String account, String strategy, String user){
        this.tradeId = tradeId;
        this.bbgCode = bbgCode;
        this.currency = currency;
        this.side = Side.fromString(side);
        this.price = price;
        this.volume = volume;
        this.portfolio = portfolio;
        this.action = action;
        this.account = account;
        this.strategy = strategy;
        this.user = user;
    }


    public BigDecimal getPnl(){
        if (cashflow == null) {
            cashflow = getVolume().multiply(getPriceInUSD()).multiply(BigDecimal.valueOf(getSide().getValue()));
        }
        return cashflow;
    }

    // TODO Improve logic of FX Rate depending on time of trade
    public BigDecimal getPriceInUSD() {
        if (priceInUSD == null) {
            priceInUSD = getPrice().multiply(CurrencyConverter.getExchangeRate(getCurrency()));
        }
        return priceInUSD;
    }

    public String getBbgCode() {
        return bbgCode;
    }

    public String getCurrency() {
        return currency;
    }

    public Side getSide() {
        return side;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public String getPortfolio() {
        return portfolio;
    }

    public String getAction() {
        return action;
    }

    public String getStrategy() {
        return strategy;
    }

    public String getUser() {
        return user;
    }

    public String getTradeId() {
        return tradeId;
    }

    public LinkedList<Trade> getTradeHistory() {
        return tradeHistory;
    }

    public String getAccount() {
        return account;
    }

    public void add(Trade trade){
        if (tradeHistory == null) tradeHistory = new LinkedList<>();
        try {
            Trade copy = (Trade) trade.clone();
            tradeHistory.add(copy);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public void amend(Trade newTrade){
        this.add(newTrade);
        copyTradeHistory(newTrade);
        if("CANCEL".equalsIgnoreCase(newTrade.getAction())){
            newTrade.setToZero();
        }
    }

    public void copyTradeHistory(Trade newTrade){
        newTrade.tradeHistory = this.getTradeHistory();
    }

    public void setToZero(){
        price = BigDecimal.ZERO;
        volume = BigDecimal.ZERO;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trade)) return false;
        Trade trade = (Trade) o;
        return getTradeId().equals(trade.getTradeId()) &&
                getBbgCode().equals(trade.getBbgCode()) &&
                getCurrency().equals(trade.getCurrency()) &&
                getSide().toString().equals(trade.getSide().toString()) &&
                getPrice().equals(trade.getPrice()) &&
                getVolume().equals(trade.getVolume()) &&
                getPortfolio().equals(trade.getPortfolio()) &&
                getAccount().equals(trade.getAccount()) &&
                getStrategy().equals(trade.getStrategy()) &&
                getUser().equals(trade.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTradeId(), getBbgCode(), getCurrency(), getSide(), getPrice(), getVolume(), getPortfolio(), getAction(), getAccount(), getStrategy(), getUser());
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private BigDecimal cashflow = null;
    private BigDecimal priceInUSD = null;
    private final String tradeId;
    private final String bbgCode;
    private final String currency;
    private final Side side;
    private BigDecimal price;
    private BigDecimal volume;
    private LinkedList<Trade> tradeHistory;
    private final String portfolio;
    private final String action;
    private final String account;
    private final String strategy;
    private final String user;

    public enum Side {
        S(1),
        B(-1);

        private final int value;

        Side(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Side fromString(String sideInput) {
            if (sideInput != null) {
                for (Side side : Side.values()) {
                    if (sideInput.equalsIgnoreCase(side.name())) {
                        return side;
                    }
                }
            }
            throw new IllegalArgumentException("No constant with name " + sideInput + " found in enum Side");
        }
    }

}
