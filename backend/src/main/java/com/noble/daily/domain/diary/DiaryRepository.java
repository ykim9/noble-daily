package com.noble.daily.domain.diary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    
    /**
     * 사용자별 일기 목록 조회 (최신순, N+1 방지)
     */
    @Query("SELECT d FROM Diary d JOIN FETCH d.user WHERE d.user.id = :userId ORDER BY d.createdAt DESC")
    List<Diary> findByUserIdWithUser(@Param("userId") Long userId);
    
    /**
     * 사용자별 일기 개수
     */
    long countByUserId(Long userId);
}
