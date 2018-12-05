const initialState ={loggedIn:"True",
userType:"EMPLOYEE",
nickName:"Kev The Machine",
userId:"ABC123",};

const loginReducer = (state = initialState, action) => {
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
    }  
    return state
}

export default loginReducer;