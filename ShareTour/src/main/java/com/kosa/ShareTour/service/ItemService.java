package com.kosa.ShareTour.service;

import com.kosa.ShareTour.dto.ItemFormDto;
import com.kosa.ShareTour.entity.Item;
import com.kosa.ShareTour.entity.ItemImg;
import com.kosa.ShareTour.repository.ItemImgRepository;
import com.kosa.ShareTour.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import com.kosa.ShareTour.dto.ItemImgDto;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;

import com.kosa.ShareTour.dto.ItemSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kosa.ShareTour.dto.MainItemDto;

@Service
@Transactional
@RequiredArgsConstructor
public class  ItemService {

    private final ItemRepository itemRepository;

    private final ItemImgService itemImgService;

    private final ItemImgRepository itemImgRepository;

    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception{

        //상품 등록
        Item item = itemFormDto.createItem();
        itemRepository.save(item);

        //이미지 등록
        for(int i=0;i<itemImgFileList.size();i++){
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);

            if(i == 0)
                itemImg.setRepimgYn("Y");
            else
                itemImg.setRepimgYn("N");

            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }

        return item.getId();
    }


    @Transactional(readOnly = true)     //상품데이터를 읽어오는 Transaction을 읽기 전용으로 설정(JPA가 더티체킹을 수행하지 않아 성능 향상 가능)
    public ItemFormDto getItemDtl(Long itemId) {

        //상품의 이미지 조회, 등록순으로 가져오기위해 오름차순 정렬
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);

        List<ItemImgDto> itemImgDtoList = new ArrayList<>();
        for (ItemImg itemImg : itemImgList) {       //조회한 ItemImg 엔티티를 ItemImgDto 객체로 만들어서 리스트에 추가
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }

        Item item = itemRepository.findById(itemId)         //상품 아이디를 통해 상품 엔티티를 조회
                .orElseThrow(EntityNotFoundException::new);     //존재하지 않을때는 EntityNotFoundException을 발생
        ItemFormDto itemFormDto = ItemFormDto.of(item);
        itemFormDto.setItemImgDtoList(itemImgDtoList);
        return itemFormDto;
    }

    public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception{
        //상품 수정
        Item item = itemRepository.findById(itemFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        //상품 등록 화면으로부터 전달 받은 상품 아이디를 이용하여 상품 엔티티를 조회

        item.updateItem(itemFormDto);
        //상품 등록 화면으로부터 전달 받은 ItemFormDto를 통해 상품 엔티티를 업데이트

        List<Long> itemImgIds = itemFormDto.getItemImgIds();
        //상품 이미지 아이디 리스트를 조회

        //이미지 등록
        for(int i=0;i<itemImgFileList.size();i++){
            itemImgService.updateItemImg(itemImgIds.get(i),
                    itemImgFileList.get(i));        //updatetItemImg 메소드에 상품이미지와 상품 이미지 파일정보를 파라미터로 전달
        }

        return item.getId();
    }
    @Transactional(readOnly = true)
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        return itemRepository.getAdminItemPage(itemSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        return itemRepository.getMainItemPage(itemSearchDto, pageable);
    }


    }