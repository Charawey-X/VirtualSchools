package org.example.interfaces;


import org.example.models.Users;

public interface IUser {
    Users login(String email, String password);
    boolean register(Users user);
    Users getUser(int id);
    Users updateUser(Users user);
    Users deleteUser(int id);
}
