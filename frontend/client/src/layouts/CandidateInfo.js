
import { useState,useEffect } from "react";
// node.js library that concatenates classes (strings)

// reactstrap components
import {
  Card,
  CardHeader,
  CardBody,
  Container,
  Row,
  Col,
  CardImg
} from "reactstrap";

import CandidateInfoHeader from "./CandidateInfoHeader";

const CandidateInfo = (props) => {
  const [activeNav, setActiveNav] = useState(1);
  
  const [candidates, setCandidates] = useState([{ id: 1, name: "Candidate1", desc: "PRESIDENT, STUDENTS GYMKHANA", image_link: "https://eciitk.com/assets/img/executive_candidate/Ghanshyam%20Waindeshkar.jpeg", manifesto_link: "https://drive.google.com/file/d/1oixPOrMZ9oFxudLUKQalpB1dZEnP_XTg/view?usp=sharing", poster_link:"https://eciitk.com/assets/img/Posters/Ghanshyam.jpg"},
                                     {id:2,name:"Candidate2",desc:"GENERAL SECRETARY, GAMES AND SPORTS",image_link:"https://eciitk.com/assets/img/executive_candidate/Rohit%20Kejriwal.jpeg",manifesto_link:"https://drive.google.com/file/d/1AQvEHZ26kRiCbJS26g_auBEaYRgCXScR/view?usp=sharing",poster_link:"https://eciitk.com/assets/img/Posters/Rohit.jpg"},
                                     {id:3,name:"Candidate3",desc:"PRESIDENT, STUDENTS GYMKHANA",image_link:"https://eciitk.com/assets/img/executive_candidate/Animesh%20Singh.png",manifesto_link:"https://drive.google.com/file/d/1AQvEHZ26kRiCbJS26g_auBEaYRgCXScR/view?usp=sharing",poster_link:"https://eciitk.com/assets/img/Posters/Animesh.jpg"},
                                     {id:4,name:"Candidate4",desc:"GENERAL SECRETARY, SCIENCE AND TECHNOLOGY",image_link:"https://eciitk.com/assets/img/executive_candidate/Animesh%20Singh.png",manifesto_link:"https://drive.google.com/file/d/1AQvEHZ26kRiCbJS26g_auBEaYRgCXScR/view?usp=sharing",poster_link:"https://eciitk.com/assets/img/Posters/Animesh.jpg"},
  ]);

  const [filteredCandidates,setFilteredCandidates] = useState({});
  var selectedCandidates;
  useEffect(() => {
    const params = props.match.params;
    //alert(params.id);
    
    selectedCandidates=candidates.filter(function (element) {
      return element.id == params.id
    }).map(function ({ id, name, desc, image_link, manifesto_link, poster_link }) {
      console.log(name);
      return { id, name, desc, image_link, manifesto_link, poster_link };
    });
    console.log(selectedCandidates);
    setFilteredCandidates(selectedCandidates[0])
  },[]);

  const toggleNavs = (e, index) => {
    e.preventDefault();
    setActiveNav(index);
  };
  return (
    <>
      <CandidateInfoHeader candidate={filteredCandidates}/>
     
      <Container className="mt--7" fluid>
        <Row>
          <Col className="mb-5 mb-xl-0" xl="8">
            <Card className="bg-gradient-default shadow">
              <CardHeader className="bg-transparent">
                <Row className="align-items-center">
                  <div className="col">
                    <h6 className="text-uppercase text-light ls-1 mb-1">
                      Poster
                    </h6>
                  </div>
                </Row>
              </CardHeader>
              <CardBody>
                <div className="chart">
                  <CardImg
                  alt="..."
                  src={filteredCandidates && filteredCandidates.poster_link}
                  top
                ></CardImg>
                </div>
              </CardBody>
            </Card>
          </Col>
          <Col xl="4">
            <Card className="shadow">
              <CardBody> 
                <div className="chart">
                  <CardImg
                  alt="..."
                  src={filteredCandidates && filteredCandidates.image_link}
                  top
                ></CardImg>
                </div>
              </CardBody>
            </Card>
          </Col>
        </Row>
      </Container>
     
    </>
    
  );
};

export default CandidateInfo;
