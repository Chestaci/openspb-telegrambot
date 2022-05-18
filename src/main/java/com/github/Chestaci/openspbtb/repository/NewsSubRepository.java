package com.github.Chestaci.openspbtb.repository;

import com.github.Chestaci.openspbtb.repository.entity.NewsSub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * {@link Repository} for {@link NewsSub} entity.
 */
@Repository
public interface NewsSubRepository extends JpaRepository<NewsSub, Long> {
}
