import React from "react";
import {
  Button,
  Col,
  ControlLabel,
  Form,
  FormControl,
  FormGroup,
  Grid,
  Image,
  ListGroup,
  ListGroupItem,
  Panel,
  Row
} from "react-bootstrap";

const Product = (props) => {
  const {name, description, imageUrl, price, quantity, updateHandler, addHandler} = props;
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
                <ListGroupItem>
                  <Form inline>
                    <FormGroup>
                      <ControlLabel>Quantity:</ControlLabel>{" "}
                      <FormControl type={"number"} value={quantity} onChange={(e) => updateHandler(e.target.value)}/>
                    </FormGroup>{" "}
                    <Button bsStyle={"success"} onClick={() => addHandler()}>Add to Cart</Button>
                  </Form>
                </ListGroupItem>
              </ListGroup>
            </Col>
          </Row>
        </Grid>
      </Panel.Body>
    </Panel>
  )
};

export default Product;
