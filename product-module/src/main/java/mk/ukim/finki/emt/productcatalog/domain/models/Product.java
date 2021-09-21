package mk.ukim.finki.emt.productcatalog.domain.models;

import lombok.Getter;
import mk.ukim.finki.emt.productcatalog.domain.valueobjects.Quantity;
import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;

import javax.persistence.*;

@Entity
@Table(name = "product")
@Getter
public class Product extends AbstractEntity<ProductId> {

    private String productName;

    @Embedded
    private Quantity quantity;

    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "price_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "price_currency"))
    })
    private Money price;

    @ManyToOne
    private Category category;

    private Product() {
        super(ProductId.randomId(ProductId.class));
    }

    public static Product build(String productName, Money price, int quantity, Category category) {
        Product p = new Product();
        p.price = price;
        p.productName = productName;
        p.quantity = new Quantity(quantity);
        p.category = category;
        return p;
    }

    public void addProducts(int qty){
        this.quantity = this.quantity.increment(qty);
    }

    public boolean sellProducts(int qty){
        if (this.quantity.getQuantity() - qty >= 0){
            this.quantity = this.quantity.decrement(qty);
            return true;
        }
        return false;
    }

    public boolean isOfCategory(Category category){
        if (category == null){
            return false;
        }

        if (category.getName().equals(this.category.getName())){
            return true;
        }

        return isOfCategory(category.getParentCategory());
    }
}
