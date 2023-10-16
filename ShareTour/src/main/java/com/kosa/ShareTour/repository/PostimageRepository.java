package com.kosa.ShareTour.repository;

import com.kosa.ShareTour.entity.Postimage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostimageRepository extends JpaRepository<Postimage, Long> {

    List<Postimage> findByPostingIdOrderByIdAsc(Long postingId);

//    Postimage findByPostIdAndRepimgYn(Long postingId, String repimgYn);

}