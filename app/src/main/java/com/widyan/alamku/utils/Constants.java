package com.widyan.alamku.utils;

/**
 * Created by widyan on 3/15/2017.
 */

public class Constants {
    public static class Apps{
        public static final String BASE_URL = "http://entry.sandbox.gits.id/api/alamku/";
        public static final String URL_IMAGE = BASE_URL + "uploads/images/";
        public static final String BANNER = BASE_URL + "index.php/api/get/filter/dataalam";
        public static final String LIST_TEMPAT_WISATA = BASE_URL + "index.php/api/get/dataalam";
        public static final String LOGIN = BASE_URL + "index.php/api/post/user/login";
        //public static final String LOGIN = "index.php/api/post/user/login";
        public static final String REGISTER = BASE_URL + "index.php/api/post/user/account";
        public static final String ADD_TEMPAT_WISATA = BASE_URL + "index.php/api/post/data/upload";
        public static final String FILTER_TEMPAT_WISATA = BASE_URL + "index.php/api/get/filter/dataalam";
        public static final String GET_DETAIL_DATA = BASE_URL + "index.php/api/get/detail/dataalam";
    }

    public static class Preferences{

    }
}
