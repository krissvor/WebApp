/**
 * Created by kriss on 01-May-17.
 */
var formIsValid;
var usernameTaken = true;

function testUserForm(){
    formIsValid = true;
    everythingFilled();
    validateYear();
    validateEmail();
    validatePassword();
    validateCredit();

    if(formIsValid && !usernameTaken){
        return true;
    }
    return false

}

function testUpdateForm(){
    formIsValid = true;
    everythingFilled();
    validateYear();
    validateEmail();
    validatePassword();
    validateCredit();

    return formIsValid;

}

function validateBookForm(){
    formIsValid = true;
    necessaryFilled();
    validateReleaseDate();
    validatePrice();
    validatePages();

    return formIsValid;
}

function validatePages(){
    var field = document.getElementById("pages");
    var value = field.value;
    var isnum = /^[\d]+$/.test(value);
    if(isnum) {
        if (value < 0 || value ==""){
            field.setAttribute("style", "border-color:#ae030e");
            formIsValid = false;
        }
        else{
            field.setAttribute("style","border-color:rgba(0,0,0,.15)");
        }
    }
    else{
        field.setAttribute("style","border-color:#ae030e");
    }
}

function validateReleaseDate(){
    var field = document.getElementById("publicationDate");
    var date = new Date();
    if(field.value!=""){
        var string = field.value.split("-");
        if(string[0]>date.getFullYear()) {
            field.setAttribute("style", "border-color:#ae030e");
            formIsValid = false;
        }
        else if(string[0] == date.getFullYear() && string[1]>date.getMonth()+1) {
            field.setAttribute("style", "border-color:#ae030e");
            formIsValid = false;
        }
        else if(string[0] == date.getFullYear()&&string[1]==date.getMonth()+1 &&string[2]>date.getDate()){
            field.setAttribute("style", "border-color:#ae030e");
            formIsValid = false;
        }
        else
            field.setAttribute("style","border-color:rgba(0,0,0,.15)");
    }
    else{
    field.setAttribute("style", "border-color:#ae030e");
    formIsValid = false;
    }

}

function validatePrice(){
    var field = document.getElementById("price");
    var value = field.value;
    var isnum = /^[\d ]+$/.test(value);
    if(isnum) {
        if (value < 0||value=="") {
            field.setAttribute("style", "border-color:#ae030e");
            formIsValid = false;
        }
        else{
            field.setAttribute("style","border-color:rgba(0,0,0,.15)");
        }
    }
    else{
        field.setAttribute("style","border-color:#ae030e");

    }

}

function necessaryFilled(){
    var forms = document.getElementsByClassName("necessary");
    for (var i = 0; i <forms.length;i++){
        if(forms[i].value.trim() == null || forms[i].value.trim() == ""){
            forms[i].setAttribute("style","border-color:#ae030e");
            formIsValid = false;
        }
        else{
            forms[i].setAttribute("style","border-color:rgba(0,0,0,.15)");
        }
    }
}


function validateCredit(){
    var field = document.getElementById("creditCard");
    var value = field.value;

    var isnum = /^[\d ]+$/.test(value);
    if(isnum){
        var numberArray = value.split(" ");
        if(numberArray.length>1) {
            var creditCardnumber = "";
            for (var i = 0; i < numberArray.length; i++) {
                creditCardnumber += numberArray[i];
            }
        }
        else
            creditCardnumber = value;

        if(creditCardnumber.length==16){
            console.log("Credit card number valid");
            field.setAttribute("style","border-color:rgba(0,0,0,.15)");

        }
        else {
            console.log("wrong length");
            formIsValid = false;
            field.setAttribute("style","border-color:#ae030e");

        }

    }
    else {
        console.log("Only use numbers");
        formIsValid = false;
        field.setAttribute("style","border-color:#ae030e");

    }
}

function everythingFilled(){
    var inputArray = document.getElementsByClassName("UserRegInput");

    for(var i = 0; i<inputArray.length;i++){
        if(inputArray[i].value.trim() == null || inputArray[i].value.trim() == ""){
            console.log(inputArray[i].getAttribute("type") + " isEmpty");
            inputArray[i].setAttribute("style","border-color:#ae030e");
            formIsValid = false;
        }
        else{
            if(inputArray[i].id != "username") {
                inputArray[i].setAttribute("style", "border-color:rgba(0,0,0,.15);");
            }
        }
    }
}

function validateUsername(){

    var request = new XMLHttpRequest();
    // vardata = "username=" + encodeURIComponent(
    var form = document.getElementById("usernameReg");
    var username = form.value;
    request.open("GET", "/checkUsername?username=" + username, true);
    request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    request.send();
    request.responseText;

    request.onreadystatechange = function () {
        if(request.readyState === XMLHttpRequest.DONE && request.status === 200){

            var res = request.responseText;
            if(res == "true"){
                // document.getElementsByclassName("formError")[0].setAttribute("hidden", false)
                // document.getElementsByClassName("formError")[0].innerHTML("HER ERRE NOE FEIL!!")

                form.setAttribute("style", "border-color:#ae030e;");
                usernameTaken=true;
            }
            if(res=="false"){
                form.setAttribute("style", "border-color:rgba(0,0,0,.15);");
                usernameTaken=false;
            }

        }
    };
}






function validatePassword(){
    var field = document.getElementById("password1");
    var field2 = document.getElementById("password2");
    var value = field.value;
    var value2 = field2.value;
    var re = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}$/;
    var valid = re.test(value);
    if(valid){
        field.setAttribute("style","border-color:rgba(0,0,0,.15)");

        if(value === value2){
            console.log("Approved password");
            field2.setAttribute("style","border-color:rgba(0,0,0,.15)");

        }
        else {
            console.log("Passwords don't match");
            field2.setAttribute("style","border-color:#ae030e");
            formIsValid = false;
        }
    }
    else{
        console.log("Password does not meet criteria");
        field.setAttribute("style","border-color:#ae030e");
        formIsValid = false;
    }
}

function validateEmail(){
    var field = document.getElementById("email");
    var value = field.value;

    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    if(!re.test(value)){
        formIsValid = false;
        field.setAttribute("style","border-color:#ae030e");

    }else
        field.setAttribute("style","border-color:rgba(0,0,0,.15)");


}

function validateLogin() {
    console.log("trying to login");
    var request = new XMLHttpRequest();
    var username = document.getElementById("username");
    var password = document.getElementById("password");
    var data = "username=" + username.value + "&password=" + password.value + "&action=checkLogin";

    request.open("POST", "/checkLogin", true);
    request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    request.send(data);
    request.responseText;

    request.onreadystatechange = function () {
        if(request.readyState === XMLHttpRequest.DONE && request.status === 200){
            var res = request.responseText;
            if(res=="false"){
                console.log("stemmer ikke");
                password.setAttribute("style","border-color:#ae030e");
                return false
            }
            else {
                console.log("stemmer");
                window.location.href = "/";
                return true;
            }
        }
    };
}

function extractReview() {
    console.log("Extracting\n");
    var request = new XMLHttpRequest();
    var review = document.getElementById("inputReview").value;
    var data = "review=" + review + "&action=extractReview";

    request.open("POST", "/extractReview", true);
    request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    request.send(data);
    request.responseText;

    request.onreadystatechange = function () {
        if(request.readyState === XMLHttpRequest.DONE && request.status === 200){
            var res = request.responseText;
            var jsonArray = JSON.parse(res);
            var table = document.createElement("TABLE");
            table.className += "table table-striped table-bordered"


            for(var i=0;i<jsonArray.extracted.length;i++) {

                var row = table.insertRow(0);

                var cell1 = row.insertCell(0);
                var cell2 = row.insertCell(1);
                console.log(jsonArray.extracted[i].tag);

                cell1.innerHTML = jsonArray.extracted[i].word;
                cell2.innerHTML = jsonArray.extracted[i].tag;

            }
            document.getElementById("extractedContainer").appendChild(table);

        }
    };
}


function validateYear(){
    var field = document.getElementById("year");
    var value = field.value;

    /*Tests year of bith input*/

    var isnum = /^\d+$/.test(value);
    if(isnum){
        var thisYear = new Date().getFullYear();
        var valid = 1
        if(value<thisYear - 120 || value >thisYear){
            valid = 0;
        }
    }
    else{
        valid = 0;
    }

    if(!valid){
        formIsValid=false;
        field.setAttribute("style","border-color:#ae030e");
    }
    else
        field.setAttribute("style","border-color:rgba(0,0,0,.15)");

}