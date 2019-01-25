export const signIn = (credentials) =>{
    return (dispatch, getState, {getFirebase})=>{
        const firebase = getFirebase();

        firebase.auth().signInWithEmailAndPassword(
            credentials.email,
            credentials.password
        ).then( ()=> {
            dispatch({type: "LOGIN_SUCCESS"})
        }).catch((error)=>{
            dispatch({type: "LOGIN_ERROR",error})
        })
    }
}

export const signOut = () =>{
    return (dispatch,getState,{getFirebase}) =>{
        const firebase = getFirebase();
        firebase.auth().signOut().then(()=>{
            dispatch({type: "SIGNOUT_SUCCESS"})
        }).catch((error)=>{
            dispatch({type: "SIGNOUT_ERROR",error})
        })
    }
}

export const signUp = (payload) =>{
    return(dispatch,getState,{getFirebase,getFirestore}) =>{
        const firebase = getFirebase();
        const firestore = getFirestore();

        firebase.auth().createUserWithEmailAndPassword(
            payload.email,
            payload.password
        ).then((res)=>{
            return firestore.collection("users").doc(res.user.uid).set({
                name:payload.nickName,
                userType:payload.userType,
                contactInformation:{
                    email:payload.email,
                    phoneNumber:payload.phoneNumber,
                    address: payload.address,
                    city: payload.city,
                    zipCode:payload.zipCode
                }
            })
        }).then(()=>{
            dispatch({type:"SIGNUP_SUCCESS"})
        }).catch(error =>{
            dispatch({type:"SIGNUP_ERROR",error})
        })
    }
}

export const signUpEmployee = (payload) =>{
    return(dispatch,getState,{getFirebase,getFirestore}) =>{
        const firebase = getFirebase();
        const firestore = getFirestore();

        firebase.auth().createUserWithEmailAndPassword(
            payload.email,
            payload.password
        ).then((res)=>{
            return firestore.collection("users").doc(res.user.uid).set({
                name:payload.nickName,
                userType:"employee",
                email:payload.email
            })
        }).then(()=>{
            dispatch({type:"SIGNUP_SUCCESS"})
        }).catch(error =>{
            dispatch({type:"SIGNUP_ERROR",error})
        })
    }
}

export const updateUserProfile = (payload) =>{
    return(dispatch, getstate, {getFirebase,getFirestore}) =>{
        const firestore = getFirestore();

        firestore.collection("users").doc(payload.id).set({
            name:payload.name,
            contactInformation:{
                email:payload.email,
                
            }
        })
    }
}

export const deleteCurrentUser = () =>{
    return(dispatch, getState, {getFirebase,getFirestore}) =>{
        const firestore = getFirestore();
        const firebase = getFirebase();
        const user = firebase.auth().currentUser;
        user.delete().then((res)=>{
            return firestore.collection("users").doc(user.uid).delete()
        }
        ).then(
            dispatch({type:"DELETION_SUCCESS"})
        ).catch((error)=>{
            dispatch({type:"DELETION_ERROR",error})
        })
    }
}

export const updateCurrentEmployeeEmail = (payload) => {
    return(dispatch, getState,{getFirebase,getFirestore})=>{
        const firebase = getFirebase();
        const firestore = getFirestore();
        const user = firebase.auth().currentUser;

        user.updateEmail(
            payload.email,
        ).then((res) =>{
            return firestore.collection("users").doc(user.uid).update({
            email: payload.email    
            })
        }).then(
            dispatch({type:"EMAIL_UPDATE_SUCCESS"})
        ).catch((error)=>{
            dispatch({type:"EMAIL_UPDATE_ERROR",error})
        });
    }
}

export const updateUser = (payload) =>{
    return(dispatch, getState,{getFirebase,getFirestore})=>{
        const firestore = getFirestore();

        firestore.collection("users").doc(payload.id).update({
            
        })
    }
}

export const updateCurrentUserName = (payload) =>{
    return(dispatch, getState,{getFirebase,getFirestore})=>{
        const firebase = getFirebase();
        const firestore = getFirestore();
        const user = firebase.auth().currentUser;

        firestore.collection("users").doc(user.uid).update({
            name:payload.name
        }).then(
            dispatch({type:"NAME_UPDATE_SUCCESS"})
        ).catch((error)=>{
            dispatch({type:"NAME_UPDATE_ERROR",error})
        })
    }
}

export const sendPasswordReset = (payload) =>{
    return(dispatch, getstate,{getFirebase}) =>{
        const firebase = getFirebase();
        var user = firebase.auth();
        user.sendEmailVerification(payload.email).then(
            dispatch({type:"EMAIL_SENT_SUCCESS"})
        ).catch((error)=>{
            dispatch({type:"EMAIL_SENT_ERROR",error})
        })
    }
}