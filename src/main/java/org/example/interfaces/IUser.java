package org.example.interfaces;


import org.example.models.Users;

public interface IUser {
    Users login(String email);
    boolean register(Users user);
    Users getUser(int id);
    Users updateUser(Users user);
    Users deleteUser(int id);
}
