package com.example.buyornot.repository;

import com.example.buyornot.domain.Item;
import com.example.buyornot.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    // ✅ 특정 유저의 대기 중 아이템만 remindDate 기준으로 정렬
    List<Item> findAllByUserIdAndStatusOrderByRemindDateAsc(String userId, Status status);

    Optional<Item> findTopByOrderByCreatedDateDesc();

    List<Item> findAllByUserIdAndStatus(String userId, Status status);

    List<Item> findAllByUserIdAndStatusOrderByRemindDateDesc(String userId, Status status);
}

