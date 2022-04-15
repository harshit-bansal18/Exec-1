import React from 'react';
import { Route} from 'react-router-dom';

export function PublicRoutes(props){
    const {component:Component, ...rest } = props;
    const render  = props => {
        return <Component {...props} />
         
    }
    return <Route {...rest} render={render} /> ;

}