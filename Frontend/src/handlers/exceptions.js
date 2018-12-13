import {variableNameToString} from './utils.js';

export function allProductsNotPackedWarning() {
    window.alert("Not all products are marked as packed.");
}

export function itemPreviouslyAddedWarning() {
    window.alert("Product already added! Please undo the selection and change amount if you wish");
    }

export function amountExceedingQuantityWarning() {
    window.alert("Amount exceeds the quantity currently in stock. Try setting a lower amount");
}

export function amountIsZeroWarning() {
    window.alert("Amount is set to zero. Please choose an amount of products to be added to this order line");
}

export function amountIsNotANumberWarning() {
    window.alert("Typed amount is not a number")
}

export function repeatedPasswordWarning() {
    window.alert("Passwords do not match");
}

export function fieldInvalidWarning(field) {
    window.alert(variableNameToString(field) + ' is not valid')
}

export function passwordsDoNotMatchWarning() {
    window.alert("Password do not match retyped password");
}

export function publisherNotSetOnClientProfileCreationWarning() {
    window.alert("Attempted to create a client profile, but a publisher was not chosen. Please select a publisher");
}


