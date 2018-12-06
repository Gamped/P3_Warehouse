const blankOrder = {order:[]}

const testOrder = {order:[{productId: "1234",productName:"Waffles",amount:5,}]}

const orderReducer = (state = testOrder ,action)=>{
    switch(action.type){
        case "ADD_ITEMTOORDER":
            let newOrder = {...state,order:[...state.order, action.payload]}
            return newOrder
        case "DESTROY_ORDER":
            return {...state,order:[]}
        default:
            return state;
    }
}
export default orderReducer;