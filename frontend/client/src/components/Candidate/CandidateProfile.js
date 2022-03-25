// reactstrap components
import React,{useState} from 'react';
import {
  Button,
  Card,
  CardHeader,
  CardBody,
  FormGroup,
  Form,
  Input,
  Container,
  Row,
  Col,
} from "reactstrap";
// core components
import UserHeader from "components/Headers/UserHeader.js";
//import {Auth } from '../../context/AuthContext';

function CandidateProfile (props){

  // const {CurrentUser} =Auth();
  const[disable,setDisable] =useState(true); 
  return (
    <>
      <UserHeader />
      {/* Page content */}
      <Container className="mt--7" fluid>
        <Row>
          <Col className="order-xl-2 mb-5 mb-xl-0" xl="4">
            <Card className="card-profile shadow">
              <Row className="justify-content-center">
                <Col className="order-lg-2" lg="3">
                  <div className="card-profile-image">
                      <img
                        alt="..."
                        className="rounded-circle"
                        src={
                          require("../../assets/img/icons/common/profile.jpg")
                            .default
                        }
                      />
                  </div>
                </Col>
              </Row>
              <CardHeader className="text-center border-0 pt-8 pt-md-4 pb-0 pb-md-4">      
              </CardHeader><br/><br/><br/><br/>
              <CardBody className="pt-0 pt-md-4">
                <div className="text-center">
                  <h3>
                  </h3>
                  <div className="h5 font-weight-300">
                    <i className="ni location_pin mr-2" />
                    CSE,Btech
                  </div>
                  <div className="h5 mt-4">
                    <i className="ni business_briefcase-24 mr-2" />
                   Student
                  </div>
                  <div>
                    <i className="ni education_hat mr-2" />
                    Indian Institute of Technology,Kanpur
                  </div>
                </div>
              </CardBody>
            </Card>
          </Col>
          <Col className="order-xl-1" xl="8">
            <Card className="bg-secondary shadow">
              <CardHeader className="bg-white border-0">
                <Row className="align-items-center">
                  <Col xs="8">
                    <h3 className="mb-0">My account</h3>
                  </Col>
                </Row>
              </CardHeader>
              <CardBody>
                <Form>
                  <h6 className="heading-small text-muted mb-4">
                    User information
                  </h6>
                  <div className="pl-lg-4">
                    <Row>
                      <Col lg="6">
                        <FormGroup>
                          <label
                            className="form-control-label"
                            htmlFor="input-username"
                          >
                            Username
                          </label>
                          <Input
                            className="form-control-alternative"
                            id="input-username"
                            placeholder="Username"
                            type="text"
                            disabled={disable}
                          /> 
                        </FormGroup>
                      </Col>
                      <Col lg="6">
                        <FormGroup>
                          <label
                            className="form-control-label"
                            htmlFor="input-email"
                          >
                            Email address
                          </label>
                          <Input
                            className="form-control-alternative"
                            id="input-email"
                            placeholder="jesse@example.com"
                            type="email"
                            disabled ={disable}
                          />
                        </FormGroup>
                      </Col>
                    </Row>
                    <Row>
                      <Col lg="6">
                        <FormGroup>
                          <label
                            className="form-control-label"
                            htmlFor="input-first-name"
                          >
                            Program
                          </label>
                          <Input
                            className="form-control-alternative"
                            defaultValue="B-Tech"
                            id="input-first-name"
                            placeholder="First name"
                            type="text"
                            disabled={disable}
                          />
                        </FormGroup>
                      </Col>
                      <Col lg="6">
                        <FormGroup>
                          <label
                            className="form-control-label"
                            htmlFor="input-last-name"
                          >
                            Department
                          </label>
                          <Input
                            className="form-control-alternative"
                            defaultValue="CSE"
                            id="input-last-name"
                            placeholder="Last name"
                            type="text"
                            disabled={disable}
                          />
                        </FormGroup>
                      </Col>
                    </Row>
                  </div>
                  <hr className="my-4" />
                  {/* Address */}
                  <h6 className="heading-small text-muted mb-4">
                    Other Additional Info
                  </h6>
                  <div className="pl-lg-4">
                    <Row>
                      <Col md="12">
                        <FormGroup>
                          <label
                            className="form-control-label"
                            htmlFor="input-college"
                          >
                            College Name
                          </label>
                          <Input
                            className="form-control-alternative"
                            defaultValue="Indian Institute of Technology , Kanpur"
                            id="input-college"
                            type="text"
                            disabled={disable}
                          />
                        </FormGroup>
                      </Col>
                    </Row>
                    <Row>
                      <Col lg="6">
                        <FormGroup>
                          <label
                            className="form-control-label"
                            htmlFor="input-phone-no"
                          >
                            Phone Number
                          </label>
                          <Input
                            className="form-control-alternative"
                            defaultValue="8901xxxxxx"
                            id="input-phone-no"
                            type="text"
                            disabled={disable}
                          />
                        </FormGroup>
                      </Col>
                      <Col lg="6">
                        <FormGroup>
                          <label
                            className="form-control-label"
                            htmlFor="input-roll-no"
                          >
                            IITK RollNo
                          </label>
                          <Input
                            className="form-control-alternative"
                            defaultValue="200471"
                            id="input-roll-no"
                            type="text"
                            disabled={disable}
                          />
                        </FormGroup>
                      </Col>
                    </Row>
                    <Button
                    color="info"
                    href="#pablo"
                    onClick={(e) => e.preventDefault()}
                    style={{float : "right"}}
                  >
                    Edit profile
                  </Button>
                  </div>
                </Form>
              </CardBody>
            </Card>
          </Col>
        </Row>
      </Container>
    </>
  );
};

export default CandidateProfile;
