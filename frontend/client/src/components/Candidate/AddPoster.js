// reactstrap components
import React,{useState} from 'react';
import {
  Badge,
  Card,
  CardHeader,
    Table,
  Modal,
  Container,
    Row,
    Button,
  CardBody,
  FormGroup,
  Form,
  Input,
  InputGroupAddon,
  InputGroupText,
  InputGroup,
} from "reactstrap";
// core components

import { Link, } from 'react-router-dom';

function AddPoster(props) {
    const [notificationModal, setNotificationModal] = useState({ visible: false });
    
    function toggleModal(value) {
      let info={visible:value}
      setNotificationModal(info)
  };
  return (
      <>
          <Modal
              className="modal-dialog-centered "
        isOpen={notificationModal.visible}
        toggle={() => toggleModal(notificationModal)}
      >
          <Card className="bg-gradient-info shadow border-0">
          <CardHeader className="bg-transparent text-white pb-5">
            <div className=" text-center mt-2 mb-3">
              <h1>Add Poster Link</h1>
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
                    placeholder="link"
                    type="link"
                    autoComplete="new-link"
                  />
                </InputGroup>
            </FormGroup>
            <div className="text-center">
                <Button className="my-4" color="default" type="button">
                  Add Link
                </Button>
            </div>
            <div className="text-center">
                <Button className="my-4" color="default" type="button" onClick={() => toggleModal(false)}>
                  Close
                </Button>
            </div>
                    </Form>
          </CardBody>
        </Card>
      </Modal>
      <div className="header bg-gradient-info pb-8 pt-5 pt-md-8">
        <Container fluid>
          <div className="header-body">
            
          </div>
        </Container>
      </div>
      {/* Page content */}
      <Container className="mt--7" fluid>
        {/* Dark table */}
        <Row className="mt-5">
          <div className="col">
            <Card className="bg-default shadow">
              <CardHeader className="bg-transparent border-0">
                              <h3 className="text-white mb-0">Posters</h3>
                              <br/>
                              <Link onClick={() => toggleModal(true)}><b><Button color="info">Add Poster</Button></b></Link>
              </CardHeader>
              <Table
                className="align-items-center table-dark table-flush"
                responsive
              >
                <thead className="thead-dark">
                  <tr>
                    <th scope="col">Poster Name</th>
                    <th scope="col">Poster Link</th>
                    <th scope="col" />
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <th scope="row">
                          <span className="mb-0 text-sm">
                            Poster 
                          </span>
                       
                    </th>
                    <td>
                     <button type="button" class="btn btn-success">Poster Link</button>
                    </td>
                  </tr>
                </tbody>
              </Table>
            </Card>
          </div>
        </Row>
      </Container>
    </>
  );
};

export default AddPoster;
