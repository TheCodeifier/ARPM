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
    private final int vatPercentage;

    public Country(String name, int vatPercentage) {
        this.name = name;
        this.vatPercentage = vatPercentage;
    }

    public String getName() {
        return name;
    }

    public int getVatPercentage() {
        return vatPercentage;
    }
}

class VATCalculator {
    public static int calculatePriceWithVAT(int basePrice, int vatPercentage) {
        return basePrice + (basePrice * vatPercentage / 100);
    }
}

class ARPRSystem {
    private final ArrayList<Country> countries;

    public ARPRSystem() {
        this.countries = new ArrayList<>();
    }

    public void addCountry(Country country) {
        countries.add(country);
    }

    public void showPricesForProduct(Product product) {
        System.out.println("Prices for product: " + product.getName());

        for (Country country : countries) {
            int finalPrice = VATCalculator.calculatePriceWithVAT(product.getPriceExclusiveVAT(), country.getVatPercentage());
            System.out.printf("- %s: €%.2f (inclusive %d%% VAT)%n",
                    country.getName(),
                    finalPrice / 100.0,
                    country.getVatPercentage());
        }
    }
}

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ARPMSystem ARPM = new ARPMSystem();

       ARPM.addCountry(new Country("Austria", 10));
       ARPM.addCountry(new Country("Belgium", 6));
       ARPM.addCountry(new Country("Croatia", 13));
       ARPM.addCountry(new Country("Cyprus", 5));
       ARPM.addCountry(new Country("Estonia", 22));
       ARPM.addCountry(new Country("Finland", 14));
       ARPM.addCountry(new Country("France", 10));
       ARPM.addCountry(new Country("Germany", 7));
       ARPM.addCountry(new Country("Greece", 13));
       ARPM.addCountry(new Country("Ireland", 0));
       ARPM.addCountry(new Country("Italy", 5));
       ARPM.addCountry(new Country("Latvia", 12));
       ARPM.addCountry(new Country("Lithuania", 21));
       ARPM.addCountry(new Country("Luxembourg", 3));
       ARPM.addCountry(new Country("Malta", 0));
       ARPM.addCountry(new Country("Portugal", 6));
       ARPM.addCountry(new Country("Slovakia", 5));
       ARPM.addCountry(new Country("Slovenia", 9,5));

    
        String name = scanner.nextLine();

        System.out.print("Please insert the price (exclusive VAT, in euros): ");
        double price = scanner.nextDouble();
        int priceInCents = (int) Math.round(price * 100);

        Product product = new Product(name, priceInCents);

        System.out.println();
        ARPM.showPricesForProduct(product);

        scanner.close();
    }
}
