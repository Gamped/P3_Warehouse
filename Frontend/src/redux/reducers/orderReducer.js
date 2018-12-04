const blankOrder = []

const orderReducer = (state = blankOrder ,action)=>{
    switch(action.type){
        case "CREATE_ORDER":
            return{orderLines:action.orderLines}

        case "DESTROY_ORDER":
            return{orderLines:blankOrder}
        default:
            return state;
    }
}
export default orderReducer;