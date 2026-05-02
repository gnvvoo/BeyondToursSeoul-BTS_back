package com.beyondtoursseoul.bts.dto.course;

import com.beyondtoursseoul.bts.domain.course.CourseItemType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "코스 구성 아이템 응답 DTO")
public class TourCourseItemResponse {
    @Schema(description = "아이템 유형 (ATTRACTION, EVENT)")
    private CourseItemType itemType;

    @Schema(description = "관광지 또는 행사 고유 ID")
    private Long id;

    @Schema(description = "이름 (관광지명 또는 행사명)")
    private String name;

    @Schema(description = "주소")
    private String address;

    @Schema(description = "대표 이미지")
    private String thumbnail;

    @Schema(description = "방문 순서")
    private Integer sequenceOrder;

    @Schema(description = "AI 추천 코멘트")
    private String aiComment;

    @Schema(description = "위도")
    private Double latitude;

    @Schema(description = "경도")
    private Double longitude;
}
