package project.seatsence.src.store.service;

import static project.seatsence.global.code.ResponseCode.STORE_SPACE_NOT_FOUND;
import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.global.utils.EnumUtils;
import project.seatsence.src.store.dao.StoreSpaceRepository;
import project.seatsence.src.store.domain.*;
import project.seatsence.src.store.dto.request.AdminStoreFormCreateRequest;
import project.seatsence.src.store.dto.response.AdminStoreChairResponse;
import project.seatsence.src.store.dto.response.AdminStoreSpaceResponse;
import project.seatsence.src.store.dto.response.AdminStoreTableResponse;

@Service
@RequiredArgsConstructor
public class StoreSpaceService {

    private final StoreSpaceRepository storeSpaceRepository;
    private final StoreService storeService;
    private final StoreTableService storeTableService;
    private final StoreChairService storeChairService;

    @Transactional
    public void save(Long id, List<AdminStoreFormCreateRequest> adminStoreFormCreateRequestList) {
        Store store = storeService.findById(id);
        List<StoreSpace> storeSpaceList = new ArrayList<>();
        List<StoreTable> storeTableList = new ArrayList<>();
        List<StoreChair> storeChairList = new ArrayList<>();
        for (AdminStoreFormCreateRequest adminStoreFormCreateRequest :
                adminStoreFormCreateRequestList) {
            StoreSpace storeSpace =
                    StoreSpace.builder()
                            .name(adminStoreFormCreateRequest.getName())
                            .width(adminStoreFormCreateRequest.getWidth())
                            .height(adminStoreFormCreateRequest.getHeight())
                            .entranceX(adminStoreFormCreateRequest.getEntranceX())
                            .entranceY(adminStoreFormCreateRequest.getEntranceY())
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
                StoreTable storeTable = new StoreTable();
                storeTable.setManageId(table.getManageId());
                storeTable.setTableX(table.getTableX());
                storeTable.setTableY(table.getTableY());
                storeTable.setWidth(table.getTableWidth());
                storeTable.setHeight(table.getTableHeight());
                storeTable.setStoreSpace(storeSpace);
                storeTableList.add(storeTable);

                List<AdminStoreFormCreateRequest.Table.@Valid Chair> chairList =
                        table.getChairList();
                for (AdminStoreFormCreateRequest.Table.Chair chair : chairList) {
                    StoreChair storeChair = new StoreChair();
                    storeChair.setManageId(chair.getManageId());
                    storeChair.setChairX(chair.getChairX());
                    storeChair.setChairY(chair.getChairY());
                    storeChair.setStoreTable(storeTable);
                    storeChairList.add(storeChair);
                }
            }
        }
        storeSpaceRepository.saveAll(storeSpaceList);
        storeTableService.saveAll(storeTableList);
        storeChairService.saveAll(storeChairList);
    }

    public List<AdminStoreSpaceResponse> getStoreForm(Store store) {
        List<StoreSpace> storeSpaceList = storeSpaceRepository.findAllByStore(store);
        List<AdminStoreSpaceResponse> adminStoreSpaceResponseList = new ArrayList<>();
        for (StoreSpace storeSpace : storeSpaceList) {
            // 각 스페이스에 속한 table과 그 table에 속한 chair 찾은 후 추가
            AdminStoreSpaceResponse adminStoreSpaceResponse =
                    AdminStoreSpaceResponse.builder()
                            .storeSpaceId(storeSpace.getId())
                            .name(storeSpace.getName())
                            .height(storeSpace.getHeight())
                            .width(storeSpace.getWidth())
                            .entranceX(storeSpace.getEntranceX())
                            .entranceY(storeSpace.getEntranceY())
                            .build();
            List<StoreTable> storeTableList = storeTableService.findAllByStoreSpace(storeSpace);
            List<AdminStoreTableResponse> adminStoreTableResponseList = new ArrayList<>();
            for (StoreTable storeTable : storeTableList) {
                List<StoreChair> storeChairList = storeChairService.findAllByStoreTable(storeTable);
                AdminStoreTableResponse adminStoreTableResponse =
                        AdminStoreTableResponse.builder()
                                .storeTableId(storeTable.getId())
                                .manageId(storeTable.getManageId())
                                .width(storeTable.getWidth())
                                .height(storeTable.getHeight())
                                .tableX(storeTable.getTableX())
                                .tableY(storeTable.getTableY())
                                .chairList(
                                        storeChairList.stream()
                                                .map(
                                                        storeChair ->
                                                                AdminStoreChairResponse.builder()
                                                                        .storeChairId(
                                                                                storeChair.getId())
                                                                        .manageId(
                                                                                storeChair
                                                                                        .getManageId())
                                                                        .chairX(
                                                                                storeChair
                                                                                        .getChairX())
                                                                        .chairY(
                                                                                storeChair
                                                                                        .getChairY())
                                                                        .build())
                                                .collect(Collectors.toList()))
                                .build();
                adminStoreTableResponseList.add(adminStoreTableResponse);
            }
            adminStoreSpaceResponse.setTableList(adminStoreTableResponseList);
            adminStoreSpaceResponseList.add(adminStoreSpaceResponse);
        }
        return adminStoreSpaceResponseList;
    }

    public StoreSpace findByIdAndState(Long id) {
        return storeSpaceRepository
                .findByIdAndState(id, ACTIVE)
                .orElseThrow(() -> new BaseException(STORE_SPACE_NOT_FOUND));
    }

    public Boolean reservationUnitIsSeat(StoreSpace storeSpace) {
        boolean result = false;
        if (storeSpace.getReservationUnit().equals(ReservationUnit.SEAT)
                || storeSpace.getReservationUnit().equals(ReservationUnit.BOTH)) {
            result = true;
        }
        return result;
    }

    public Boolean reservationUnitIsSpace(StoreSpace storeSpace) {
        boolean result = false;
        if (storeSpace.getReservationUnit().equals(ReservationUnit.SPACE)
                || storeSpace.getReservationUnit().equals(ReservationUnit.BOTH)) {
            result = true;
        }
        return result;
    }
}
