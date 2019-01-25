const initialState ={error:null};

const loginReducer = (state = initialState, action) => {
    
    switch(action.type){

        case "LOGIN_SUCCESS":
            console.log("User was logged in.")
            state = {...state,error:null};
            break;
        case "LOGIN_ERROR":
            console.log("Error",action.error)
            state = {...state,error:action.error.message};
            break;
        case "SIGNOUT_SUCCESS":
            console.log("User was logged out")
            break;
        case "SIGNOUT_ERROR":
            console.log("Error",action.error)
            break;
        case "SIGNUP_SUCCESS":
            console.log("New User created")
            state = {...state,error:null};
            break;
        case "SIGNUP_ERROR":
            console.log("Error",action.error)
            state = {...state,error:action.error.message};
            break;
        case "EMAIL_SENT_SUCCESS":
            console.log("Email sent")
            state = {...state,error:null};
            break;
        case "EMAIL_SENT_ERROR":
            console.log("Error",action.error)
            state = {...state,error:action.error.message};
            break;
        case "EMAIL_UPDATE_SUCCESS":
            console.log("Email updated")
            state = {...state,error:null};
            break;
        case "EMAIL_UPDATE_ERROR":
            console.log("Error",action.error)
            state = {...state,error:action.error.message};
            break;
        case "NAME_UPDATE_SUCCESS":
            console.log("Name updated")
            state = {...state,error:null};
            break;
        case "NAME_UPDATE_ERROR":
            console.log("Error",action.error)
            state = {...state,error:action.error.message};
            break;
        default:
    }  
    return state;
}

export default loginReducer;
