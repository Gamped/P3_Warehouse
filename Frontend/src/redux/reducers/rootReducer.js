import { sessionReducer } from 'redux-react-session';
import { combineReducers } from "redux";
import loginReducer from "./loginReducer";
import orderReducer from "./orderReducer";
import addressReducer from "./addressReducer";
import productReducer from "./productReducer";
import profileReducer from "./profileReducer";


export default combineReducers({
    addressReducer,
    profileReducer,
    productReducer,
    loginReducer,
    orderReducer,
    sessionReducer
})