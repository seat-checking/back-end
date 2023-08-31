package project.seatsence.src.store.service;

import static project.seatsence.global.code.ResponseCode.CUSTOM_RESERVATION_FIELD_NOT_FOUND;
import static project.seatsence.global.code.ResponseCode.SUCCESS_NO_CONTENT;
import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.ACTIVE;
import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.INACTIVE;

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
import project.seatsence.src.store.dto.request.admin.custom.StoreCustomReservationFieldRequest;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreCustomService {

    private final StoreService storeService;
    private final StoreCustomRepository storeCustomRepository;

    public void postStoreCustomReservationField(
            Long storeId, StoreCustomReservationFieldRequest storeCustomReservationFieldRequest)
            throws JsonProcessingException {

        Store store = storeService.findByIdAndState(storeId);

        ObjectMapper objectMapper = new ObjectMapper();
        String contentGuide =
                objectMapper.writeValueAsString(
                        storeCustomReservationFieldRequest.getContentGuide());

        CustomReservationField newCustomReservationField =
                new CustomReservationField(
                        store,
                        storeCustomReservationFieldRequest.getTitle(),
                        storeCustomReservationFieldRequest.getType(),
                        contentGuide);
        storeCustomRepository.save(newCustomReservationField);
    }

    public List<CustomReservationField> findAllByStoreIdAndState(Long storeId) {
        List<CustomReservationField> customReservationFieldList =
                storeCustomRepository.findAllByStoreIdAndState(storeId, ACTIVE);
        if (customReservationFieldList == null || customReservationFieldList.isEmpty())
            throw new BaseException(SUCCESS_NO_CONTENT);
        return customReservationFieldList;
    }

    public CustomReservationField findByIdAndState(Long id) {
        return storeCustomRepository
                .findByIdAndState(id, ACTIVE)
                .orElseThrow(() -> new BaseException(CUSTOM_RESERVATION_FIELD_NOT_FOUND));
    }

    public void update(Long storeId, Long id, StoreCustomReservationFieldRequest request)
            throws JsonProcessingException {
        Store store = storeService.findByIdAndState(storeId);
        CustomReservationField customReservationField = findByIdAndState(id);
        ObjectMapper objectMapper = new ObjectMapper();
        String contentGuide = objectMapper.writeValueAsString(request.getContentGuide());

        customReservationField.setTitle(request.getTitle());
        customReservationField.setType(request.getType());
        customReservationField.setContentGuide(contentGuide);
    }

    public void delete(Long id) {
        CustomReservationField customReservationField = findByIdAndState(id);
        customReservationField.setState(INACTIVE);
    }
}
