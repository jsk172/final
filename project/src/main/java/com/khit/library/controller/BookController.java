package com.khit.library.controller;

import com.khit.library.config.SecurityUser;
import com.khit.library.dto.BookDTO;
import com.khit.library.dto.MemberDTO;
import com.khit.library.entity.Book;
import com.khit.library.service.BookService;
import com.khit.library.service.MemberService;
import com.khit.library.service.RentalReturnService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;
    private final MemberService memberService;
    private final RentalReturnService rentalReturnService;


    //책등록 폼
    @GetMapping("/register")
    public String insertFrom(@AuthenticationPrincipal SecurityUser principal, Model model){
        if(principal == null){
        	return "book/register";
        }else{
            MemberDTO memberDTO = memberService.findByMid(principal);
            model.addAttribute("member", memberDTO);
            return "book/register";
        }
    }
    //책 등록 처리
    @PostMapping("/register")
    public String insert(@ModelAttribute BookDTO bookDTO, MultipartFile bookFile) throws Exception{
        bookService.save(bookDTO, bookFile);
        return "redirect:/book/list";
    }
    //책 리스트
    @GetMapping("/list")
    public String getList(@RequestParam(value = "page", defaultValue = "0") int page,
                          @RequestParam(value = "size", defaultValue = "10") int size,
                          @RequestParam(value = "keyword", required = false) String keyword,
                          @AuthenticationPrincipal SecurityUser principal,
                          Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<BookDTO> bookPage;


        if (keyword != null) {
            bookPage = bookService.search(keyword, pageable);
        } else {
            bookPage = bookService.paging(pageable);
        }



        List<BookDTO> bookDTOList = bookService.findAll();

        model.addAttribute("bookPage", bookPage);
        model.addAttribute("bookList", bookDTOList);
        model.addAttribute("keyword", keyword);

        if (principal == null) {
            return "book/list";
        } else {
            MemberDTO memberDTO = memberService.findByMid(principal);
            model.addAttribute("member", memberDTO);
            return "book/list";
        }
    }
    //책 상세
    @GetMapping("/detail/{bookId}")
    public String detail(@PathVariable Long bookId, Model model, @AuthenticationPrincipal SecurityUser principal){
        BookDTO bookDTO = bookService.findById(bookId);
        model.addAttribute("book", bookDTO);
        if(principal == null){
            return "book/detail";
        }else{
            MemberDTO memberDTO = memberService.findByMid(principal);
            model.addAttribute("member", memberDTO);
            model.addAttribute("able", rentalReturnService.rentalAble());
            return "book/detail";
        }
    }
    //책 수정
    @GetMapping("/update/{bookId}")
    public String updateForm(@PathVariable Long bookId, Model model,
    						@AuthenticationPrincipal SecurityUser principal){
        BookDTO bookDTO = bookService.findById(bookId);
        model.addAttribute("book", bookDTO);
        
        if(principal == null){
        	return "book/update";
        }else{
            MemberDTO memberDTO = memberService.findByMid(principal);
            model.addAttribute("member", memberDTO);
            return "book/update";
        }
        
    }
    
    //책 수정 처리
    @PostMapping("/update")
    public String update(@ModelAttribute BookDTO bookDTO,MultipartFile bookFile) throws Exception {
        bookService.update(bookDTO, bookFile);
        return "redirect:/book/list";
    }
    
    //책 삭제
    @GetMapping("/delete/{bookId}")
    public String delete(@PathVariable Long bookId){
        bookService.deleteById(bookId);
        return "redirect:/book/list";
    }


}
