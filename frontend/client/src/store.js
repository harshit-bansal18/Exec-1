import { createStore, applyMiddleware, compose } from 'redux';
import rootReducer from 'components/reducers';
import thunkMiddleware from 'redux-thunk';
import { sessionService } from 'redux-react-session';
const store = createStore(rootReducer, compose(applyMiddleware(thunkMiddleware), window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()));
sessionService.initSessionService(store);
export default store;
