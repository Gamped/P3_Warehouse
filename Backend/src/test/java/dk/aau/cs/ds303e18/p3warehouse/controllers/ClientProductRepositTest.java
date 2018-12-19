package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.models.users.Client;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ClientRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ProductRepository;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataMongoTest

public class ClientProductRepositTest {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ProductRepository productRepository;

    @Test
    public void ProductChekID(){

        ObjectId clientId = new ObjectId();
        ObjectId productId = new ObjectId();
        Client testClient = new Client(clientId);
        Product testProduct = new Product(productId);
        clientRepository.save(testClient);
        productRepository.save(testProduct);
        Assert.assertEquals(productId,testProduct.getId());
        clientRepository.delete(testClient);
        productRepository.delete(testProduct);
    }

    @Test
    public void Clientsave(){
        ObjectId clientId = new ObjectId();
        ObjectId productId = new ObjectId();
        Client testClient = new Client(clientId);
        Client chechClient;
        Product testProduct = new Product(productId);
        clientRepository.save(testClient);
        productRepository.save(testProduct);
        chechClient = clientRepository.findById(clientId).orElse(null);
        Assert.assertEquals(chechClient.getId(),testClient.getId());
        clientRepository.delete(testClient);
        productRepository.delete(testProduct);
    }

    @Test
    public void productSave(){
        ObjectId clientId = new ObjectId();
        ObjectId productId = new ObjectId();
        Client testClient = new Client(clientId);
        Product testProduct = new Product(productId);
        Product checkProduct;
        clientRepository.save(testClient);
        productRepository.save(testProduct);
        checkProduct = productRepository.findById(productId).orElse(null);
        Assert.assertEquals(checkProduct.getId(),testProduct.getId());
        clientRepository.delete(testClient);
        productRepository.delete(testProduct);
    }
}
