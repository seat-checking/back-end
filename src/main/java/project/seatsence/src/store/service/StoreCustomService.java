package project.seatsence.src.store.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.store.dao.StoreCustomRepository;
import project.seatsence.src.store.domain.CustomReservationField;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.store.domain.StoreMember;
import project.seatsence.src.store.domain.StorePosition;
import project.seatsence.src.store.dto.request.StoreCustomReservationFieldRequest;

import static project.seatsence.global.code.ResponseCode.CUSTOM_RESERVATION_FIELD_NOT_FOUND;
import static project.seatsence.global.code.ResponseCode.STORE_MEMBER_NOT_FOUND;
import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.ACTIVE;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreCustomService {

    private final StoreService storeService;
    private final StoreCustomRepository storeCustomRepository;

    public void postStoreCustomReservationField(
            Long storeId,
            List<StoreCustomReservationFieldRequest> storeCustomReservationFieldRequests)
            throws JsonProcessingException {

        Store store = storeService.findByIdAndState(storeId);

        ObjectMapper objectMapper = new ObjectMapper();
        for (StoreCustomReservationFieldRequest request :
                storeCustomReservationFieldRequests) {
            String contentGuide = objectMapper.writeValueAsString(request.getContentGuide());

            CustomReservationField newCustomReservationField =
                    new CustomReservationField(
                            store, request.getTitle(), request.getType(), contentGuide);
            storeCustomRepository.save(newCustomReservationField);
        }
    }

    public List<CustomReservationField> findAllByStoreIdAndState(Long storeId){
        List<CustomReservationField> customReservationFieldList =
                storeCustomRepository.findAllByStoreIdAndState(storeId, ACTIVE);
        if (customReservationFieldList == null || customReservationFieldList.isEmpty())
            throw new BaseException(CUSTOM_RESERVATION_FIELD_NOT_FOUND);
        return customReservationFieldList;
    }
}
