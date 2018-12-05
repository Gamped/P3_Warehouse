import {createStore} from "redux";
import rootReducer from "./reducers/rootReducer";
import {sessionService} from "redux-react-session";

const store = createStore(rootReducer);
sessionService.initSessionService(store);

export default store;