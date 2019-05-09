import React from "react";
import {
  Col,
  Grid,
  Image,
  ListGroup,
  ListGroupItem,
  Panel,
  Row
} from "react-bootstrap";

const Product = (props) => {
  const {name, description, imageUrl, price} = props;
  return (
    <Panel bsStyle={"primary"}>
      <Panel.Heading>
        <Panel.Title componentClass={"h3"}>{name}</Panel.Title>
      </Panel.Heading>
      <Panel.Body>
        <Grid>
          <Row>
            <Col xs={12} md={4}>
              <Image src={imageUrl} rounded width={"50%"} height={"50%"}/>
            </Col>
            <Col xs={12} md={7}>
              <ListGroup>
                <ListGroupItem>{description}</ListGroupItem>
                <ListGroupItem>${price.toFixed(2)}</ListGroupItem>
              </ListGroup>
            </Col>
          </Row>
        </Grid>
      </Panel.Body>
    </Panel>
  )
};

export default Product;
