package mk.ukim.finki.emt.productcatalog.domain.repository;

import mk.ukim.finki.emt.productcatalog.domain.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
}
