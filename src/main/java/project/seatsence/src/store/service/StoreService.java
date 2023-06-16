package project.seatsence.src.store.service;

import static project.seatsence.global.code.ResponseCode.*;
import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.store.dao.StoreRepository;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.store.dto.StoreMapper;
import project.seatsence.src.store.dto.response.AdminStoreResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreService {
    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;
    public AdminStoreResponse findById(Long id) {
        Store store =
                storeRepository
                        .findByIdAndState(id, ACTIVE)
                        .orElseThrow(() -> new BaseException(STORE_NOT_FOUND));
        return storeMapper.toDto(store);
    }
}
