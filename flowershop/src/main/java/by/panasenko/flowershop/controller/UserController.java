package by.panasenko.flowershop.controller;

import by.panasenko.flowershop.model.User;
import by.panasenko.flowershop.model.UserInfo;
import by.panasenko.flowershop.model.security.PasswordToken;
import by.panasenko.flowershop.repository.PasswordTokenRepository;
import by.panasenko.flowershop.service.MailSender;
import by.panasenko.flowershop.service.UserInfoService;
import by.panasenko.flowershop.service.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.UUID;

@Controller
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private PasswordTokenRepository passwordTokenRepository;

    @Autowired
    private MailSender mailSender;

    @GetMapping("/login")
    public String login(Model model, @RequestParam(value = "token", required = false) String token){
        if (token != null) {
            PasswordToken passToken = userService.getPasswordToken(token);
            if (passToken == null) {
                String message = "Invalid token";
                model.addAttribute("message", message);
                return "redirect:/badRequest";
            }
            User user = passToken.getUser();
            model.addAttribute("tokenExists", true);
            model.addAttribute("user", user);
        } else {
            model.addAttribute("tokenNotExists", true);
        }
        model.addAttribute("activeLogin", true);
        return "myAccount";
    }

    @GetMapping("/myProfile")
    public String myProfile (Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("activeEdit", true);
        model.addAttribute("userPaymentList", user.getUserInfo().getPayments());
        model.addAttribute("userAddress", user.getUserInfo().getAddress());
        model.addAttribute("orderList", user.getOrders());
        model.addAttribute("listOfCreditCards", true);
        model.addAttribute("listOfAddress", true);
        model.addAttribute("classActiveEdit", true);
        return "myProfile";
    }

    @PostMapping("/forgetPassword")
    public String forgetPassword(Model model, @ModelAttribute("recoverEmail") String email){
        model.addAttribute("activeForgetPassword", true);
        model.addAttribute("tokenNotExists", true);

        User user = userService.findByEmail(email);
        if(user == null) {
            model.addAttribute("emailNotExists", true);
        } else {
            PasswordToken passToken = passwordTokenRepository.findByUser(user);
            String token = UUID.randomUUID().toString();
            passToken.updateToken(token);
            passwordTokenRepository.save(passToken);
            String password = userService.generateRandomPassword();
            user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt(12)));
            userService.saveUser(user);
            mailSender.send(user.getEmail(), "My FlowerShop", MailSender.messageForget(user.getUsername(), token, password));
            model.addAttribute("forgetPasswordEmail", true);
        }
        return "myAccount";
    }

    @PostMapping("/newAccount")
    public String newUserPost(@ModelAttribute("email") String userEmail, @ModelAttribute("username") String username, Model model){
        model.addAttribute("activeNewAccount", true);
        model.addAttribute("tokenNotExists", true);
        model.addAttribute("email", userEmail);
        model.addAttribute("username", username);
        if(userService.findByUsername(username) != null) {
            model.addAttribute("usernameExists", true);
            return "myAccount";
        }
        if(userService.findByEmail(userEmail) != null) {
            model.addAttribute("emailExists", true);
            return "myAccount";
        }
        String password = userService.generateRandomPassword();
        User user = userService.createUser(username, userEmail, password, "USER");
        String token = UUID.randomUUID().toString();
        userService.createPasswordTokenForUser(user, token);
        mailSender.send(user.getEmail(), "My FlowerShop", MailSender.messageCreateUser(user.getUsername(), token, password));
        model.addAttribute("emailSent", true);
        logger.info("Created new account");
        return "myAccount";
    }

    @PostMapping("/updateUserInfo")
    public String updateUserInfo (
            Model model,
            @ModelAttribute("firstName") String firstName,
            @ModelAttribute("lastName") String lastName,
            @ModelAttribute("address") String address,
            @ModelAttribute("newUsername") String newUsername,
            @ModelAttribute("email") String email,
            @ModelAttribute("currentPassword") String currentPassword,
            @ModelAttribute("newPassword") String newPassword,
            @ModelAttribute("confirmPassword") String confirmPassword) {

        User user = userService.findByEmail(email);

        if (!BCrypt.checkpw(currentPassword, user.getPassword())) {
            model.addAttribute("currentPasswordNotEquals" , true);
        } else if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("passwordNotEquals", true);
        }
        else {
            UserInfo userInfo = userInfoService.findByUser(user);
            userInfo.createUserInfo(firstName, lastName, address);
            userInfoService.save(userInfo);
            user.setUsername(newUsername);
            user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt(12)));
            userService.saveUser(user);
            model.addAttribute("updateUserInfo", true);
        }
        model.addAttribute("activeEdit", true);
        model.addAttribute("user", user);
        model.addAttribute("orderList", user.getOrders());
        logger.info("User " + user.getUsername() + " update info");
        return "myProfile";
    }
}
