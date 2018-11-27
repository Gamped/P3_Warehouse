package dk.aau.cs.ds303e18.p3warehouse.controllers;

import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestClientModel;
import dk.aau.cs.ds303e18.p3warehouse.models.restmodels.RestPublisherModel;
import dk.aau.cs.ds303e18.p3warehouse.models.users.*;
import dk.aau.cs.ds303e18.p3warehouse.models.warehouse.Product;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ProductRepository;
import dk.aau.cs.ds303e18.p3warehouse.repositories.PublisherRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.web.bind.annotation.*;
import dk.aau.cs.ds303e18.p3warehouse.repositories.ClientRepository;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;


import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ClientController {

    @Autowired
    ClientRepository clientRepository;

    @GetMapping("/clients")
    private Iterable<Client> findAll() {
        return clientRepository.findAll();
    }

    @GetMapping("/clients/{id}")
    Optional<Client> findById(@PathVariable ObjectId id) {

        return clientRepository.findById(id);
    }

    @PostMapping("/clients/{publisherId}/new")
    private Client newClient(@PathVariable String publisherId, @RequestBody RestClientModel restClientModel) {

        ObjectId id = new ObjectId();
        Client newClient = new Client(id);
        //newClient.setClientName(restClientModel.getClientName());
        BeanUtils.copyProperties(restClientModel, newClient);

        return clientRepository.save(newClient);
    }

    @PutMapping("/clients/edit/{hexId}")
    String update(@PathVariable("hexId") String hexId, @RequestBody RestClientModel restClientModel) {

        ObjectId id = new ObjectId(hexId);
        Optional<Client> optClient = clientRepository.findById(id);
        Client clientToSave = optClient.get();

        BeanUtils.copyProperties(restClientModel, clientToSave);

        clientRepository.save(clientToSave);

        return "Publisher updated! \n" + clientToSave.getClientName() + "\n" + clientToSave.getHexId();
    }

    @DeleteMapping("/clients/delete/{id}")
    void delete(@PathVariable String hexId) {
        ObjectId id = new ObjectId(hexId);

        clientRepository.deleteById(id);
    }

}