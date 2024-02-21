const checkMid = () => {
    let inputMid = document.getElementById("mid").value;
    let checkResult = document.getElementById("check-result");
    $.ajax({
        type: "POST",
        url: "/member/check-id",
        data: {
            mid: inputMid
        },
        success: function (res){
            if(res == "OK"){
                checkResult.innerHTML = "사용가능한 아이디입니다.";
                checkResult.style.color = "green";
            }else if(res == "len"){
                checkResult.innerHTML = "아이디는 6글자이상 14글자 이하로 입력해 주세요.";
                checkResult.style.color = "#991";
            }else{
                checkResult.innerHTML = "이미 등록된 아이디입니다.";
                checkResult.style.color = "red";
            }
            checkResult.style.display = "block";
        },
        error: function (error){
            console.log("실패: " , error);
        }
    })
};
$("#checkBtn").click(checkMid);

$("#passwordConfirm").on("keyup", function() {
    let password = $("#password").val();
    let passwordConfirm = $(this).val();
    let errorMessage = $("#passwordConfirm-error");
    let successMessage = $("#passwordConfirm-success");

    if (password === passwordConfirm) {
        errorMessage.text("");
        successMessage.text("비밀번호가 일치합니다.");
        successMessage.css("color", "green");
        $(this).css("border-color", "green");
        successMessage.removeClass("check_password");
    } else {
        errorMessage.text("비밀번호가 일치하지 않습니다.");
        errorMessage.css("color", "red");
        $(this).css("border-color", "red");
        successMessage.text("");
        errorMessage.removeClass("check_password");
    }
});

$("#password").on("keyup", function (){
    let password = $(this).val();
    let passwordMessage = $("#passwordMessage");

    let passwordRegex = /^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!*]).*$/;

    if (password.length < 8 || password.length > 20) {
        passwordMessage.text("비밀번호는 8글자 이상 20글자 이하여야 합니다.");
        passwordMessage.css("color", "red");
    } else if (!passwordRegex.test(password)) {
        passwordMessage.text("비밀번호는 영문, 숫자, 특수문자를 모두 포함해야 합니다.");
        passwordMessage.css("color", "red");
    } else {
        passwordMessage.text("비밀번호가 유효합니다.");
        passwordMessage.css("color", "green");
    }
})