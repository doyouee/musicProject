<template>
    <div>
        <nav class="router_area">
            <router-link to="/main/genre">장르정보</router-link> <!-- a태그임 -->
            <router-link to="/main/company">기획사정보</router-link> <!-- a태그임 -->
        </nav>
        <div class="user_box">
            <span>{{ userinfo.admin_id }}</span>
            <span>{{ userinfo.admin_name }}님</span>
            <button @click="logout()">로그아웃</button>
        </div>
    </div>
    <router-view></router-view>
</template>

<script>
    export default {
        name:"MainView",
        data() {
            return {
                userinfo: null
            }
        },
        beforeCreate() {
            if(sessionStorage.getItem('userToken') == '' || sessionStorage.getItem('userToken') == null) {
                alert("로그인이 필요합니다.")
                location.href="/"
            }
        },
        created() {
            this.userinfo = JSON.parse(sessionStorage.getItem("userinfo"));
        },
        methods: {
            logout() {
                if(confirm("로그아웃 하시겠습니까?")) {
                    sessionStorage.removeItem('userToken')
                    sessionStorage.removeItem('userInfo')
                    location.href = "/"
                }
            }
        }
    }
</script>

<style>
/* 라우터 링크 기본 스타일 */
.router_area {
    text-align: center;
    padding: 10px;
    border-bottom: 1px solid #b0e2f1;
}
.router_area a {
    display: inline-block; margin :5px;
    text-decoration: none; color:rgb(255, 206, 114); padding: 10px;
    border-radius:3px
}
/* 라우터링크에 마우스 올렸을때 스타일 */
    .router_area a:hover{
    background: #cafae4; color: #9a9bff;
}
/* 현태 표시되고있는 페이지의 링크스타일 */
    .router_area a.router-link-active{
    background-color:rgb(255, 252, 224); color:#ff8ae6
}
</style>