import React,{Component} from "react";
import { useLocation, Route, Switch, Redirect } from "react-router-dom";
import { useState } from "react";
import axios from "axios";
import {lrs} from 'lrs';
import {CryptoJS} from 'crypto-js';
// reactstrap components
import {
  Button,
  Card,
  CardHeader,
  CardBody,
  FormGroup,
  Form,
  Input,
  InputGroupAddon,
  InputGroupText,
  InputGroup,
  Row,
  Col,
} from "reactstrap";

// core components
import CandidateLoginNavbar from "components/Navbars/CandidateLoginNavbar";

function ReportingPortal  (props) {
  const mainContent = React.useRef(null);
  const location = useLocation();
  const base_url = "http://localhost:8080/";
  const [keys, setKeys] = useState([]);

  React.useEffect(() => {
    document.body.classList.add("bg-default");
    return () => {
      document.body.classList.remove("bg-default");
    };
  }, []);
  React.useEffect(() => {
    document.documentElement.scrollTop = 0;
    document.scrollingElement.scrollTop = 0;
    mainContent.current.scrollTop = 0;
  }, [location]);

  async function addReport(event){
    event.preventDefault();

    await axios
      .get(base_url + "api/report/keys/public/")
      .then((response) => {
        setKeys(response.data);
      })
      .catch((error) => {
        console.log(error);
      });

    let roll_no = prompt("Please enter your roll number");

    if(roll_no == null || roll_no == ""){
      alert("No roll number added");
      return;
    }

    var roll_key = keys.find((obj) => {obj.roll == roll_no;});

    if(roll_key == undefined){
      alert("This roll no hasn't signed up yet");
      return;
    }

    let pub_keys = [];
    keys.forEach((obj) => {pub_keys.append(obj.publicKey)} );

    let password = prompt("Please enter your password");
    var bytes = CryptoJS.AES.decrypt(roll_key.encryptedPriv, password);
    var decryptedPriv = bytes.toString(CryptoJS.enc.Utf8);
    let message = document.getElementById("message").value;
    var secretKey='{"publicKey":'+roll_key.publicKey+', "privateKey":' + decryptedPriv + '}';
    secretKey= JSON.parse(secretKey);

    var signed = lrs.sign(pub_keys, secretKey, message);

    await axios
      .post(base_url + "api/report/post/", {
        "message": message,
        "signed": signed,
      })
      .then((response) => {
        alert("Report added successfully");
      })
      .catch((error) => {
        console.log(error);
        alert("some error took place");
      });
    
  } 

  return (
    <>
      <div className="main-content" ref={mainContent}>
        <CandidateLoginNavbar />
        <div className="header bg-gradient-info py-7 py-lg-8">         
          <div className="separator separator-bottom separator-skew zindex-100">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              preserveAspectRatio="none"
              version="1.1"
              viewBox="0 0 2560 100"
              x="0"
              y="0"
            >
              <polygon
                className="fill-default"
                points="2560 0 2560 100 0 100"
                          />
                          
            </svg>
          </div>
        </div>
        <div style={{display: 'flex',  justifyContent:'center', alignItems:'center', height: '100vh'}}>
        <Col lg="10" md="20">
        <Card className="bg-secondary shadow border-0 ">
          <CardBody className="px-lg-5 py-lg-5">
            <div className="text-center text-muted mb-4">
              <h1>Anonymous Reporting Portal</h1>
            </div>
            <Form role="form">
                <FormGroup>
                <InputGroup className="input-group-alternative">
                  <Input
                    placeholder="Description of the Complaint/Proof"
                    type="textarea"
                    autoComplete="rollno"
                        max-height="100px"
                        class="textarea"
                         rows="20" cols="100"
                  />
                  </InputGroup>
              </FormGroup>
              <Row className="my-4">
                <Col xs="12">
                      <span className="text-muted"> 
                      <h3>Note:</h3> This portal is completely anonymous. Add a proper description of the complaint.
                      <br />
                      </span>
                    
                </Col>
                </Row>
                <Row className="my-4">
                    <Col sm="12" style={{ display: 'flex', justifyContent: 'right', alignItems: 'center' }}>

                      <Button className="mt-4" color="primary" type="button" >
                          Report
                      </Button>   
                  </Col>
              </Row>
            </Form>
          </CardBody>
        </Card>
          </Col>  
      </div>    
        </div>
          
    </>
  );
};

export default ReportingPortal;
