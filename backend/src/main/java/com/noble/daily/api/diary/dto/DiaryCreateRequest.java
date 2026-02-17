package com.noble.daily.api.diary.dto;

import com.noble.daily.domain.diary.Persona;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 일기 작성 요청 DTO
 */
@Getter
@NoArgsConstructor
public class DiaryCreateRequest {
    
    @NotBlank(message = "일기 내용을 입력해주세요.")
    @Size(max = 500, message = "일기는 최대 500자까지 작성 가능합니다.")
    private String originalText;
    
    @NotNull(message = "페르소나를 선택해주세요.")
    private Persona persona;
}
