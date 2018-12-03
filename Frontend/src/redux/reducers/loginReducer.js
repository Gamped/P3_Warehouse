const inistialState = { loggedIn:"false",
userType:"",
nickName:"",
userId:""};

const loginReducer = (state = inistialState, action) => {
    switch(action.type){
        case "LOGIN":
            let loggeProfile = action.user
            return {user:loggeProfile};
        default:
            return state;
    }  
}

export default loginReducer;