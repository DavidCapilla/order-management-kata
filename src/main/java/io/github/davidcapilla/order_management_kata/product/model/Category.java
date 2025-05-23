package io.github.davidcapilla.order_management_kata.product.model;

import java.util.Optional;
import java.util.UUID;
import lombok.Data;

@Data
public class Category {

    private final UUID id;
    private final String name;
    private final Category parentCategory;

    public Category(UUID id, String name, Category parentCategory) {
        this.id = id;
        this.name = name;
        this.parentCategory = parentCategory;
    }

    public Optional<Category> getParentCategory() {
        return Optional.ofNullable(parentCategory);
    }
}
