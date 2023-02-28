<template>
    <div class="popup_wrap">
        <div class="popup">
            <h2>{{ title }} 정보 {{ type }}</h2>
            <input type="text" placeholder="이름" v-model="name">
            <p>{{ errMsg }}</p>
            <button @click="saveInfo()">저장</button>
            <button @click="cancel">취소</button>
        </div>
    </div>
</template>
<script>
// import { Axios } from 'axios'
    export default{
        name:"CompanyGenrePopup",
        props:{
            mode: String,
            data: Object,
            title: String,
            apiUrl: String
        },
        data(){
            return{
                seq:"",
                name:"",
                errMsg:"",
                type:""
            }
        },
        mounted(){
            if(this.data!=null && this.data!=undefined){
                this.type="수정"
                this.seq = this.data.seq
                this.name = this.data.name
            }else if(this.company==null){
                this.seq = null
                this.name=""
                this.type="추가"
            }
        },
        methods:{
            saveInfo(){
                let url = ""
                let reqMethod=""
                let token = sessionStorage.getItem('userToken')
                if(this.mode=="add"){
                    url= this.apiUrl + "/add?name="+this.name
                    reqMethod="put"
                }
                if(this.mode=="update"){
                    url= this.apiUrl + "/update?name="+this.name+"&no="+this.seq
                    reqMethod="patch"
                }
                // this.$http.put("/api/genre/add?name="+this.name)
                // this.errMsg=""
                this.$http({
                    method:reqMethod, url:url,
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then((r)=>{
                    if(r.data.status){
                        alert(this.title + "정보를 "+this.type+"했습니다.")
                        // this.$emit("dataSaveComplete")
                        // this.name=""
                        // this.$emit('closePopup')
                        location.reload() //새로고침 시킴 (F5)
                    }
                    else{
                        this.errMsg=r.data.message
                    }
                })
            },
            cancel(){
                if(!confirm("취소하시겠습니까?\n입력된 정보는 사라집니다.")) return
                //부모 컴포턴트에 closePopup 이벤트가 발생했음을 알림
                this.$emit('closePopup')
                // this.name=""
            }
        }
    }
</script>
<style>
    .popup_wrap{
        position: fixed; width:100vw; height: 100vh;
        left: 0; top: 0; z-index: 9999; background-color: #55555570;
    }
    .popup{
        position:absolute; left:50%; top: 50%;
        margin-left:-150px; margin-top:-130px;
        background-color: rgb(255, 255, 255);
        padding:30px;
    }
</style>