package mk.ukim.finki.emt.productmodule.domain.repository;

import mk.ukim.finki.emt.productmodule.domain.models.Product;
import mk.ukim.finki.emt.productmodule.domain.models.ProductId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, ProductId> {
}
