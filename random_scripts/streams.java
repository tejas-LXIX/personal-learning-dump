import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class streams {
    public static void main(String[] args) {

        List<ProductList> productsList = new ArrayList<>();
        productsList.add(new ProductList(34));
        productsList.add(new ProductList(304756));

        List<Float> productPriceList =productsList.stream()
                .map(p-> p.getPrice())        // fetching price
                .filter(p -> p > 30000)// filtering data
                .collect(Collectors.toList());
        productPriceList.forEach(System.out::println);

        Stream<ProductList> productListStream = productsList.stream();
        System.out.println(productListStream.map(p -> p.getPrice()).collect(Collectors.toList()));
        productPriceList.add(1726356.974F);
        System.out.println(productPriceList);
    }
}

class ProductList {
    private float price;

    ProductList(float price) {
        this.price = price;
    }

    public float getPrice() {
        return price;
    }
}
