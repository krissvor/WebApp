/**
 * Created by kriss on 01-May-17.
 */

function testUserForm(){
    everythingFilled();
   validateYear();
   validateEmail();
   validatePassword();
   validateCredit();

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
        }
        else
            console.log("wrong lengt")
    }
    else
        console.log("Only use numbers");

}

function everythingFilled(){
    var inputArray = document.getElementsByClassName("UserRegInput");

    for(var i = 0; i<inputArray.length;i++){
        if(inputArray[i].value.trim() == null || inputArray[i].value.trim() == ""){
            console.log(inputArray[i].getAttribute("name") + " isEmpty");
        }
    }
}

function validatePassword(){
    var field = document.getElementById("password");
    var field2 = document.getElementById("password2");
    var value = field.value;
    var value2 = field2.value;
    var re = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}$/;
    var valid = re.test(value);
    if(valid){
        if(value === value2){
            console.log("Approved password");
        }
        else
            console.log("Passwords don't match");
    }
    else{
        console.log("Password does not meet criteria");
    }
}

function validateEmail(){
    var field = document.getElementById("email");
    var value = field.value;

    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    if(!re.test(value)){
        field.value="Enter valid";
    }

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
        field.value="Enter valid Year";
    }
}