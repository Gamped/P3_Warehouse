import React from 'react';

// The header component
const Header = (props) => {
    const title= "4N: " + props.title
    return(
        <div className="headerStyle">
            <titleText>{title}</titleText>
        </div>
    );
}

export default Header