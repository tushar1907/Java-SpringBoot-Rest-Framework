package com.springproject.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.springproject.demo.model.Tires;



@Component
public interface TipeJpaRepo extends JpaRepository<Tires, String>{

}
