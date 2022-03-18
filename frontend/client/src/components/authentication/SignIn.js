import React,{useState} from 'react';
import {Link,useHistory} from 'react-router-dom';
import { Alert } from 'react-bootstrap';
import {
  UncontrolledCollapse,
  NavbarBrand,
  Navbar,
  NavItem,
  NavLink,
  Nav,
  Container,
  Row,
  Col,
} from "reactstrap";
// import {Auth} from '../../context/AuthContext';

export default function SignIn(props) {
    // const {googleSignup,facebookSignup,login}=Auth();
    const [error,setError]=useState('');
    const history=useHistory();

    async function loginUser(){
        let email=document.getElementById('email').value;
        let password=document.getElementById('password').value;

    }
    return (
    <div style={{backgroundColor:"blue",right:0,bottom:0,left:0,top:0,position:"fixed"}}>
            <div class="container">
             <Navbar className="navbar-top navbar-horizontal navbar-dark" expand="md">
        <Container className="px-4">
          <NavbarBrand to="#" tag={Link}>
            Exec
          </NavbarBrand>
          <button className="navbar-toggler" id="navbar-collapse-main">
            <span className="navbar-toggler-icon" />
          </button>
          <UncontrolledCollapse navbar toggler="#navbar-collapse-main">
            <div className="navbar-collapse-header d-md-none">
              <Row>
                <Col className="collapse-brand" xs="6">
                  <Link to="/">
                    <img
                      alt="..."
                      src={
                        require("../../assets/img/brand/argon-react.png")
                          .default
                      }
                    />
                  </Link>
                </Col>
                <Col className="collapse-close" xs="6">
                  <button className="navbar-toggler" id="navbar-collapse-main">
                    <span />
                    <span />
                  </button>
                </Col>
              </Row>
            </div>
            <Nav className="ml-auto" navbar>
              <NavItem>
                <NavLink className="nav-link-icon" to="/candidate/login" tag={Link}>
                  <i className="ni ni-planet" />
                  <span className="nav-link-inner--text">Candidate_Login</span>
                </NavLink>
              </NavItem>
              <NavItem>
                <NavLink className="nav-link-icon" to="/candidates" tag={Link}>
                  <i className="ni ni-planet" />
                  <span className="nav-link-inner--text">Dashboard</span>
                </NavLink>
              </NavItem>
            </Nav>
          </UncontrolledCollapse>
        </Container>
      </Navbar>

        <div class="row justify-content-center">

            <div class="col-xl-10 col-lg-12 col-md-9">

                <div class="card o-hidden border-0 shadow-lg my-5">
                    <div class="card-body p-0">
                        <div class="row">
                            <div class="col-lg-6 d-none d-lg-block bg-login-image"></div>
                            <div class="col-lg-6">
                                <div class="p-5">
                                    <div class="text-center">
                                        <h1 class="h4 text-gray-900 mb-4">Welcome Back!</h1>
                                        {error && <Alert variant="danger"> {error}</Alert>}
                                    </div>
                                    <form class="user">
                                        <div class="form-group">
                                            <input type="email" class="form-control form-control-user"
                                                id="email" aria-describedby="emailHelp"
                                                placeholder="Enter Email Address..." />
                                        </div>
                                        <div class="form-group">
                                            <input type="password" class="form-control form-control-user"
                                                id="password" placeholder="Password" />
                                        </div>
                                        <a  class="btn btn-primary btn-user btn-block" onClick={()=> {history.push('/gbm/dashboard')}}>
                                            Login
                                        </a>
                                    </form>
                                    <hr/>
                                    <div class="text-center">
                                        <Link to="/gbm/forget">Forgot Password?</Link>
                                    </div>
                                    <div class="text-center">
                                        <Link to="/gbm/signup" class="small" >Create an Account!</Link>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>

        </div>

    </div>
    </div>
    )
}
