import React from 'react';
import { Link } from 'react-router-dom'

import {MdClearAll} from 'react-icons/md';
import {
    Button,
    Nav,
    Navbar,
    NavItem,
    NavLink,
    Popover,
    PopoverBody,
    DropdownMenu,
    DropdownItem,
    DropdownToggle,
    UncontrolledDropdown
} from 'reactstrap';
import Avatar from "../components/Avatar";
import '../style/_header.scss';

const Header = () => {

    return (
        <Navbar light expand className="header">
            <Nav navbar className="mr-2">
                <Button outline>
                    <MdClearAll size={25} />
                </Button>
            </Nav>
            <Nav navbar>

            </Nav>

            <Nav navbar>
                <NavItem className="d-inline-flex">
                    <NavLink id="Popover1" className="position-relative">

                    </NavLink>
                    <Popover placement="bottom" target="Popover1">
                        <PopoverBody>

                        </PopoverBody>
                    </Popover>
                </NavItem>
                <NavItem>
                    <NavLink href="/components/">오디오 변환기</NavLink>
                </NavItem>
                <NavItem>
                    <Link to="/convertvideo">
                        <NavLink>비디오 컨버터</NavLink>
                    </Link>
                </NavItem>
                <NavItem>
                    <NavLink href="/components/">이미지 변환기</NavLink>
                </NavItem>
                <NavItem>
                    <NavLink id="Popover2">
                        <Avatar />
                    </NavLink>
                    <Popover placement="bottom-end" target="Popover2" className="p-0 border-0" style={{ minWidth: 250 }}>
                        <PopoverBody className="p-0 border-light">

                        </PopoverBody>
                    </Popover>
                </NavItem>
            </Nav>
        </Navbar>
    )
};

export default Header;