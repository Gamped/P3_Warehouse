const blankOrder = {orderLines:[],
                    customer:""};


const orderReducer = (state = blankOrder ,action)=>{
    switch(action.type){
        case "SET_ORDERLINES":
            state= {...state, ...action.payload}
            console.log(state)
            return state;
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