/**
 * Created by kriss on 01-May-17.
 */

function testUserForm(){
   validateYear();
   validateEmail();
   validatePassword();

}

function validatePassword(){
    var field = document.getElementById("password");
    var value = field.value;
    var re = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}$/;
    var valid = re.test(value);
    console.log(valid);


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
    var thisYear = new Date().getFullYear();
    var valid = 1
    if(isnum){
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