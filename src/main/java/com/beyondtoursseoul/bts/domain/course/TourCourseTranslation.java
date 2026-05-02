package com.beyondtoursseoul.bts.domain.course;

import com.beyondtoursseoul.bts.domain.tour.TourLanguage;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "tour_course_translations",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"course_id", "language"})
    }
)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class TourCourseTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private TourCourse course;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TourLanguage language;

    @Column(nullable = false)
    private String title; // 번역된 제목

    @Column(length = 500)
    private String hashtags; // 번역된 해시태그
}
