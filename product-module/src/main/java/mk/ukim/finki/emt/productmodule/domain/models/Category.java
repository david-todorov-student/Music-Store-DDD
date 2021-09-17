package mk.ukim.finki.emt.productmodule.domain.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Category {

    @ManyToOne
    private Category parentCategory;

    @Id
    private String name;

    public Category() {

    }

    public Category(String categoryName) {
        this.name = categoryName;
        this.parentCategory = null;
    }

    public Category(Category parentCategory, String name) {
        this.parentCategory = parentCategory;
        this.name = name;
    }
}
