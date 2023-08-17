package project.seatsence.src.store.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.src.store.dao.StoreCustomRepository;
import project.seatsence.src.store.domain.CustomReservationField;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.store.dto.request.AdminStoreCustomReservationFieldRequest;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreCustomService {

    private final StoreService storeService;
    private final StoreCustomRepository storeCustomRepository;

    public void storeReservationFieldCustom(
            Long storeId,
            List<AdminStoreCustomReservationFieldRequest> adminStoreCustomReservationFieldRequests)
            throws JsonProcessingException {

        Store store = storeService.findByIdAndState(storeId);

        ObjectMapper objectMapper = new ObjectMapper();
        for (AdminStoreCustomReservationFieldRequest request :
                adminStoreCustomReservationFieldRequests) {
            String contentGuide = objectMapper.writeValueAsString(request.getContentGuide());

            CustomReservationField newCustomReservationField =
                    new CustomReservationField(
                            store, request.getTitle(), request.getType(), contentGuide);
            storeCustomRepository.save(newCustomReservationField);
        }
    }
}
