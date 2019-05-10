import React, {Component} from "react";
import {connect} from "react-redux";
import {fetchProducts, updateProduct} from "../actions/actions";
import {Col, Grid, Row} from "react-bootstrap";
import Product from "../views/Product";

class ProductController extends Component {
  componentDidMount() {
    this.props.fetchProducts();
  }

  addProduct(id) {
    let cart = this.props.cart;
    let lineItems = cart.lineItems ? cart.lineItems : [];
    let lineItem = lineItems.find(lineItem => lineItem.product.id === id);
    return this.props.updateProduct(id, lineItem ? lineItem.quantity + 1 : 1);
  }

  render() {
    const products = this.props.products;
    let productViews = [];
    for (let i = 0; i < products.length; i++) {
      let product = products[i];
      productViews.push(
        <Col key={i} xs={12} md={12}>
          <Product name={product.name}
                   description={product.description}
                   imageUrl={product.imageUrl}
                   price={product.price}
                   handler={() => this.addProduct(product.id)}
          />
        </Col>
      )
    }
    return (
      <Grid>
        {productViews.length === 0 && <h2>No products available.</h2>}
        {productViews.length !== 0 && <h2>Products</h2>}
        <Row>
          {productViews}
        </Row>
      </Grid>
    );
  }
}

function mapStateToProps(state) {
  return {
    cart: state.content.cart,
    products: state.content.products
  }
}

function mapDispatchToProps(dispatch) {
  return {
    fetchProducts: () => {
      dispatch(fetchProducts())
    },
    updateProduct: (id, quantity) => {
      dispatch(updateProduct(id, quantity))
    }
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(ProductController);
