package project.seatsence.src.user.service;

import project.seatsence.src.user.domain.User;

public interface UserAdaptor {

    User findById(Long id);
}
