package vn.iotstar.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.iotstar.entity.UserInfo;
import vn.iotstar.service.UserService;

@Controller
@RequestMapping("/admin/user-list")
public class UserManageController {

	@Autowired
	UserService userService;
	
	@GetMapping("")
	public String getAllUser(ModelMap model) {
		List<UserInfo> list = userService.findAllExceptAdmin();
		model.addAttribute("users", list);
		return "admin/user-manage";
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserInfo> getUserById(@PathVariable Integer id) {
		UserInfo user = userService.findById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
	}


    @PostMapping("/edit")
    public String updateUser(@ModelAttribute UserInfo user, RedirectAttributes redirectAttributes) {
        userService.updateUser(user);
        redirectAttributes.addFlashAttribute("message", "User updated successfully!");
        return "redirect:/admin/user-list"; // Quay lại trang hiện tại
    }
	
	@DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
