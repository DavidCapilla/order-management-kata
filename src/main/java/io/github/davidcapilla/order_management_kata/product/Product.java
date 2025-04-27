package io.github.davidcapilla.order_management_kata.product;

import java.util.UUID;

public record Product(UUID id, String name, Price price, Category category, Image image) {

}
