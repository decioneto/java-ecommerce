package com.dailycodework.dream_shops.repository;

import com.dailycodework.dream_shops.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CateogoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String category);

    boolean existsByName(String name);
}
