package io.github.davidcapilla.order_management_kata.product;

public record Product(Long id, String name, Price price, Category category, Image image) {

}
