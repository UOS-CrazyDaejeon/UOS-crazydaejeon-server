package com.daejeongwang.uoscrazydaejeon.repository;

import com.daejeongwang.uoscrazydaejeon.entity.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    boolean existsByPlaceNameAndPlaceAddressAndCategoryLarge(String placeName, String placeAddress, String categoryLarge);

    Page<Place> findAll(Pageable pageable);

    Optional<Place> findByPlaceName(String placeName);

    Optional<Place> findFirstByPlaceName(String placeName);

    Optional<Place> findFirstByPlaceNameAndGu(String placeName, String gu);

    @Query("""
        SELECT p
        FROM Place p
        WHERE LOWER(COALESCE(p.placeName, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(COALESCE(p.placeDescription, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(COALESCE(p.placeAddress, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(COALESCE(p.gu, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(COALESCE(p.dong, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(COALESCE(p.categoryLarge, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(COALESCE(p.categoryMedium, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(COALESCE(p.categorySmall, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(COALESCE(p.tag, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
        ORDER BY p.placeName ASC
        """)
    Page<Place> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query(value = """
        SELECT *
        FROM place p
        WHERE p.place_id <> :placeId
          AND p.latitude IS NOT NULL
          AND p.longitude IS NOT NULL
          AND ST_Distance_Sphere(
                POINT(p.longitude, p.latitude),
                POINT(:longitude, :latitude)
              ) <= :radius
        ORDER BY ST_Distance_Sphere(
                POINT(p.longitude, p.latitude),
                POINT(:longitude, :latitude)
              )
        """, nativeQuery = true)
    Page<Place> findNearbyPlaces(
            @Param("placeId") Long placeId,
            @Param("latitude") Double latitude,
            @Param("longitude") Double longitude,
            @Param("radius") Double radius,
            Pageable pageable
    );

    @Query(value = """
    SELECT *
    FROM place p
    WHERE p.place_id <> :placeId
      AND p.latitude IS NOT NULL
      AND p.longitude IS NOT NULL
      AND ST_Distance_Sphere(
            POINT(p.longitude, p.latitude),
            POINT(:longitude, :latitude)
          ) <= :radius
    ORDER BY ST_Distance_Sphere(
            POINT(p.longitude, p.latitude),
            POINT(:longitude, :latitude)
          )
    """, nativeQuery = true)
    List<Place> findAllNearbyPlacesWithoutPaging(
            @Param("placeId") Long placeId,
            @Param("latitude") Double latitude,
            @Param("longitude") Double longitude,
            @Param("radius") Double radius
    );
}
