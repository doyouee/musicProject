package com.doyouee.flo_service.api;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.doyouee.flo_service.service.GenreService;
import com.doyouee.flo_service.vo.GenreListResponseVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;

@Tag(name = "곡 장르 정보 관리", description="장르 정보 CRUD API")
@RestController //(비동기방식) -> axios tutorial로 불러올거임
@RequestMapping("api/genre")
public class GenreAPIController { //일반 controller를 apiController로 바꿈. -> restController 응답방식에 맞도록. -> model로 내보내는걸 responseEntity에 담아서 내보낸다.
    @Autowired GenreService genreService;
    
    @Operation(summary = "장르 리스트", description="등록된 장르 정보를 10개 단위로 보여줍니다.")
    @PageableAsQueryParam
    @GetMapping("/list")
    public ResponseEntity<GenreListResponseVO> getGenreList(
        @Parameter(description = "검색어", example = "재즈") @RequestParam @Nullable String keyword,
        // @Parameter(description = "페이징처리, URL 파라미터로 요청 (예시) /api/genre/list?page=0") @PageableDefault(size=10, sort="genreSeq", direction = Sort.Direction.DESC) Pageable pageable) {
        @Parameter(hidden = true)
        @PageableDefault(size=10, sort="seq", direction = Sort.Direction.DESC) Pageable pageable) {
        // @PageableDefault 에서 size=10 : 한 페이지 당 출력 할 ROW 수
        //                      sort : 정렬 기준이 될 엔터티 변수 명 (framework꺼)
        //                      direction : 정렬 방향 (디폴트는 오름차순, desc:내림차순)

        if(keyword == null) {   keyword = "";  }
        // model.addAttribute("result", genreService.getGenreList(keyword, pageable));
        // model.addAttribute("keyword", keyword);
        return new ResponseEntity<>(genreService.getGenreList(keyword, pageable), HttpStatus.OK);
    }


    @PatchMapping("/update")
    public ResponseEntity<Object> postGenreUpdate(@RequestParam Long no, @RequestParam String name) {
        Map<String, Object> map = genreService.updateGenreInfo(no, name);
        return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
    }


    @GetMapping("/detail")
    public ResponseEntity<Object> getGenreDetail(@RequestParam Long no,
        // 보고있던 페이지 정보 받아오는 방법
        @RequestParam @Nullable Integer page,
        // 키워드 검색 페이지로 다시 돌아가는 방법
        @RequestParam @Nullable String keyword) {
        if(page == null) {  page = 0;   } // 보고있던 페이지가 없었다면 첫 페이지로 이동
        if(keyword == null) {   keyword = "";   }
        Map<String, Object> map = genreService.selectGenreInfo(no);
        // map.put("message", null);
        // model.addAttribute("genre", map);
        // model.addAttribute("page", page); // 보고 있던 페이지 정보를 전달
        // model.addAttribute("keyword", keyword); // 보고 있던 키워드검색 페이지 전달
        if((Boolean)map.get("status")) {
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/add")
    public ResponseEntity<Object> postGenreAdd(@RequestParam String name) {
        Map<String, Object> map = genreService.addGenreInfo(name);
        return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
    }
    
    
    @DeleteMapping("/delete")
    public ResponseEntity<Object> getGenreDelete(@RequestParam Long no) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        genreService.deleteGenre(no);
        map.put("message", "장르 정보를 삭제했습니다.");
        return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
    }
}
