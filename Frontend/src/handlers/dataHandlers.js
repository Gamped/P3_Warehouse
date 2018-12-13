
export function makeProductsData(productStream) {
   
    var products = [];
    productStream.forEach((product) => {
      products.push({
        productId: product.productId,
        productName: product.productName,
        quantity: product.quantity,
        amount: 0,
        hexId: product.hexId,
        ownerHexId: product.owner.userHexId,
        owner: product.owner.nickName
        });
    });
    return products;
  }

  export function setProductProps(data) {

    let product = {
    productName: data.productName,
    productId: data.productId,
    owner: data.owner.nickName,
    quantity: data.quantity
    }
    return product;
}



  export function makePublisherAndClientOrdersData(data) {
        var orders = [];
        let owner, ownerHexId = "";

        data.forEach((publisher) => {
            if (ordersExist(publisher)) {
                owner = publisher.contactInformation.nickName;
                ownerHexId = publisher.hexId;

                publisher.orderStream.forEach((order) => {
                    orders.push(addOrder(order, owner, ownerHexId));
                }); 
            }

            if (clientsExist(publisher)) {
                publisher.clientStream.forEach((client) => {
                    
                    if (ordersExist(client)) {
                        owner = client.contactInformation.nickName;
                        ownerHexId = client.hexId;
    
                        client.orderStream.forEach((order) => {
                            orders.push(addOrder(order, owner, ownerHexId));
                            
                        });
                    }
                });
            }
        });

        return orders;
}

export function makeClientOrdersData(data){
    var orders = [];
    let owner, ownerHexId = "";

    data.forEach((client)=>{
        if(ordersExist(client)){
            owner = client.contactInformation.nickName;
                ownerHexId = client.hexId;

                client.orderStream.forEach((order) => {
                    orders.push(addOrder(order, owner, ownerHexId));
                }); 
        }
    })

    return orders;
}


export function ordersExist(customer) {
    return customer.orderStream != null && customer.orderStream != undefined;
}

export function clientsExist(publisher) {
    return publisher.numberOfClient != 0;
}

export function addOrder(order, owner, ownerHexId) {
    let orderObject = {};
    orderObject.ownerHexId = ownerHexId;
    orderObject.owner = owner;
    orderObject.orderId = order.orderId;
    orderObject.data = order.date;
    orderObject.hexId = order.hexId;
    orderObject.orderLines = order.orderLines.map((orderLine) => {
        return {
            productName: orderLine.product.productName,
            amount: orderLine.quantity,
            productId: orderLine.product.productId
        }
    })
    return orderObject;
}

export function makeOwnersData(data) {
    this.setState({rawOwnerData: data});
    let owners = [];
    
    data.forEach((publisher) => {
        owners.push({
            ownerName: publisher.contactInformation.nickName,
            hexId: publisher.hexId,
            userType: publisher.userType
        })
        
        if (publisher.numberOfClients !== 0) {
            
            publisher.clientStream.forEach((client) => {
                owners.push({
                    ownerName: client.contactInformation.nickName,
                    hexId: client.hexId,
                    userType: client.userType
                })
            })      
        }
    })
    
    return owners;
}

export function makeEmployeeData(data) {
    let employees = [];
    data.forEach((employee) => {
    employees.push({
        userName: employee.userName,
        nickname: employee.nickname,
        hexId: employee.hexId
        })
    })

    return employees;
}

export function makeOrderLinesData(data) {
    let orderLines = [];

    data.orderLines.forEach((orderLine) => {
        orderLines.push({
            productName: orderLine.product.productName,
            amount: orderLine.quantity,
            quantity: orderLine.product.quantity,
            productId: orderLine.product.productId
        })
    })
    return orderLines;
}

export const makeCustomerData = (data) =>{
    var customers = [];
    data.forEach((customer) => {
        
        let letter = customer.userType.slice(0,1);
        let userType = letter + customer.userType.slice(1,customer.userType.length).toLowerCase();
        customers.push({
            userName: customer.userName,
            userType: userType,
            password: customer.password,
            hexId: customer.hexId,
            nickName: customer.contactInformation.nickName,
            email: customer.contactInformation.email,
            phoneNumber: customer.contactInformation.phoneNumber,
            address: customer.contactInformation.address,
            zipCode: customer.contactInformation.zipCode,
            city: customer.contactInformation.city              
        })
    });
    return customers;
}

export function makeClientDetails(publisher) {
    let clients = [];
    if (clientsExist(publisher)) {
        publisher.clientStream.forEach((client) => {
            let cci = client.contactInformation;
            if (cci) {

                let address = cci.address + " " + cci.city + " " + cci.zipCode;
                clients.push({
                    address: address,
                    phoneNumber: cci.phoneNumber,
                    client: cci.nickName,
                    email: cci.email
            });
            }
        });
    }
    return clients;
}

export function makeCustomerProductsData(customer) {
    let products = [];
    console.log("Customer: " + customer)
    if (productsExist(customer)) {
        console.log("productsExist")
        products = makeProductsData(customer.productStream);
    }

    console.log("Producsts: " + products);

    if (isPublisher(customer)) {
        if (clientsExist(customer)) {
            console.log("Clients exist ");
            let clientProducts = [];

            customer.clientStream.forEach((client) => {
             console.log("ClientStream exist ");    
                clientProducts = makeProductsData(client.productStream);
                products = [...products, ...clientProducts];
            });
        }
    }

    return products;
}

export function productsExist(owner) {
    return owner.productStream.length != 0;
}

export function isPublisher(owner) {
    return owner.userType == "PUBLISHER";

}

export function makeOrderAddressData(data) {
    let order = {};

    order.address = data.address;
    order.zipCode = data.zipCode;
    order.country = data.country;
    order.company = data.company;
    order.phoneNumber = data.phoneNumber;
    order.contactPerson = data.contactPerson;
    order.city = data.city;

    return order;
}
