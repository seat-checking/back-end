package project.seatsence.src.admin.service;

import java.util.List;
import project.seatsence.src.admin.domain.AdminInfo;

public interface AdminAdapter {

    AdminInfo findById(Long id);

    List<AdminInfo> findAllByUserId(Long userId);
}
