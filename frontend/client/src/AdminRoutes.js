import React from 'react';
import { Route, Redirect} from 'react-router-dom';
//import { useSelector } from 'react-redux';

export function AdminRoutes(props){
    //const session = useSelector((state) => state.session);
    const {component:Component, ...rest } = props;
    const accesslevel = sessionStorage.getItem("access_level")
    const render  = props => {
        if(accesslevel != "Admin"){
            return <Redirect to="/admin/login"/>
        }
        return <Component {...props} />
         
    }
    return <Route {...rest} render={render} /> ;

}