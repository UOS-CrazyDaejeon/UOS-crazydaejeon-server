package com.daejeongwang.uoscrazydaejeon.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(
        name = "uk_place_click_log_member_place_date",
        columnNames = {"member_id", "place_id", "clicked_date"}
))
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceClickLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "click_log_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @Column(name = "clicked_at", nullable = false)
    private LocalDateTime clickedAt;

    @Column(
            name = "clicked_date",
            insertable = false,
            updatable = false,
            columnDefinition = "date generated always as (date(clicked_at)) stored"
    )
    private LocalDate clickedDate;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        this.clickedAt = now;
    }
}
