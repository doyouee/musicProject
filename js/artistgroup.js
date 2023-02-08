//artistgroup.js
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
})