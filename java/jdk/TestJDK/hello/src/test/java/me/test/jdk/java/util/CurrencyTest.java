package me.test.jdk.java.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class CurrencyTest {


    public static void main(String[] args) throws IOException {

        List<Currency> currencyList = Arrays.asList(
                // 人民币
                Currency.getInstance("CNY"),
                // 美元
                Currency.getInstance("USD"),
                // 欧元
                Currency.getInstance("EUR"),
                // 日元
                Currency.getInstance("JPY"),
                // 加拿大所用的货币
                Currency.getInstance(Locale.CANADA)
        );

        System.out.println("| currencyCode | symbol | displayName      | numericCode | FractionDigits |");
        System.out.println("|--------------|--------|------------------|-------------|----------------|");
        currencyList.forEach(currency -> {

            System.out.printf("| %12s | %6s | %16s | %11d | %14d |%n",
                    currency.getCurrencyCode(),
                    currency.getSymbol(Locale.US),
                    currency.getDisplayName(),
                    currency.getNumericCode(),
                    currency.getDefaultFractionDigits()
            );

        });

    }
}
