import React from 'react';
import {BrowserRouter as Switch,Route, Redirect,BrowserRouter} from "react-router-dom";
import SignUp from './components/gbm_authentication/SignUp';
import SignIn from './components/gbm_authentication/SignIn';
import ForgetPassword from './components/gbm_authentication/ForgetPassword';
import OTPVerification from 'components/gbm_authentication/OTPVerification';
import MainDashboardLayout from "layouts/MainDashboard.js";
import CandidateDashboard from 'components/Dashboard/CandidateDashboard';
import GBMLayout from "layouts/GBMLayout.js";
import CandidateLayout from "layouts/CandidateLayout";
import PageNotFound from 'views/examples/404Page';
import ReportingPortal from 'components/Dashboard/ReportingPortal';
import AdminLayout from 'layouts/AdminLayout.js';
import AdminDashboard from 'components/Dashboard/AdminDashboard';

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
        
        <Route exact path="/candidates/" render={(props) => <MainDashboardLayout {...props} />} />
        <Route exact path="/info/:id" render={(props) => <MainDashboardLayout {...props} />} />      
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
        <Route exact path="/candidate/manage-forms" render={(props) => <CandidateDashboard {...props} />} />
        <Route exact path="/candidate/otp-verification" render={(props) => <CandidateLayout {...props} />} />
                
        
                {/* <Route exact path="/candidates/:id" render={(props) => <ReportingPortal {...props} />} /> */}
        
        {/* gbm routes */}
        <Route exact path="/gbm/dashboard" render={(props) => <GBMLayout {...props} />} />
        <Route exact path="/gbm/profile" render={(props) => <GBMLayout {...props} />} />
        <Route exact path="/gbm/campaign-request" render={(props) => <GBMLayout {...props} />} />
        <Route exact path="/gbm/login" children={<SignIn/>} />
        <Route exact path="/gbm/signup" children={<SignUp />}/>
        <Route exact path="/gbm/forget" children={<ForgetPassword />} />
        <Route exact path="/gbm/otp-verification" children={<OTPVerification />} />
                

        {/* admin routes */}
        <Route exact path="/admin/login" render={(props)=><AdminLayout {...props}/>} />
        <Route exact path="/admin/dashboard" render={(props) => <AdminDashboard {...props} />} />
        <Route exact path="/admin/view-nominations" render={(props)=><AdminDashboard {...props}/>} />
        <Route exact path="/pagenotfound" children={<PageNotFound />} />
    </Switch>
  </BrowserRouter>
       
    )
}