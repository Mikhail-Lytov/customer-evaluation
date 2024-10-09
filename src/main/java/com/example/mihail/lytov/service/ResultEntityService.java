package com.example.mihail.lytov.service;

import com.example.mihail.lytov.entity.ResultEntity;
import com.example.mihail.lytov.repository.ResultEntityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResultEntityService {
    private final ResultEntityRepository repository;

    @Transactional
    public ResultEntity save(ResultEntity resultEntity) {
        return repository.save(resultEntity);
    }
}
