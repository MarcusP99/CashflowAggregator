import java.math.BigDecimal;
import java.util.Map;

public class DisplayUtils {

    public static void showPnl(BigDecimal pnl){
        System.out.println("Total PnL: "+ pnl);
    }

    public static void showPnlMap(Map<String, BigDecimal> pnlMap, String grouping){
        pnlMap.entrySet().stream().forEach(trade -> System.out.println(grouping + ":" + trade.getKey() + " PNL: " + trade.getValue()));
    }

    public static void askUserInput(String inputType){
        System.out.println("Enter " + inputType + ": ");
    }

    public static void complete(){
        System.out.println("Complete!");
    }

    public static void invalidInputEnterInteger(){
        System.out.println("Invalid command. Please enter a single digit (e.g. 3) corresponding to the action you want to select. \n");
    }
}
