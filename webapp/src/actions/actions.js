import axios from "axios";
import {FETCH_CART, FETCH_PRODUCTS, UPDATE_CART} from "./types";

export const fetchProducts = () => {
  return function (dispatch) {
    return axios.get("/product/all")
    .then(response => {
      dispatch({type: FETCH_PRODUCTS, payload: response.data});
    })
    .catch(error => {
      console.log(error.response);
    })
  }
};

export const fetchCart = () => {
  return function (dispatch) {
    return axios.get("/cart")
    .then(response => {
      dispatch({type: FETCH_CART, payload: response.data});
    })
    .catch(error => {
      console.log(error.response);
    })
  }
};

export const updateProduct = (id, quantity) => {
  return function (dispatch) {
    return axios.patch("/cart", {
      productId: id,
      quantity: quantity
    })
    .then(response => {
      dispatch({type: UPDATE_CART, payload: response.data});
    })
    .catch(error => {
      console.log(error.response);
    })
  }
};
