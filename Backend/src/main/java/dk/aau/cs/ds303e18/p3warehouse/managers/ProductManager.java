package dk.aau.cs.ds303e18.p3warehouse.managers;

import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestProductModel;
import dk.aau.cs.ds303e18.p3warehouse.models.users.Customer;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ProductRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductManager {
    @Autowired
    private static ProductRepository productRepository;
    public static Product saveProductToDb(Product product, Customer owner){
        owner.addProduct(product);
        CustomerManager.saveCustomerToDatabase(owner);
        return productRepository.save(product);
    }

    private static Product saveNewProduct(RestProductModel newProduct, Customer owner){
        Product product = new Product(new ObjectId());
        BeanUtils.copyProperties(newProduct, product);
        owner.addProduct(product);
        CustomerManager.saveCustomerToDatabase(owner);
        return productRepository.save(product);
    }

    public static void removeProductFromDb(Product product, Customer owner){
        owner.removeProduct(product);
        CustomerManager.saveCustomerToDatabase(owner);
        productRepository.delete(product);
    }
}
