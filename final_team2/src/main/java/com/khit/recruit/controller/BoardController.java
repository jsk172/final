package com.khit.recruit.controller;

import com.khit.recruit.entity.Free;
import com.khit.recruit.entity.Noti;
import com.khit.recruit.entity.Qna;
import com.khit.recruit.entity.Review;
import com.khit.recruit.entity.Comment;
import com.khit.recruit.service.BoardService;
import com.khit.recruit.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board")
@Controller
public class BoardController {

    private final BoardService boardService;

    private final CommentService commentService;


    @GetMapping("/noti")
    public String boardListForm(
        @RequestParam(name = "keyword", required = false) String keyword,
        @PageableDefault(page = 1) Pageable pageable,
        Model model) {
        Page<Noti> notiList = null;
        if (keyword != null) {
            notiList = boardService.findNotiListByKeyword(keyword, pageable);
        } else {
            notiList = boardService.findNotiListAll(pageable);
        }

        int blockLimit = 10;
        int startPage =
            ((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit)) - 1) * blockLimit
                + 1;
        int endPage = (startPage + blockLimit - 1) > notiList.getTotalPages() ?
            notiList.getTotalPages() : startPage + blockLimit - 1;
        endPage = Math.max(endPage, startPage);

        model.addAttribute("notiList", notiList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("keyword", keyword);
        return "board/noti";
    }

    @GetMapping("/qna")
    public String boardQnaForm(
        @RequestParam(name = "keyword", required = false) String keyword,
        @PageableDefault(page = 1) Pageable pageable,
        Model model) {

        Page<Qna> qnaList = null;
        if (keyword != null) {
            qnaList = boardService.findQnaListByKeyword(keyword, pageable);
        } else {
            qnaList = boardService.findQnaListAll(pageable);
        }

        int blockLimit = 10;
        int startPage =
            ((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit)) - 1) * blockLimit
                + 1;
        int endPage = (startPage + blockLimit - 1) > qnaList.getTotalPages() ?
            qnaList.getTotalPages() : startPage + blockLimit - 1;
        endPage = Math.max(endPage, startPage);

        log.info("qnaList : " + qnaList);

        model.addAttribute("qnaList", qnaList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("keyword", keyword);
        return "board/qna";
    }

    @GetMapping("/free")
    public String boardFreeForm(
        @RequestParam(name = "keyword", required = false) String keyword,
        @PageableDefault(page = 1) Pageable pageable,
        Model model
    ) {
        Page<Free> freeList = null;
        if (keyword != null) {
            freeList = boardService.findFreeListByKeyword(keyword, pageable);
        } else {
            freeList = boardService.findFreeListAll(pageable);
        }

        int blockLimit = 10;
        int startPage =
            ((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit)) - 1) * blockLimit
                + 1;
        int endPage = (startPage + blockLimit - 1) > freeList.getTotalPages() ?
            freeList.getTotalPages() : startPage + blockLimit - 1;
        endPage = Math.max(endPage, startPage);

        log.info("freeList : " + freeList);

        model.addAttribute("freeList", freeList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("keyword", keyword);
        return "board/free";
    }

    @PostMapping("/write/{id}")
    public String boardDetailForm(
        Comment comment,
        @PathVariable Long id,
        @RequestParam(name = "boardType") String boardType,
        HttpServletRequest request
    ) {
        String userName =
            Objects.nonNull(request.getUserPrincipal()) ? request.getUserPrincipal().getName()
                : "noop";
        comment.setId(null);
        comment.setAuthor(userName);
        switch (boardType) {
            case "free" -> {
                comment.setFree(boardService.findFreeById(id));
                commentService.commentSave(comment);
            }
            case "qna" -> {
                comment.setQna(boardService.findQnaById(id));
                commentService.commentSave(comment);
            }
            case "noti" -> {
                comment.setNoti(boardService.findNotiById(id));
                commentService.commentSave(comment);
            }
            case "review" -> {
                comment.setReview(boardService.findReviewById(id));
                commentService.commentSave(comment);
            }
        }
        return "redirect:/board/detail/" + id + "?boardType=" + boardType;
    }

    @GetMapping("/detail/{id}")
    public String boardDetailForm(
        @PageableDefault(page = 1) Pageable pageable,
        @RequestParam(name = "boardType", required = false) String boardType,
        @PathVariable Long id,
        Model model
    ) {
        model.addAttribute("boardType", boardType);
        switch (boardType) {
            case "free" -> {
                Free free = boardService.findFreeById(id);
                Page<Comment> commentList = commentService.findFreeComments(id, pageable);
                model.addAttribute("commentList", commentList);
                model.addAttribute("comment", new Comment());
                model.addAttribute("board", free);
                boardService.increaseViews(free);
            }
            case "qna" -> {
                Qna qna = boardService.findQnaById(id);
                Page<Comment> commentList = commentService.findQnaComments(id, pageable);
                model.addAttribute("commentList", commentList);
                model.addAttribute("comment", new Comment());
                model.addAttribute("board", qna);
                boardService.increaseViews(qna);
            }
            case "noti" -> {
                Noti noti = boardService.findNotiById(id);
                Page<Comment> commentList = commentService.findNotiComments(id, pageable);
                model.addAttribute("commentList", commentList);
                model.addAttribute("comment", new Comment());
                model.addAttribute("board", noti);
                boardService.increaseViews(noti);
            }
            case "review" -> {
                Review review = boardService.findReviewById(id);
                Page<Comment> commentList = commentService.findReviewComments(id, pageable);
                model.addAttribute("commentList", commentList);
                model.addAttribute("comment", new Comment());
                model.addAttribute("board", review);
                boardService.increaseViews(review);
            }
        }
        return "board/detail";
    }

    @GetMapping("/write")
    public String boardWriteForm(
        Model model,
        @RequestParam(name = "boardType") String boardType
    ) {
        model.addAttribute("boardType", boardType);
        switch (boardType) {
            case "free":
                model.addAttribute("board", new Free());
                break;
            case "qna":
                model.addAttribute("board", new Qna());
                break;
            case "noti":
                model.addAttribute("board", new Noti());
                break;
            case "review":
                model.addAttribute("board", new Review());
                break;
        }
        return "board/write";
    }

    @PostMapping("/write")
    public String boardWritePost(
        Review review,
        @RequestParam(name = "boardType") String boardType,
        HttpServletRequest request // request header에 security에서 설정한 사용자 인증정보가 들어있습니다.
    ) {
        String userName =
            Objects.nonNull(request.getUserPrincipal()) ? request.getUserPrincipal().getName()
                : "noop";
        switch (boardType) {
            case "free" -> {
                try {
                    boardService.freeSave(Free.builder()
                        .title(review.getTitle())
                        .content(review.getContent())
                        .likes(0)
                        .views(0)
                        .author(userName)
                        .build());
                } catch (Exception e) {
                    log.info("freeSave error....." + e.getMessage());
                    return "board/write";
                }
            }
            case "qna" -> {
                try {
                    boardService.qnaSave(Qna.builder()
                        .title(review.getTitle())
                        .content(review.getContent())
                        .likes(0)
                        .views(0)
                        .author(userName)
                        .build());
                } catch (Exception e) {
                    log.info("qnaSave error....." + e.getMessage());
                    return "board/write";
                }
            }
            case "noti" -> {
                try {
                    boardService.notiSave(Noti.builder()
                        .title(review.getTitle())
                        .content(review.getContent())
                        .likes(0)
                        .views(0)
                        .author(userName)
                        .build());
                } catch (Exception e) {
                    log.info("notiSave error....." + e.getMessage());
                    return "board/write";
                }
            }
            case "review" -> {
                try {
                    review.setAuthor(userName);
                    review.setViews(0);
                    review.setLikes(0);
                    boardService.reviewSave(review);
                } catch (Exception e) {
                    log.info("reviewSave error....." + e.getMessage());
                    return "board/write";
                }
            }
        }

        return boardType.equals("review") ? "redirect:/rboard/list"
            : "redirect:/board/" + boardType;
    }

    @PostMapping("/update/{boardId}")
    public String updateBoard(
        @PathVariable Long boardId,
        @RequestParam(name = "boardType") String boardType,
        @RequestParam String title,
        @RequestParam String content

    ) {
        switch (boardType) {
            case "free":
                boardService.freeUpdate(boardId, title, content);
                break;
            case "qna":
                boardService.qnaUpdate(boardId, title, content);
                break;
            case "noti":
                boardService.notiUpdate(boardId, title, content);
                break;
        }

        return "redirect:"; // 여기도 화면 맞게 Redirect
    }

    @PostMapping("/delete/{boardId}")
    public String deleteBoard(
        @PathVariable Long boardId,
        @RequestParam(name = "boardType") String boardType
    ) {
        switch (boardType) {
            case "free":
                boardService.freeDelete(boardId);
                break;
            case "qna":
                boardService.qnaDelete(boardId);
                break;
            case "noti":
                boardService.notiDelete(boardId);
                break;
        }

        return "redirect:"; // 여기도 화면 맞게 Redirect
    }

    @PostMapping("/like/{boardId}")
    public String likeBoard(
        @PathVariable Long boardId,
        @RequestParam(name = "boardType") String boardType
    ) {
        switch (boardType) {
            case "free":
                boardService.freeLike(boardId);
                break;
            case "qna":
                boardService.qnaLike(boardId);
                break;
            case "noti":
                boardService.notiLike(boardId);
                break;
            case "review":
                boardService.reviewLike(boardId);
                break;
        }
        return "redirect:"; // 여기도 화면 맞게 Redirect
    }
}