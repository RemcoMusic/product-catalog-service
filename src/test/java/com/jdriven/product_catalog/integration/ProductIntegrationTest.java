package com.jdriven.product_catalog.integration;

import com.jdriven.product_catalog.infrastructure.persistence.PostgresRepository;
import com.jdriven.product_catalog.infrastructure.persistence.ProductRepositoryAdapter;
import com.jdriven.product_catalog.infrastructure.persistence.entity.ProductEntity;
import com.redis.testcontainers.RedisContainer;
import org.junit.jupiter.api.Test;
import org.openapitools.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@Testcontainers
@SpringBootTest(webEnvironment = DEFINED_PORT)
@AutoConfigureWebTestClient
class ProductIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:18-alpine");

    @Container
    @ServiceConnection
    static RedisContainer redisContainer = new RedisContainer("redis:8.4-alpine");

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PostgresRepository postgresRepository;

    @MockitoSpyBean
    private ProductRepositoryAdapter productRepositoryAdapter;

    @Test
    void givenValidProductWhenCreatingProductThenSuccess() {
        Product givenProduct = createProduct("01");

        webTestClient.post().uri("/products")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(givenProduct)
                .exchange()
                .expectStatus().isCreated();

        Optional<ProductEntity> storedProduct = postgresRepository.findBySerialNumber(givenProduct.getSerialNumber());
        assertThat(storedProduct.isPresent()).isTrue();
        assertThat(storedProduct.get().getSerialNumber()).isEqualTo(givenProduct.getSerialNumber());
        assertThat(storedProduct.get().getName()).isEqualTo(givenProduct.getName());
        assertThat(storedProduct.get().getDescription()).isEqualTo(givenProduct.getDescription());
        assertThat(storedProduct.get().getCategory()).isEqualTo(givenProduct.getCategory());
        assertThat(storedProduct.get().getPrice()).isEqualTo(givenProduct.getPrice());
        assertThat(storedProduct.get().getCurrency()).isEqualTo(givenProduct.getCurrency());
    }

    @Test
    void givenInvalidProductWhenCreatingProductThenFailure() {
        webTestClient.post().uri("/products")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue("{'hackerman':'hack'}")
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    void givenValidSerialNumberWhenGetProductBySerialNumberThenSuccess() {
        ProductEntity storedProduct = postgresRepository.save(createProductEntity("02"));

        Product retrievedProduct = webTestClient.get().uri("/products/02")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class)
                .returnResult().getResponseBody();

        assertThat(retrievedProduct).isNotNull();
        assertThat(retrievedProduct.getSerialNumber()).isEqualTo(storedProduct.getSerialNumber());
        assertThat(retrievedProduct.getName()).isEqualTo(storedProduct.getName());
        assertThat(retrievedProduct.getDescription()).isEqualTo(storedProduct.getDescription());
        assertThat(retrievedProduct.getCategory()).isEqualTo(storedProduct.getCategory());
        assertThat(retrievedProduct.getPrice()).isEqualTo(storedProduct.getPrice());
        assertThat(retrievedProduct.getCurrency()).isEqualTo(storedProduct.getCurrency());
    }

    @Test
    void givenInvalidSerialNumberWhenGetProductBySerialNumberThenFailure() {
        webTestClient.get().uri("/products/nothere")
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void givenValidSerialNumberWhenGetProductBySerialNumberIsCalledTwiceThenRedisIsUsed() {
        Product givenProduct = createProduct("03");

        webTestClient.post().uri("/products")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(givenProduct)
                .exchange();

        Product retrievedFirstProduct = webTestClient.get().uri("/products/03")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class)
                .returnResult().getResponseBody();

        assertThat(retrievedFirstProduct).isNotNull();

        Product retrievedSecondProduct = webTestClient.get().uri("/products/03")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class)
                .returnResult().getResponseBody();

        assertThat(retrievedSecondProduct).isNotNull();

        verify(productRepositoryAdapter, times(1))
                .findBySerialNumber(givenProduct.getSerialNumber());
    }

    private Product createProduct(String serialNumber) {
        Product product = new Product();
        product.setSerialNumber(serialNumber);
        product.setName("test_product");
        product.setDescription("test_description");
        product.setCategory("test_category");
        product.setPrice(2.50);
        product.setCurrency("EUR");
        return product;
    }

    private ProductEntity createProductEntity(String serialNumber) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setSerialNumber(serialNumber);
        productEntity.setName("test_product");
        productEntity.setDescription("test_description");
        productEntity.setCategory("test_category");
        productEntity.setPrice(2.50);
        productEntity.setCurrency("EUR");
        return productEntity;
    }
}
