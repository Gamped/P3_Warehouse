export function makeProductsRowsFromResponseData(data) {

    var products = [];
    data.forEach((product) => {
      products.push({
        productId: product.productId,
        productName: product.productName,
        quantity: product.quantity,
        amount: 0,
        hexId: product.hexId

        });
    });
    return products;
  }

  export function makeProductsRowsWithOwner(data) {
    
    var products = [];
    data.forEach((product) => {
        products.push({
            ownerName: product.owner.name,
            productId: product.productId,
            productName: product.productName,
            quantity: product.quantity,
            hexId: product.hexId
        });
    });
    return products;
}

  export function setProductProps(data) {

    let product = {
    productName: data.productName,
    productId: data.productId,
    owner: data.owner.contactInformation.nickName,
    quantity: data.quantity
    }
    return product;
}



  export function makePublisherAndClientOrdersData(data) {
    var orders = [];
    let orderObject = {};
    let owner = "";

    data.forEach((publisher) => {
        if (ordersExist(publisher)) {
     
            owner = publisher.contactInformation.nickName;

            publisher.orderStream.forEach((order) => {
                orders.push(addOrder(order, owner));
            });
            
            orders.push(orderObject);   
        }

        if (clientsExist(publisher)) {
            publisher.clientStream.forEach((client) => {
                
                if (ordersExist(client)) {
                    owner = client.contactInformation.nickName;

                    client.orderStream.forEach((order) => {
                        orders.push(addOrder(order, owner));
                        
                    });
                }
            });
        }
    });

    return orders;
}


export function ordersExist(customer) {
    return customer.orderStream != null && customer.orderStream != undefined;
}

export function clientsExist(publisher) {
    return publisher.numberOfClient != 0;
}

export function addOrder(order, owner) {
    let orderObject = {};
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
            quantity: orderLine.product.quantity
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
            zipCode: customer.contactInformation.zipCode              
        })
    });
    return customers;
}