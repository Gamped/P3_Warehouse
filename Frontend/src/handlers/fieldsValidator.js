export function userProfileFieldsAreValidated(fields) {
   
    if (isAddressValid(fields.address)) {
        if (isCityValid(fields.city)) {
            if (isZipCodeValid(fields.zipCode)) {
                if (isUserNameValid(fields.userName)) {
                    if (isEmailValid(fields.email)) {
                        if (isPasswordValid(fields.changed)) {
                            if (isPhoneValid(fields.phone)) {
                                if (isCountryValid(fields.country)) {
                                    if (isNickNameValid(fields.nickName)) {
                                        return true;
                                    }
                                }
                            }    
                        }
                    }
                }
            }
        }
    }
    return false;
}

export function isAddressValid(address) {
    if (address.match(/[A-Za-z0-9\.\-\s\,\d]/)) {
        return true;
    } else {
        fieldInvalidWarning({address});
        return false;
    }
}

export function isCityValid(city) {
    if (city.match(/\w*/)) {
        return true;
    } else {
        fieldInvalidWarning({city});
        return false;
    }
}

export function isZipCodeValid(zip) {
    if (zip.match(/\d{4,5}/)) {
        return true;
    } else {
        fieldInvalidWarning({zip});
        return false;
    }
}

export function isUserNameValid(userName) {
    if (userName.match(/[\w\d]/)) {
        return true;
    } else {
        fieldInvalidWarning({userName});
        return false;
    }
}

export function isNickNameValid(nickName) {
    if (nickName.match(/[\w\d]/)) {
        return true;
    } else {
        fieldInvalidWarning({nickName});
        return false;
    }
}

export function isEmailValid(email) {
    if (email.match(/.+@.+\.\w+/)) {
        return true;
    } else {
        fieldInvalidWarning({email});
        return false;
    }
}

export function isPhoneValid(phone) {
    if (phone.match(/\+?\d{8,10}/)) {
        return true;
    } else {
        fieldInvalidWarning({phone});
        return false;
    }
}

export function isCountryValid(country) {
    if (country.match(/\w*/)) {
        return true;
    } else {
        fieldInvalidWarning({country});
        return false;
    }
}

export function isPasswordValid(password) {
    if (password.passwordNew === password.passwordNewRepeat) {
    if (password.passwordNew.match(/[\S]/)) {
        return true;
    } else {
        fieldInvalidWarning({password});
        return false;
        }
    } else {
        window.alert("Password do not match retyped password");
    }
}


export function fieldInvalidWarning(field) {
    window.alert(variableNameToString(field) + ' is not valid')
}

export function variableNameToString(objectWithField) {
    let name = Object.keys(objectWithField)[0];
    return name;
}