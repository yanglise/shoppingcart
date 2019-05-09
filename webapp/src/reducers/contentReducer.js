import {FETCH_PRODUCTS} from "../actions/types";

export default function reducer(state = {
  products: []
}, action) {
  switch (action.type) {
    case FETCH_PRODUCTS:
      return {...state, products: action.payload, error: null};
    default:
      return state;
  }
}
