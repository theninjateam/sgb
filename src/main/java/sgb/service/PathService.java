package main.java.sgb.service;

import java.io.File;

public class PathService {

    private String sgbRootPath;

    public PathService(String sgbRootPath) {
        this.sgbRootPath = System.getenv(sgbRootPath);
    }


    public String getSgbRootPath() {
        return sgbRootPath;
    }

    public String getFullPath(String relativePath){

        return sgbRootPath+ File.separator +relativePath;
    }
}