package com.selma.halal.food.project.api.v1;

import com.kumuluz.ee.cors.annotations.CrossOrigin;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/v1")
@CrossOrigin
public class SearchHalalPlaceApplication extends Application{
}
