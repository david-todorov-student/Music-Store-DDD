package mk.ukim.finki.emt.productcatalog.domain.valueobjects;

import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Quantity implements ValueObject {
    private final Integer quantity;

    protected Quantity() {
        this.quantity = 0;
    }

    public Quantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Quantity increment(int qty){
        return new Quantity(this.quantity+qty);
    }

    public Quantity decrement(int qty){
        return new Quantity(this.quantity-qty);
    }
}
