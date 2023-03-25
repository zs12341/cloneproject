package com.example.cloneburgerking.service;


import com.example.cloneburgerking.dto.FileDto;
import com.example.cloneburgerking.entity.FileEntity;
import com.example.cloneburgerking.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    public void save(FileDto fileDto) {
        System.out.println("---------file Service-----------");
        FileEntity fileEntity = new FileEntity(fileDto.getTitle(),fileDto.getUrl());
        fileRepository.save(fileEntity);
    }

    public List<FileEntity> getFiles() {
        List<FileEntity> fileall = fileRepository.findAll();
        return fileall;
    }
}