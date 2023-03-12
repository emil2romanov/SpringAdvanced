package bg.softuni.events.model;

import org.springframework.context.ApplicationEvent;

import java.util.ArrayList;
import java.util.List;


public class OrderCreatedEvent extends ApplicationEvent {


    private List<Long> allProductsIDs = new ArrayList<>();

    public OrderCreatedEvent(Object source) {
        super(source);
    }

    public List<Long> getAllProductsIDs() {
        return allProductsIDs;
    }

    public OrderCreatedEvent setAllProductsIDs(List<Long> allProductsIDs) {
        this.allProductsIDs = allProductsIDs;
        return this;
    }

    public OrderCreatedEvent addProductId(Long productID) {
        this.allProductsIDs.add(productID);
        return this;
    }
}
