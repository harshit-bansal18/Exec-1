import GBMProfile from "components/GBM/GBMProfile.js";
import EventAddForm from "views/examples/EventAddForm.js";
import CampaignTables from 'components/GBM/CampaignRequestTable.js';
import MainDashboard from "components/Dashboard/MainDashboard";

var gbm_routes = [
  {
    path: "/gbm/dashboard",
    name: "Admin Dashboard",
    icon: "ni ni-single-02 text-yellow",
    component: MainDashboard,
    layout: "/ouradmin",
  },
  {
    path: "/gbm/profile",
    name: "My Profile",
    icon: "ni ni-single-02 text-yellow",
    component: GBMProfile,
    layout: "/ouradmin",
  },
   {
    path: "/gbm/campaign-request",
    name: "Campaign Request",
    icon: "ni ni-single-02 text-yellow",
    component: CampaignTables,
    layout: "/ouradmin",
  }
];
export default gbm_routes;