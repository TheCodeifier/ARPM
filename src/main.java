import java.util.ArrayList;
import java.util.Scanner;

class Product {
    private final String name;
    private final int priceExclusiveVAT;

    public Product(String name, int priceExclusiveVAT) {
        this.name = name;
        this.priceExclusiveVAT = priceExclusiveVAT;
    }

    public String getName() {
        return name;
    }

    public int getPriceExclusiveVAT() {
        return priceExclusiveVAT;
    }
}

class Country {
    private final String name;
    private final double vatPercentage;
    private final String currencySymbol;
    private final double exchangeRateToEuro;

    public Country(String name, double vatPercentage, String currencySymbol, double exchangeRateToEuro) {
        this.name = name;
        this.vatPercentage = vatPercentage;
        this.currencySymbol = currencySymbol;
        this.exchangeRateToEuro = exchangeRateToEuro;
    }

    public String getName() {
        return name;
    }

    public double getVatPercentage() {
        return vatPercentage;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public double getExchangeRateToEuro() {
        return exchangeRateToEuro;
    }
}

class VATCalculator {
    public static double calculatePriceWithVAT(double basePrice, double vatPercentage) {
        return basePrice + (basePrice * vatPercentage / 100);
    }
}

class ARPMSystem {
    private final ArrayList<Country> countries;

    public ARPMSystem() {
        this.countries = new ArrayList<>();
    }

    public void addCountry(Country country) {
        countries.add(country);
    }

    public void showPricesForProduct(Product product) {
    System.out.println("Prices for product: " + product.getName());

    for (Country country : countries) {
        double basePriceEuros = product.getPriceExclusiveVAT() / 100.0;
        double priceWithVAT = VATCalculator.calculatePriceWithVAT(basePriceEuros, country.getVatPercentage());
        double localPrice = priceWithVAT * country.getExchangeRateToEuro();

        System.out.printf("- %s: €%.2f -> %s%.2f (incl. %.1f%% VAT)%n",
                country.getName(),
                priceWithVAT,
                country.getCurrencySymbol(),
                localPrice,
                country.getVatPercentage());
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ARPMSystem arpm = new ARPMSystem();

        arpm.addCountry(new Country("Austria", 10.0, "€", 1.00));
        arpm.addCountry(new Country("Albania", 20.0, "L", 99.40));
        arpm.addCountry(new Country("Belgium", 6.0, "€", 1.00));
        arpm.addCountry(new Country("Bulgaria", 9.0, "€", 1.00));
        arpm.addCountry(new Country("Bosnia and Herzegovina", 17.0, "KM", 1.96));
        arpm.addCountry(new Country("Croatia", 13.0, "€", 1.00));
        arpm.addCountry(new Country("Cyprus", 5.0, "€", 1.00));
        arpm.addCountry(new Country("Czechia", 12.0, "KČ", 25.01));
        arpm.addCountry(new Country("Denmark", 25.0, "KR", 7.47));
        arpm.addCountry(new Country("Estonia", 22.0, "€", 1.00));
        arpm.addCountry(new Country("Finland", 14.0, "€", 1.00));
        arpm.addCountry(new Country("France", 10.0, "€", 1.00));
        arpm.addCountry(new Country("Germany", 7.0, "€", 1.00));
        arpm.addCountry(new Country("Greece", 13.0, "€", 1.00));
        arpm.addCountry(new Country("Hungary", 5.0, "FT", 407.58));
        arpm.addCountry(new Country("Ireland", 0.0, "€", 1.00));
        arpm.addCountry(new Country("Italy", 5.0, "€", 1.00));
        arpm.addCountry(new Country("Kosovo", 8.0, "€", 1.00));
        arpm.addCountry(new Country("Latvia", 12.0, "€", 1.00));
        arpm.addCountry(new Country("Lithuania", 21.0, "€", 1.00));
        arpm.addCountry(new Country("Luxembourg", 3.0, "€", 1.00));
        arpm.addCountry(new Country("Malta", 0.0, "€", 1.00));
        arpm.addCountry(new Country("Montenegro", 7.0, "€", 1.00));
        arpm.addCountry(new Country("North Macedonia", 5.0, "ДЕН", 61.54));
        arpm.addCountry(new Country("Portugal", 6.0, "€", 1.00));
        arpm.addCountry(new Country("Serbia", 10.0, "DIN", 117.25));
        arpm.addCountry(new Country("Slovakia", 5.0, "€", 1.00));
        arpm.addCountry(new Country("Slovenia", 9.5, "€", 1.00));
        arpm.addCountry(new Country("Spain", 4.0, "€", 1.00));
        arpm.addCountry(new Country("Sweden", 12.0, "KR", 11.10));
        arpm.addCountry(new Country("Switzerland", 2.6, "₣", 0.93));
        arpm.addCountry(new Country("The Netherlands", 8, "€", 1.00));
        arpm.addCountry(new Country("United Kingdom", 0.0, "£", 0.86 ));
        arpm.addCountry(new Country("United States", 0.0, "$", 1.14));

        System.out.print("Enter the product name: ");
        String name = scanner.nextLine();

        System.out.print("Please insert the price (exclusive VAT, in euros): ");
        double price = scanner.nextDouble();
        int priceInCents = (int) Math.round(price * 100);

        Product product = new Product(name, priceInCents);

        System.out.println();
        arpm.showPricesForProduct(product);

        scanner.close();
    }
}