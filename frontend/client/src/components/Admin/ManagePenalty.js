// reactstrap components
import React,{useEffect, useState} from 'react';
import {
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
  Col
} from "reactstrap";
// core components

import { Link, useHistory} from 'react-router-dom';
import { isJSDocReadonlyTag } from 'typescript';

function ManagePenalty(props) {
    const [notificationModal, setNotificationModal] = useState({ visible: false });
    const [penaltyList, setPenaltyList] = useState([{roll_no:200471,name:"Jaya",post:"Gensec",clause:"COC Clause 1",level:2,fine:5000}]);
    const history = useHistory();
    function toggleModal(value) {
      let info={visible:value}
      setNotificationModal(info)
    };

  
    const PenaltyList = penaltyList.map((data) => {
        return (
            <>
                <th scope="row">
                            <span className="mb-0 text-sm">
                        {data.post} 
                            </span>
                        
                </th>
                <td>{data.name}</td>
                <td>{data.roll_no}</td>
                <td>{data.clause}</td>
                <td>{data.level}</td>
                <td>{data.fine}</td>
                <td><Button>Delete</Button></td>
            </>
        );
    });
  return (
      <>
          <Modal
              className="modal-dialog-centered "
        isOpen={notificationModal.visible}
        toggle={() => toggleModal(notificationModal)}
      >          
        <Card className="bg-secondary shadow border-0 bg-gradient-success">
          <CardHeader className="bg-transparent pb-5">
            <div className=" text-center mt-2 mb-3">
              <h1>Add Penalty</h1>
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
                    placeholder="Candidate Roll Number"
                                      type="roll_no"
                                      name ="roll_no"
                    autoComplete="new-roll_no"
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
                    placeholder="Candidate Name"
                    type="name"
                    name="name"
                    autoComplete="new-name"
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
                  <Input type="select" placeholder="Select your post" name = "post">

                          <option>Enter Post</option>
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
                    placeholder="Add Clause"
                                      type="text"
                                      name="clause"
                    autoComplete="new-clause"
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
                    placeholder="Add Level"
                                      type="number"
                                      name = "level"
                    autoComplete="new-level"
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
                    placeholder="Add Fine Amount"
                                      type="numner"
                                      name = "fine"
                    autoComplete="new-fine"
                  />
                </InputGroup>
                          </FormGroup>
                          
              <div className="text-center">
                <Button block  className="my-4" color="primary" type="button" >
                  Add Penalty
                </Button>
                          </div>
             <div className="text-center">
                <Button block className="my-4" color="primary" type="button" onClick={() => toggleModal(false)}>
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
          <>
           
              <Container className="mt--7" fluid>
                  <div width="100%">
                      <Link onClick={() => toggleModal(true)}><b>
                        <Button className="btn-icon btn-2" block color="warning" width="100rem">
                              <span className="btn-inner--icon">
                                <i className="ni ni-bag-17"></i>
                                </span>
                                <span className="btn-inner--text">Add Penalty</span>
                        </Button></b>
                      </Link>
                </div>
        {/* Dark table */}
        <Row className="mt-5">
          <div className="col">
            <Card className="bg-default shadow">
              <CardHeader className="bg-transparent border-0">
                              <h3 className="text-white mb-0">Penalties Imposed</h3>
                              
              </CardHeader>
              <Table
                className="align-items-center table-dark table-flush"
                responsive
              >
                <thead className="thead-dark">
                  <tr>
                    <th scope="col">Role</th>
                                      <th scope="col">Name</th>
                                      <th scope="col">Roll No</th>
                                      <th scope="col">Clause</th>
                                      <th scope="col">Level</th>
                                          <th scope="col">Fine Amount</th>
                                          <th scope="col">Delete Entry</th>
                    <th scope="col" />
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    {PenaltyList}
                  </tr>
                </tbody>
              </Table>
            </Card>
          </div>
        </Row>
              </Container>
              </>
    </>
  );
};

export default ManagePenalty;
