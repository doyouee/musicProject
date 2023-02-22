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
        methods:{
            login() {
                alert(`아이디 : ${this.id} / 비밀번호 ${this.pwd}`)
            }
        }
    }
</script>

<style>
</style>