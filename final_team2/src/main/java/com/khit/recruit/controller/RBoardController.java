package com.khit.recruit.controller;

import com.khit.recruit.dto.ReviewDTO;
import com.khit.recruit.entity.Comment;
import com.khit.recruit.entity.Free;
import com.khit.recruit.entity.Noti;
import com.khit.recruit.entity.Review;
import com.khit.recruit.service.BoardService;
import com.khit.recruit.service.CommentService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequestMapping("/rboard")
@RequiredArgsConstructor
@Controller
public class RBoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/list")
    public String boardListForm(
        @RequestParam(name = "keyword", required = false) String keyword,
        @PageableDefault(page = 1) Pageable pageable,
        Model model
    ) {
        Page<Review> reviewList = null;
        List<ReviewDTO> reviewDTOList = new ArrayList<>();
        Page<ReviewDTO> result = null;
        if (keyword != null) {
            reviewList = boardService.findReviewListByKeyword(keyword, pageable);
            reviewList.getContent().forEach(r -> {
                    ReviewDTO reviewDTO = r.convertToDTO();
                    Page<Comment> reviewComments = commentService.findReviewComments(r.getId(),
                        pageable);
                    reviewDTO.setCommentCount(reviewComments.getTotalElements());
                    reviewDTOList.add(reviewDTO);
                }
            );
            result = new PageImpl<>(reviewDTOList, reviewList.getPageable(),
                reviewList.getTotalElements());
        } else {
            reviewList = boardService.findReviewListAll(pageable);
            reviewList.getContent().forEach(r -> {
                    ReviewDTO reviewDTO = r.convertToDTO();
                    Page<Comment> reviewComments = commentService.findReviewComments(r.getId(),
                        pageable);
                    reviewDTO.setCommentCount(reviewComments.getTotalElements());
                    reviewDTOList.add(reviewDTO);
                }
            );
            result = new PageImpl<>(reviewDTOList, reviewList.getPageable(),
                reviewList.getTotalElements());

        }

        int blockLimit = 10;
        int startPage =
            ((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit)) - 1) * blockLimit
                + 1;
        int endPage = (startPage + blockLimit - 1) > reviewList.getTotalPages() ?
            reviewList.getTotalPages() : startPage + blockLimit - 1;
        endPage = Math.max(endPage, startPage);

        log.info("reviewList : " + reviewList);

        model.addAttribute("reviewList", result);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("keyword", keyword);
        return "rboard/list";
    }

    @GetMapping("/detail")
    public String boardDetailForm() {
        return "rboard/detail";
    }

    @GetMapping("/write")
    public String rBoardwriteForm(Model model) {
        model.addAttribute("review", new Review());
        return "rboard/write";
    }

    @PostMapping("/write")
    public String rBoardWritePost(
        Review review,
        BindingResult bindingResult,
        @RequestParam(name = "reviewFile", required = false) MultipartFile reviewFile)
        throws IllegalStateException, IOException {
        log.info("review : " + review);

        if (bindingResult.hasErrors()) {
            log.info("has errors.....");
            return "main";
        }
        try {
            boardService.reviewSave(review, reviewFile);
        } catch (Exception e) {
            log.info("reviewSave error....." + e.getMessage());
            return "main";
        }

        return "redirect:/rboard/list";
    }

    @PostMapping("/update/{rBoardId}")
    public String updateRBoard(
        @PathVariable Long rBoardId,
        BindingResult bindingResult,
		@RequestParam(name = "") String content, //name 화면 구성할 때 맞춰 지정하세요
        @RequestParam(name = "reviewFile", required = false) MultipartFile reviewFile)
        throws IllegalStateException, IOException {
        if (bindingResult.hasErrors()) {
            log.info("has errors.....");
            return "main";
        }
		boardService.reviewUpdate(rBoardId, content, reviewFile);
		return "redirect:"; // 여기도 화면 맞게 Redirect
    }

    @PostMapping("/delete/{rBoardId}")
    public String deleteRBoard(
        @PathVariable Long rBoardId
    ){
        boardService.deleteReview(rBoardId);
        return "redirect:"; // 여기도 화면 맞게 Redirect
    }

}
