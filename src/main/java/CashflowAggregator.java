import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CashflowAggregator {

    public static void main(String[] args){
        TradeManager tradeManager = new TradeManager();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;


        while (running) {
            try {
                possibleActions();
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character after reading int

                switch (choice) {
                    case 1:
                        System.out.print("Enter CSV path: ");
                        String csvPath = scanner.nextLine().trim();
                        TradeBooking.bulkBookTrades(tradeManager, csvPath);
                        DisplayUtils.complete();
                        break;
                    //TODO: Handle Input Errors
                    case 2:
                        tradeManager.addTrade(addNewBooking(scanner));
                        DisplayUtils.complete();
                        break;
                    case 3:
                        DisplayUtils.showPnl(calculatePnL(tradeManager));
                        DisplayUtils.complete();
                        break;
                    case 4:
                        calculatePnLByGrouping(scanner, tradeManager);
                        DisplayUtils.complete();
                        break;
                    case 5:
                        tradeManager.clear();
                        DisplayUtils.complete();
                        break;
                    case 6:
                        running = false;
                        System.out.println("Exiting application.");
                        break;
                    default:
                        DisplayUtils.invalidInputEnterInteger();
                        break;
                }
            } catch (InputMismatchException e) {
                DisplayUtils.invalidInputEnterInteger();
                scanner.next();
            }
        }
        scanner.close();
    }

    private static void possibleActions() {
        System.out.println("Choose an action");
        System.out.println("1. Book multiple trades via CSV file");
        System.out.println("2. Add/Amend/Cancel a single trade");
        System.out.println("3. Calculate Aggregated Cash Position");
        System.out.println("4. Calculate Aggregated Cash Position by Grouping");
        System.out.println("5. Restart Application");
        System.out.println("6. Exit");
    }


    private static Trade addNewBooking(Scanner scanner){
        //TODO Automatic this format in display utils
        DisplayUtils.askUserInput(tradeId);
        String tradeId = scanner.nextLine().trim();

        DisplayUtils.askUserInput(bBGCode);
        String bbgCode = scanner.nextLine().trim();

        DisplayUtils.askUserInput(currency);
        String currency = scanner.nextLine().trim();

        DisplayUtils.askUserInput("Side (B or S)");
        String side = scanner.nextLine().trim();

        DisplayUtils.askUserInput("price");
        BigDecimal price = new BigDecimal(scanner.nextLine().trim());

        DisplayUtils.askUserInput("volume");
        BigDecimal volume = new BigDecimal(scanner.nextLine().trim());

        DisplayUtils.askUserInput(portfolio);
        String portfolio = scanner.nextLine().trim();

        DisplayUtils.askUserInput("action (NEW, AMEND, CANCEL)");
        String action = scanner.nextLine().trim();

        DisplayUtils.askUserInput(account);
        String account = scanner.nextLine().trim();

        DisplayUtils.askUserInput(strategy);
        String strategy = scanner.nextLine().trim();

        DisplayUtils.askUserInput(user);
        String user = scanner.nextLine().trim();

        return new Trade(tradeId, bbgCode, currency, side, price, volume, portfolio, action, account, strategy, user);
    }

    private static void calculatePnLByGrouping(Scanner scanner, TradeManager tradeManager){
        System.out.println("Choose a grouping");
        //TODO - Automatic this format
        System.out.println("1. By " + tradeId);
        System.out.println("2. By " + bBGCode);
        System.out.println("3. By " + currency);
        System.out.println("4. By " + portfolio);
        System.out.println("5. By " + account);
        System.out.println("6. By " + strategy);
        System.out.println("7. By " + user);

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character after reading int

        switch (choice) {
            case 1:
                DisplayUtils.showPnlMap(CashflowAggregator.calculatePnLByGrouping(tradeManager, Trade::getTradeId), tradeId);
                break;
            case 2:
                DisplayUtils.showPnlMap(CashflowAggregator.calculatePnLByGrouping(tradeManager, Trade::getBbgCode), bBGCode);
                break;
            case 3:
                DisplayUtils.showPnlMap(CashflowAggregator.calculatePnLByGrouping(tradeManager, Trade::getCurrency), currency);
                break;
            case 4:
                DisplayUtils.showPnlMap(CashflowAggregator.calculatePnLByGrouping(tradeManager, Trade::getPortfolio), portfolio);
                break;
            case 5:
                DisplayUtils.showPnlMap(CashflowAggregator.calculatePnLByGrouping(tradeManager, Trade::getAccount), account);
                break;
            case 6:
                DisplayUtils.showPnlMap(CashflowAggregator.calculatePnLByGrouping(tradeManager, Trade::getStrategy), strategy);
                break;
            case 7:
                DisplayUtils.showPnlMap(CashflowAggregator.calculatePnLByGrouping(tradeManager, Trade::getUser), user);
                break;
            default:
                DisplayUtils.invalidInputEnterInteger();
                calculatePnLByGrouping(scanner, tradeManager);
                break;
        }
    }

    public static BigDecimal calculatePnL(TradeManager tradeManager) {
        return tradeManager.getTradeMap().values().stream()
                .map(Trade::getPnl).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static Map<String, BigDecimal> calculatePnLByGrouping(TradeManager tradeManager, Function<Trade, String> groupingFunction) {
        return tradeManager.getTradeMap().values().stream()
                .collect(Collectors.groupingBy(groupingFunction,
                        Collectors.reducing(BigDecimal.ZERO, Trade::getPnl, BigDecimal::add)));
    }

    private static final String tradeId = "Trade ID";
    private static final String bBGCode = "BBG Code";
    private static final String currency = "Currency";
    private static final String portfolio = "Portfolio";
    private static final String account = "Account";
    private static final String strategy = "Strategy";
    private static final String user = "User";
}
