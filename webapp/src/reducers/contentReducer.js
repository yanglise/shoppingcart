import {
  FETCH_CART,
  FETCH_ORDERS,
  FETCH_PRODUCTS,
  HIDE_ERROR,
  SHOW_ERROR,
  UPDATE_CART
} from "../actions/types";

export default function reducer(state = {
  products: [],
  cart: {},
  orders: [],
  error: null
}, action) {
  switch (action.type) {
    case FETCH_PRODUCTS:
      return {...state, products: action.payload, error: null};
    case FETCH_CART:
      return {...state, cart: action.payload, error: null};
    case UPDATE_CART:
      return {...state, cart: action.payload, error: null};
    case FETCH_ORDERS:
      return {...state, orders: action.payload, error: null};
    case SHOW_ERROR:
      return {...state, error: action.payload};
    case HIDE_ERROR:
      return {...state, error: null};
    default:
      return state;
  }
}
