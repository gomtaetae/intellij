package com.kosa.ShareTour.controller;

import com.kosa.ShareTour.dto.AuctionOrderDto;
import com.kosa.ShareTour.dto.AuctionOrderHistDto;
import com.kosa.ShareTour.dto.OrderDto;
import com.kosa.ShareTour.dto.OrderHistDto;
import com.kosa.ShareTour.service.AuctionOrderService;
import com.kosa.ShareTour.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AuctionOrderController {
    private final AuctionOrderService auctionOrderService;

    @PostMapping(value = "/auctionOrder")
    public @ResponseBody ResponseEntity auctionOrder(@RequestBody @Valid AuctionOrderDto auctionOrderDto
            , BindingResult bindingResult, Principal principal){

        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }

            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String email = principal.getName();
        Long auctionOrderId;

        try {
            auctionOrderId = auctionOrderService.auctionOrder(auctionOrderDto, email);
        } catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(auctionOrderId, HttpStatus.OK);
    }

    @GetMapping(value = {"/auctionOrders", "/auctionOrders/{page}"})
    public String auctionOrderHist(@PathVariable("page") Optional<Integer> page, Principal principal, Model model){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 4);
        Page<AuctionOrderHistDto> auctionOrdersHistDtoList = auctionOrderService.getAuctionOrderList(principal.getName(), pageable);

        model.addAttribute("auctionOrders", auctionOrdersHistDtoList);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 5);

        return "auctionOrder/auctionOrderHist";
    }

    @PostMapping("/auctionOrder/{auctionOrderId}/cancel")
    public @ResponseBody ResponseEntity cancelAuctionOrder(@PathVariable("auctionOrderId") Long auctionOrderId , Principal principal){

        if(!auctionOrderService.validateAuctionOrder(auctionOrderId, principal.getName())){
            return new ResponseEntity<String>("주문 취소 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        auctionOrderService.cancelAuctionOrder(auctionOrderId);
        return new ResponseEntity<Long>(auctionOrderId, HttpStatus.OK);
    }
}
