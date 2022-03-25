import React from 'react';
import {BrowserRouter as Switch,Route, Redirect,BrowserRouter} from "react-router-dom";
import SignUp from './components/authentication/SignUp';
import SignIn from './components/authentication/SignIn';
import ForgetPassword from './components/authentication/ForgetPassword';
import MainDashboardLayout from "layouts/MainDashboard.js";
import CandidateDashboard from 'components/Dashboard/CandidateDashboard';
import GBMLayout from "layouts/GBMLayout.js";
import CandidateLayout from "layouts/CandidateLayout";
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
        
        {/* general routes */}
        <Route exact path="/candidates" render={(props) => <MainDashboardLayout {...props} />} />
        <Route exact path="/reporting" render={(props) => <ReportingPortal {...props} />} />

        {/* candidate routes         */}
        <Route exact path="/candidate/login" render={(props) => <CandidateLayout {...props} />} />
        <Route exact path="/candidate/signup" render={(props) => <CandidateLayout {...props} />} />
        <Route exact path="/candidate/forget" render={(props) => <CandidateLayout {...props} />} />
        <Route exact path="/candidate/request-campaigner" render={(props) => <CandidateDashboard {...props} />} />
        <Route exact path="/candidate/dashboard" render={(props) => <CandidateDashboard {...props} />} />
        <Route exact path="/candidate/profile" render={(props) => <CandidateDashboard {...props} />} />
        <Route exact path="/candidate/add-videos" render={(props) => <CandidateDashboard {...props} />} />
        <Route exact path="/candidate/add-posters" render={(props) => <CandidateDashboard {...props} />} />
        
        {/* gbm routes */}
        <Route exact path="/gbm/dashboard" render={(props) => <GBMLayout {...props} />} />
        <Route exact path="/gbm/profile" render={(props) => <GBMLayout {...props} />} />
        <Route exact path="/gbm/campaign-request" render={(props) => <GBMLayout {...props} />} />
        <Route exact path="/gbm/login" children={<SignIn/>} />
        <Route exact path="/gbm/signup" children={<SignUp />}/>
        <Route exact path="/gbm/forget" children={<ForgetPassword />} />
        <Route exact path="/pagenotfound" children={<PageNotFound />} />
    </Switch>
  </BrowserRouter>
       
    )
}