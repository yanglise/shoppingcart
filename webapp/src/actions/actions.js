import axios from "axios";
import {FETCH_PRODUCTS} from "./types";

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
