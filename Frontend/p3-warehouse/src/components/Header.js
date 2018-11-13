import React from 'react';

// The header component
const Header = (props) => {
    let title="P3-Warehouse"
    return(
        <div className="headerStyle">
            <titleText>{title}</titleText>
        </div>
    );
}

export default Header