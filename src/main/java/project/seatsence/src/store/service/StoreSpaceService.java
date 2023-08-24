package project.seatsence.src.store.service;

import static project.seatsence.global.code.ResponseCode.STORE_NOT_FOUND;
import static project.seatsence.global.code.ResponseCode.STORE_SPACE_NOT_FOUND;
import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.*;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.store.dao.StoreSpaceRepository;
import project.seatsence.src.store.domain.*;
import project.seatsence.src.store.dto.request.*;
import project.seatsence.src.store.dto.response.*;

@Service
@RequiredArgsConstructor
public class StoreSpaceService {

    private final StoreSpaceRepository storeSpaceRepository;
    private final StoreTableService storeTableService;
    private final StoreChairService storeChairService;
    private final StoreService storeService;

    @Transactional
    public AdminStoreSpaceCreateResponse save(
            Long id, AdminStoreSpaceCreateRequest adminStoreSpaceCreateRequest) {
        Store store = storeService.findByIdAndState(id);
        List<StoreTable> storeTableList = new ArrayList<>();
        List<StoreChair> storeChairList = new ArrayList<>();

        StoreSpace storeSpace =
                StoreSpace.builder()
                        .store(store)
                        .name(adminStoreSpaceCreateRequest.getStoreSpaceName())
                        .height(adminStoreSpaceCreateRequest.getHeight())
                        .reservationUnit(
                                getReservationUnitFromRequest(
                                        adminStoreSpaceCreateRequest.getReservationUnit()))
                        .storeTableList(new ArrayList<>())
                        .storeChairList(new ArrayList<>())
                        .build();

        List<AdminStoreSpaceTableRequest> tableList = adminStoreSpaceCreateRequest.getTableList();
        for (AdminStoreSpaceTableRequest tableRequest : tableList) {
            StoreTable storeTable =
                    StoreTable.builder()
                            .tableX(tableRequest.getTableX())
                            .tableY(tableRequest.getTableY())
                            .width(tableRequest.getWidth())
                            .height(tableRequest.getHeight())
                            .storeSpace(storeSpace)
                            .idByWeb(tableRequest.getIdByWeb())
                            .build();
            storeTableList.add(storeTable);
            storeSpace.getStoreTableList().add(storeTable);
        }

        List<AdminStoreSpaceChairRequest> chairList = adminStoreSpaceCreateRequest.getChairList();

        for (AdminStoreSpaceChairRequest chairRequest : chairList) {
            StoreChair storeChair =
                    StoreChair.builder()
                            .chairX(chairRequest.getChairX())
                            .chairY(chairRequest.getChairY())
                            .storeSpace(storeSpace)
                            .idByWeb(chairRequest.getIdByWeb())
                            .manageId(chairRequest.getManageId())
                            .build();
            storeChairList.add(storeChair);
            storeSpace.getStoreChairList().add(storeChair);
        }

        storeTableService.saveAll(storeTableList);
        storeChairService.saveAll(storeChairList);
        StoreSpace save = storeSpaceRepository.save(storeSpace);
        return new AdminStoreSpaceCreateResponse(save.getId());
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
                        .reservationUnit(
                                getReservationUnitFromEntity(storeSpace.getReservationUnit()))
                        .build();

        List<StoreTable> storeTableList = storeTableService.findAllByStoreSpaceAndState(storeSpace);
        for (StoreTable storeTable : storeTableList) {
            AdminStoreSpaceTableResponse adminStoreSpaceTableResponse =
                    AdminStoreSpaceTableResponse.builder()
                            .idByWeb(storeTable.getIdByWeb())
                            .width(storeTable.getWidth())
                            .height(storeTable.getHeight())
                            .tableX(storeTable.getTableX())
                            .tableY(storeTable.getTableY())
                            .build();
            adminStoreSpaceSeatResponse.getTableList().add(adminStoreSpaceTableResponse);
        }

        List<StoreChair> storeChairList = storeChairService.findAllByStoreSpaceAndState(storeSpace);
        for (StoreChair storeChair : storeChairList) {
            AdminStoreSpaceChairResponse adminStoreSpaceChairResponse =
                    AdminStoreSpaceChairResponse.builder()
                            .ibByWeb(storeChair.getIdByWeb())
                            .manageId(storeChair.getManageId())
                            .chairX(storeChair.getChairX())
                            .chairY(storeChair.getChairY())
                            .build();
            adminStoreSpaceSeatResponse.getChairList().add(adminStoreSpaceChairResponse);
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
        List<StoreTable> storeTableList = new ArrayList<>();
        List<AdminStoreSpaceTableRequest> tableList = adminStoreSpaceUpdateRequest.getTableList();

        for (AdminStoreSpaceTableRequest tableRequest : tableList) {
            StoreTable storeTable =
                    StoreTable.builder()
                            .tableX(tableRequest.getTableX())
                            .tableY(tableRequest.getTableY())
                            .width(tableRequest.getWidth())
                            .height(tableRequest.getHeight())
                            .storeSpace(storeSpace)
                            .idByWeb(tableRequest.getIdByWeb())
                            .build();
            storeTableList.add(storeTable);
        }
        storeSpace.getStoreTableList().addAll(storeTableList); // storeSpace에 테이블 추가
        storeTableService.saveAll(storeTableList);

        List<StoreChair> storeChairList = new ArrayList<>();
        List<AdminStoreSpaceChairRequest> chairList = adminStoreSpaceUpdateRequest.getChairList();

        for (AdminStoreSpaceChairRequest chairRequest : chairList) {
            StoreChair storeChair =
                    StoreChair.builder()
                            .chairX(chairRequest.getChairX())
                            .chairY(chairRequest.getChairY())
                            .storeSpace(storeSpace)
                            .idByWeb(chairRequest.getIdByWeb())
                            .manageId(chairRequest.getManageId())
                            .build();
            storeChairList.add(storeChair);
        }
        storeSpace.getStoreChairList().addAll(storeChairList); // storeSpace에 의자 추가
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

    private ReservationUnit getReservationUnitFromRequest(ReservationUnitRequest request) {
        if (request.getSpace() && !request.getChair()) return ReservationUnit.SPACE;
        else if (!request.getSpace() && request.getChair()) return ReservationUnit.SEAT;
        else return ReservationUnit.BOTH;
    }

    private ReservationUnitResponse getReservationUnitFromEntity(ReservationUnit entity) {
        if (entity == ReservationUnit.SPACE) return new ReservationUnitResponse(true, false);
        else if (entity == ReservationUnit.SEAT) return new ReservationUnitResponse(false, true);
        else return new ReservationUnitResponse(true, true);
    }
}
