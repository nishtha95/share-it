package com.shareit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shareit.models.FileDAO;

@Repository
public interface FileRepository  extends JpaRepository<FileDAO, Long>{

	FileDAO findByTitle(String title);
}
