import {makeDateString} from './../../handlers/utils.js';

export function makeAllOrdersData(data) {
    var orders = [];

    if (orders) {
 
    data.forEach((order) => {
            orders.push(makeOrderObject(order));
            });
        }
    return orders;
}

export function makeOrderObject(order) {
    let orderObject = {};
    orderObject.ownerHexId = order.owner.userHexId;
    orderObject.owner = order.owner.nickName;
    orderObject.orderId = order.orderId;
    orderObject.date = makeDateString(order.date);
    orderObject.hexId = order.hexId;
    orderObject.orderLines = order.orderLines.map((orderLine) => {
        return {
            productName: orderLine.product.productName,
            quantity: orderLine.product.quantity,
            amount: orderLine.quantity,
            productId: orderLine.product.productId
        }
    })
    return orderObject;
}