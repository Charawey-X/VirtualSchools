package org.example.interfaces;


import org.example.models.Users;

import java.util.List;

public interface IUser {
    Users login(String email);
    boolean register(Users user);
    Users getUser(int id);

    List<Users> getAllUsers();
    boolean updateUser(Users user);
    Users deleteUser(int id);
}
