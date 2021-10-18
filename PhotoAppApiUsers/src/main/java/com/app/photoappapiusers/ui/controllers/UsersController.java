package com.app.photoappapiusers.ui.controllers;

import com.app.photoappapiusers.data.UserEntity;
import com.app.photoappapiusers.service.UsersService;
import com.app.photoappapiusers.shared.UserDto;
import com.app.photoappapiusers.ui.model.CreateUserRequestModel;
import com.app.photoappapiusers.ui.model.CreateUserResponseModel;
import com.app.photoappapiusers.ui.model.UserResponseModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private Environment env;

    @Autowired
    UsersService usersService;

    @GetMapping("/status/check")
    public String status(){
        return "working Hello" + env.getProperty("local.server.port");
    }

    @PostMapping(consumes = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
                produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userDetail){

        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(userDetail, UserDto.class);
        UserDto createdUser = usersService.createUser(userDto);
        CreateUserResponseModel  returnValue = modelMapper.map(createdUser, CreateUserResponseModel.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }

    @GetMapping(value = "/{userId}",  produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserResponseModel> getUser(@PathVariable("userId") String userId){
        UserDto userDto = usersService.getUserByUserId(userId);
        UserResponseModel userResponseModel = new ModelMapper().map(userDto, UserResponseModel.class);

        return ResponseEntity.status(HttpStatus.OK).body(userResponseModel);
    }

}
