# order-management-test

Kata for creation of an order management platform

## Considerations outside requirements

- An `id` has been added to `Order`.

## Limitations

- We are aware that when updating an order we are passing the whole order, among others, we are not
  validating the products, which can lead to modifications in the price.