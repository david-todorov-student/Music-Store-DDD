package mk.ukim.finki.emt.productcatalog.config;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.productcatalog.domain.models.Category;
import mk.ukim.finki.emt.productcatalog.domain.models.Product;
import mk.ukim.finki.emt.productcatalog.domain.repository.ProductRepository;
import mk.ukim.finki.emt.productcatalog.xport.rest.ProductResource;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Currency;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
@AllArgsConstructor
public class DataInitializer {

    private final ProductRepository productRepository;

    @PostConstruct
    public void initData() {
        Category italian = new Category("Italian");
        Category pizzas = new Category(italian, "Pizzas");
        Product p1 = Product.build("Pizza", Money.valueOf(Currency.MKD,500), 10, pizzas);
        Product p2 = Product.build("Spaghetti", Money.valueOf(Currency.MKD,100), 5, italian);
        if (productRepository.findAll().isEmpty()) {
            productRepository.saveAll(Arrays.asList(p1,p2));
        }
    }
}
