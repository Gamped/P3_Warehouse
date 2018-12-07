const initialState ={loggedIn:"False",
userType:"",
nickName:"",
userId:"",};

const devState ={loggedIn:"True",
userType:"PUBLISHER",
nickName:"Kev The Machine",
userId:"123ABC",};

const loginReducer = (state = devState, action) => {
    switch(action.type){
        case "SET_USERTYPE":
            state = {...state, userType: action.payload}
            console.log(state)
            break;
        case "SET_USERNAME":
            state = {...state, nickName: action.payload}
            console.log(state)
            break;
        case "SET_USERID":
            state = {...state, userId: action.payload}
            console.log(state)
            break;
        case "SET_LOGIN":
            state = {...state, loggedIn: action.payload}
            console.log(state)
            break;
        case "LOGOUT":
            state ={...state, initialState} 
            break;
        default:
    }  
    return state
}

export default loginReducer;