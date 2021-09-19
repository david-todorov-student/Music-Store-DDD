package mk.ukim.finki.emt.productmodule.services.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.productmodule.domain.exceptions.ProductNotFoundException;
import mk.ukim.finki.emt.productmodule.domain.models.Product;
import mk.ukim.finki.emt.productmodule.domain.models.ProductId;
import mk.ukim.finki.emt.productmodule.domain.repository.ProductRepository;
import mk.ukim.finki.emt.productmodule.services.ProductService;
import mk.ukim.finki.emt.productmodule.services.forms.ProductForm;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product findById(ProductId id) {
        return productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public Product createProduct(ProductForm form) {
        Product p = Product.build(form.getProductName(),form.getPrice(),form.getSales());
        productRepository.save(p);
        return p;
    }

    @Override
    public Product orderItemCreated(ProductId productId, int quantity) {
        Product p = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
        p.addSales(quantity);
        productRepository.saveAndFlush(p);
        return p;
    }

    @Override
    public Product orderItemRemoved(ProductId productId, int quantity) {
        Product p = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
        p.removeSales(quantity);
        productRepository.saveAndFlush(p);
        return p;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }
}
