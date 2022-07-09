package com.example.virtualschools.network;

import com.example.virtualschools.models.Login;
import com.example.virtualschools.models.Student;
import com.example.virtualschools.models.StudentDetails;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Api {
    @POST("users/login") Call<Student> login (@Body Login login);

    @GET("users") Call<StudentDetails> getStudentDetails(@Header("Authorization") String token);
}