import { sessionService } from "redux-react-session";
import axios from "axios";

const base_url = "http://localhost:8080/";

export const login = (roll_no, access_level) => async() => {
    try{
        const response = {
            roll_no: roll_no,
            access_level: access_level,
        };

        sessionService.saveSession();
        sessionService.saveUser(response);
    }
    catch(err){
        console.log("error while logging in");
        console.log(err);
    }
}

export const logout = () => async() => {
    try{
        sessionService.deleteSession();
        sessionService.deleteUser();
    }
    catch(err){
        console.log("error while logging out");
        console.log(err);
    }
}