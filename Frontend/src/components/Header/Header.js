import React from 'react';
import "./Header.css";

// The header component
const Header = (props) => {
    const title= "4N: " + props.title
    return(
        <div className="headerStyle">
            <h1 className="headerText">{title}</h1>
        </div>
    );
}

export default Header;