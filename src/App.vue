<template>
  <!-- HTML ********************************************************************************* --> 
  <h1>My First Project</h1>
  <div id="genre_list_area">
    <!-- table>(thead>tr>(th*3))+tbody>tr>(td*3) -->
    <table>
      <thead>
        <tr>
          <th>번호</th>
          <th>장르명</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in list" :key="item.genreSeq">
          <td>{{ item.genreSeq }}</td>
          <td>
            <button class="genre_name" @click="genreDetail(item.genreSeq)">{{ item.genreName }}</button>
          </td>
          <td>
            <button @click="deleteGenre(item.genreSeq)">삭제</button>
          </td>
        </tr>
      </tbody>
    </table>
    <div class="pager_area">
      <button class="pager" v-for="p in totalPage" :key="p" @click="pagerClick(p-1)">{{ p }}</button>
    </div>
  </div>
</template>


<script>
  // JavaScript *******************************************************************************
  import MyComp from "./components/MyComp.vue"
  import axios from 'axios'
  export default {
    name: 'App',
    data() {
      return {
        list:null,
        currentPage:0,
        keyword:"",
        totalPage:0
      }
    },
    created() {
      console.log("created()")
      this.loadGenreList()
    },
    methods:{
      loadGenreList() {
        axios.get("http://localhost:8585/api/genre/list",  //실행하고 있는 flo_service의 주소
          {
            params:{
              page:this.currentPage,
              keyword:this.keyword
            }
          }
        )
        // .then(function(e) { function 으로 하면 list에 데이터가 안들어감. javaScript가 function을 클래스 타입으로 인식하기 때문에.
        .then( (e) => {
          this.list = e.data.list
          this.totalPage = e.data.totalPage
        })
      },
      deleteGenre(seq) {
        alert(seq)
      },
      genreDetail(seq) {
        alert(seq)
      },
      pagerClick(page) {
        this.currentPage = page
      },
    },
    watch: { // watch : 값 변경 막아줌
      // data - currentPage의 값이 변경되었을 때만 실행
      // 같은 값을 세팅했을 경우에는 실행되지 않는다.
      currentPage(value, oldValue) { // value : 변경 된 값 , oldValue : 변경 전 값
        // console.log(value, oldValue)
        console.log("watch - currentPage")
        this.loadGenreList()
      } 
    }
    // activated() {  console.log("activated()");  },
    // beforeCreate() {  console.log("beforeCreate()");  },
    // beforeMount() {  console.log("beforeMount()");  },
    // beforeUnmount() {  console.log("beforeUnmount()");  },
    // beforeUpdate() {  console.log("beforeUpdate()");  },
    // deactivated() {  console.log("deactivated()");  }, // 불러온 값의 초기화
    // mounted() {  console.log("mounted()");  },
    // unmounted() {  console.log("unmounted()");  }, // 불러온 값의 초기화
    // updated() {  console.log("updated()");  }


  }
</script>

<style>
  /* CSS ************************************************************************************** */ 
</style>
