package project.seatsence.src.store.service;

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
import project.seatsence.src.store.dao.StoreSpaceRepository;
import project.seatsence.src.store.domain.*;
import project.seatsence.src.store.dto.request.AdminStoreFormCreateRequest;
import project.seatsence.src.store.dto.response.AdminSpaceChairResponse;
import project.seatsence.src.store.dto.response.AdminSpaceTableResponse;
import project.seatsence.src.store.dto.response.AdminStoreSpaceResponse;

@Service
@RequiredArgsConstructor
public class StoreSpaceService {

    private final StoreSpaceRepository storeSpaceRepository;
    private final StoreTableService storeTableService;
    private final StoreChairService storeChairService;
    private final StoreService storeService;

    @Transactional
    public void save(Long id, List<AdminStoreFormCreateRequest> adminStoreFormCreateRequestList) {
        Store store = storeService.findByIdAndState(id);
        List<StoreSpace> storeSpaceList = new ArrayList<>();
        List<StoreTable> storeTableList = new ArrayList<>();
        List<StoreChair> storeChairList = new ArrayList<>();
        for (AdminStoreFormCreateRequest adminStoreFormCreateRequest :
                adminStoreFormCreateRequestList) {
            StoreSpace storeSpace =
                    StoreSpace.builder()
                            .name(adminStoreFormCreateRequest.getName())
                            .height(adminStoreFormCreateRequest.getHeight())
                            .reservationUnit(
                                    EnumUtils.getEnumFromString(
                                            adminStoreFormCreateRequest.getReservationUnit(),
                                            ReservationUnit.class))
                            .store(store)
                            .build();

            storeSpaceList.add(storeSpace);

            List<AdminStoreFormCreateRequest.@Valid Table> tableList =
                    adminStoreFormCreateRequest.getTableList();
            for (AdminStoreFormCreateRequest.Table table : tableList) {
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
            }

            List<AdminStoreFormCreateRequest.@Valid Chair> chairList =
                    adminStoreFormCreateRequest.getChairList();
            for (AdminStoreFormCreateRequest.Chair chair : chairList) {
                StoreChair storeChair =
                        StoreChair.builder()
                                .storeChairId(chair.getStoreChairId())
                                .manageId(chair.getManageId())
                                .chairX(chair.getChairX())
                                .chairY(chair.getChairY())
                                .storeSpace(storeSpace)
                                .build();
                storeChairList.add(storeChair);
            }
        }
        storeSpaceRepository.saveAll(storeSpaceList);
        storeTableService.saveAll(storeTableList);
        storeChairService.saveAll(storeChairList);
    }

    public List<AdminStoreSpaceResponse> getStoreSpace(Store store) {
        List<StoreSpace> storeSpaceList =
                storeSpaceRepository.findAllByStoreAndState(store, ACTIVE);
        List<AdminStoreSpaceResponse> adminStoreSpaceResponseList = new ArrayList<>();
        for (StoreSpace storeSpace : storeSpaceList) {
            AdminStoreSpaceResponse adminStoreSpaceResponse =
                    AdminStoreSpaceResponse.builder()
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
                adminStoreSpaceResponse.getTableList().add(adminSpaceTableResponse);
            }

            List<StoreChair> storeChairList =
                    storeChairService.findAllByStoreSpaceAndState(storeSpace);
            for (StoreChair storeChair : storeChairList) {
                AdminSpaceChairResponse adminSpaceChairResponse =
                        AdminSpaceChairResponse.builder()
                                .storeChairId(storeChair.getStoreChairId())
                                .manageId(storeChair.getManageId())
                                .chairX(storeChair.getChairX())
                                .chairY(storeChair.getChairY())
                                .build();
                adminStoreSpaceResponse.getChairList().add(adminSpaceChairResponse);
            }

            adminStoreSpaceResponseList.add(adminStoreSpaceResponse);
        }
        return adminStoreSpaceResponseList;
    }

    public StoreSpace findByIdAndState(Long id) {
        return storeSpaceRepository
                .findByIdAndState(id, ACTIVE)
                .orElseThrow(() -> new BaseException(STORE_SPACE_NOT_FOUND));
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
}
