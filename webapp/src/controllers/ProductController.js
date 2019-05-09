import React, {Component} from "react";
import {connect} from "react-redux";
import {fetchProducts} from "../actions/actions";
import {Col, Grid, Row} from "react-bootstrap";
import Product from "../views/Product";

class ProductController extends Component {
  componentDidMount() {
    this.props.fetchProducts();
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
            />
          </Col>
      )
    }
    return (
        <Grid>
          <h2>Products</h2>
          <Row>
            {productViews}
          </Row>
        </Grid>
    );
  }
}

function mapStateToProps(state) {
  return {
    products: state.content.products
  }
}

function mapDispatchToProps(dispatch) {
  return {
    fetchProducts: () => {
      dispatch(fetchProducts())
    }
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(ProductController);
