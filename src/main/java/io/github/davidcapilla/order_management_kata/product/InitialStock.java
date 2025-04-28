package io.github.davidcapilla.order_management_kata.product;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class InitialStock {

    private InitialStock() {
    }

    public static ConcurrentMap<UUID, Stock> generateStock() {

        ConcurrentHashMap<UUID, Stock> result = new ConcurrentHashMap<>();

        Category foodCategory = new Category(UUID.fromString("f48614d1-0dc5-4166-a3b5-69a283feae19"), "food");
        Category perfumesCategory = new Category(UUID.fromString("97de3238-827b-49f9-b762-329dfd2a80a7"), "perfumes");

        Product bltSandwich = new Product(
                UUID.fromString("8e7f052c-982b-451f-8f01-f305078ef5c9"),
                "BLT Sandwich",
                new Price(7.99),
                foodCategory,
                new Image("https://product-images.s3.amazonaws.com/food/blt-sandwich.jpg"));
        result.put(bltSandwich.id(), new Stock(bltSandwich, 20));

        Product misoSoup = new Product(
                UUID.fromString("89fe1019-ef27-4100-831b-f2b4256dbfa8"),
                "Miso Soup",
                new Price(4.99),
                foodCategory,
                new Image("https://product-images.s3.amazonaws.com/food/miso-soup.jpg"));
        result.put(misoSoup.id(), new Stock(misoSoup, 15));

        Product sushiRoll = new Product(
                UUID.fromString("2e770da0-12ae-41a6-93c2-3495480fd53e"),
                "Sushi Roll",
                new Price(12.99),
                foodCategory,
                new Image("https://product-images.s3.amazonaws.com/food/sushi-roll.jpg"));
        result.put(sushiRoll.id(), new Stock(sushiRoll, 10));

        Product bossPerfumeMan = new Product(
                UUID.fromString("dda2889d-7083-418d-b257-a92436814f76"),
                "Boss bottled infinity man",
                new Price(43.40),
                perfumesCategory,
                new Image("https://product-images.s3.amazonaws.com/perfumes/boss-man.jpg"));
        result.put(bossPerfumeMan.id(), new Stock(bossPerfumeMan, 5));

        Product bossPerfumeWoman = new Product(
                UUID.fromString("dc82b617-9e8a-4b79-984f-b038de0d0d36"),
                "Boss woman eu de toilette",
                new Price(44.00),
                perfumesCategory,
                new Image("https://product-images.s3.amazonaws.com/perfumes/boss-woman.jpg"));
        result.put(bossPerfumeWoman.id(), new Stock(bossPerfumeWoman, 5));

        return result;
    }

}
