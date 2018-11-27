package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ProductRepository;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void productControllerLoads() throws Exception {
        assertThat(productController).isNotNull();
    }

    @Before
    public void start() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindProductById() {
        ObjectId id = new ObjectId();
        Product product = new Product(id);

        when(productRepository.findById(id)).thenReturn(Optional.ofNullable(product));

        Optional<Product> optProduct = productController.findById(String.valueOf(id));
        Product retrievedProduct = optProduct.get();
        assertEquals(product.getId(), retrievedProduct.getId());
    }


}
