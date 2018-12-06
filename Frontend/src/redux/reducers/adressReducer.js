const initialState ={
    company: "", 
    contactPerson:"", 
    phoneNumber:"", 
    address:"", 
    zip:"",
    city:"",
    cvr:"",
    country:""};

    const loginReducer = (state = initialState, action) => {
        switch(action.type){
            case "SET_COMPANY":
                state = {...state, company: action.payload}
                break;
            case "SET_CONTACTPERSON":
                state = {...state, contactPerson: action.payload}
                break;
            case "SET_PHONENUMBER":
                state = {...state, phoneNumber: action.payload}
                break;
            case "SET_ADDRESS":
                state = {...state, address: action.payload}
                break;
            case "SET_ZIP":
                state = {...state, zip: action.payload}
                break;
            case "SET_CITY":
                state = {...state, city: action.payload}
                break;
            case "SET_CVR":
                state = {...state, cvr: action.payload}
                break;
            case "SET_COUNTRY":
                state = {...state, country: action.payload}
                break;
            case "DELETE_ADRESS":
                state ={...state, initialState} 
                break;
            default:
        }  
        return state
    }
    
    export default loginReducer;