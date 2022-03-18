import React,{Component} from "react";
import { useLocation, Route, Switch, Redirect } from "react-router-dom";
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
                <InputGroup className="input-group-alternative mb-3">
                  <InputGroupAddon addonType="prepend">
                    <InputGroupText>
                      <i className="ni ni-hat-3" />
                    </InputGroupText>
                  </InputGroupAddon>
                  <Input placeholder="Name" type="text" />
                </InputGroup>
              </FormGroup>
              <FormGroup>
                <InputGroup className="input-group-alternative mb-3">
                  <InputGroupAddon addonType="prepend">
                    <InputGroupText>
                      <i className="ni ni-email-83" />
                    </InputGroupText>
                  </InputGroupAddon>
                  <Input
                    placeholder="IITK Email"
                    type="email"
                    autoComplete="new-email"
                    />
                </InputGroup>
              </FormGroup>
              <FormGroup>
                <InputGroup className="input-group-alternative">
                  <InputGroupAddon addonType="prepend">
                    <InputGroupText>
                      <i className="ni ni-lock-circle-open" />
                    </InputGroupText>
                  </InputGroupAddon>
                  <Input
                    placeholder="Roll No"
                    type="rollno"
                    autoComplete="rollno"
                  />
                  </InputGroup>
                </FormGroup>
                <FormGroup>
                <InputGroup className="input-group-alternative">
                  <InputGroupAddon addonType="prepend">
                    <InputGroupText>
                      <i className="ni ni-lock-circle-open" />
                    </InputGroupText>
                  </InputGroupAddon>
                  <Input
                    placeholder="Description of the Complaint/Proof"
                    type="rollno"
                    autoComplete="rollno"
                  />
                  </InputGroup>
              </FormGroup>
              <Row className="my-4">
                <Col xs="12">
                      <span className="text-muted"> 
                      <h3>Note:</h3> This portal is completely anonymous. Add a proper description and upload proofs. Fields of name, roll no and email are optional.
                      <br />
                      You can also upload more than 1 proofs.
                      </span>
                    
                </Col>
                </Row>
                <Row className="my-4">
                    <Col sm="12" style={{ display: 'flex', justifyContent: 'right', alignItems: 'center' }}>
                      
                      <Button className="mt-4" color="primary" type="input">
                          Upload Files
                      </Button>

                      <Button className="mt-4" color="primary" type="button" >
                          Create account
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
