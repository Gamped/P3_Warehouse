const blankOrder = {orderLines:[],
                    customer:"",
                    selectedOrder:[]
};

const orderReducer = (state = blankOrder ,action)=>{
    
    switch(action.type){
        case "SET_SELECTEDORDER":
        case "SET_ORDERLINES":
        case "SET_CUSTOMER":
            state = {...state, ...action.payload}
            console.log(state)
            return state;
        case "DESTROY_ORDER":
            return {...state,orderLines:[]}
        default:
            return state;
    }
}
export default orderReducer;