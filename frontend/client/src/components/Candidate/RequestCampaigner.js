// reactstrap components
import {
  Badge,
  Card,
  CardHeader,
  DropdownMenu,
  DropdownItem,
  UncontrolledDropdown,
  DropdownToggle,
    Table,
  Button,
  CardBody,
  FormGroup,
  Form,
  Input,
  InputGroupAddon,
  InputGroupText,
  InputGroup,
  Container,
    Row,
} from "reactstrap";
// core components

import {Link} from 'react-router-dom';

function RequestCampaigner  (props) {
  return (
    <>
      <div className="header bg-gradient-info pb-8 pt-5 pt-md-8">
       
      </div>
      {/* Page content */}
      <Container className="mt--7" fluid>
        {/* Dark table */}
        <Row className="mt-5">
          <div className="col">
            <Card className="bg-default shadow">
              <CardHeader className="bg-transparent border-0">
                <h3 className="text-white mb-0">Campaign Request</h3>
              </CardHeader>
               <Card className="bg-secondary shadow border-0">
          <CardHeader className="bg-transparent pb-5">
            <div className=" text-center mt-2 mb-3">
              <h1>Request Campaigner
              </h1>
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
                    placeholder="roll_no"
                    type="roll_no"
                    autoComplete="new-email"
                  />
                </InputGroup>
              </FormGroup>
              <div className="text-center">
                <Button className="my-4" color="primary" type="button">
                  Request Campaigner
                </Button>
             </div>
            </Form>
          </CardBody>
        </Card>
            </Card>
          </div>
        </Row>
      </Container>
    </>
  );
};

export default RequestCampaigner;
