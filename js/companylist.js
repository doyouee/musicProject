document.addEventListener("DOMContentLoaded", function() {
    // 230202 장르 팝업 버튼 이벤트 추가
    document.getElementById("genre_popup_btn").addEventListener("click", function() {
        document.getElementById("genre_add_wrap").style.display = "block"
    })
    elemId("genre_add_form").addEventListener("submit", (e)=>{e.preventDefault()}) // 버튼누르면 자동으로 폼이 새로고침 되는 걸 막기위해
    elemId("genre_add_btn").addEventListener("click", function() {
        let name = elemId("genre_name").value
        // alert(name)
        axios.put("http://localhost:8585/api/company/add?name=" + name)
        .then((result) => {
            //성공 시 (응답코드 100~399)
            console.log(result)
            if(result.data.status) {
                alert(result.data.message)
                elemId("genre_add_wrap").style.display = ""
                loadpubList()
                elemId("genre_name").value = ""
                elemId("add_err_msg").innerHTML = "";
            }
            else {
                elemId("add_err_msg").innerHTML = result.data.message;
            }
        })
        .catch((error) => {
            //실패 시 (응답코드 400~599)
            console.log(error)
        })
    })
})
        
document.addEventListener("DOMContentLoaded", function() {

    // DOMContentLoaded : HTML 파일의 모든 내용을 DOM으로 로드 완료한 시점
    // 브라우저에서 HTML을 모두 로드 한 시점
            
    loadpubList()
    /* 얘네 다 loadpubList() 함수에 집어넣어서 하드코딩이 되지 않도록 한다.
    axios.get("http://localhost:8585/api/company/list") // FLO_SERVICE의 GenreAPIController 를 불러옴 (axios. + 매핑종류)
    // axios.get("http://localhost:8585/api/company/list?page=1") // ?page=1 : 다음 페이지가 나옴
    // axios.get("http://localhost:8585/api/company/list?keyword=pop") // ?keyword=pop : 키워드가 pop인 장르가 나옴
    .then(function(result) {
        // console.log(result.data.list)
        const pubList = result.data.list
        let tbody_content = "" //view String을 하나 만든다
        for(let i=0; i<pubList.length; i++) {
            // console.log(pubList[i].pubSeq)
            // console.log(pubList[i].pubName)
            // 아래 tbody 에 있던 걸 복사해와서 문자열로 만든다.
            tbody_content += '<tr>'+
                '<td>'+pubList[i].pubSeq+'</td>'+
                '<td>'+
                    '<a href="#">'+pubList[i].pubName+'</a>'+
                '</td>'+
                '<td>'+
                    '<button onclick="deleteCategory('+pubList[i].pubSeq+')">삭제</button>'+
                '</td>'+
            '</tr>'
        }
        // console.log(tbody_content) // 콘솔에 제대로 찍히는지 확인 후
        document.querySelector("#genre_list_table > tbody").innerHTML = tbody_content // 화면에 나타나도록 한다.
    }) */
            
    document.getElementById("search").addEventListener("click", function() {
        const keyword = document.getElementById("keyword").value
        loadpubList(0, keyword)
    })



    //230207
    elemId("genre_add_close").addEventListener("click", function(){
        if(!confirm("창을 닫으시겠습니까?\n입력된 내용은 사라집니다.")) return;
        //확인이 눌렸을때 실행
        elemId("genre_name").value = ''; // 입력된 값을 비우고
        elemId("genre_add_wrap").style.display=''; // 입력창의 display를 기존상태로 되돌린다.
        elemId("add_err_msg").innerHTML = "";
    })
    elemId("genre_update_close").addEventListener("click", function(){
        if(!confirm("창을 닫으시겠습니까?\n입력된 내용은 사라집니다.")) return;
        //확인이 눌렸을때 실행
        elemId("genre_update_name").value = ""; // 입력된 값을 비우고
        elemId("genre_update_seq").value = ""; // 입력된 값을 비우고
        elemId("genre_update_wrap").style.display=""; // 입력창의 display를 기존상태로 되돌린다.
        elemId("add_err_msg").innerHTML = "";
    })


    //230207
    elemId("genre_update_form").addEventListener("submit", (e) => {e.preventDefault()})
    elemId("genre_update_btn").addEventListener("click", function(){
        let no = elemId("genre_update_seq").value
        let name = elemId("genre_update_name").value

        axios.patch("http://localhost:8585/api/company/update?no="+no+"&name="+name)
        .then(function(result){
            if(result.data.updated == false) { // 업데이트 실패 시
                // 별 다른 동작 하지 않고 에러메시지 표시
                elemId("update_err_msg").innerHTML= result.data.message
            }
            else {
                alert("장르 정보가 수정되었습니다.")
                // 기존 입력 초기화
                elemId("update_err_msg").innerHTML = ""
                elemId("genre_update_seq").value = ""
                elemId("genre_update_name").value = ""
                elemId("genre_update_wrap").style.display="";
                elemId("add_err_msg").innerHTML = "";
                // 리스트 다시 생성
                loadpubList()
            }
        })

        console.log(data)
    })



    // form 태그의 기본 기능 중 하나인 요청기능을 제거하는 코드
    document.getElementById("search_area").addEventListener("submit", function(){
        event.preventDefault() // event - 이벤트 파라미터, submit 이벤트에 대한 정보를 담고 있다.
    })
})

function deleteCategory(no) { // 삭제 버튼 기능 추가(바로 삭제되지 않도록)
    if( !confirm("카테고리를 삭제하시겠습니까?") ) return
    alert(no + "번 카테고리 삭제");
    //delete 하려면 axios로 deleteMapping 을 불러와야한다.
    axios.delete("http://localhost:8585/api/company/delete?company_no="+no).then(function(result){
        // console.log(result)
        alert(result.data.message);
        loadpubList();
    }) // 새로고침 해야 화면에 삭제된 리스트가 뜬다. -> 바로 적용하려고 loadpubList() 메서드 생성해서 불러옴
}


// 230207
function genreDetail(no) {
    axios.get("http://localhost:8585/api/company/detail?company_no="+no)
    .then(function(result) {
        console.log(result)
        elemId("genre_update_wrap").style.display = "block"
        elemId("genre_update_seq").value = result.data.no
        elemId("genre_update_name").value = result.data.name
    })
}


function loadpubList(page, keyword) { // JavaScript의 파라미터는 꼭 집어넣지않아도 된다.
    //페이지 설정
    if(page == null || page == undefined) { //undefined : 아직 지정되지 않았다. (JavaScript의 특징)
        page = 0
    }

    //키워드 설정
    if(keyword == null || keyword == undefined) keyword = ""
    axios.get("http://localhost:8585/api/company/list?page=" + page + "&keyword=" + keyword)
            
    // axios.get("http://localhost:8585/api/company/list") // FLO_SERVICE의 GenreAPIController 를 불러옴 (axios. + 매핑종류)
    // axios.get("http://localhost:8585/api/company/list?page=1") // ?page=1 : 다음 페이지가 나옴
    // axios.get("http://localhost:8585/api/company/list?keyword=pop") // ?keyword=pop : 키워드가 pop인 장르가 나옴
    .then(function(result) { // 여기서 result = FLO_SERVICE의 GenreAPIController 의 return 되는 ResponseEntity 자체 값이다. result라고 안해도 된다.
        const pubList = result.data.list
        let tbody_content = "" //view String을 하나 만든다
        for(let i=0; i<pubList.length; i++) {
            // console.log(pubList[i].pubSeq)
            // console.log(pubList[i].pubName)
            // 아래 tbody 에 있던 걸 복사해와서 문자열로 만든다.
            tbody_content += '<tr>'+
                '<td>'+pubList[i].pubSeq+'</td>'+
                '<td>'+
                    '<button class="genre_name" onclick="genreDetail('+pubList[i].pubSeq+')">'+pubList[i].pubName+'</button>'+
                '</td>'+
                '<td>'+
                    '<button onclick="deleteCategory('+pubList[i].pubSeq+')">삭제</button>'+
                '</td>'+
            '</tr>'
        }
        // console.log(tbody_content) // 콘솔에 제대로 찍히는지 확인 후
        document.querySelector("#genre_list_table > tbody").innerHTML = tbody_content // 화면에 나타나도록 한다.

        const totalPage = result.data.totalPage; // 페이지 가져오고
        const currentPage = result.data.currentPage;
        let pager_content = ""
        for(let i=0; i<totalPage; i++) {
            if(i == currentPage) {
                pager_content += '<button class="current" onclick="loadpubList(' + i + ')">' + (i+1) + '</button>'
            }
            else {
                pager_content += '<button onclick="loadpubList(' + i + ')">' + (i+1) + '</button>'
            }
        }
        document.getElementById("pager_area").innerHTML = pager_content
    })
}