import React from "react";
import { useLocation, Route, Switch, Redirect } from "react-router-dom";
// reactstrap components
import { Container, Row , Button,
    Card,
    CardHeader,
    CardBody,
    FormGroup,
    Form,
    Input,
    InputGroupAddon,
    InputGroupText,
    InputGroup,
    Col} from "reactstrap";

import { Link, useHistory } from 'react-router-dom';


function NominationApplicationForm  (props) {
  const mainContent = React.useRef(null);
  const location = useLocation();
  const history = useHistory();
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
        {/* Page content */}
        <Container className="mt--8 pb-5">
          <Row className="justify-content-center">
             <Col lg="6" md="5" className="justify-content-center">
            <Card className="bg-secondary shadow border-0" style={{width:"130%", height:"100%"}}>
          <CardHeader className="bg-transparent pb-5">
            <div className=" text-center mt-2 mb-3">
              <h1>Nomination Form</h1>
          </div>
          </CardHeader>
          <CardBody className="px-lg-5 py-lg-5">
            <Form role="form">
              <FormGroup className="mb-3">
                <InputGroup className="input-group-alternative">
                  <InputGroupAddon addonType="prepend">
                    <InputGroupText>
                      <i className="ni ni-email-83" />
                    </InputGroupText>
                  </InputGroupAddon>
                  <Input
                    placeholder="Enter your roll number"
                    type="roll_no"
                    autoComplete="roll_no"
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
                    placeholder="Enter your Name"
                    type="name"
                    autoComplete="new-name"
                  />
                </InputGroup>
                    </FormGroup>
                    <FormGroup>
                <InputGroup className="input-group-alternative">
                  <InputGroupAddon addonType="prepend">
                    <InputGroupText>
                      <i className="ni ni-email-83" />
                    </InputGroupText>
                  </InputGroupAddon>
                    <Input type="select" placeholder="Select your post">

                          <option>Enter your post</option>
                          <option>President,Student Gymkhana</option>
                          <option>General Secretary,Science and Technology</option>
                          <option>Senator Y20</option>
                          <option>Senator Y19</option>
                           <option>Senator Y18</option>
                    </Input>
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
                    placeholder="Enter your Proposer Roll No"
                    type="proposer"
                    autoComplete="new-proposer"
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
                    placeholder="Enter your 1st Seconder Roll No"
                    type="seconder1"
                    autoComplete="new-seconder"
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
                    placeholder="Enter your 2nd Seconder Roll No"
                    type="seconder2"
                    autoComplete="new-seconder"
                  />
                </InputGroup>
              </FormGroup>
              <FormGroup>
                <InputGroup className="input-group-alternative">
                  <InputGroupAddon addonType="prepend">
                    <InputGroupText>
                      <i className="ni ni-email-83" />
                    </InputGroupText>
                  </InputGroupAddon>
                  <Input
                    placeholder="Enter your manifesto link"
                    type="manifesto"
                    autoComplete="new-manifesto"
                  />
                </InputGroup>
              </FormGroup>
              <div className="text-center">
                <Button className="my-4" color="primary" type="button" onClick={()=> {history.push('/gbm/dashboard')}}>
                  File Nomination
                </Button>
              </div>
            </Form>
          </CardBody>
        </Card>
      </Col>
          </Row>
        </Container>
      </div>
    </>
  );
};

export default NominationApplicationForm;
