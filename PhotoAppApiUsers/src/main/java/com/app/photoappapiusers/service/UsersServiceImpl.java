package com.app.photoappapiusers.service;

import com.app.photoappapiusers.data.AlbumsServiceClient;
import com.app.photoappapiusers.data.UserEntity;
import com.app.photoappapiusers.data.UsersRepository;
import com.app.photoappapiusers.shared.UserDto;
import com.app.photoappapiusers.ui.model.AlbumResponseModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService{

    UsersRepository usersRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;
//    RestTemplate restTemplate;
    AlbumsServiceClient albumsServiceClient;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder, AlbumsServiceClient albumsServiceClient){
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.albumsServiceClient = albumsServiceClient;
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

    @Override
    public UserDto getUserDetailsByUserName(String email) {
        UserEntity userEntity = usersRepository.findByEmail(email);
        if(userEntity == null ) throw new UsernameNotFoundException(email);
        return new ModelMapper().map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = usersRepository.findByUserId(userId);
        if(userEntity == null ) throw new UsernameNotFoundException(userId);
        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
//        String albumsUrl = String.format("http://ALBUMS-WS/users/%s/albums", userId);
//        ResponseEntity<List<AlbumResponseModel>> albumsListResponse = restTemplate.exchange(albumsUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<AlbumResponseModel>>() {
//        });
//        List<AlbumResponseModel> albumResponseModels = albumsListResponse.getBody();
        List<AlbumResponseModel> albumResponseModels = albumsServiceClient.getAlbums(userId);
        userDto.setAlbums(albumResponseModels);
        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = usersRepository.findByEmail(username);
        if(userEntity == null ) throw new UsernameNotFoundException(username);
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true,true, true, true, new ArrayList<>());
    }
}
