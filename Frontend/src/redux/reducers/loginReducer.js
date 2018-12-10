/*const initialState ={loggedIn:"False",
userType:"",
userName:"",
userId:"",};*/

const devState ={loggedIn:"True",
userType:"EMPLOYEE",
nickName:"Kev The Machine",
userId:"5c0d63279b0db229ecf399cf",};

const loginReducer = (state = devState, action) => {
    switch(action.type){
        case "SET_USERTYPE":
        case "SET_USERNAME":
        case "SET_USERID":
        case "SET_LOGIN":
            state = {...state, ...action.payload}
            console.log(state)
            break;
        case "LOGOUT":
            state ={...state, userType: ""}
            state ={...state, userName: ""}
            state ={...state, userId: ""}
            state ={...state, loggedIn: "False"} 
            break;
        default:
    }  
    return state
}

export default loginReducer;
