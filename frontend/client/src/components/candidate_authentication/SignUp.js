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

import { Link,useHistory } from "react-router-dom";

const OTPVerification = () => {
    const history = useHistory();
  return (
    <>
      <Col lg="6" md="8">
        <Card className="bg-secondary shadow border-0">
        <CardHeader className="bg-transparent pb-5">
            <div className=" text-center mt-2 mb-3">
              <h1>Candidate Signup</h1>
          </div>
          </CardHeader>
          <CardBody className="px-lg-5 py-lg-5">
            <div className="text-center text-muted mb-4">
              <small>Or sign up with credentials</small>
            </div>
            <Form role="form">
              <FormGroup>
                <InputGroup className="input-group-alternative mb-3">
                  <InputGroupAddon addonType="prepend">
                    <InputGroupText>
                      <i className="ni ni-email-83" />
                    </InputGroupText>
                  </InputGroupAddon>
                  <Input
                    placeholder="Roll No"
                    type="roll_no"
                  />
                </InputGroup>
              </FormGroup>
                          
                          <div className="text-center">
                              <Button className="mt-4" color="primary" type="button" onClick={() => {history.push('/candidate/otp-verification')}}>
                                    Create account
                              </Button>
                              <br/><br/>
                              Already have a account? <Link to="/candidate/login" class="small" >Login</Link>
                              <br/><br/>
                              <Link to="/candidate/forget" class="small" >Forget Password</Link>
            </div>
                          
            </Form>
          </CardBody>
        </Card>
      </Col>
    </>
  );
};

export default OTPVerification;