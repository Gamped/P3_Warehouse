import {createStore, applyMiddleware} from "redux";
import rootReducer from "./reducers/rootReducer";
import {sessionService} from "redux-react-session";
import thunk from "redux-thunk";

const inistialState = {login:{loggedIn:"True",
userType:"CLIENT",
nickName:"Kev The Machine",
userId:"AESD1238",}};

const middleware = [thunk];

const store = createStore(rootReducer, inistialState,applyMiddleware(...middleware));
sessionService.initSessionService(store);

export default store;