

const testValue = {
    loggedIn:"true",
    userType:"admin",
    name: "The Kev Machine",
    userId:"2",
}

const rootReducer = (state, action) => {
    switch(action.type){
        case "LOGIN":
            let loggeProfile = action.user
            return {user:loggeProfile};
        default:
            return state;
    }
}

export default rootReducer;