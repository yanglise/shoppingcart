import React, {Component} from "react";
import {connect} from "react-redux";
import {fetchOrders} from "../actions/actions";
import {Col, Panel} from "react-bootstrap";
import LineItem from "../views/LineItem";

class OrderController extends Component {
  componentDidMount() {
    this.props.fetchOrders();
  }

  render() {
    const orders = this.props.orders;
    let orderViews = [];
    for (let i = 0; i < orders.length; i++) {
      let order = orders[i];
      let lineItems = order.lineItems ? order.lineItems : [];
      let lineItemViews = [];
      for (let j = 0; j < lineItems.length; j++) {
        let product = lineItems[j].product;
        let quantity = lineItems[j].quantity;
        let subtotal = lineItems[j].subtotal;
        lineItemViews.push(
          <Col key={j} xs={12} md={12}>
            <LineItem name={product.name}
                      description={product.description}
                      imageUrl={product.imageUrl}
                      price={product.price}
                      quantity={quantity}
                      subtotal={subtotal}
            />
            </Col>
        )
      }
      orderViews.push(
        <Panel key={i} bsStyle={"success"}>
          <Panel.Heading>
            <Panel.Title componentClass={"h3"}>Total Price: ${order.totalPrice.toFixed(2)}</Panel.Title>
          </Panel.Heading>
          <Panel.Body>
            {lineItemViews}
          </Panel.Body>
        </Panel>
      )
    }
    return (
      <div className={"container"}>
        {orderViews.length === 0 && <h2>No order history.</h2>}
        {orderViews.length !== 0 && <h2>Orders</h2>}
        {orderViews}
      </div>
    );
  }
}

function mapStateToProps(state) {
  return {
    orders: state.content.orders
  }
}

function mapDispatchToProps(dispatch) {
  return {
    fetchOrders: () => {
      dispatch(fetchOrders())
    }
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(OrderController);
