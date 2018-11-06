package com.cryptoweb.repos;

import com.cryptoweb.domain.User;
import com.cryptoweb.repos.cammon.BaseRepository;

public interface UserRepo extends BaseRepository<User, Long> {

    User findByUsername(String username);
}
