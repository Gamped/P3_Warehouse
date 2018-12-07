import { sessionReducer } from 'redux-react-session';
import { combineReducers } from "redux";
import loginReducer from "./loginReducer"
import orderReducer from "./orderReducer"
import adressReducer from "./adressReducer"


export default combineReducers({
    adressReducer,
    loginReducer,
    orderReducer,
    sessionReducer
})