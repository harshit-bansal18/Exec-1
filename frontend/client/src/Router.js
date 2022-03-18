import React from 'react';
import {BrowserRouter as Switch,Route, Redirect,BrowserRouter} from "react-router-dom";
import SignUp from './components/authentication/SignUp';
import SignIn from './components/authentication/SignIn';
import ForgetPassword from './components/authentication/ForgetPassword';
import MainDashboardLayout from "layouts/MainDashboard.js";
import GBMDashboardLayout from "layouts/GBMDashboard.js";
import CandidateLoginLayout from "layouts/CandidateLogin";
import PageNotFound from 'views/examples/404Page';
import ReportingPortal from 'components/Dashboard/ReportingPortal';

export default function RoutingComponent(props) {
  
    return (   
    <BrowserRouter>
        <Switch>
          
        <Route path="/argon-dashboard-react">
            <Redirect to="/candidates" />
        </Route>
          
        <Route exact path="/">
            <Redirect to="/candidates" />
        </Route>
          
        <Route exact path="/candidates" render={(props) => <MainDashboardLayout {...props} />} />
        <Route exact path="/reporting" render={(props) => <ReportingPortal {...props} />}/>
        <Route exact path="/candidate/login" render={(props) => <CandidateLoginLayout {...props} />} />
                
        <Route exact path="/gbm/dashboard" render={(props) => <GBMDashboardLayout {...props} />} />
        <Route exact path="/gbm/profile" render={(props) => <GBMDashboardLayout {...props} />} />
        <Route exact path="/gbm/campaign-request" render={(props) => <GBMDashboardLayout {...props} />} />
        <Route exact path="/gbm/login" children={<SignIn/>} />
        <Route exact path="/gbm/signup" children={<SignUp />}/>
        <Route exact path="/gbm/forget" children={<ForgetPassword />} />
        <Route exact path="/pagenotfound" children={<PageNotFound />} />
    </Switch>
  </BrowserRouter>
       
    )
}