//package vn.iotstar.controller;
//
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import vn.iotstar.entity.UserInfo;
//import vn.iotstar.service.UserService;
//
//import lombok.RequiredArgsConstructor;
//
//@RestController
//@RequestMapping("/user")
//@RequiredArgsConstructor
//public class AccountController {
//	
//	private final UserService userService;
//
//	@PostMapping("/new")
//	public String addUser(@RequestBody UserInfo userInfo) {
//		return userService.registerUser(userInfo);
//	}
//}
