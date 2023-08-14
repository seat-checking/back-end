package project.seatsence.src.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.src.store.dto.request.AdminStoreReservationFieldCustomRequest;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreCustomService {

    public void storeReservationFieldCustom(Long storeId,
                                            AdminStoreReservationFieldCustomRequest adminStoreReservationFieldCustomRequest){


    }
}