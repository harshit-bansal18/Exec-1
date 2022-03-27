import { Card, CardBody, CardTitle, Container, Row, Col } from "reactstrap";

const CandidateInfoHeader = (props) => {
  return (
    <>
      <div className="header bg-gradient-info pb-8 pt-5 pt-md-8">
        <Container fluid>
          <div className="header-body">
            {/* Card stats */}
            <Row>
              <Col lg="6" xl="3">
                <Card className="card-stats mb-4 mb-xl-0">
                  <CardBody>
                    <Row>
                      <div className="col">
                        <CardTitle
                          tag="h5"
                          className="text-uppercase text-muted mb-0"
                        >
                          Candidate Name
                        </CardTitle>
                        <span className="h2 font-weight-bold mb-0">
                          {props.candidate && props.candidate.name}
                        </span>
                      </div>
                    </Row>
                    <p className="mt-3 mb-0 text-muted text-sm">
                      <span className="h2 font-weight-bold mb-0">
                          {props.candidate && props.candidate.desc}
                      </span>
                    </p>
                  </CardBody>
                </Card>
              </Col>
              <Col lg="6" xl="3">
                <Card className="card-stats mb-4 mb-xl-0">
                  <CardBody>
                    <Row>
                      <div className="col">
                        <CardTitle
                          tag="h5"
                          className="text-uppercase text-muted mb-0"
                        >
                          Proposers
                        </CardTitle>
                            <span className="h2 font-weight-bold mb-0">Proposer1</span>
                            <br/>
                            <span className="h2 font-weight-bold mb-0">Proposer2</span>
                            <br/>
                            <span className="h2 font-weight-bold mb-0">Proposer3</span>
                            <br/>
                      </div>
                    </Row>
                  </CardBody>
                </Card>
              </Col>
              <Col lg="6" xl="3">
                <Card className="card-stats mb-4 mb-xl-0">
                  <CardBody>
                    <Row>
                      <div className="col">
                        <CardTitle
                          tag="h5"
                          className="text-uppercase text-muted mb-0"
                        >
                          Seconders
                        </CardTitle>
                                              <span className="h2 font-weight-bold mb-0">S1</span>
                                              <br />
                                              <span className="h2 font-weight-bold mb-0">S2</span>
                                              <br />
                                              <span className="h2 font-weight-bold mb-0">S3</span>
                                              <br/>
                      </div>
                    </Row>
                  </CardBody>
                </Card>
              </Col>
              <Col lg="6" xl="3">
                <Card className="card-stats mb-4 mb-xl-0">
                  <CardBody>
                    <Row>
                      <div className="col">
                        <CardTitle
                          tag="h5"
                          className="text-uppercase text-muted mb-0"
                        >
                          Manifesto Link
                        </CardTitle>
                      </div>
                    </Row>
                    <p className="mt-3 mb-0 text-muted text-sm">
                      <span className="text-success mr-2">
                        <a href = {props.candidate&& props.candidate.manifesto_link}>{props.candidate&& props.candidate.manifesto_link}</a> 
                      </span>{" "}
                    </p>
                  </CardBody>
                </Card>
              </Col>
            </Row>
          </div>
        </Container>
      </div>
    </>
  );
};

export default CandidateInfoHeader;
