<template>
    <div class="view_wrap">
        <h1>{{ title }} 정보</h1>
        <div id="list_area">
            <table>
                <thead>
                    <tr>
                        <th>번호</th>
                        <th>{{ title }} 명</th>
                        <th>
                            <button @click="add()">{{ title }} 추가</button>
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="item in list" :key="item.seq">
                        <td>{{ item.seq }}</td>
                        <td><button class="name" @click="detail(item.seq)">{{ item.name }}</button></td>
                        <td>
                            <button @click="deleteData(item.seq)">삭제</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="search_area">
            <!-- <br> -->
            <input type="text" v-model="keyword">
            <button @click="search()">검색</button>
        </div>
        <div class="pager_area">
            <!-- <br> -->
            <!-- :class="{'current' : currentPage == p-1}"  =>  currentPage 값이 페이저의 값과 동일하면 태그에 current 클래스를 추가한다. -->
            <button class="pager" :class="{'current' : currentPage == p-1}" v-for="p in totalPage" :key="p" @click="pagerClick(p-1)">{{ p }}</button>
        </div>
        <CompanyGenrePopup :title="title"  :apiUrl="apiUrl"  :mode="popupMode" :data="selected" v-if="popup" @closePopup="closePopup()" />
    </div>
</template>

<script>
    import CompanyGenrePopup from "@/components/CompanyGenrePopup.vue"
    export default {
        name: "CompanyGenreView",
        components:{CompanyGenrePopup},
        props:{ title:String, apiUrl:String },
        data() {
            return {
                list: null,
                currentPage: 0,
                totalPage: 0,
                keyword: null,
                popup: false,
                popupMode: "add",
                selected: null
            }
        },
        watch:{
            currentPage() {
                this.loadList()
            }
        },
        created() {
            this.loadList()
        },
        methods: {
            loadList() {
                let token = sessionStorage.getItem('userToken')
                console.log(token)
                this.$http.get(this.apiUrl + "/list", 
                    {
                        headers: {
                            Authorization: `Bearer ${token}`
                        },
                        params: {
                            page: this.currentPage,
                            keyword: this.keyword
                        }
                    })
                    .then((r) => {
                        console.log(r)
                        this.list = r.data.list
                        this.totalPage = r.data.totalPage
                        this.currentPage = r.data.currentPage

                    })
            },
            deleteData(seq) {
                if (!confirm("삭제하시겠습니까? 삭제된데이터는 되돌릴 수 없습니다.")) return
                let token = sessionStorage.getItem('userToken')
                this.$http.delete(this.apiUrl + "/delete?no=" + seq,
                    {
                        headers: {
                            Authorization: `Bearer ${token}`
                        }
                    })
                    .then((r) => {
                        alert(r.data.message)
                        location.reload()
                    })
            },
            detail(seq) {
                this.popup = true
                this.popupMode = "update"
                this.selected = this.list.filter((data) => data.seq == seq)[0] //첫번째 거만 가져옴
                this.no = seq
                console.log(this.list)
                console.log(this.selected)
            },
            pagerClick(page) {
                this.currentPage = page
                this.loadList()
            },
            search() {
                this.currentPage = 0
                this.loadList()
            },
            add() {
                this.popup = true
                this.popupMode = "add"
            },
            closePopup() {
                this.popup = false
                this.selected = null
            }
        }
    }
</script>

<style>
    .view_wrap tr {box-shadow: 0 0 0 #ffffe0; transition: 0.2s;}
    .view_wrap tr:hover {box-shadow: 0 2px 5px hsl(313, 100%, 84%); transition: box-shadow 0.3s;
        background-color: rgb(255, 255, 220);
    }
    .view_wrap {width: 960px; margin:0 auto; position: relative;}
    .view_wrap h1 {
        font-size: 24px; font-weight: bold; padding:30px 0;
    }
    .view_wrap table {width: 100%;}
    .view_wrap table th {
        background-color:rgb(245, 240, 255); color: #9b92e4;
        border-bottom: 1px solid #b0e2f1;
        padding:10px; text-align: center;
    }
    .view_wrap table td {
        border-bottom: 1px solid #cff7fa;
        padding:10px; text-align: center;
    }
    .view_wrap table td:nth-child(2) {
        width:700px;
    }
    .view_wrap td:nth-child(2) button {
        border: 0; font-size: 16px; background-color: transparent;
        cursor:pointer; 
    }
    .view_wrap td:nth-child(2) button:hover {
        text-decoration: underline;
    }
    .view_wrap table th:nth-child(3) button {
        border: 0;
        cursor:pointer; background-color: rgb(195, 216, 255); color: #e2ffe1;
        padding:5px 10px; border-radius: 5px;
    }
    .view_wrap table th:nth-child(3) button:hover {
        background-color: rgb(236, 203, 255);  color: #e4fcff;
    }
    .view_wrap td:nth-child(3) button {
        border: 0;
        cursor:pointer; background-color:rgb(255, 215, 231); color: #fff;
        padding:5px 10px; border-radius: 5px;
    }
    .view_wrap td:nth-child(3) button:hover {
        background-color: rgb(231, 136, 208);
    }

    .search_area {text-align: center; padding:10px; position: absolute; right: 0; top: 20px;}
    .search_area input {
        box-sizing: border-box; height: 30px; vertical-align: middle; margin-right: 10px; border: 0;
        border-bottom: 1px solid #b2a5f8cb;
    }
    .search_area button {height:30px; vertical-align: middle;
        border: 0;padding:2px 10px; border-radius: 5px; background-color: #abe7ce; color: #fff;
        cursor:pointer;
    }
    .pager_area {text-align: center; padding:10px; margin-top: 30px;}
    .pager {
        border: 0; background-color: transparent;
        font-size: 16px; vertical-align: middle;
    }
    .pager:hover {
        font-weight: bold; text-decoration: underline; cursor: pointer;
    }
    .pager.current {
        font-weight: bold; text-decoration: underline; font-size: 25px;
    }
</style>