package project.seatsence.src.admin.service;

import project.seatsence.src.admin.domain.AdminInfo;

public interface AdminAdapter {

    AdminInfo findById(Long id);
}
