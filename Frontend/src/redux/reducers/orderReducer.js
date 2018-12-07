const blankOrder = {order:[]}


const orderReducer = (state = blankOrder ,action)=>{
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