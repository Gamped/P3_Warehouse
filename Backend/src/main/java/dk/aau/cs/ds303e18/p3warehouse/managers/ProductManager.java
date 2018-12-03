package dk.aau.cs.ds303e18.p3warehouse.managers;

import dk.aau.cs.ds303e18.p3warehouse.models.users.Customer;
import dk.aau.cs.ds303e18.p3warehouse.models.users.UserType;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ClientRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ProductRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductManager {
    @Autowired
    private static ProductRepository productRepository;
    public static Product addProductToDb(Product product){
        Customer owner = CustomerManager.getCustomerFromProduct(product);
        owner.addProduct(product);
        CustomerManager.saveCustomerToDatabase(owner);
        return productRepository.save(product);
    }

    public static void removeProductFromDb(Product product){
        Customer owner = CustomerManager.getCustomerFromProduct(product);
        owner.removeProduct(product);
        CustomerManager.saveCustomerToDatabase(owner);
        productRepository.delete(product);
    }
}
