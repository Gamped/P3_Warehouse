const initialState ={loggedIn:"False",
userType:"",
userName:"",
userId:"",};

const loginReducer = (state = initialState, action) => {
    
    switch(action.type){
        case "SET_USERTYPE":
        case "SET_USERNAME":
        case "SET_USERID":
        case "SET_LOGIN":
            state = {...state, ...action.payload}
            break;
        case "LOGOUT":
            state ={...state, userType: ""}
            state ={...state, userName: ""}
            state ={...state, userId: ""}
            state ={...state, loggedIn: "False"} 
            break;
        default:
    }  
    return state;
}

export default loginReducer;
