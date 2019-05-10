import React from "react";
import {Nav, Navbar, NavItem} from "react-bootstrap";
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
      <Nav>
        <LinkContainer to={"/cart"}>
          <NavItem eventKey={1}>
            Cart
          </NavItem>
        </LinkContainer>
      </Nav>

    </Navbar>
  );
};

export default Header;
