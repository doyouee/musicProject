document.addEventListener("DOMContentLoaded", function() {
    console.log(sessionStorage.getItem("user"))
    let userInfo = sessionStorage.getItem("user")
    if(userInfo == null || userInfo == undefined) {
        alert("로그인이 필요한 서비스입니다.")
        location.href="/login.html"
    }
    //로그인 된 상태
    userInfo = JSON.parse(userInfo); // JSON 형태를 오브젝트 형태로 변환
    document.getElementById("id").innerHTML = userInfo.miId
    document.getElementById("name").innerHTML = "(" + userInfo.miName + ")"
    document.getElementById("logout").addEventListener("click", function() {
        sessionStorage.removeItem("user")
        location.href = "/login.html"
    })
})