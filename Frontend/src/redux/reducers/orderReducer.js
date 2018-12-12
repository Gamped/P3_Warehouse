const blankOrder = {};


const orderReducer = (state = blankOrder ,action)=>{
    switch(action.type){
        case "ADD_ITEMTOORDER":
            let orderLines = {...state, ...action.payload}
            return orderLines
        case "DESTROY_ORDER":
            return {...state,orderLines:[]}
        default:
            return state;
    }
}
export default orderReducer;