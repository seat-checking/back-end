package project.seatsence.src.store.service;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.seatsence.src.store.dao.StoreSpaceRepository;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.store.domain.StoreChair;
import project.seatsence.src.store.domain.StoreSpace;
import project.seatsence.src.store.domain.StoreTable;
import project.seatsence.src.store.dto.request.AdminStoreFormCreateRequest;

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
            StoreSpace storeSpace = new StoreSpace();
            storeSpace.setName(adminStoreFormCreateRequest.getName());
            storeSpace.setWidth(adminStoreFormCreateRequest.getWidth());
            storeSpace.setHeight(adminStoreFormCreateRequest.getHeight());
            storeSpace.setEntranceX(adminStoreFormCreateRequest.getEntranceX());
            storeSpace.setEntranceY(adminStoreFormCreateRequest.getEntranceY());
            storeSpace.setStore(store);
            storeSpaceList.add(storeSpace);

            List<AdminStoreFormCreateRequest.@Valid Table> tableList =
                    adminStoreFormCreateRequest.getTableList();
            for (AdminStoreFormCreateRequest.Table table : tableList) {
                StoreTable storeTable = new StoreTable();
                storeTable.setTableX(table.getTableX());
                storeTable.setTableY(table.getTableY());
                storeTable.setStoreSpace(storeSpace);
                storeTableList.add(storeTable);

                List<AdminStoreFormCreateRequest.Table.@Valid Chair> chairList =
                        table.getChairList();
                for (AdminStoreFormCreateRequest.Table.Chair chair : chairList) {
                    StoreChair storeChair = new StoreChair();
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
}
