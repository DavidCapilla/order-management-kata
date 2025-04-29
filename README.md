# order-management-test

Kata for creation of an order management platform

## Running the Application

1. Clone the repository:
   ```bash
   git clone https://github.com/DavidCapilla/order-management-kata
   cd order-management-kata
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

4. Access the application at `http://localhost:8080`.

### Running Tests

To execute the test suite, run:
```bash
mvn test
```
## List of endpoints

### Order Management

- **Create Order**: `POST /orders`
    - Request Body: JSON object representing a Seat (only required thing).
    - Response: Created order object.

      Example
      ```shell
        curl -X POST 'http://localhost:8080/orders' \
        --header 'Content-Type: application/json' \
        --data '{
            "letter": "E",
            "number": "1"
        }'
      ```
- **Cancel Order**: `POST /orders/{id}/cancel`
  - Request Body: None.
  - Response: Cancelled order object.
    
  Example
  ```shell
    curl -X POST 'http://localhost:8080/orders/19d60371-fd4f-47e5-a548-5103a18ca042/cancel'
  ```
- **Update Order**: `PUT /orders`
  - Request Body: JSON object representing an order.
  - Response: Updated order object.
  Example
  ```shell
    curl --location --request PUT 'http://localhost:8080/orders' \
    --header 'Content-Type: application/json' \
    --data '{
        "id": "173c9e01-bad4-4a32-9cda-c39a17bc6941",
        "products": [
            {
            "id": "dc82b617-9e8a-4b79-984f-b038de0d0d36",
            "name": "Boss woman eu de toilette",
            "price": {
                "amount": 44.0
            },
            "category": {
                "id": "97de3238-827b-49f9-b762-329dfd2a80a7",
                "name": "perfumes",
                "parentCategory": null
            },
            "image": {
                "url": "https://product-images.s3.amazonaws.com/perfumes/boss-woman.jpg"
            }
        }
        ],
        "paymentDetails": {
            "totalPrice": {
                "amount": 0.0
            },
            "cardToken": "",
            "paymentStatus": null,
            "paymentDate": null,
            "paymentGateway": null
        },
        "status": "OPEN",
        "customerDetails": {
            "email": null,
            "seat": {
                "letter": "E",
                "number": "1"
            }
        }
    }'
  ```
- **Process Order**:  `POST /orders/{id}/process`
  - Request Body: None.
  - Response: Processed order object.
    
  Example
  ```shell
    curl --location 'http://localhost:8080/orders/173c9e01-bad4-4a32-9cda-c39a17bc6941/process' \
    --header 'Content-Type: application/json' \
    --data '{
    "name": "Add your name in the body"
    }'
  ```  

### Product Management
- **Get Products**: `GET /products`
  - Request Body: None.
  - Response: List of products.

  Example
  ```shell
    curl --location 'http://localhost:8080/products'
  ```
  
  - **Get Product by ID**: `GET /products/{productId}`
    - Request Body: None.
    - Response: Product object.

    Example
    ```shell
      curl --location 'http://localhost:8080/products/8e7f052c-982b-451f-8f01-f305078ef5c9'
    ```

- **Get Products by Category**: `GET /products/category/{categoryId}`
  - Request Body: None.
  - Response: List of products in the specified category.

  Example
  ```shell
    curl --location 'http://localhost:8080/products/category/f48614d1-0dc5-4166-a3b5-69a283feae19'
  ```

## Considerations Outside Requirements

- An `id` has been added to `Order` for better identification and management.

## Limitations and Issues

- No logging has been provided in the application,
- Error handling is almost non-existent,
- Request and Response objects are directly the models, should be DTOs,
- We can only create empty orders (besides mandatory seat),
- **IMPORTANT:** Since the `updateOrder` endpoint currently accepts the entire order object, may lead
  to potential issues such as price manipulation. The application does not validate product details
  when updating an order, and only checks id,
- Limited validation of objects, such as Order and Product, either they will be used even if not
  valid or fields may be ignored because not used,
- There are concurrency issues and invalid states not addressed, such as error when removing from stock
  when order is already processed, this would leave the stock in an inconsistent state,
- Repetitive code, needs a good refactoring,
- Non optimised queries or storage, for example for Products, which would be great to store them by
  category (getProductsByCategory is rather inefficient), 
- `OrderRepository` and `ProductStockRepository` do not have update method, use `save` instead,
- Inconsistent testing, missing bits, repetitive data, mocking vs real objects,
- Lacking of E2E testing, relatively complex logic in processOrder endpoint not covered,
- Missing Bonus points.
