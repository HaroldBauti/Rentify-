package com.univerisity.DataAccess.api;

import com.cloudinary.Cloudinary;

import java.util.HashMap;
import java.util.Map;

public class CloudinaryClient {
    private static Cloudinary cloudinary;

    public static Cloudinary getInstance() {
        if (cloudinary == null) {
            Map<String, String> config = new HashMap<>();
            config.put("cloud_name", "dfahcjklj");
            config.put("api_key", "212435184375591");
            config.put("api_secret", "_Ypzvk_EYVY6Alut8vQNmZFAcfA");
            cloudinary = new Cloudinary(config);
        }
        return cloudinary;
    }
}
