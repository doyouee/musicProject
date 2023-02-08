document.addEventListener("DOMContentLoaded", function(){
    document.getElementById("login_box").addEventListener("submit", function(e){
        e.preventDefault(); // form의 submit 기능 제거
    })
    document.getElementById("login_btn").addEventListener("click", function(){
        axios.post("http://localhost:8585/api/member/login", {
            id:document.getElementById("user_id").value,
            pwd:document.getElementById("user_pwd").value
        })
        .then(function(result) {
            if(result.data.status) {
                console.log(result.data)
                if(result.data.user.miStatus == 1) { // 정상 회원
                    sessionStorage.setItem("user", JSON.stringify(result.data.user)) // JSON 형태로 내보내서 정보 확인하려고 해본 코드
                    // sessionStorage.setItem("user", result.data.user) // 오브젝트 형식으로 내보내기
                    location.href = "/genrelist.html"
                }
                else if(result.data.user.miStatus == 2) { // 정지 회원
                    alert("이용 정지된 사용자 입니다.")
                }
                else if(result.data.user.miStatus == 3) { // 영구 정지 회원
                    alert("영구 정지된 사용자 입니다.\n 관리자에게 문의하세요.")
                }
            }
            else {
                alert(result.data.message)
            }
        })
    })
})