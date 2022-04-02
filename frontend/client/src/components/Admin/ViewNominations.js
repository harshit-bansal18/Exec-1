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
import { useState, useEffect } from 'react';
import { useHistory } from 'react-router-dom';
import axios from "axios";

function ViewNominations(props) {
  const history = useHistory();
  const base_url = "http://localhost:8080/";
  //  const [nominee,setNominee]=useState([{ id: 1,roll_no:'200122', name: "Candidate1", desc: "PRESIDENT, STUDENTS GYMKHANA", image_link: "https://eciitk.com/assets/img/executive_candidate/Ghanshyam%20Waindeshkar.jpeg", manifesto_link: "https://drive.google.com/file/d/1oixPOrMZ9oFxudLUKQalpB1dZEnP_XTg/view?usp=sharing", poster_link:"https://eciitk.com/assets/img/Posters/Ghanshyam.jpg"},
  //                                    {id:2,roll_no:'200122',name:"Candidate2",desc:"GENERAL SECRETARY, GAMES AND SPORTS",image_link:"https://eciitk.com/assets/img/executive_candidate/Rohit%20Kejriwal.jpeg",manifesto_link:"https://drive.google.com/file/d/1AQvEHZ26kRiCbJS26g_auBEaYRgCXScR/view?usp=sharing",poster_link:"https://eciitk.com/assets/img/Posters/Rohit.jpg"},
  //                                    {id:3,roll_no:'200122',name:"Candidate3",desc:"PRESIDENT, STUDENTS GYMKHANA",image_link:"https://eciitk.com/assets/img/executive_candidate/Animesh%20Singh.png",manifesto_link:"https://drive.google.com/file/d/1AQvEHZ26kRiCbJS26g_auBEaYRgCXScR/view?usp=sharing",poster_link:"https://eciitk.com/assets/img/Posters/Animesh.jpg"},
  //                                    {id:4,roll_no:'200122',name:"Candidate4",desc:"GENERAL SECRETARY, SCIENCE AND TECHNOLOGY",image_link:"https://eciitk.com/assets/img/executive_candidate/Animesh%20Singh.png",manifesto_link:"https://drive.google.com/file/d/1AQvEHZ26kRiCbJS26g_auBEaYRgCXScR/view?usp=sharing",poster_link:"https://eciitk.com/assets/img/Posters/Animesh.jpg"},
  // ]);
  const [nominee, setNominee] = useState([]);

  useEffect(() => {
      async function fetchData() {
        axios.defaults.withCredentials = true;
        await axios
          .get(base_url + "api/admin/viewAllNominations")
          .then((response) => {
            setNominee(response.data);
          })
          .catch((error) => {
            console.log(error);
          });
        }
        fetchData();
      }, []); 

  const viewDetails = (event,id) => {
    history.push('/admin/info/'+id);
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
        <td>{data.post}</td>
        <td>
          <Badge color="" className="badge-dot mr-4">
            {data.roll_no}
          </Badge>
        </td>
        <td>
          <button type="button" class="btn btn-success" onClick={accept(data.roll_no)}>Accept</button>
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
                onClick={(event) => viewDetails(event,data.id)}
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
                <h3 className="text-white mb-0">Nominations Filed</h3>
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
