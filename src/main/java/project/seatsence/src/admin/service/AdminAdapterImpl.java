package project.seatsence.src.admin.service;

import java.util.List;
import org.springframework.stereotype.Service;
import project.seatsence.global.code.ResponseCode;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.admin.dao.AdminInfoRepository;
import project.seatsence.src.admin.domain.AdminInfo;

@Service
public class AdminAdapterImpl implements AdminAdapter {
    private final AdminInfoRepository adminInfoRepository;

    public AdminAdapterImpl(AdminInfoRepository adminInfoRepository) {
        this.adminInfoRepository = adminInfoRepository;
    }

    @Override
    public AdminInfo findById(Long adminInfoId) {
        return adminInfoRepository
                .findById(adminInfoId)
                .orElseThrow(() -> new BaseException(ResponseCode.ADMIN_INFO_NOT_FOUND));
    }

    @Override
    public List<AdminInfo> findAllByUserId(Long userId) {
        return adminInfoRepository.findAllByUserId(userId);
    }
}
