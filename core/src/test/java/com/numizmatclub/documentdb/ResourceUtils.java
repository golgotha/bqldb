package com.numizmatclub.documentdb;

import java.io.File;
import java.net.URL;

/**
 * @author Valerii Kantor
 */
public final class ResourceUtils {

    public static String getResourceAbsolutePath(String name) {
        URL url = ResourceUtils.class.getClassLoader().getResource(name);
        File file = new File(url.getFile());
        String absolutePath = file.getAbsolutePath();
        int index = absolutePath.lastIndexOf("/");
        return absolutePath.substring(0, index);
    }
}
