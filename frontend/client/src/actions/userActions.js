import { sessionService } from "redux-react-session";
import axios from "axios";

const base_url = "http://localhost:8080/";

export const login = (roll_no, access_level) => async() => {
    try{

        sessionService.saveSession();
        sessionStorage.setItem('roll_no', roll_no);
        sessionStorage.setItem('access_level', access_level);
    }
    catch(err){
        console.log("error while logging in");
        console.log(err);
    }
}

export const logout = () => async() => {
    try{
        sessionService.deleteSession();
        sessionStorage.removeItem('access_level');
        sessionStorage.removeItem('roll_no');
    }
    catch(err){
        console.log("error while logging out");
        console.log(err);
    }
}