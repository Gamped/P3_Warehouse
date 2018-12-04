import { sessionReducer } from 'redux-react-session';
import { combineReducers } from "redux";
import loginReducer from "./loginReducer"
import orderReducer from "./orderReducer"


export default combineReducers({
    login: loginReducer,
    order: orderReducer,
    session: sessionReducer
})