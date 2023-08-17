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
import project.seatsence.src.store.dao.StoreSpaceRepository;
import project.seatsence.src.store.domain.*;
import project.seatsence.src.store.dto.request.AdminStoreSpaceCreateRequest;
import project.seatsence.src.store.dto.request.AdminStoreSpaceUpdateRequest;
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

        List<StoreTable> storeTableList = storeTableService.findAllByStoreSpaceAndState(storeSpace);
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
        Store store = storeService.findByIdAndState(storeId);
        return storeSpaceRepository.findAllByStoreAndState(store, ACTIVE);
    }

    @Transactional
    public void updateStoreSpace(
            Long storeSpaceId, AdminStoreSpaceUpdateRequest adminStoreSpaceUpdateRequest) {
        StoreSpace storeSpace =
                storeSpaceRepository
                        .findByIdAndState(storeSpaceId, ACTIVE)
                        .orElseThrow(() -> new BaseException(STORE_SPACE_NOT_FOUND));
        storeTableService
                .findAllByStoreSpaceAndState(storeSpace)
                .forEach(
                        storeTable -> {
                            storeTable.setState(INACTIVE);
                        }); // 기존에 등록되어있던 테이블 전체 삭제
        storeChairService
                .findAllByStoreSpaceAndState(storeSpace)
                .forEach(
                        storeChair -> {
                            storeChair.setState(INACTIVE);
                        }); // 기존에 등록되어 있던 의자 전체 삭제
        storeSpace.updateBasicInformation(adminStoreSpaceUpdateRequest); // 이름, 예약 단위, 높이 변경

        // 테이블 및 좌석 새로 등록
        List<AdminStoreSpaceUpdateRequest.Table> tableList =
                adminStoreSpaceUpdateRequest.getTableList();

        List<StoreTable> storeTableList = new ArrayList<>();
        for (AdminStoreSpaceUpdateRequest.Table table : tableList) {
            StoreTable storeTable =
                    StoreTable.builder()
                            .storeSpace(storeSpace)
                            .storeTableId(table.getStoreTableId())
                            .tableX(table.getTableX())
                            .tableY(table.getTableY())
                            .width(table.getTableWidth())
                            .height(table.getTableHeight())
                            .build();
            storeTableList.add(storeTable);
        }
        storeTableService.saveAll(storeTableList);

        List<StoreChair> storeChairList = new ArrayList<>();
        List<AdminStoreSpaceUpdateRequest.Chair> chairList =
                adminStoreSpaceUpdateRequest.getChairList();
        for (AdminStoreSpaceUpdateRequest.Chair chair : chairList) {
            StoreChair storeChair =
                    StoreChair.builder()
                            .storeSpace(storeSpace)
                            .storeChairId(chair.getStoreChairId())
                            .chairX(chair.getChairX())
                            .chairY(chair.getChairY())
                            .manageId(chair.getManageId())
                            .build();
            storeChairList.add(storeChair);
        }
        storeChairService.saveAll(storeChairList);
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
        StoreSpace storeSpace =
                storeSpaceRepository
                        .findByIdAndState(storeSpaceId, ACTIVE)
                        .orElseThrow(() -> new BaseException(STORE_SPACE_NOT_FOUND));
        storeSpace.setState(INACTIVE);
    }

    @Transactional
    public void updateBasicInformation(Long storeSpaceId, AdminStoreSpaceUpdateRequest request) {
        StoreSpace storeSpace =
                storeSpaceRepository
                        .findByIdAndState(storeSpaceId, ACTIVE)
                        .orElseThrow(() -> new BaseException(STORE_SPACE_NOT_FOUND));
        storeSpace.updateBasicInformation(request);
    }
}
