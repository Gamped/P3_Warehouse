const initialState ={
    id: "", 
    quantity:"", 
    name:"", 
};

const loginReducer = (state = initialState, action) => {
    switch(action.type){
        case "SET_PRODUCT_ID":
        case "SET_PRODCUT_QUANTITY":
        case "SET_PRODUCT_NAME":
            state = {...state, ...action.payload}                
            break;
        case "DELETE_ADRESS":
            state ={...state, initialState} 
            break;
        default:
    }  
    return state
}
    
export default loginReducer;