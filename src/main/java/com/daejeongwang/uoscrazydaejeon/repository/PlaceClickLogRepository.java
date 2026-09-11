package com.daejeongwang.uoscrazydaejeon.repository;

import com.daejeongwang.uoscrazydaejeon.entity.Place;
import com.daejeongwang.uoscrazydaejeon.entity.PlaceClickLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface PlaceClickLogRepository extends JpaRepository<PlaceClickLog, Long> {
    long countByPlace_Id(Long placeId);
    long countByPlace_IdAndClickedAtGreaterThanEqualAndClickedAtLessThan(
            Long placeId,
            LocalDateTime startOfDay,
            LocalDateTime startOfNextDay
    );

    boolean existsByMember_IdAndPlace_IdAndClickedAtGreaterThanEqualAndClickedAtLessThan(
            Long memberId,
            Long placeId,
            LocalDateTime startOfDay,
            LocalDateTime startOfNextDay
    );

    @Modifying
    @Query("update PlaceClickLog clickLog set clickLog.member = null where clickLog.member.id = :memberId")
    void detachMember(@Param("memberId") Long memberId);
}
