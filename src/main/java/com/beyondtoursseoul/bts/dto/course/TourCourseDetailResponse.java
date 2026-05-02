package com.beyondtoursseoul.bts.dto.course;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(description = "추천 여행 코스 상세 응답 DTO")
public class TourCourseDetailResponse {
    @Schema(description = "코스 ID")
    private Long id;

    @Schema(description = "코스 제목")
    private String title;

    @Schema(description = "코스 해시태그")
    private String hashtags;

    @Schema(description = "대표 이미지 URL")
    private String featuredImage;

    @Schema(description = "사용자 저장 여부")
    private boolean isSaved;

    @Schema(description = "코스 구성 아이템 목록")
    private List<TourCourseItemResponse> items;
}
