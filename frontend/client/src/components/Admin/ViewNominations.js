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
  Container,
    Row,
  Button,
} from "reactstrap";
// core components
import { useState } from 'react';
import { useHistory } from 'react-router-dom';

function ViewNominations(props) {
  const history = useHistory();
   const [nominee,setNominee]=useState([{name:"Candidate1",desc:"PRESIDENT, STUDENTS GYMKHANA",roll_no:"12121"},
                                     {name:"Candidate2",desc:"GENERAL SECRETARY, GAMES AND SPORTS",roll_no:"12122"},
                                     {name:"Candidate3",desc:"PRESIDENT, STUDENTS GYMKHANA",roll_no:"12123"},
                                     {name:"Candidate4",desc:"GENERAL SECRETARY, SCIENCE AND TECHNOLOGY",roll_no:"12124"},
                                     {name:"Candidate5",desc:"GENERAL SECRETARY, MEDIA AND CULTURE",roll_no:"12125"},
                                     { name: "Candidate6", desc: "GENERAL SECRETARY, UG ACADEMICS AND CAREER" ,roll_no:"12126"},]);

  const viewDetails = event => {
    history.push('/info');
    event.preventDefault();
  };

  const accept = event => {
    // event.preventDefault();
  };

  const reject = event => {
    // event.preventDefault();
  };

  const nomineeList = nominee.map((data) => {
    return (
      <tr>
        <th scope="row">
          <span className="mb-0 text-sm">
            {data.name}
          </span>
                       
        </th>
        <td>{data.desc}</td>
        <td>
          <Badge color="" className="badge-dot mr-4">
            {data.roll_no}
          </Badge>
        </td>
        <td>
          <button type="button" class="btn btn-success" onClick={accept()}>Accept</button>
        </td>
        <td>
          <div className="d-flex align-items-center">
            <button type="button" class="btn btn-danger" onClick={reject()}>Decline</button>
          </div>
        </td>
        <td className="text-right">
          <UncontrolledDropdown>
            <DropdownToggle
              className="btn-icon-only text-light"
              role="button"
              size="sm"
              color=""
              onClick={(e) => e.preventDefault()}
            >
              <i className="fas fa-ellipsis-v" />
            </DropdownToggle>
            <DropdownMenu className="dropdown-menu-arrow" right>
              <DropdownItem
                href="#pablo"
                onClick={viewDetails}
              >
                View Complete Details
              </DropdownItem>
            </DropdownMenu>
          </UncontrolledDropdown>
        </td>
      </tr>
    );
  });

  return (
    <>
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
                <h3 className="text-white mb-0">Campaign Request</h3>
              </CardHeader>
              <Table
                className="align-items-center table-dark table-flush"
                responsive
              >
                <thead className="thead-dark">
                  <tr>
                    <th scope="col">Candidate Name</th>
                    <th scope="col">Contesting for</th>
                    <th scope="col">Roll Number</th>
                    <th scope="col">Accept</th>
                    <th scope="col">Reject</th>
                    <th scope="col" />
                  </tr>
                </thead>
                <tbody>
                  {nomineeList}
                </tbody>
              </Table>
            </Card>
          </div>
        </Row>
      </Container>
    </>
  );
};

export default ViewNominations;
