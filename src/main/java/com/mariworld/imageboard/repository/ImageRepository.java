package com.mariworld.imageboard.repository;

import com.mariworld.imageboard.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
