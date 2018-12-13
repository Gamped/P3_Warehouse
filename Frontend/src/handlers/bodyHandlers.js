export const makeOrderBodyFromData = (data, orderLines) =>{
    
    let body = {};

    let orderList=[];
    for(let key in orderLines) {
        orderList.push({product:orderLines[key],
                        quantity:orderLines[key].amount})
      }
    
    body.orderLines = orderList;
    body.address = data.address;
    body.contactPerson = data.contactPerson;
    body.phoneNumber = data.phoneNumber;
    body.country = data.country;
    body.company = data.company;
    body.zipCode = data.zip;

    return body;
}