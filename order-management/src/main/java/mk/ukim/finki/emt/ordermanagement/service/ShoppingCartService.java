package mk.ukim.finki.emt.ordermanagement.service;

import mk.ukim.finki.emt.ordermanagement.domain.exceptions.OrderItemIdDoesNotExistException;
import mk.ukim.finki.emt.ordermanagement.domain.exceptions.ShoppingCartIdDoesNotExistException;
import mk.ukim.finki.emt.ordermanagement.domain.model.OrderItemId;
import mk.ukim.finki.emt.ordermanagement.domain.model.ShoppingCart;
import mk.ukim.finki.emt.ordermanagement.domain.model.ShoppingCartId;
import mk.ukim.finki.emt.ordermanagement.domain.valueobjects.ProductId;
import mk.ukim.finki.emt.ordermanagement.service.forms.OrderItemForm;
import mk.ukim.finki.emt.ordermanagement.service.forms.ShoppingCartForm;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartService {

    ShoppingCartId openShoppingCart(ShoppingCartForm shoppingCartForm);

    List<ShoppingCart> findAll();

    Optional<ShoppingCart> findById(ShoppingCartId id);

    void addItem(ShoppingCartId shoppingCartId, OrderItemForm orderItemForm) throws ShoppingCartIdDoesNotExistException;

    void deleteItem(ShoppingCartId shoppingCartId, OrderItemId orderItemId) throws ShoppingCartIdDoesNotExistException, OrderItemIdDoesNotExistException;

    void deleteItemsWithProductId(ProductId productId);
}
