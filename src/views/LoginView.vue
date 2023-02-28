<template>
    <div class="login_box">
        <h1>Admin Login</h1>
        <p>Admin Id <span>(관리자 아이디)</span></p>
        <input type="text" v-model="id">
        <p>Admin Pwd <span>(관리자 비밀번호)</span></p>
        <input type="password" v-model="pwd">
        <button @click="login()">로그인</button>
    </div>
</template>

<script>
    export default {
        name:'LoginView',
        data() {
            return { id:"", pwd:"", }
        },
        beforeCreate() {
            if(sessionStorage.getItem('userToken') != '' && sessionStorage.getItem('userToken') != null) {
                location.href="/main"
            }
        },
        methods:{
            login() {
                // alert(`아이디 : ${this.id} / 비밀번호 ${this.pwd}`)
                this.$http.post("/api/admin/login", {
                    "id":this.id,
                    "pwd":this.pwd
                })
                .then((r) => {
                    // 로그인 성공
                    // this.$userToken = r.data.token.accessToken
                    // 세션에 토큰 정보 기록
                    sessionStorage.setItem("userToken", r.data.token.accessToken)
                    // $ - Symbol

                    // 사용자 상세정보 요청
                    this.$http.get("/api/admin/details/" + this.id, 
                    {
                        // 발급받은 토큰 설정
                        headers: {
                            Authorization: `Bearer ${r.data.token.accessToken}`
                        }
                    })
                    .then((info) => {
                        // 세션에 사용자 정보 기록
                        // sessionStorage.setItem("userInfo", info.data)
                        sessionStorage.setItem("userinfo", JSON.stringify(info.data))
                        location.href="/main"; // 메인으로 이동
                    })
                })
                .catch((e) => {
                    // 로그인 실패
                    alert(e.response.data.message);
                })
            }
        }
    }
</script>

<style>
</style>