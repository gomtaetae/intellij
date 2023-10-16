package com.kosa.ShareTour.repository;

import com.kosa.ShareTour.dto.PostingSearchDto;
import com.kosa.ShareTour.entity.Posting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import com.kosa.ShareTour.dto.MainPostingDto;

public interface PostingRepositoryCustom {

    Page<Posting> getUserPostingPage(PostingSearchDto postingSearchDto, Pageable pageable);

//    Page<MainPostingDto> getMainPostingPage(PostingSearchDto postingSearchDto, Pageable pageable);
}