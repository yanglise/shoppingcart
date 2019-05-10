import React, {Component} from "react";
import {connect} from "react-redux";
import {checkout, fetchCart, updateProduct} from "../actions/actions";
import {Button, Col, Grid, Row} from "react-bootstrap";
import LineItem from "../views/LineItem";

class CartController extends Component {
  componentDidMount() {
    this.props.fetchCart();
  }

  render() {
    let cart = this.props.cart;
    let lineItems = cart.lineItems ? cart.lineItems : [];
    let lineItemViews = [];
    for (let i = 0; i < lineItems.length; i++) {
      let product = lineItems[i].product;
      let quantity = lineItems[i].quantity;
      let subtotal = lineItems[i].subtotal;
      lineItemViews.push(
        <Col key={i} xs={12} md={12}>
          <LineItem name={product.name}
                    description={product.description}
                    imageUrl={product.imageUrl}
                    price={product.price}
                    quantity={quantity}
                    subtotal={subtotal}
                    handler={(value) => this.props.updateProduct(product.id, value)}
          />
        </Col>
      )
    }
    return (
      <Grid>
        <Row>
          <Col xs={6} md={6}>
            {lineItemViews.length === 0 && <h2>Your cart is empty.</h2>}
            {lineItemViews.length !== 0 && <h2>Cart</h2>}
          </Col>
          <Col xs={6} md={6}>
            {lineItemViews.length !== 0 && <h2><Button className={"pull-right"} bsStyle={"success"} onClick={() => this.props.checkout()}>Checkout</Button></h2>}
          </Col>

        </Row>
        <Row>
          {lineItemViews}
        </Row>
      </Grid>
    );
  }
}

function mapStateToProps(state) {
  return {
    cart: state.content.cart
  }
}

function mapDispatchToProps(dispatch) {
  return {
    fetchCart: () => {
      dispatch(fetchCart())
    },
    updateProduct: (id, quantity) => {
      dispatch(updateProduct(id, quantity))
    },
    checkout: () => {
      dispatch(checkout())
    }
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(CartController);
