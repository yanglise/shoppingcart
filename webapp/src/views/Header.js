import React from "react";
import {Navbar} from "react-bootstrap";
import {LinkContainer} from "react-router-bootstrap";

const Header = () => {
  return (
    <Navbar>
      <Navbar.Header>
        <LinkContainer to={"/"}>
          <Navbar.Brand>
            Shopping Cart
          </Navbar.Brand>
        </LinkContainer>
      </Navbar.Header>
    </Navbar>
  );
};

export default Header;
