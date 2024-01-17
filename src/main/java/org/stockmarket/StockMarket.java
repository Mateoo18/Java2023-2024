package org.stockmarket;

import org.stockmarket.model.Stock;
import org.stockmarket.model.StockType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StockMarket {
    private final List<Stock> stocks;

    public StockMarket() {
        stocks = new ArrayList<>();
    }

    public void addStock(Stock stock) {
        stocks.add(stock);
    }

    // TODO zaimplementuj metodę, która zwraca opisy wszystkich akcje danego typu, razem z opisem, np.
    //  "AAPL - Technology and innovation sector - $150.0",
    //  "MSFT - Technology and innovation sector - $200.0",
    //  ...
    public List<String> listStocksByType(StockType type) {
        return stocks.stream()
                .filter(stock -> stock.getType() == type)
                .map(stock -> String.format("%s - %s - $%.2f", stock.getSymbol(), stock.getTypeDescription(), stock.getPrice()))
                .collect(Collectors.toList());
    }

    // TODO zaimplementuj metodę, która ustawia nową cenę danej akcji
    public void updateStockPrice(String symbol, double newPrice) {
        stocks.stream()
                .filter(stock -> stock.getSymbol().equals(symbol))
                .findFirst()
                .ifPresent(stock -> stock.setPrice(newPrice));
    }

    // TODO zaimplentuj metodę, która zwraca daną akcję.
    //  Użyj optional na wypadek, gdyby nie było akcji o danym symbolu.
    public Optional<Stock> getStock(String symbol) {
        return stocks.stream()
                .filter(stock -> stock.getSymbol().equals(symbol))
                .findFirst();
    }

    // TODO zaimplentuj metodę, która zwraca wszystkie akcje
    public List<Stock> getStocks() {
        return new ArrayList<>(stocks);
    }
}
