package dk.aau.cs.ds303e18.p3warehouse.systemTest;

import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import org.bson.types.ObjectId;

public class MakeMockProductData {

    public static Product makeProduct() {

        Product product = new Product(new ObjectId());

        product.setProductName("move");
        product.setQuantity(20);
        product.setProductId("343253beb");

        return product;
    }

    public static Product makeSecondProduct() {

        Product product = new Product(new ObjectId());

        product.setProductName("run styles");
        product.setQuantity(20);
        product.setProductId("42432b");

        return product;
    }

    public static Product makeThirdProduct() {

        Product product = new Product(new ObjectId());

        product.setProductName("jump techniques");
        product.setQuantity(30);
        product.setProductId("24325de");

        return product;
    }

    public static Product makeFourthProduct() {

        Product product = new Product(new ObjectId());

        product.setProductName("bike shop");
        product.setQuantity(60);
        product.setProductId("353645765756");

        return product;
    }

    public static Product makeFifthProduct() {

        Product product = new Product(new ObjectId());

        product.setProductName("car magazine");
        product.setQuantity(30);
        product.setProductId("43256456457");

        return product;
    }

    public static Product makeSixthProduct() {

        Product product = new Product(new ObjectId());

        product.setProductName("books about ship");
        product.setQuantity(65);
        product.setProductId("3243354654");

        return product;
    }

    public static Product makeSeventhProduct() {

       Product product = new Product(new ObjectId());

       product.setProductName("books about busses");
       product.setQuantity(66);
       product.setProductId("3r23543645765");

       return product;
    }

    public static Product makeEigthProduct() {

       Product product = new Product(new ObjectId());

       product.setProductName("music magazine");
       product.setQuantity(26);
       product.setProductId("35264564765765");

       return product;
    }

    public static Product makeExtraProduct() {

        Product product = new Product(new ObjectId());

        product.setQuantity(400);
        product.setProductName("cycling news");
        product.setProductId("342525");

        return product;
    }
}
