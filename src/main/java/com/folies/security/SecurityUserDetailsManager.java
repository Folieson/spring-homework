package com.folies.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SecurityUserDetailsManager implements UserDetailsManager {
    @Override
    public void createUser(UserDetails user) {

    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //тут должна быть реализована логика по вычитке пользователя из хранилища данных
        if (username == null || !username.equals("user")) {
            return null;
        }

        List<SecurityPermission> permissions = new ArrayList<>();

        permissions.add(new SecurityPermission("book.create"));
        permissions.add(new SecurityPermission("book.read"));
        permissions.add(new SecurityPermission("book.update"));
        permissions.add(new SecurityPermission("book.delete"));

        permissions.add(new SecurityPermission("writer.create"));
        permissions.add(new SecurityPermission("writer.read"));
        permissions.add(new SecurityPermission("writer.update"));
        permissions.add(new SecurityPermission("writer.delete"));

        return new SecurityUser(username, "{noop}123", permissions);
    }
}
