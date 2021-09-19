package mk.ukim.finki.emt.productmodule.services;

import mk.ukim.finki.emt.productmodule.domain.models.Product;
import mk.ukim.finki.emt.productmodule.domain.models.ProductId;
import mk.ukim.finki.emt.productmodule.services.forms.ProductForm;

import java.util.List;

public interface ProductService {
    Product findById(ProductId id);
    Product createProduct(ProductForm form);
    Product orderItemCreated(ProductId productId, int quantity);
    Product orderItemRemoved(ProductId productId, int quantity);
    List<Product> getAll();

}
