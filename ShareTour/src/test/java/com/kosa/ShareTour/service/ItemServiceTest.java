package com.kosa.ShareTour.service;

import com.kosa.ShareTour.constant.ItemSellStatus;
import com.kosa.ShareTour.dto.ItemFormDto;
import com.kosa.ShareTour.entity.Item;
import com.kosa.ShareTour.entity.ItemImg;
import com.kosa.ShareTour.repository.ItemImgRepository;
import com.kosa.ShareTour.repository.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemImgRepository itemImgRepository;

    List<MultipartFile> createMultipartFiles() throws Exception{

        List<MultipartFile> multipartFileList = new ArrayList<>();

        for(int i=0;i<5;i++){
            String path = "C:/shop/item/";
            String imageName = "image" + i + ".jpg";
            MockMultipartFile multipartFile =
                    new MockMultipartFile(path, imageName, "image/jpg", new byte[]{1,2,3,4});
            multipartFileList.add(multipartFile);
        }

        return multipartFileList;
    }

    @Test
    @DisplayName("상품 등록 테스트")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void saveItem() throws Exception {
        ItemFormDto itemFormDto = new ItemFormDto();
        itemFormDto.setTitle("테스트상품");
        itemFormDto.setTotalPrice(1000);
        itemFormDto.setContent("테스트 상품 입니다.");
        itemFormDto.setInStock(100);
        itemFormDto.setStockLeft(20);
        itemFormDto.setItemSellStatus(ItemSellStatus.SELL);

        List<MultipartFile> multipartFileList = createMultipartFiles();
        Long itemId = itemService.saveItem(itemFormDto, multipartFileList);
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);

        Item item = itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new);

        assertEquals(itemFormDto.getTitle(), item.getTitle());
        assertEquals(itemFormDto.getTotalPrice(), item.getTotalPrice());
        assertEquals(itemFormDto.getContent(), item.getContent());
        assertEquals(itemFormDto.getInStock(), item.getInStock());
        assertEquals(itemFormDto.getStockLeft(), item.getStockLeft());
        assertEquals(itemFormDto.getItemSellStatus(), item.getItemSellStatus());
        assertEquals(multipartFileList.get(0).getOriginalFilename(), itemImgList.get(0).getOriImgName());
    }

}