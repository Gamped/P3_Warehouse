import { sessionReducer } from 'redux-react-session';
import { combineReducers } from "redux";
import loginReducer from "./loginReducer";
import orderReducer from "./orderReducer";
import adressReducer from "./adressReducer";
import productReducer from "./productReducer";


export default combineReducers({
    adressReducer,
    productReducer,
    loginReducer,
    orderReducer,
    sessionReducer
})