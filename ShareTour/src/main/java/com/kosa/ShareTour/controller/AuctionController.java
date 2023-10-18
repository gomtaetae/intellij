package com.kosa.ShareTour.controller;

import com.kosa.ShareTour.dto.*;
import com.kosa.ShareTour.entity.Auction;
import com.kosa.ShareTour.service.AuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class AuctionController {

    private final AuctionService auctionService;

    @GetMapping(value = "/admin/auction/new")
    public String auctionForm(Model model) {
        model.addAttribute("auctionFormDto", new AuctionFormDto());
        return "/auction/auctionForm";
    }

    @PostMapping(value = "/admin/auction/new")
    public String auctionNew(@Valid AuctionFormDto auctionFormDto, BindingResult bindingResult,
                          Model model, @RequestParam("auctionImgFile") List<MultipartFile> auctionImgFileList){

        if (bindingResult.hasErrors()) {
            return "auction/auctionForm";
        }

        if (auctionImgFileList.get(0).isEmpty() && auctionFormDto.getId() == null) {
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "auction/auctionForm";
        }

        try {
            auctionService.saveAuction(auctionFormDto, auctionImgFileList);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "auction/auctionForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = "/admin/auction/{auctionId}")
    public String auctionDtl(@PathVariable("auctionId") Long auctionId, Model model) {

        try {
            AuctionFormDto auctionFormDto = auctionService.getAuctionDtl(auctionId);
            //조회한 상품 데이터를 모델에 담아서 뷰로 전달

            model.addAttribute("auctionFormDto", auctionFormDto);

        } catch(EntityNotFoundException e) {        //상품 엔티티가 존재하지 않을 경우 에러메시지를 담아서 상품 등록 페이지로 이동
            model.addAttribute("errorMessage", "존재하지 않는 상품 입니다.");
            model.addAttribute("auctionFormDto", new AuctionFormDto());
            return "auction/auctionForm";
        }

        return "auction/auctionForm";
    }

    @PostMapping(value = "/admin/auction/{auctionId}")
    public String auctionUpdate(@Valid AuctionFormDto auctionFormDto, BindingResult bindingResult,
                             @RequestParam("auctionImgFile") List<MultipartFile> auctionImgFileList, Model model){
        if(bindingResult.hasErrors()){
            return "auction/auctionForm";
        }

        if(auctionImgFileList.get(0).isEmpty() && auctionFormDto.getId() == null){
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "auction/auctionForm";
        }

        try {
            auctionService.updateAuction(auctionFormDto, auctionImgFileList);       //상품 수정로직을 호출
        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
            return "auction/auctionForm";
        }

        return "redirect:/";
    }

    //value에 상품 관리 화면 진입 시 URL에 페이지 번호가 없는 경우와 페이지 번확 있는 경우 2가지를 매핑
    @GetMapping(value = {"/admin/auctions", "/admin/auctions/{page}"})
    public String auctionManage(AuctionSearchDto auctionSearchDto, @PathVariable("page") Optional<Integer> page, Model model){

        //페이징을 위해 PageRequest.of 메소드를 토앻 Pageable 객체를 생성
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);

        //조회 조건과 페이징 정보를 파라미터로 넘겨서 Page<Auction> 객체를 반환
        Page<Auction> auctions = auctionService.getAdminAuctionPage(auctionSearchDto, pageable);

        //조회한 상품 데이터 및 페이징 정보를 뷰에 전달
        model.addAttribute("auctions", auctions);

        //페이지 전환 시 기존 검색 조건을 유지한 채 이동할 수 있도록 뷰에 다시 전달
        model.addAttribute("auctionSearchDto", auctionSearchDto);

        //상품 관리 메뉴 하단에 보여줄 페이지 번호의 최대 개수
        model.addAttribute("maxPage", 5);

        return "auction/auctionMng";
    }

    @GetMapping(value = "/auction/{auctionId}")
    public String auctionDtl(Model model, @PathVariable("auctionId") Long auctionId){
        AuctionFormDto auctionFormDto = auctionService.getAuctionDtl(auctionId);
        model.addAttribute("auction", auctionFormDto);
        return "auction/auctionDtl";
    }

    @GetMapping(value = "/auction")
    public String auctionList(AuctionSearchDto auctionSearchDto, Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
        Page<MainAuctionDto> auctions = auctionService.getMainAuctionPage(auctionSearchDto, pageable);
        model.addAttribute("auctions", auctions);
        model.addAttribute("auctionSearchDto", auctionSearchDto);
        model.addAttribute("maxPage", 5);
        return "auction/auctionList";
    }
}
