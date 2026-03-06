package org.example.service;

import org.example.pojo.User;
import org.example.pojo.VO.LoginVO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
     LoginVO login(User user);
}
