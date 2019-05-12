package com.buddman.zepespot.utils;


import com.buddman.zepespot.models.Course;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * Created by wonny on 2017. 1. 6..
 */

public class NetworkManager {
    private static final String MULTIPART_FORM_DATA = "multipart/form-data";

    public static API service = NetworkServiceGenerator.getInstance().createService(API.class);

    public static Observable<ArrayList<Course>> getCourse(int courseNumber) {
        return service.getCourse(courseNumber);
    }
}