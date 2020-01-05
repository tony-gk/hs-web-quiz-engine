package engine.controller;

import engine.domain.User;
import engine.service.UserService;
import engine.transfer.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserApi {
    final private UserService userService;

    @Autowired
    public UserApi(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/register")
    public void register(@Valid @RequestBody UserDto userDto) {
        userService.register(userDto.getEmail(), userDto.getPassword());
    }
}
