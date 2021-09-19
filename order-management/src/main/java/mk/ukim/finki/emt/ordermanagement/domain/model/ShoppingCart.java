package mk.ukim.finki.emt.ordermanagement.domain.model;

import lombok.Getter;
import lombok.NonNull;
import mk.ukim.finki.emt.ordermanagement.domain.valueobjects.Product;
import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Currency;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "shopping_cart")
@Getter
public class ShoppingCart extends AbstractEntity<ShoppingCartId> {

    @Enumerated(EnumType.STRING)
    private ShoppingCartState shoppingCartState;

    @Column(name = "shopping_cart_currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<OrderItem> orderItems = new HashSet<>();

    private ShoppingCart() {
        super(ShoppingCartId.randomId(ShoppingCartId.class));
    }

    public ShoppingCart(Currency currency){
        super(ShoppingCartId.randomId(ShoppingCartId.class));
        this.currency = currency;
    }

    public Money totalPrice() {
        return this.orderItems.stream()
                .map(OrderItem::subtotal)
                .reduce(new Money(currency, 0D), Money::add);
    }

    public OrderItem addItem(@NonNull Product product, int qty) {
        Objects.requireNonNull(product,"Product must not be null");
        var item  = new OrderItem(product.getId(),product.getPrice(),qty);
        orderItems.add(item);
        return item;
    }

    public void removeItem(@NonNull OrderItemId orderItemId) {
        Objects.requireNonNull(orderItemId,"Order Item must not be null");
        orderItems.removeIf(v->v.getId().equals(orderItemId));
    }

}
