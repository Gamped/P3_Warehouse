const initialState ={
    id: "", 
    quantity:"", 
    name:"",
    owner: null 
};

const loginReducer = (state = initialState, action) => {
    
    switch(action.type){
        case "PRODUCT_UPDATED":
            console.log("Product Updated")
            break;
        case "PRODUCT_UPDATED_ERROR":
            console.log("Error",action.error.message);
            break;
        case "CREATE_PRODUCT":
            console.log("Product was created",action);
            break;
        case "CREATE_PRODCUT_ERROR":
            console.log("ERROR:", action.error.message);
            break;
        case "PRODUCT_DELETED_SUCCESS":
            console.log("Product deleted");
            break;
        case "PRODUCT_DELETED_ERROR":
            console.log("Error",action.error.message);
            break;
        default:
    }  
    return state
}
    
export default loginReducer;