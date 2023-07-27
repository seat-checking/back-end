package project.seatsence.src.store.service;

import static project.seatsence.global.code.ResponseCode.*;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.store.dao.StoreChairRepository;
import project.seatsence.src.store.domain.StoreChair;
import project.seatsence.src.store.domain.StoreTable;

@Service
@RequiredArgsConstructor
public class StoreChairService {

    private final StoreChairRepository storeChairRepository;

    public void saveAll(List<StoreChair> storeChairList) {
        storeChairRepository.saveAll(storeChairList);
    }

    public List<StoreChair> findAllByStoreTable(StoreTable storeTable) {
        return storeChairRepository.findAllByStoreTable(storeTable);
    }

    public StoreChair findById(Long id) {
        storeChairRepository
                .findById(id)
                .orElseThrow(() -> new BaseException(STORE_CHAIR_NOT_FOUND));
    }
}
