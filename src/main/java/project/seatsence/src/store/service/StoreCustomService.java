package project.seatsence.src.store.service;

import static project.seatsence.global.code.ResponseCode.CUSTOM_UTILIZATION_FIELD_NOT_FOUND;
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
import project.seatsence.src.store.dao.CustomUtilizationFieldRepository;
import project.seatsence.src.store.domain.CustomUtilizationField;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.store.dto.request.StoreCustomUtilizationFieldRequest;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreCustomService {

    private final StoreService storeService;
    private final CustomUtilizationFieldRepository customUtilizationFieldRepository;

    public void postStoreCustomUtilizationField(
            Long storeId, StoreCustomUtilizationFieldRequest storeCustomUtilizationFieldRequest)
            throws JsonProcessingException {

        Store store = storeService.findByIdAndState(storeId);

        ObjectMapper objectMapper = new ObjectMapper();
        String contentGuide =
                objectMapper.writeValueAsString(
                        storeCustomUtilizationFieldRequest.getContentGuide());

        CustomUtilizationField newCustomUtilizationField =
                new CustomUtilizationField(
                        store,
                        storeCustomUtilizationFieldRequest.getTitle(),
                        storeCustomUtilizationFieldRequest.getType(),
                        contentGuide);
        customUtilizationFieldRepository.save(newCustomUtilizationField);
    }

    public List<CustomUtilizationField> findAllByStoreIdAndState(Long storeId) {
        List<CustomUtilizationField> customUtilizationFieldList =
                customUtilizationFieldRepository.findAllByStoreIdAndState(storeId, ACTIVE);
        if (customUtilizationFieldList == null || customUtilizationFieldList.isEmpty())
            throw new BaseException(SUCCESS_NO_CONTENT);
        return customUtilizationFieldList;
    }

    public CustomUtilizationField findByIdAndState(Long id) {
        return customUtilizationFieldRepository
                .findByIdAndState(id, ACTIVE)
                .orElseThrow(() -> new BaseException(CUSTOM_UTILIZATION_FIELD_NOT_FOUND));
    }

    public void update(Long storeId, Long id, StoreCustomUtilizationFieldRequest request)
            throws JsonProcessingException {
        Store store = storeService.findByIdAndState(storeId);
        CustomUtilizationField customUtilizationField = findByIdAndState(id);
        ObjectMapper objectMapper = new ObjectMapper();
        String contentGuide = objectMapper.writeValueAsString(request.getContentGuide());

        customUtilizationField.setTitle(request.getTitle());
        customUtilizationField.setType(request.getType());
        customUtilizationField.setContentGuide(contentGuide);
    }

    public void delete(Long id) {
        CustomUtilizationField customUtilizationField = findByIdAndState(id);
        customUtilizationField.setState(INACTIVE);
    }
}
