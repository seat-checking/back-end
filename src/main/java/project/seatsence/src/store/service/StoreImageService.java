package project.seatsence.src.store.service;

import static project.seatsence.global.entity.BaseTimeAndStateEntity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.seatsence.src.store.dao.StoreImageRepository;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.store.domain.StoreImage;

@Service
@RequiredArgsConstructor
public class StoreImageService {

    private final StoreImageRepository storeImageRepository;

    public void saveStoreImageList(Store store, List<String> urls) {
        List<StoreImage> storeImageList = new ArrayList<>();
        for (int i = 1; i < urls.size(); i++) {
            storeImageList.add(StoreImage.createStoreImage(store, urls.get(i)));
        }
        storeImageRepository.saveAll(storeImageList);
    }

    public List<String> getStoreImages(Store store) {
        return storeImageRepository.findAllByStoreAndState(store, State.ACTIVE).stream()
                .map(StoreImage::getUrl)
                .collect(Collectors.toList());
    }
}
