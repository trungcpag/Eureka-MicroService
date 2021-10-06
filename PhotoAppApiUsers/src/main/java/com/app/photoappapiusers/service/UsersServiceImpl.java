package com.app.photoappapiusers.service;

import com.app.photoappapiusers.data.UserEntity;
import com.app.photoappapiusers.data.UsersRepository;
import com.app.photoappapiusers.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService{

    UsersRepository usersRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDto createUser(UserDto userDetail) {
        userDetail.setUserId(UUID.randomUUID().toString());
        userDetail.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetail.getPassword()));
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = modelMapper.map(userDetail, UserEntity.class);

        usersRepository.save(userEntity);

        UserDto userDto = modelMapper.map(userEntity, UserDto.class);
        return userDto;
    }
}
