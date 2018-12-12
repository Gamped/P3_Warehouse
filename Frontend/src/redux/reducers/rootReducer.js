import { sessionReducer } from 'redux-react-session';
import { combineReducers } from "redux";
import loginReducer from "./loginReducer";
import orderReducer from "./orderReducer";
import adressReducer from "./adressReducer";
import productReducer from "./productReducer";
import profileReducer from "./profileReducer";


export default combineReducers({
    adressReducer,
    profileReducer,
    productReducer,
    loginReducer,
    orderReducer,
    sessionReducer
})