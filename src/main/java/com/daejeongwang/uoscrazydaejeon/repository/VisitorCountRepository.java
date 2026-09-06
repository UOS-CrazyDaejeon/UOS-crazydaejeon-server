package com.daejeongwang.uoscrazydaejeon.repository;

import com.daejeongwang.uoscrazydaejeon.entity.Place;
import com.daejeongwang.uoscrazydaejeon.entity.VisitorCount;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VisitorCountRepository extends JpaRepository<VisitorCount,Long> {
    boolean existsByPlaceAndDate(Place place, LocalDate date);
    List<VisitorCount> findAllByPlace_IdOrderByDateDesc(Long placeId);
    Optional<VisitorCount> findFirstByPlace_IdOrderByDateDesc(Long placeId);

    @Query(value = """
            SELECT vc.*
            FROM visitor_count vc
            JOIN (
                SELECT place_id, MAX(date) AS latest_date
                FROM visitor_count
                GROUP BY place_id
            ) latest
                ON latest.place_id = vc.place_id
               AND latest.latest_date = vc.date
            JOIN place p ON p.place_id = vc.place_id
            WHERE p.latitude IS NOT NULL
              AND p.longitude IS NOT NULL
              AND ST_Distance_Sphere(
                    POINT(p.longitude, p.latitude),
                    POINT(:longitude, :latitude)
                  ) <= :radius
            ORDER BY vc.visitor_count DESC, p.place_id ASC
            """, nativeQuery = true)
    List<VisitorCount> findTopByLatestVisitorCountNearLocation(
            @Param("latitude") Double latitude,
            @Param("longitude") Double longitude,
            @Param("radius") Double radius,
            Pageable pageable
    );
}
