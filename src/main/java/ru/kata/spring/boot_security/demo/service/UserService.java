package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Service
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    // в коде явно не вызывается
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (username == null) {
            System.out.println("public UserDetails loadUserByUsername(String username) = UsernameNotFoundException(\"User not found\")");
            throw new UsernameNotFoundException("User not found");
        }

        return user;

        /* // Ручное получение разрешений, которые у него есть в соответствии с идентификатором пользователя
        // и добавление его URL-адреса в разрешения пользователя

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        System.out.println("работает UserService, user.getRoles() = " + user.getRoles());
        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority((role.getRole())));
        }

        System.out.println("работает UserService, grantedAuthorities = " + grantedAuthorities);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities); */
    }

    public User getUserById(Long id) {
        Optional<User> userFromDB = userRepository.findById(id);
        return userFromDB.orElse(new User());
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public boolean saveUser(User user, String roleUser, String roleAdmin) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return false;
        }
        // insert into ROLE (role_id, role_name) values (1, "ROLE_USER");
        // insert into ROLE (role_id, role_name) values (2, "ROLE_ADMIN");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> userRoles = new HashSet<>();
        if (roleUser != null) {
            userRoles.add(roleRepository.getById(1L));
        }
        if (roleAdmin != null) {
            userRoles.add(roleRepository.getById(2L));
        }
        user.setRoles(userRoles);
        userRepository.save(user);
        return true;
    }

    @Transactional
    public boolean updateUser(User user, String roleUser, String roleAdmin) {
        Set<Role> userRoles = new HashSet<>();
        if (roleUser != null) {
            userRoles.add(roleRepository.getById(1L));
        }
        if (roleAdmin != null) {
            userRoles.add(roleRepository.getById(2L));
        }
        user.setRoles(userRoles);
        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<User> usergtList(Long id) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.id > :paramId", User.class)
                .setParameter("paramId", id).getResultList();
    }

}
