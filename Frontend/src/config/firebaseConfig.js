import firebase from "firebase/app"
import "firebase/firestore"
import "firebase/auth"

//Initialize Firebase
var config = {
apiKey: "AIzaSyDT25X77Iyhdyz9jCW-m7aem1PCg0asU08",
authDomain: "usersystem-4n.firebaseapp.com",
databaseURL: "https://usersystem-4n.firebaseio.com",
projectId: "usersystem-4n",
storageBucket: "usersystem-4n.appspot.com",
messagingSenderId: "989542366622"
};
firebase.initializeApp(config);
firebase.firestore().settings( {timestampsInSnapshots:true})

export default firebase;