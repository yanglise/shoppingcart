import axios from "axios";
import {
  FETCH_CART,
  FETCH_ORDERS,
  FETCH_PRODUCTS,
  HIDE_ERROR,
  SHOW_ERROR,
  UPDATE_CART
} from "./types";

export const fetchProducts = () => {
  return function (dispatch) {
    return axios.get("/product/all")
    .then(response => {
      dispatch({type: FETCH_PRODUCTS, payload: response.data});
    })
    .catch(error => {
      dispatch({type: SHOW_ERROR, payload: error.response});
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
      dispatch({type: SHOW_ERROR, payload: error.response});
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
      dispatch({type: SHOW_ERROR, payload: error.response});
    })
  }
};


export const fetchOrders = () => {
  return function (dispatch) {
    return axios.get("/order/all")
    .then(response => {
      dispatch({type: FETCH_ORDERS, payload: response.data});
    })
    .catch(error => {
      dispatch({type: SHOW_ERROR, payload: error.response});
    })
  }
};

export const checkout = () => {
  return function (dispatch) {
    return axios.post("/order")
    .then(() => {
      dispatch(fetchOrders());
      dispatch(fetchCart());
    })
    .catch(error => {
      dispatch({type: SHOW_ERROR, payload: error.response});
    })
  }
};

export const hideError = () => {
  return function (dispatch) {
    dispatch({type: HIDE_ERROR});
  }
};
