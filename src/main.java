import java.util.ArrayList;
import java.util.Scanner;

interface CurrencyConverter {
    double convertToLocalCurrency(double priceInEuros, double exchangeRateToEuro);
}

class EuroToCurrencyConverter implements CurrencyConverter {
    public double convertToLocalCurrency(double priceInEuros, double exchangeRateToEuro) {
        return priceInEuros * exchangeRateToEuro;
    }
}

class Product {
    private final String name;
    private final double priceExclusiveVAT;

    public Product(String name, double priceExclusiveVAT) {
        this.name = name;
        this.priceExclusiveVAT = priceExclusiveVAT;
    }

    public String getName() {
        return name;
    }

    public double getPriceExclusiveVAT() {
        return priceExclusiveVAT;
    }
}

class Country {
    private final String name;
    private final double vatPercentage;
    private final String currencySymbol;
    private final double exchangeRateToEuro;
    private CurrencyConverter currencyConverter; 

    public Country(String name, double vatPercentage, String currencySymbol, double exchangeRateToEuro, CurrencyConverter currencyConverter) {
        this.name = name;
        this.vatPercentage = vatPercentage;
        this.currencySymbol = currencySymbol;
        this.exchangeRateToEuro = exchangeRateToEuro;
        this.currencyConverter = currencyConverter;
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

    public CurrencyConverter getCurrencyConverter() {
        return currencyConverter;
    }
}

class VATCalculator {
    public static double calculatePriceWithVAT(double basePrice, double vatPercentage) {
        return basePrice + (basePrice * vatPercentage / 100);
    }
}

class ARPMSystem {
    private final ArrayList<Country> countries;
    private final ArrayList<Product> products;

    public ARPMSystem() {
        this.countries = new ArrayList<>();
        this.products = new ArrayList<>();
    }

    public void addCountry(Country country) {
        countries.add(country);
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void showPricesForProduct(Product product) {
        System.out.println("\nPrices for product: " + product.getName() + "\n");

        System.out.printf("%-25s %20s %20s %25s %30s%n", "Country", "Price excl. VAT (€)", "Price incl. VAT (€)", "Price in Local Currency", "VAT Amount (€ / Local)");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");

        for (Country country : countries) {
            double basePriceEuros = product.getPriceExclusiveVAT();
            double priceWithVAT = VATCalculator.calculatePriceWithVAT(basePriceEuros, country.getVatPercentage());
            double vatAmountEuro = priceWithVAT - basePriceEuros;
            double localPrice = country.getCurrencyConverter().convertToLocalCurrency(priceWithVAT, country.getExchangeRateToEuro());
            double vatAmountLocal = vatAmountEuro * country.getExchangeRateToEuro();

            System.out.printf("%-25s %20.2f %20.2f %s%-23.2f %10.2f / %s%-14.2f%n", country.getName(), basePriceEuros, priceWithVAT, country.getCurrencySymbol(), localPrice, vatAmountEuro, country.getCurrencySymbol(), vatAmountLocal);
        }
    }
}

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ARPMSystem arpm = new ARPMSystem();

        arpm.addCountry(new Country("Austria", 10.0, "€", 1.00, new EuroToCurrencyConverter()));
        arpm.addCountry(new Country("Albania", 20.0, "L", 99.40, new EuroToCurrencyConverter()));
        arpm.addCountry(new Country("Belgium", 6.0, "€", 1.00, new EuroToCurrencyConverter()));
        arpm.addCountry(new Country("Bulgaria", 9.0, "€", 1.00, new EuroToCurrencyConverter()));
        arpm.addCountry(new Country("Bosnia and Herzegovina", 17.0, "KM", 1.96, new EuroToCurrencyConverter()));
        arpm.addCountry(new Country("Croatia", 13.0, "€", 1.00, new EuroToCurrencyConverter()));
        arpm.addCountry(new Country("Cyprus", 5.0, "€", 1.00, new EuroToCurrencyConverter()));
        arpm.addCountry(new Country("Czechia", 12.0, "KČ", 25.01, new EuroToCurrencyConverter()));
        arpm.addCountry(new Country("Denmark", 25.0, "KR", 7.47, new EuroToCurrencyConverter()));
        arpm.addCountry(new Country("Estonia", 22.0, "€", 1.00, new EuroToCurrencyConverter()));
        arpm.addCountry(new Country("Finland", 14.0, "€", 1.00, new EuroToCurrencyConverter()));
        arpm.addCountry(new Country("France", 10.0, "€", 1.00, new EuroToCurrencyConverter()));
        arpm.addCountry(new Country("Germany", 7.0, "€", 1.00, new EuroToCurrencyConverter()));
        arpm.addCountry(new Country("Greece", 13.0, "€", 1.00, new EuroToCurrencyConverter()));
        arpm.addCountry(new Country("Hungary", 5.0, "FT", 407.58, new EuroToCurrencyConverter()));
        arpm.addCountry(new Country("Ireland", 0.0, "€", 1.00, new EuroToCurrencyConverter()));
        arpm.addCountry(new Country("Italy", 5.0, "€", 1.00, new EuroToCurrencyConverter()));
        arpm.addCountry(new Country("Kosovo", 8.0, "€", 1.00, new EuroToCurrencyConverter()));
        arpm.addCountry(new Country("Latvia", 12.0, "€", 1.00, new EuroToCurrencyConverter()));
        arpm.addCountry(new Country("Lithuania", 21.0, "€", 1.00, new EuroToCurrencyConverter()));
        arpm.addCountry(new Country("Luxembourg", 3.0, "€", 1.00, new EuroToCurrencyConverter()));
        arpm.addCountry(new Country("Malta", 0.0, "€", 1.00, new EuroToCurrencyConverter()));
        arpm.addCountry(new Country("Montenegro", 7.0, "€", 1.00, new EuroToCurrencyConverter()));
        arpm.addCountry(new Country("North Macedonia", 5.0, "ДЕН", 61.54, new EuroToCurrencyConverter()));
        arpm.addCountry(new Country("Portugal", 6.0, "€", 1.00, new EuroToCurrencyConverter()));
        arpm.addCountry(new Country("Serbia", 10.0, "DIN", 117.25, new EuroToCurrencyConverter()));
        arpm.addCountry(new Country("Slovakia", 5.0, "€", 1.00, new EuroToCurrencyConverter()));
        arpm.addCountry(new Country("Slovenia", 9.5, "€", 1.00, new EuroToCurrencyConverter()));
        arpm.addCountry(new Country("Spain", 4.0, "€", 1.00, new EuroToCurrencyConverter()));
        arpm.addCountry(new Country("Sweden", 12.0, "KR", 11.10, new EuroToCurrencyConverter()));
        arpm.addCountry(new Country("Switzerland", 2.6, "₣", 0.93, new EuroToCurrencyConverter()));
        arpm.addCountry(new Country("The Netherlands", 8.0, "€", 1.00, new EuroToCurrencyConverter()));
        arpm.addCountry(new Country("United Kingdom", 0.0, "£", 0.86, new EuroToCurrencyConverter()));
        arpm.addCountry(new Country("United States", 0.0, "$", 1.14, new EuroToCurrencyConverter()));

        arpm.addProduct(new Product("Apples", 1.55));
        arpm.addProduct(new Product("Gouda cheese", 2.79));
        arpm.addProduct(new Product("Chef Select orange juice", 1.82));
        arpm.addProduct(new Product("Freeway Ice Tea", 0.83));
        arpm.addProduct(new Product("Weihenstephan long-life milk", 0.90));
        arpm.addProduct(new Product("Lipton tea", 1.55));
        arpm.addProduct(new Product("Chicken schnitzel", 7.33));
        arpm.addProduct(new Product("Sourdough bread", 0.91));
        arpm.addProduct(new Product("Arugula", 0.73));
        arpm.addProduct(new Product("Sunny Day chips", 0.82));

        System.out.println("Available Products:");
        ArrayList<Product> allProducts = arpm.getProducts();
        for (int i = 0; i < allProducts.size(); i++) {
            System.out.println((i + 1) + ". " + allProducts.get(i).getName());
        }

        System.out.print("\nSelect a product by number: ");
        int selectedProductIndex = scanner.nextInt() - 1;

        if (selectedProductIndex >= 0 && selectedProductIndex < allProducts.size()) {
            arpm.showPricesForProduct(allProducts.get(selectedProductIndex));
        } else {
            System.out.println("Invalid product selection.");
        }
    }
}
