package com.kosa.ShareTour.repository;
import com.kosa.ShareTour.entity.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    Accommodation findByAddress(String address);
    List<Accommodation> findByName(String name);
    List<Accommodation> findByArea(String area);
    List<Accommodation> findByGrade(String grade);
    List<Accommodation> findByParking(String parking);

    List<Accommodation> findByIdOrderByPriceDesc(Long id);


}