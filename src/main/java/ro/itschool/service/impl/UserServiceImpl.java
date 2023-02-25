package ro.itschool.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ro.itschool.entity.MyUser;
import ro.itschool.entity.Role;
import ro.itschool.repository.UserRepository;
import ro.itschool.service.UserService;
import ro.itschool.service.email.EmailBodyService;
import ro.itschool.service.email.EmailSender;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    final private UserRepository userRepository;
    final private EmailBodyService emailBodyService;
    final private EmailSender emailSender;

    public MyUser findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public MyUser findUserByUserName(String userName) {
        return userRepository.findByUsernameIgnoreCase(userName);
    }

    public MyUser findUserByRandomToken(String randomToken) {
        return userRepository.findByRandomToken(randomToken);
    }

    public boolean findUserByUserNameAndPassword(String userName, String password) {
        final Optional<MyUser> myUser = Optional.ofNullable(userRepository.findByUsernameIgnoreCase(userName));
        return myUser.filter(user -> BCrypt.checkpw(password, user.getPassword())).isPresent();
    }

    public List<MyUser> findAll() {
        return userRepository.findAll();
    }

    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    public MyUser saveUser(MyUser receivedUser) {
        MyUser myUser = new MyUser(receivedUser);
        myUser.setPassword(new BCryptPasswordEncoder().encode(receivedUser.getPassword()));
        myUser.setRandomToken(UUID.randomUUID().toString());
        emailSender.sendEmail(myUser.getEmail(), "Activate your Account", emailBodyService.emailBody(myUser));
        return userRepository.save(myUser);
    }

    public MyUser updateUser(MyUser receivedUser) {
        return userRepository.save(receivedUser);
    }

    @Override
    public Optional<MyUser> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<MyUser> searchUser(String keyword) {
        return userRepository.searchUser(Objects.requireNonNullElse(keyword, ""));
    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (Role role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new ArrayList<>(roles);
    }

}
