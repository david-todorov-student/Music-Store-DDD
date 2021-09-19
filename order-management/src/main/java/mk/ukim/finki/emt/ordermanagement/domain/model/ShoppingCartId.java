package mk.ukim.finki.emt.ordermanagement.domain.model;

import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;

public class ShoppingCartId extends DomainObjectId {
    private ShoppingCartId() {
        super(ShoppingCartId.randomId(ShoppingCartId.class).getId());
    }

    public ShoppingCartId(@NonNull String uuid) {
        super(uuid);
    }

}
