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

    private final UserService userService;

    @Transactional
    public ResultEntity save(ResultEntity resultEntity) {
        try {
            resultEntity.setUser(userService.getCurrentUser());
            return repository.save(resultEntity);
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getClass() + " error save result. " + e.getMessage());
        }
    }
}
