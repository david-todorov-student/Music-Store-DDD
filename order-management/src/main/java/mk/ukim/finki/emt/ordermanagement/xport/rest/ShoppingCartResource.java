package mk.ukim.finki.emt.ordermanagement.xport.rest;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.ordermanagement.domain.valueobjects.ProductId;
import mk.ukim.finki.emt.ordermanagement.service.ShoppingCartService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
public class ShoppingCartResource {

    public final ShoppingCartService shoppingCartService;

    @PostMapping("/delete/{id}")
    public void deleteOrderItems(@PathVariable String id){
        ProductId productId = new ProductId(id);
        this.shoppingCartService.deleteItemsWithProductId(productId);
    }

}
