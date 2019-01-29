package com.workfort.aps.assigmetintent.data.constant;

public interface Const {
    // Invalid
    int INVALID_INTEGER = -1;

    // Default
    String DEFAULT_STRING = "";
    boolean DEFAULT_BOOLEAN = false;
    int DEFAULT_INTEGER = 0;

    // Directory
    String DIRECTORY_ROOT = "/Assignment_Intent/";

    // Prefix
    String PREFIX_IMAGE = "IMG_";

    // Postfix
    String SUFFIX_IMAGE = ".jpg";

    interface RequestCode {
        int PIC_USER_PHOTO = 671;
    }

    interface Key {
        String USER = "user";
    }
}
