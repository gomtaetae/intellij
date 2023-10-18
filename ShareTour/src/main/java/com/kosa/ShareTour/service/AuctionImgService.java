package com.kosa.ShareTour.service;

import com.kosa.ShareTour.entity.AuctionImg;
import com.kosa.ShareTour.repository.AuctionImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class AuctionImgService {

    @Value("${auctionImgLocation}")
    private String auctionImgLocation;

    private final AuctionImgRepository auctionImgRepository;

    private final FileService fileService;

    public void
    saveAuctionImg(AuctionImg auctionImg, MultipartFile auctionImgFile) throws Exception {
        String oriImgName = auctionImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        //파일 업로드
        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(auctionImgLocation, oriImgName,
                    auctionImgFile.getBytes());
            imgUrl = "/images/auction/" + imgName;
        }

        //상품 이미지 정보 저장
        auctionImg.updateAuctionImg(oriImgName, imgName, imgUrl);
        auctionImgRepository.save(auctionImg);
    }

    public void updateAuctionImg(Long auctionImgId, MultipartFile auctionImgFile) throws Exception{
        if(!auctionImgFile.isEmpty()){     //상품 이미지를 수정한 경우 상품 이미지를 업데이트

            AuctionImg savedAuctionImg = auctionImgRepository.findById(auctionImgId)    //상품 이미지 아이디를 이용해 기존에 저장했던 이미지 엔티티 조회
                    .orElseThrow(EntityNotFoundException::new);

            //기존 이미지 파일 삭제
            if(!StringUtils.isEmpty(savedAuctionImg.getImgName())) {       //기존에 등록된 상품 이미지 파일이 있을 경우 해당 파일을 삭제
                fileService.deleteFile(auctionImgLocation+"/"+
                        savedAuctionImg.getImgName());
            }

            String oriImgName = auctionImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(auctionImgLocation, oriImgName, auctionImgFile.getBytes());
            //업데이트한 상품 이미지 파일을 업로드

            String imgUrl = "/images/auction/" + imgName;
            savedAuctionImg.updateAuctionImg(oriImgName, imgName, imgUrl);
            //변경된 상품 이미지 정보를 세팅
            //auctionImgRepositorty.save()를 쓰지 않는 것은 savedAuctionImg 엔티티는 현재 영속 상태이므로
            //데이터를 변경하는 것만으로 변경 감지 기능이 동작하여 트랜잭션이 끝날 때 update 쿼리가 실행된다(?)
        }
    }
}
