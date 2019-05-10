import React, {Component} from "react";
import {connect} from "react-redux";
import {Alert, Modal} from "react-bootstrap";
import {hideError} from "../actions/actions";

class ErrorController extends Component {
  render() {
    const show = this.props.error !== null;
    const error = this.props.error;
    return (
        <Modal show={show} onHide={() => this.props.hideError()}>
          <Alert bsStyle={"danger"} style={{marginBottom: 0}}>
            {show && error.data.messages}
          </Alert>
        </Modal>
    );
  }
}

function mapStateToProps(state) {
  return {
    error: state.content.error
  }
}

function mapDispatchToProps(dispatch) {
  return {
    hideError: () => {
      dispatch(hideError())
    }
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(ErrorController);
