import React from 'react';
import "./Header.css";

// The header component
const Header = (props) => {
    const title= "4N: " + props.title
    return(
        <div className="headerStyle">
            <headerText>{title}</headerText>
        </div>
    );
}

export default Header;