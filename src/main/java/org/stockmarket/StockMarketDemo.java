package org.stockmarket;

import org.stockmarket.model.Stock;
import org.stockmarket.model.StockType;

public class StockMarketDemo {
    public static void main(String[] args) {
        StockMarket market = new StockMarket();


        market.addStock(new Stock("AAPL", StockType.TECHNOLOGY, 150.0));
        market.addStock(new Stock("MSFT", StockType.TECHNOLOGY, 200.0));
        market.addStock(new Stock("JPM", StockType.FINANCE, 100.0));
        market.addStock(new Stock("PFE", StockType.HEALTHCARE, 50.0));

        System.out.println("Apple sector description:");
        market.getStock("AAPL").ifPresent(stock -> System.out.println(stock.getTypeDescription()));


        System.out.println("\nTechnology Stocks:");
        market.listStocksByType(StockType.TECHNOLOGY).forEach(System.out::println);

        System.out.println("\nUpdating price of AAPL...");
        market.updateStockPrice("AAPL", 155.0);

        System.out.println("Updated Stock:");
        market.getStocks().stream()
                .filter(stock -> stock.getSymbol().equals("AAPL"))
                .findFirst()
                .ifPresent(System.out::println);
    }
}
