package com.mymatch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mymatch.entity.MaterialItem;

public interface MaterialItemRepository extends JpaRepository<MaterialItem, Long> {
    List<MaterialItem> findAllByIdInAndMaterialIsNull(List<Long> ids);
}
