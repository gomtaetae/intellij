package com.kosa.ShareTour.repository;


import com.kosa.ShareTour.dto.ItemSearchDto;
import com.kosa.ShareTour.dto.MainItemDto;
import com.kosa.ShareTour.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    //Main 페이지 메소드를 해당 파일에 구현
    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
