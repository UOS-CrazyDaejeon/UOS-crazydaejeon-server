package com.daejeongwang.uoscrazydaejeon.repository;

import com.daejeongwang.uoscrazydaejeon.entity.PlacePhoto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlacePhotoRepository extends JpaRepository<PlacePhoto, Long> {
    @EntityGraph(attributePaths = "member")
    Page<PlacePhoto> findAllByPlace_IdOrderByCreatedAtDesc(Long placeId, Pageable pageable);

    Optional<PlacePhoto> findByIdAndMember_Id(Long placePhotoId, Long memberId);

    @EntityGraph(attributePaths = "place")
    List<PlacePhoto> findAllByMember_IdOrderByCreatedAtDesc(Long memberId);

    void deleteAllByMember_Id(Long memberId);
}
