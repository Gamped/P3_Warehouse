package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestProductModel;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
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

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
/*
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

        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        Optional<Product> optProduct = productController.findById(String.valueOf(id));
        Product retrievedProduct = optProduct.get();
        verify(productRepository).findById(id);
        assertEquals(product.getId(), retrievedProduct.getId());
    }

    @Test
    public void testUpdateProduct() {
        ObjectId id = new ObjectId();
        Product product = new Product(id);
        product.setProductName("mice");
        product.setQuantity(1);
        System.out.println(product.toString());
        RestProductModel restProduct = new RestProductModel();
        restProduct.setProductName("OX");
        restProduct.setQuantity(4);
        restProduct.setProductId(product.getProductId());

        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        String updateProduct = productController.update(product.getHexId() , restProduct);
        String productString = product.toString();

        assertEquals(productString, updateProduct);
    }

    @Test
    public void testDeleteProductById() {
        ObjectId id = new ObjectId();
        Product product = new Product(id);

        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        System.out.println(productRepository.findById(id));

        productController.delete(product.getHexId());
        verify(productRepository).deleteById(id);

        List<Product> products = new ArrayList<>();
        assertEquals(products, productRepository.findAll());
    }

    @Test
    public void testDeleteProductById02() {
        ObjectId id = new ObjectId();
        ObjectId newId = new ObjectId();
        Product product = new Product(id);
        Product a_product = new Product(newId);
        List<Product> products = new LinkedList<>();
        products.add(product);
        products.add(a_product);

        when(productRepository.findAll()).thenReturn(products);

        productController.delete(((LinkedList<Product>) products).remove().getHexId());
        verify(productRepository).deleteById(id);

        List<Product> productList = new ArrayList<>();
        productList.add(a_product);

        System.out.println(products);

        assertEquals(products, productRepository.findAll());
    }

    @Test
    public void testFindOwner() {
        ObjectId productId = new ObjectId();
        ObjectId pId = new ObjectId();
        ObjectId clientId = new ObjectId();
        Product product = new Product(productId);
        Product product_new = new Product(pId);
        Client client = new Client(clientId);
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        productList.add(product_new);

        when(productRepository.findByOwner(client)).thenReturn(productList);

        Iterable<Product> products = productController.findByOwner(client);

        verify(productRepository).findByOwner(client);

        assertEquals(product, products);
    }
}
*/