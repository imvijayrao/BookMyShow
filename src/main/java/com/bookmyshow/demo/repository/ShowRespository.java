package com.bookmyshow.demo.repository;

import com.bookmyshow.demo.models.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowRespository extends JpaRepository<Show, Long> {
}
