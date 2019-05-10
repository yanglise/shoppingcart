import {FETCH_CART, FETCH_PRODUCTS, UPDATE_CART} from "../actions/types";

export default function reducer(state = {
  products: [],
  cart: {}
}, action) {
  switch (action.type) {
    case FETCH_PRODUCTS:
      return {...state, products: action.payload, error: null};
    case FETCH_CART:
      return {...state, cart: action.payload, error: null};
    case UPDATE_CART:
      return {...state, cart: action.payload, error: null};
    default:
      return state;
  }
}
