function fnRegist() {
    var oPass = document.getElementById("upass").value;
    var oPass1 = document.getElementById("upass1").value;
    if (oPass != oPass1) {
        document.getElementById("error_box").innerHTML = "�����������벻һ��"
    }
}