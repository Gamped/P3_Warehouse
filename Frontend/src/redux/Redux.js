import {createStore, applyMiddleware} from "redux";
import rootReducer from "./reducers/rootReducer";
import {sessionService} from "redux-react-session";
import thunk from "redux-thunk";

const inistialState = { loggedIn:"false",
userType:null,
nickName:null,
userId:null,};

const middleware = [thunk];

const store = createStore(rootReducer, inistialState,applyMiddleware(...middleware));
sessionService.initSessionService(store);

export default store;