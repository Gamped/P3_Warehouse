package dk.aau.cs.ds303e18.p3warehouse.models.users;

import dk.aau.cs.ds303e18.p3warehouse.models.orders.Order;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Stream;

@Document(collection = "publishers")

public class Publisher extends Customer {

    @Id
    private ObjectId id;
    @DBRef
    private Collection<Client> clients;

    public Publisher(ObjectId id){
        super(id);
        this.id = id;
        clients = new HashSet<>();
    }

    public String getHexId() {
        return id.toString();
    }

    public int getNumberOfClients(){
        int size;
        size = clients.size();
        return size;
    }

    public  void addClient(Client newClient){
        clients.add(newClient);
    }

    public void removeClient(Client clientToRemove){
        clients.remove(clientToRemove);
    }

    public Stream<Client> getClientStream(){
        return clients.stream();
    }
}


