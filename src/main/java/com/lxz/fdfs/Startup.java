package com.lxz.fdfs;

import java.util.Set;

import org.mockito.internal.util.collections.Sets;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.lxz.fdfs.api.FileController;

/**
 * Created by xiaolezheng on 16/5/19.
 */
@SpringBootApplication
public class Startup {
    public static void main(String[] args){
        SpringApplication app = new SpringApplication(Startup.class);
        app.setWebEnvironment(true);

        Set<Object> set = Sets.newSet();
        set.add(FileController.class);
        app.setSources(set);
        app.run(args);
    }
}
