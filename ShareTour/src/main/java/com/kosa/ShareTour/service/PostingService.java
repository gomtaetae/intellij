package com.kosa.ShareTour.service;

import com.kosa.ShareTour.dto.PostingFormDto;
import com.kosa.ShareTour.entity.Posting;
import com.kosa.ShareTour.entity.Postimage;
import com.kosa.ShareTour.dto.PostimageDto;
import com.kosa.ShareTour.repository.MemberRepository;
import com.kosa.ShareTour.repository.PostimageRepository;
import com.kosa.ShareTour.repository.PostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.ArrayList;

import com.kosa.ShareTour.dto.PostingSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@Transactional
@RequiredArgsConstructor
public class PostingService {

    private final PostingRepository postingRepository;
    private final PostimageService postimageService;
    private final PostimageRepository postimageRepository;
    private final MemberRepository memberRepository;

    //Principle 활용하여 Posting에 Member(email) 함께 저장시키기
    public Long savePosting(PostingFormDto postingFormDto,
                            List<MultipartFile> postimageFileList,
                            String email)
            throws Exception{

        var member = memberRepository.findByEmail(email);       //멤버 이메일을 조회해서 함께저장
//                .orElseThrow(EntityNotFoundException::new);
        //상품 등록
        Posting posting = postingFormDto.createPosting();       //조회된 이메일을 posting에 작성
//        posting.setMember(member);
        postingRepository.save(posting);                        //posting에 **member-email을 같이저장

        //이미지 등록
        for(int i=0; i < postimageFileList.size(); i++){
            Postimage postimage = new Postimage();
            postimage.setPosting(posting);

            if(i == 0)
                postimage.setRepimgYn("Y");
            else
                postimage.setRepimgYn("N");

            postimageService.savePostimage(postimage, postimageFileList.get(i));
        }

        return posting.getId();

    }

    @Transactional(readOnly = true)
    public PostingFormDto getPostingDtl(Long postingId){
        List<Postimage> postimageList = postimageRepository.findByPostingIdOrderByIdAsc(postingId);
        List<PostimageDto> postimageDtoList = new ArrayList<>();
        for (Postimage postimage : postimageList) {
            PostimageDto postimageDto = PostimageDto.of(postimage);
            postimageDtoList.add(postimageDto);
        }

        Posting posting = postingRepository.findById(postingId)
                .orElseThrow(EntityNotFoundException::new);
        PostingFormDto postingFormDto = PostingFormDto.of(posting);
        postingFormDto.setPostimageDtoList(postimageDtoList);
        return postingFormDto;
    }

    public Long updatePosting(PostingFormDto postingFormDto, List<MultipartFile> postimageFileList) throws Exception{
        //상품 수정
        Posting posting = postingRepository.findById(postingFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        posting.updatePosting(postingFormDto);
        List<Long> postimageIds = postingFormDto.getPostimageIds();

        //이미지 등록
        for(int i = 0; i < postimageFileList.size();i++){
            postimageService.updatePostimage(postimageIds.get(i),
                    postimageFileList.get(i));
        }

        return posting.getId();
    }

    @Transactional(readOnly = true)
    public Page<Posting> getUserPostingPage(PostingSearchDto postingSearchDto, Pageable pageable) {
        return postingRepository.getUserPostingPage(postingSearchDto, pageable);
    }


}