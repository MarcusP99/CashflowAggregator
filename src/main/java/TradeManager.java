import java.util.HashMap;
import java.util.Map;

public class TradeManager {
    public TradeManager() {
        // Initialize the data structure (in this case, a HashMap)
        tradeMap = new HashMap<>();
    }

    public void addTrade(Trade trade) {
        try{
            String tradeId = trade.getTradeId();
            if (tradeMap.containsKey(tradeId)) {
                tradeMap.get(tradeId).amend(trade);
            } else {
                trade.add(trade);
            }
            tradeMap.put(trade.getTradeId(), trade);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public Map<String, Trade> getTradeMap() {
        return tradeMap;
    }

    public Trade getTradeById(String tradeId) {
        return tradeMap.get(tradeId);
    }

    public void clear(){
        tradeMap.clear();
    }

    private Map<String, Trade> tradeMap;
}