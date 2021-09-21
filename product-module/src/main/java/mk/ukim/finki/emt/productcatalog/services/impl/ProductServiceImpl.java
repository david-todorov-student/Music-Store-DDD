package mk.ukim.finki.emt.productcatalog.services.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.productcatalog.domain.exceptions.CategoryDoesNotExistException;
import mk.ukim.finki.emt.productcatalog.domain.exceptions.ErrorWithOrderClientException;
import mk.ukim.finki.emt.productcatalog.domain.exceptions.NotEnoughStockException;
import mk.ukim.finki.emt.productcatalog.domain.exceptions.ProductNotFoundException;
import mk.ukim.finki.emt.productcatalog.domain.models.Category;
import mk.ukim.finki.emt.productcatalog.domain.models.Product;
import mk.ukim.finki.emt.productcatalog.domain.models.ProductId;
import mk.ukim.finki.emt.productcatalog.domain.repository.CategoryRepository;
import mk.ukim.finki.emt.productcatalog.domain.repository.ProductRepository;
import mk.ukim.finki.emt.productcatalog.services.ProductService;
import mk.ukim.finki.emt.productcatalog.services.forms.ProductForm;
import mk.ukim.finki.emt.productcatalog.xport.client.OrderClient;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final OrderClient orderClient;

    @Override
    public Product findById(ProductId id) {
        return productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public Product createProduct(ProductForm form) {
        Product p = Product.build(form.getProductName(),form.getPrice(),form.getQuantity().getQuantity(), form.getCategory());
        productRepository.save(p);
        return p;
    }

    @Override
    public void deleteProduct(ProductId productId) {
        productRepository.deleteById(productId);
        if (!orderClient.deleteItems(productId.getId())){
            throw new ErrorWithOrderClientException();
        }
    }

    @Override
    public Product orderItemCreated(ProductId productId, int quantity) {
        Product p = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
        if (!p.sellProducts(quantity)){
            throw new NotEnoughStockException();
        }
        productRepository.saveAndFlush(p);
        return p;
    }

    @Override
    public Product orderItemRemoved(ProductId productId, int quantity) {
        Product p = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
        p.addProducts(quantity);
        productRepository.saveAndFlush(p);
        return p;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getAllByCategory(String categoryName) {
        Category category = categoryRepository.findById(categoryName).orElseThrow(CategoryDoesNotExistException::new);
        return getAll().stream().filter(product -> product.isOfCategory(category)).collect(Collectors.toList());
    }
}
