import java.util.ArrayList;
import java.util.Scanner;

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

abstract class Country {
    protected String name;
    protected double vatPercentage;
    protected String currencySymbol;

    public Country(String name, double vatPercentage, String currencySymbol) {
        this.name = name;
        this.vatPercentage = vatPercentage;
        this.currencySymbol = currencySymbol;
    }

    public abstract double convertToLocalCurrency(double priceInEuros);

    public String getName() { return name; }
    public double getVatPercentage() { return vatPercentage; }
    public String getCurrencySymbol() { return currencySymbol; }
}

class EuroCountry extends Country {
    public EuroCountry(String name, double vatPercentage, String currencySymbol) {
        super(name, vatPercentage, currencySymbol);
    }

    @Override
    public double convertToLocalCurrency(double priceInEuros) {
        return priceInEuros;
    }
}

class NonEuroCountry extends Country {
    private final double exchangeRateToEuro;

    public NonEuroCountry(String name, double vatPercentage, String currencySymbol, double exchangeRateToEuro) {
        super(name, vatPercentage, currencySymbol);
        this.exchangeRateToEuro = exchangeRateToEuro;
    }

    @Override
    public double convertToLocalCurrency(double priceInEuros) {
        return priceInEuros * exchangeRateToEuro;
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
            double localPrice = country.convertToLocalCurrency(priceWithVAT);
            double vatAmountLocal = country.convertToLocalCurrency(vatAmountEuro);
    
            System.out.printf("%-25s %20.2f %20.2f %s%-23.2f %10.2f / %s%-14.2f%n",country.getName(), basePriceEuros, priceWithVAT, country.getCurrencySymbol(), localPrice, vatAmountEuro, country.getCurrencySymbol(), vatAmountLocal);
        }
    }    
}

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ARPMSystem arpm = new ARPMSystem();

        arpm.addCountry(new EuroCountry("Austria", 10.0, "€"));
        arpm.addCountry(new NonEuroCountry("Albania", 20.0, "L", 99.40));
        arpm.addCountry(new EuroCountry("Belgium", 6.0, "€"));
        arpm.addCountry(new EuroCountry("Bulgaria", 9.0, "€"));
        arpm.addCountry(new NonEuroCountry("Bosnia and Herzegovina", 17.0, "KM", 1.96));
        arpm.addCountry(new EuroCountry("Croatia", 13.0, "€"));
        arpm.addCountry(new EuroCountry("Cyprus", 5.0, "€"));
        arpm.addCountry(new NonEuroCountry("Czechia", 12.0, "KČ", 25.01));
        arpm.addCountry(new NonEuroCountry("Denmark", 25.0, "KR", 7.47));
        arpm.addCountry(new EuroCountry("Estonia", 22.0, "€"));
        arpm.addCountry(new EuroCountry("Finland", 14.0, "€"));
        arpm.addCountry(new EuroCountry("France", 10.0, "€"));
        arpm.addCountry(new EuroCountry("Germany", 7.0, "€"));
        arpm.addCountry(new EuroCountry("Greece", 13.0, "€"));
        arpm.addCountry(new NonEuroCountry("Hungary", 5.0, "FT", 407.58));
        arpm.addCountry(new EuroCountry("Ireland", 0.0, "€"));
        arpm.addCountry(new EuroCountry("Italy", 5.0, "€"));
        arpm.addCountry(new EuroCountry("Kosovo", 8.0, "€"));
        arpm.addCountry(new EuroCountry("Latvia", 12.0, "€"));
        arpm.addCountry(new EuroCountry("Lithuania", 21.0, "€"));
        arpm.addCountry(new EuroCountry("Luxembourg", 3.0, "€"));
        arpm.addCountry(new EuroCountry("Malta", 0.0, "€"));
        arpm.addCountry(new EuroCountry("Montenegro", 7.0, "€"));
        arpm.addCountry(new NonEuroCountry("North Macedonia", 5.0, "ДЕН", 61.54));
        arpm.addCountry(new EuroCountry("Portugal", 6.0, "€"));
        arpm.addCountry(new NonEuroCountry("Serbia", 10.0, "DIN", 117.25));
        arpm.addCountry(new EuroCountry("Slovakia", 5.0, "€"));
        arpm.addCountry(new EuroCountry("Slovenia", 9.5, "€"));
        arpm.addCountry(new EuroCountry("Spain", 4.0, "€"));
        arpm.addCountry(new NonEuroCountry("Sweden", 12.0, "KR", 11.10));
        arpm.addCountry(new NonEuroCountry("Switzerland", 2.6, "₣", 0.93));
        arpm.addCountry(new EuroCountry("The Netherlands", 8.0, "€"));
        arpm.addCountry(new NonEuroCountry("United Kingdom", 0.0, "£", 0.86));
        arpm.addCountry(new NonEuroCountry("United States", 0.0, "$", 1.14));

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
