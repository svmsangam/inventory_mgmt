/**
 * Created by dhiraj on 2/8/18.
 */

/*function validate(evt) {
    var key = window.event ? event.keyCode : event.which;
    if (event.keyCode === 8 || event.keyCode === 46) {
        return true;
    } else if ( key < 48 || key > 57 ) {
        return false;
    } else {
        return true;
    }

    return (event.charCode >= 48 && event.charCode <= 57) || event.charCode == 46 || event.charCode == 0
}*/

function validate(event) {



    if(event.charCode >= 48 && event.charCode <= 57){
        console.log("you press num : " + event.charCode);
        return true;
    } else if(event.charCode === 46 || event.charCode === 0){
        console.log("you press bac : " + event.charCode);
        return true;
    }else {
        console.log("you press false : " + event.charCode);
        return false;
    }
}
