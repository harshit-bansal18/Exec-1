/*!

=========================================================
* Argon Dashboard React - v1.2.1
=========================================================

* Product Page: https://www.creative-tim.com/product/argon-dashboard-react
* Copyright 2021 Creative Tim (https://www.creative-tim.com)
* Licensed under MIT (https://github.com/creativetimofficial/argon-dashboard-react/blob/master/LICENSE.md)

* Coded by Creative Tim

=========================================================

* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

*/
import Index from "views/Index.js";
import Profile from "views/examples/Profile.js";
import EventAddForm from "views/examples/EventAddForm.js";
import Login from './views/examples/Login';
import Tables from './views/examples/Tables';
import Register from "views/examples/Register";
import MainDashboard from "components/Dashboard/MainDashboard";

var routes = [
  {
    path: "/candidates",
    name: "Candidates",
    icon: "ni ni-key-25 text-info",
    component: Index,
    layout: "/admin",
  },
  {
    path: "/about-us",
    name: "About Us",
    icon: "ni ni-circle-08 text-pink",
    component: Index,
    layout: "/admin",
  },
  {
    path: "/admin/dashboard",
    name: "Admin Dashboard",
    icon: "ni ni-single-02 text-yellow",
    component: MainDashboard,
    layout: "/ouradmin",
  },
  {
    path: "/add-event",
    name: "Add New Event",
    icon: "ni ni-single-02 text-yellow",
    component: EventAddForm,
    layout: "/ouradmin",
  },
   {
    path: "/add-event",
    name: "Add New Event",
    icon: "ni ni-single-02 text-yellow",
    component: EventAddForm,
    layout: "/ouradmin",
  },
  {
    path: "/add-event",
    name: "Add New Event",
    icon: "ni ni-single-02 text-yellow",
    component: EventAddForm,
    layout: "/ouradmin",
  },
  {
    path: "/candidate/login",
    name: "Admin Login Page",
    icon: "ni ni-single-02 text-yellow",
    component: Login,
    layout: "/auth",
  }
];
export default routes;
