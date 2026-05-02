package com.beyondtoursseoul.bts.domain.course;

import com.beyondtoursseoul.bts.domain.Attraction;
import com.beyondtoursseoul.bts.domain.tour.TourApiEvent;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tour_course_items")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class TourCourseItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private TourCourse course;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_type", nullable = false)
    private CourseItemType itemType; // ATTRACTION or EVENT

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attraction_id")
    private Attraction attraction; // itemType이 ATTRACTION일 때 연결

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private TourApiEvent event; // itemType이 EVENT일 때 연결

    @Column(name = "sequence_order", nullable = false)
    private Integer sequenceOrder; // 방문 순서

    @Column(name = "ai_comment", columnDefinition = "TEXT")
    private String aiComment; // AI가 작성한 해당 장소/행사 추천 이유
}
