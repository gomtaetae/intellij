package com.kosa.ShareTour.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.kosa.ShareTour.dto.PostingFormDto;

import com.kosa.ShareTour.service.PostingService;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

//Principal 활용
import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import javax.persistence.EntityNotFoundException;

import com.kosa.ShareTour.dto.PostingSearchDto;
import com.kosa.ShareTour.entity.Posting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PostingController {

    private final PostingService postingService;

    @GetMapping(value = "/user/posting/new")
    public String postingForm(Model model) {
        model.addAttribute("postingFormDto", new PostingFormDto());
        return "/posting/postingForm";
    }

    @PostMapping(value = "/user/posting/new")
    public String postingNew(@Valid PostingFormDto postingFormDto, BindingResult bindingResult,
                             Model model, @RequestParam("postimageFile") List<MultipartFile> postimageFileList,
                             Principal principal) {

        if(bindingResult.hasErrors()){
            return "posting/postingForm";
        }

//        if(postimageFileList.get(0).isEmpty() && postingFormDto.getId() == null){
//            model.addAttribute("errorMessage", "첫번째 이미지는 필수 입력 값 입니다.");
//            return "posting/postingForm";
//        }

        //Principle를 활용하여 게시글 작성자와 함께 가져오기 멤버 Id 가져오기
        try {
            postingService.savePosting(postingFormDto, postimageFileList, principal.getName());
        } catch (Exception e){
            model.addAttribute("errorMessage", "게시글 등록 중 에러가 발생하였습니다.");
            return "posting/postingForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = "/user/posting/{postingId}")
    public String postingDtl(@PathVariable("postingId") Long postingId, Model model){

        try {
            PostingFormDto postingFormDto = postingService.getPostingDtl(postingId);
            model.addAttribute("postingFormDto", postingFormDto);
        } catch(EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않는 게시글 입니다.");
            model.addAttribute("postingFormDto", new PostingFormDto());
            return "posting/postingForm";
        }

        return "posting/postingForm";
    }

    @PostMapping(value = "/user/posting/{postingId}")
    public String postingUpdate(@Valid PostingFormDto postingFormDto, BindingResult bindingResult,
                                @RequestParam("postimageFile") List<MultipartFile> postimageFileList, Model model){
        if(bindingResult.hasErrors()){
            return "posting/postingForm";
        }

//        if(postimageFileList.get(0).isEmpty() && postingFormDto.getId() == null){
//            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
//            return "posting/postingForm";
//        }

        try {
            postingService.updatePosting(postingFormDto, postimageFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "게시글 수정 중 에러가 발생하였습니다.");
            return "posting/postingForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = {"/user/postings", "/user/postings/{page}"})
    public String postingManage(PostingSearchDto postingSearchDto, @PathVariable("page") Optional<Integer> page, Model model){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
        Page<Posting> postings = postingService.getUserPostingPage(postingSearchDto, pageable);
        model.addAttribute("postings", postings);
        model.addAttribute("postingSearchDto", postingSearchDto);
        model.addAttribute("maxPage", 5);

        return "posting/postingMng";
    }

    @GetMapping(value = "/posting/{postingId}")
    public String postingDtl(Model model, @PathVariable("postingId") Long itemId){
        PostingFormDto postingFormDto = postingService.getPostingDtl(itemId);
        model.addAttribute("posting", postingFormDto);
        return "posting/postingDtl";
    }

}