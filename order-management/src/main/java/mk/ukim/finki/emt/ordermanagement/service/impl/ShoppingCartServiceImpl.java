package mk.ukim.finki.emt.ordermanagement.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.ordermanagement.domain.exceptions.OrderItemIdDoesNotExistException;
import mk.ukim.finki.emt.ordermanagement.domain.exceptions.ShoppingCartIdDoesNotExistException;
import mk.ukim.finki.emt.ordermanagement.domain.model.OrderItemId;
import mk.ukim.finki.emt.ordermanagement.domain.model.ShoppingCart;
import mk.ukim.finki.emt.ordermanagement.domain.model.ShoppingCartId;
import mk.ukim.finki.emt.ordermanagement.domain.repository.ShoppingCartRepository;
import mk.ukim.finki.emt.ordermanagement.domain.valueobjects.ProductId;
import mk.ukim.finki.emt.ordermanagement.service.ShoppingCartService;
import mk.ukim.finki.emt.ordermanagement.service.forms.OrderItemForm;
import mk.ukim.finki.emt.ordermanagement.service.forms.ShoppingCartForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final Validator validator;

    @Override
    public ShoppingCartId openShoppingCart(ShoppingCartForm shoppingCartForm) {
        Objects.requireNonNull(shoppingCartForm,"Shopping cart must not be null.");
        var constraintViolations = validator.validate(shoppingCartForm);
        if (constraintViolations.size()>0) {
            throw new ConstraintViolationException("The shopping cart form is not valid", constraintViolations);
        }
        var newShoppingCart = shoppingCartRepository.saveAndFlush(toDomainObject(shoppingCartForm));
        return newShoppingCart.getId();
    }

    @Override
    public List<ShoppingCart> findAll() {
        return shoppingCartRepository.findAll();
    }

    @Override
    public Optional<ShoppingCart> findById(ShoppingCartId id) {
        return shoppingCartRepository.findById(id);
    }

    @Override
    public void addItem(ShoppingCartId shoppingCartId, OrderItemForm orderItemForm) throws ShoppingCartIdDoesNotExistException {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId).orElseThrow(ShoppingCartIdDoesNotExistException::new);
        shoppingCart.addItem(orderItemForm.getProduct(),orderItemForm.getQuantity());
        shoppingCartRepository.saveAndFlush(shoppingCart);
    }

    @Override
    public void deleteItem(ShoppingCartId shoppingCartId, OrderItemId orderItemId) throws ShoppingCartIdDoesNotExistException, OrderItemIdDoesNotExistException {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId).orElseThrow(ShoppingCartIdDoesNotExistException::new);
        shoppingCart.removeItem(orderItemId);
        shoppingCartRepository.saveAndFlush(shoppingCart);
    }

    @Override
    public void deleteItemsWithProductId(ProductId productId) {
        this.shoppingCartRepository.findAll().forEach(shoppingCart -> shoppingCart.getOrderItems()
                .removeIf(orderItem -> productId.equals(orderItem.getProductId())));
    }

    private ShoppingCart toDomainObject(ShoppingCartForm shoppingCartForm) {
        var shoppingCart = new ShoppingCart(shoppingCartForm.getCurrency());
        shoppingCartForm.getItems().forEach(item->shoppingCart.addItem(item.getProduct(),item.getQuantity()));
        return shoppingCart;
    }

}
