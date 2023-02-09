package com.doyouee.flo_service.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.doyouee.flo_service.service.CompanyService;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/company")
public class CompanyController {
    @Autowired CompanyService companyService;
    
    @GetMapping("/list")
    public String getcompanyList(Model model, @RequestParam @Nullable String keyword,
                    @PageableDefault(size=10, sort="companySeq", direction = Sort.Direction.DESC) Pageable pageable, HttpSession session) {
                    // @PageableDefault 에서 size=10 : 한 페이지 당 출력 할 ROW 수
                    //                      sort : 정렬 기준이 될 엔터티 변수 명 (framework꺼)
                    //                      direction : 정렬 방향 (디폴트는 오름차순, desc:내림차순)

        if(keyword == null) {   keyword = "";  }
        model.addAttribute("result", companyService.getCompanyList(keyword, pageable));
        model.addAttribute("keyword", keyword);
        return "/company/list";
    }


    @PostMapping("/update")
    public String postcompanyUpdate(Long no, String name, Model model) {
        Map<String, Object> map = companyService.updateCompanyInfo(no, name);
        if((Boolean)map.get("updated")) {
            return "redirect:/company/list";
        }
        else {
            map.put("status", true);
            model.addAttribute("company", map);
            return "/company/detail";
        }
    }


    @GetMapping("/add")
    public String getcompanyAdd() {
        return "/company/add";
    }

    @GetMapping("/detail")
    public String getcompanyDetail(@RequestParam Long company_no, Model model,
        // 보고있던 페이지 정보 받아오는 방법
        @RequestParam @Nullable Integer page,
        // 키워드 검색 페이지로 다시 돌아가는 방법
        @RequestParam @Nullable String keyword) {
        if(page == null) {  page = 0;   } // 보고있던 페이지가 없었다면 첫 페이지로 이동
        if(keyword == null) {   keyword = "";   }
        Map<String, Object> map = companyService.selectCompanyInfo(company_no);
        map.put("message", null);
        model.addAttribute("company", map);
        model.addAttribute("page", page); // 보고 있던 페이지 정보를 전달
        model.addAttribute("keyword", keyword); // 보고 있던 키워드검색 페이지 전달
        return "/company/detail";
    }

    @PostMapping("/add")
    public String postcompanyAdd(String name, Model model) {
        Map<String, Object> map = companyService.addCompanyInfo(name);
        if((Boolean)map.get("status")) {
            return "redirect:/company/list";
        }
        else {
            model.addAttribute("name", name);
            model.addAttribute("result", map);
            return "/company/add";
        }
    }

    @GetMapping("/delete")
    public String getcompanyDelete(@RequestParam Long company_no) {
        companyService.deleteCompany(company_no);
        return "redirect:/company/list";
    }
}
