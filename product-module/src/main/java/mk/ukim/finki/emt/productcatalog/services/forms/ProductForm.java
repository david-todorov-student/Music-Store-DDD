package mk.ukim.finki.emt.productcatalog.services.forms;

import lombok.Data;
import mk.ukim.finki.emt.productcatalog.domain.models.Category;
import mk.ukim.finki.emt.productcatalog.domain.valueobjects.Quantity;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;

@Data
public class ProductForm {

    private String productName;
    private Money price;
    private Quantity quantity;
    private Category category;
}
