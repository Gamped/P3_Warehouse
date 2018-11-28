const initialValue = {
    loggedIn:false,
    userType:null,
    name: null,
    userId:null,
};

const testValue = {
    loggedIn:true,
    userType:"admin",
    name: "The Kev Machine",
    userId:"2",
}

const rootReducer = (state = testValue, action) => {
    if(action.type==="LOGIN"){
        let profile = {
            loggedIn:action.loggedIn,
            userType:action.userType,
            name:action.name,
            userId:action.userId,
        }
        return{profile}

    }
    if(action.type==="LOGOUT"){
        let nullProfile = {
            loggedIn:false,
            userType:null,
            name: null,
            userId:null,
        };
        return{nullProfile}
    }
    return state;
}

export default rootReducer;