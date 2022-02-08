function isPasswordMatch() {
    const x = document.getElementById("password").value;
    const y = document.getElementById("confirm_password").value;
    if (x === y) {
        return true;
    } else {
        alert("Password not same");
        return false;
    }
}