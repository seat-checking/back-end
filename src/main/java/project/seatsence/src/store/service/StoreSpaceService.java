package project.seatsence.src.store.service;

import static project.seatsence.global.code.ResponseCode.STORE_NOT_FOUND;
import static project.seatsence.global.code.ResponseCode.STORE_SPACE_NOT_FOUND;
import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.*;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.global.utils.EnumUtils;
import project.seatsence.src.store.dao.StoreRepository;
import project.seatsence.src.store.dao.StoreSpaceRepository;
import project.seatsence.src.store.domain.*;
import project.seatsence.src.store.dto.request.AdminStoreSpaceCreateRequest;
import project.seatsence.src.store.dto.response.AdminSpaceChairResponse;
import project.seatsence.src.store.dto.response.AdminSpaceTableResponse;
import project.seatsence.src.store.dto.response.AdminStoreSpaceSeatResponse;

@Service
@RequiredArgsConstructor
public class StoreSpaceService {

    private final StoreSpaceRepository storeSpaceRepository;
    private final StoreTableService storeTableService;
    private final StoreChairService storeChairService;
    private final StoreService storeService;
    private final StoreRepository storeRepository;

    @Transactional
    public void save(Long id, AdminStoreSpaceCreateRequest adminStoreSpaceCreateRequest) {
        Store store = storeService.findByIdAndState(id);
        List<StoreTable> storeTableList = new ArrayList<>();
        List<StoreChair> storeChairList = new ArrayList<>();

        StoreSpace storeSpace =
                StoreSpace.builder()
                        .store(store)
                        .name(adminStoreSpaceCreateRequest.getName())
                        .height(adminStoreSpaceCreateRequest.getHeight())
                        .reservationUnit(
                                EnumUtils.getEnumFromString(
                                        adminStoreSpaceCreateRequest.getReservationUnit(),
                                        ReservationUnit.class))
                        .storeTableList(new ArrayList<>())
                        .storeChairList(new ArrayList<>())
                        .build();

        List<AdminStoreSpaceCreateRequest.@Valid Table> tableList =
                adminStoreSpaceCreateRequest.getTableList();
        for (AdminStoreSpaceCreateRequest.Table table : tableList) {
            StoreTable storeTable =
                    StoreTable.builder()
                            .tableX(table.getTableX())
                            .tableY(table.getTableY())
                            .width(table.getTableWidth())
                            .height(table.getTableHeight())
                            .storeSpace(storeSpace)
                            .storeTableId(table.getStoreTableId())
                            .build();
            storeTableList.add(storeTable);
            storeSpace.getStoreTableList().add(storeTable);
        }

        List<AdminStoreSpaceCreateRequest.@Valid Chair> chairList =
                adminStoreSpaceCreateRequest.getChairList();
        for (AdminStoreSpaceCreateRequest.Chair chair : chairList) {
            StoreChair storeChair =
                    StoreChair.builder()
                            .storeChairId(chair.getStoreChairId())
                            .manageId(chair.getManageId())
                            .chairX(chair.getChairX())
                            .chairY(chair.getChairY())
                            .storeSpace(storeSpace)
                            .build();
            storeChairList.add(storeChair);
            storeSpace.getStoreChairList().add(storeChair);
        }

        storeSpaceRepository.save(storeSpace);
        storeTableService.saveAll(storeTableList);
        storeChairService.saveAll(storeChairList);
    }

    public AdminStoreSpaceSeatResponse getStoreSpaceSeat(Long storeSpaceId) {
        StoreSpace storeSpace =
                storeSpaceRepository
                        .findByIdAndState(storeSpaceId, ACTIVE)
                        .orElseThrow(() -> new BaseException(STORE_NOT_FOUND));

        AdminStoreSpaceSeatResponse adminStoreSpaceSeatResponse =
                AdminStoreSpaceSeatResponse.builder()
                        .storeSpaceId(storeSpace.getId())
                        .storeSpaceName(storeSpace.getName())
                        .height(storeSpace.getHeight())
                        .tableList(new ArrayList<>())
                        .chairList(new ArrayList<>())
                        .build();

        List<StoreTable> storeTableList = storeTableService.findAllByStoreSpace(storeSpace);
        for (StoreTable storeTable : storeTableList) {
            AdminSpaceTableResponse adminSpaceTableResponse =
                    AdminSpaceTableResponse.builder()
                            .storeTableId(storeTable.getStoreTableId())
                            .width(storeTable.getWidth())
                            .height(storeTable.getHeight())
                            .tableX(storeTable.getTableX())
                            .tableY(storeTable.getTableY())
                            .build();
            adminStoreSpaceSeatResponse.getTableList().add(adminSpaceTableResponse);
        }

        List<StoreChair> storeChairList = storeChairService.findAllByStoreSpaceAndState(storeSpace);
        for (StoreChair storeChair : storeChairList) {
            AdminSpaceChairResponse adminSpaceChairResponse =
                    AdminSpaceChairResponse.builder()
                            .storeChairId(storeChair.getStoreChairId())
                            .manageId(storeChair.getManageId())
                            .chairX(storeChair.getChairX())
                            .chairY(storeChair.getChairY())
                            .build();
            adminStoreSpaceSeatResponse.getChairList().add(adminSpaceChairResponse);
        }

        return adminStoreSpaceSeatResponse;
    }

    public StoreSpace findByIdAndState(Long id) {
        return storeSpaceRepository
                .findByIdAndState(id, ACTIVE)
                .orElseThrow(() -> new BaseException(STORE_SPACE_NOT_FOUND));
    }

    public List<StoreSpace> findAllByStoreAndState(Long storeId) {
        Store store =
                storeRepository
                        .findByIdAndState(storeId, ACTIVE)
                        .orElseThrow(() -> new BaseException(STORE_NOT_FOUND));
        return storeSpaceRepository.findAllByStoreAndState(store, ACTIVE);
    }

    public Boolean reservationUnitIsOnlySeat(StoreSpace storeSpace) {
        boolean result = false;
        if (storeSpace.getReservationUnit().equals(ReservationUnit.SEAT)) {
            result = true;
        }
        return result;
    }

    public Boolean reservationUnitIsOnlySpace(StoreSpace storeSpace) {
        boolean result = false;
        if (storeSpace.getReservationUnit().equals(ReservationUnit.SPACE)) {
            result = true;
        }
        return result;
    }

    @Transactional
    public void deleteById(Long storeSpaceId) {
        storeSpaceRepository.deleteById(storeSpaceId);
    }
}
