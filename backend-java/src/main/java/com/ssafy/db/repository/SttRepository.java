package com.ssafy.db.repository;

import com.ssafy.db.entity.Stt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SttRepository extends JpaRepository<Stt, Long>, SttCustomRepository {
}
