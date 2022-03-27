// reactstrap components
import React,{useState} from 'react';
import {
  Button,
  Card,
  CardBody,
  CardHeader,
  CardImg,
  CardTitle,
  Col,
  Row,
  Modal,
  Container,
  FormGroup,
  Form,
  Input,
  InputGroupAddon,
  InputGroupText,
  InputGroup,
  
} from "reactstrap";
import { Link,useHistory } from 'react-router-dom';

function MainDashboard(){
  const history = useHistory();
  const [notificationModal,setNotificationModal]=useState({content:{},visible:false});
  const [candidates, setCandidates] = useState([{ id: 1, name: "Candidate1", desc: "PRESIDENT, STUDENTS GYMKHANA", image_link: "https://eciitk.com/assets/img/executive_candidate/Ghanshyam%20Waindeshkar.jpeg", manifesto_link: "https://drive.google.com/file/d/1oixPOrMZ9oFxudLUKQalpB1dZEnP_XTg/view?usp=sharing", poster_link:"https://eciitk.com/assets/img/Posters/Ghanshyam.jpg"},
                                     {id:2,name:"Candidate2",desc:"GENERAL SECRETARY, GAMES AND SPORTS",image_link:"https://eciitk.com/assets/img/executive_candidate/Rohit%20Kejriwal.jpeg",manifesto_link:"https://drive.google.com/file/d/1AQvEHZ26kRiCbJS26g_auBEaYRgCXScR/view?usp=sharing",poster_link:"https://eciitk.com/assets/img/Posters/Rohit.jpg"},
                                     {id:3,name:"Candidate3",desc:"PRESIDENT, STUDENTS GYMKHANA",image_link:"https://eciitk.com/assets/img/executive_candidate/Animesh%20Singh.png",manifesto_link:"https://drive.google.com/file/d/1AQvEHZ26kRiCbJS26g_auBEaYRgCXScR/view?usp=sharing",poster_link:"https://eciitk.com/assets/img/Posters/Animesh.jpg"},
                                     {id:4,name:"Candidate4",desc:"GENERAL SECRETARY, SCIENCE AND TECHNOLOGY",image_link:"https://eciitk.com/assets/img/executive_candidate/Animesh%20Singh.png",manifesto_link:"https://drive.google.com/file/d/1AQvEHZ26kRiCbJS26g_auBEaYRgCXScR/view?usp=sharing",poster_link:"https://eciitk.com/assets/img/Posters/Animesh.jpg"},
   ]);
  const style = {
    width: "22rem",
    height: "25rem"
  };

  function toggleModal(value,item) {
    console.log("hello");
      console.log(item);
      let info={content:item,visible:value}
      console.log(info);
      setNotificationModal(info)
  };

  function cardClick(id) {
    history.push('/info/'+id);
}

  let contents;

  function Split(){

    let copy=candidates.slice();

    let arr=[],size=4;

    while(copy.length>0){
      arr.push(copy.splice(0,size));
    }

    contents=arr.map((data,i)=>{
      let cols= data.map((item,j)=>{
        return(
          <>  
            <Col lg="6" xl="3">            
              <Card style={style} className="card-stats mb-3 mb-xl-0">
                <CardImg
                  alt="..."
                  src={"https://demos.creative-tim.com/argon-design-system-pro/assets/img/faces/alejandro-escamilla.jpg"}
                  top
                ></CardImg>
                <CardBody>
              <a style={{ cursor: 'pointer' }} onClick={()=>toggleModal(true,item)}>
              <Row>
                <div className="col">
                  <CardTitle
                    tag="h5"
                    className="text-uppercase text-muted mb-0"
                  >
                    {item.name}
                  </CardTitle>
                  <span className="text-nowrap">
                    {item.desc}
                  </span>
                </div>
                <Col className="col-auto">
                  <img
                    alt=""
                    className="navbar-brand-img"
                    src=""
                  /> 
                </Col>
              </Row>
              </a>
              <p className="mt-3 mb-0 text-muted text-sm">
              <span className="text-Hex"><Link onClick={() => cardClick(item.id)}><b><Button color="info">View Candidate Details</Button></b></Link></span>
              </p>
            </CardBody>
              </Card>
              
        </Col>
        </>
        );
        })

      return(
        <>
        <Row>  
           {cols}
        </Row><br/><br/>
        </>
      );
    })

    return(
      <>
      {contents}
      </>
    )
  }
   
  return (
    <>
       <Modal
        className="modal-dialog-centered modal-danger"
        contentClassName="bg-gradient-danger"
        isOpen={notificationModal.visible}
        toggle={() => toggleModal(notificationModal)}
      >
        <div className="modal-header">
          <h6 className="modal-title" id="modal-title-notification">
            Event Details
          </h6>
          <button
            aria-label="Close"
            className="close"
            data-dismiss="modal"
            type="button"
            onClick={() => toggleModal(false)}
          >
            <span aria-hidden={true}>×</span>
          </button>
        </div>
        {notificationModal.visible &&
          <div className="modal-body">
            <div className="py-3 text-center">
              <i className="ni ni-bell-55 ni-3x" />
              <h4 className="heading mt-4">{notificationModal.content.name}</h4>
              <p>
              {notificationModal.content.desc}
              </p>
            </div>
          </div>
        }
        <div className="modal-footer">
          <Button
            className="text-white ml-auto"
            color="link"
            data-dismiss="modal"
            type="button"
            onClick={() => toggleModal(false)}
          >
            Close
          </Button>
        </div>
      </Modal>

      <div className="header bg-gradient-info pb-8 pt-5 pt-md-8">
        <Container fluid>
          <div className="header-body">
            <Split />
          </div>
        </Container>
      </div>
    </>
  );
}

export default MainDashboard;
