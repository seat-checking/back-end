package project.seatsence.src.store.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.src.store.dao.StoreCustomRepository;
import project.seatsence.src.store.domain.CustomReservationField;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.store.dto.request.AdminStoreReservationFieldCustomRequest;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreCustomService {

    private final StoreService storeService;
    private final StoreCustomRepository storeCustomRepository;

    public void storeReservationFieldCustom(Long storeId,
                                            AdminStoreReservationFieldCustomRequest adminStoreReservationFieldCustomRequest)
            throws JsonProcessingException {

        Store store = storeService.findByIdAndState(storeId);

        ObjectMapper objectMapper = new ObjectMapper();
        String contentGuide =
                objectMapper.writeValueAsString(
                        adminStoreReservationFieldCustomRequest.getContentGuide());

        CustomReservationField newCustomReservationField =
                new CustomReservationField(
                        store,
                        adminStoreReservationFieldCustomRequest.getTitle(),
                        adminStoreReservationFieldCustomRequest.getType(),
                        contentGuide);
        storeCustomRepository.save(newCustomReservationField);
    }
}