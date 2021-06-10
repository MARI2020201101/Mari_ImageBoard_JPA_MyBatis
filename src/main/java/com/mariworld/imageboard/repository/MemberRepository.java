package com.mariworld.imageboard.repository;

import com.mariworld.imageboard.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
}
