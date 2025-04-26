package io.github.davidcapilla.order_management_kata.model;

public record Product(Long id, String name, Price price, Category category, Image image) {

}
