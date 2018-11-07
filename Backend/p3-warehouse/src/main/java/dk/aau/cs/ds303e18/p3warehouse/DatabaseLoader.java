package dk.aau.cs.ds303e18.p3warehouse;

import dk.aau.cs.ds303e18.p3warehouse.Warehouse.Product;
import dk.aau.cs.ds303e18.p3warehouse.Warehouse.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements CommandLineRunner {
    private final ProductRepository repository;

    @Autowired
    public DatabaseLoader(ProductRepository repository){
        this.repository = repository;
    }
    @Override
    public void run(String... strings) throws Exception {
        this.repository.save(new Product("OpenRA 101: A guide in rushing"));
        this.repository.save(new Product("Dildo Swaggins and the quest for Booty"));
        this.repository.save(new Product("MacOS, eller hvordan du bliver behandlet som et barn i din hverdag"));
        this.repository.save(new Product("Bliv en mand med Linux"));
        this.repository.save(new Product("Scambaiting Indians with a Virtual Machine"));
    }
}
