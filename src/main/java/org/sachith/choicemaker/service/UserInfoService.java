package org.sachith.choicemaker.service;

import org.sachith.choicemaker.model.User;
/*import org.sachith.choicemaker.model.UserInfoDetails;*/
import org.sachith.choicemaker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
/*import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;*/
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserInfoService /*implements UserDetailsService*/ {

    @Autowired
    private UserRepository userRepository;

    /*@Autowired
    @Lazy
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUsername(username);
        return user.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    public String addUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User Added Successfully";
    }*/

    public User loadUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
