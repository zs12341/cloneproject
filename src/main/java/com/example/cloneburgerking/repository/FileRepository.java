package com.example.cloneburgerking.repository;

import com.example.cloneburgerking.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.io.File;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {

}
