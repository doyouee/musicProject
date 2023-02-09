//artistgroup.js

//230209 밖에다가 global 변수로 두고 써야 편함
let companyPage = 0
let keyword = ""


document.addEventListener("DOMContentLoaded", function(){
    elemId("imgfile").addEventListener("change", function(e) {
        if(e.target.files && e.target.files[0]) { // 파일이 있다면
            const reader = new FileReader(); // 파일 리더 객체를 만들고
            reader.onload = function(e) { // 웹 브라우저 상에 파일로드가 완료되었다면
                elemId("preview").src = e.target.result // 이미지태그의 src 값으로 로드된 파일을 설정한다.
            }
            reader.readAsDataURL(e.target.files[0]) // 설정된 파일 데이터를 읽어들인다.
        }
        else {
            elemId("preview").src = ""
        }
    })

    elemId("save").addEventListener("click", function() {
        const formData = new FormData()
        formData.append("name", elemId("name").value) // append를 통해서 데이터를 하나씩 추가
        formData.append("debutYear", elemId("debut").value)
        formData.append("company", elemId("company").value)
        formData.append("img", elemId("imgfile").files[0])
        axios.put("http://localhost:8585/api/artist/group/insert", formData)
        .then(function(result) {
            console.log(result)
        })
        .catch(function(err) {
            console.log(err)
        })
    })

    // 230209
    elemId("company_sel").addEventListener("click",function(){
        getCompanyList();
    })
})


// 230209 html 아니고 ajax 쓰면 파라미터 구체화 가능
function getCompanyList(page, keyword) {
    let url = baseURL + "/company/list"
    let params = {}
    if(page != undefined && page != null) {
        // url += "?page=" + page -> 파라미터 구체화 안되는 방식
        params.page = page;
    }
    if(keyword != undefined && keyword != null && keyword != "") {
        // url += "&keyword=" + keyword -> 파라미터 구체화 안되는 방식
        params.keyword = keyword
    }
    console.log(params)
    axios.get(url, {params:params}).then(function(result) {
        // console.log(result)
        const list = result.data.list;
        let tbodyContent = ""
        list.forEach((item) => {
            tbodyContent +=   
                    // 선생님 방법
                    // '<tr>'+
                    //     '<td> + item.pubSeq + </td>'+
                    //     '<td> + item.pubName + </td>'+
                    //     '<td>'+
                    //         '<button onclick="selectCompany(' + item.pubSeq + ', \'' + item.pubName + '\')">선택</button>'+
                    //     '</td>'+
                    // '</tr>'

                    //영준님 방법(백틱)
                    `<tr>
                        <td>${item.pubSeq}</td>
                        <td>${item.pubName}</td>
                        <td>
                            <button onclick="selectCompany(${item.pubSeq}, '${item.pubName}')">선택</button>
                        </td>
                    </tr>`
        })
        let pagers = ""
        for(let i=0; i<result.data.totalPage; i++) {
            // pagers += '<button onclick="getCompanyList(' + i + '. \'' + elemId("search_keyword").value + '\')">' + (i+1) + '</button>'
            // 위 코드를 백틱을 사용하면 (백틱의 단점 : JS 사용법과 겹쳐서 헷갈릴 가능성 있음)
            pagers += `<button onclick="getCompanyList(${i}, '${elemId("search_keyword").value}')">${i+1}</button>`
        }
        document.querySelector(".company_popup tbody").innerHTML = tbodyContent
        document.querySelector(".popup_pager_area").innerHTML = pagers
    })
    // 서치버튼이 클릭됐을때 이벤트 추가
    elemId("search_btn").addEventListener("click", function() {
        getCompanyList(0, elemId("search_keyword").value)
    })
}
//getCompanyList()

function selectCompany(seq, name) {
    elemId("company").value = seq
    elemId("company_name").innerHTML = name
}