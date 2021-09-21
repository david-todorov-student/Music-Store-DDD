package mk.ukim.finki.emt.ordermanagement.service;

import mk.ukim.finki.emt.ordermanagement.domain.exceptions.ShoppingCartIdDoesNotExistException;
import mk.ukim.finki.emt.ordermanagement.domain.model.ShoppingCart;
import mk.ukim.finki.emt.ordermanagement.domain.model.ShoppingCartId;
import mk.ukim.finki.emt.ordermanagement.domain.valueobjects.Product;
import mk.ukim.finki.emt.ordermanagement.domain.valueobjects.ProductId;
import mk.ukim.finki.emt.ordermanagement.service.forms.OrderItemForm;
import mk.ukim.finki.emt.ordermanagement.service.forms.ShoppingCartForm;
import mk.ukim.finki.emt.ordermanagement.xport.client.ProductClient;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Currency;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class ShoppingCartServiceImplTests {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ProductClient productClient;

    private static Product newProduct(String name, Money price) {
        Product p = new Product(ProductId.randomId(ProductId.class), name, price, 0);
        return p;
    }

    @Test
    public void testPlaceOrder() {
        OrderItemForm oi1 = new OrderItemForm();
        oi1.setProduct(newProduct("Pizza",Money.valueOf(Currency.MKD,1500)));
        oi1.setQuantity(1);

        OrderItemForm oi2 = new OrderItemForm();
        oi2.setProduct(newProduct("Hot Dog",Money.valueOf(Currency.MKD,500)));
        oi2.setQuantity(2);

        ShoppingCartForm shoppingCartForm = new ShoppingCartForm();
        shoppingCartForm.setCurrency(Currency.MKD);
        shoppingCartForm.setItems(Arrays.asList(oi1,oi2));

        ShoppingCartId newShoppingCartId = shoppingCartService.openShoppingCart(shoppingCartForm);
        ShoppingCart newShoppingCart = shoppingCartService.findById(newShoppingCartId).orElseThrow(ShoppingCartIdDoesNotExistException::new);
        Assertions.assertEquals(newShoppingCart.totalPrice(),Money.valueOf(Currency.MKD,2500));

    }

    @Test
    public void testPlaceOrderWithRealData() {
        List<Product> productList = productClient.findAll();
        System.out.println(productList);

        Product p1 = productList.get(0);
        Product p2 = productList.get(1);

        OrderItemForm oi1 = new OrderItemForm();
        oi1.setProduct(p1);
        oi1.setQuantity(1);

        OrderItemForm oi2 = new OrderItemForm();
        oi2.setProduct(p2);
        oi2.setQuantity(2);

        ShoppingCartForm shoppingCartForm = new ShoppingCartForm();
        shoppingCartForm.setCurrency(Currency.MKD);
        shoppingCartForm.setItems(Arrays.asList(oi1,oi2));

        ShoppingCartId newOrderId = shoppingCartService.openShoppingCart(shoppingCartForm);
        ShoppingCart newOrder = shoppingCartService.findById(newOrderId).orElseThrow(ShoppingCartIdDoesNotExistException::new);

        Money outMoney = p1.getPrice().multiply(oi1.getQuantity()).add(p2.getPrice().multiply(oi2.getQuantity()));
        Assertions.assertEquals(newOrder.totalPrice(),outMoney);
    }

}
