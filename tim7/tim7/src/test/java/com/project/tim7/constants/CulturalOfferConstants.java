package com.project.tim7.constants;

public class CulturalOfferConstants {

    //REPOSITORY

    //FilterByCategory
    public static final String FILTER_CATEGORY_VALUE_SUCCESS = "Category1";
    public static final int FILTER_CATEGORY_VALUE_SUCCESS_RESULT = 2;
    public static final String FILTER_CATEGORY_VALUE_SUCCESS_ZERO = "Random";

    //FilterBySubcategory
    public static final String FILTER_SUBCATEGORY_VALUE_SUCCESS = "Subcategory1";
    public static final int FILTER_SUBCATEGORY_VALUE_SUCCESS_RESULT = 2;
    public static final String FILTER_SUBCATEGORY_VALUE_SUCCESS_ZERO = "Random";

    //FilterByLocation
    public static final String FILTER_LOCATION_VALUE_SUCCESS = "Novi Sad";
    public static final int FILTER_LOCATION_VALUE_SUCCESS_RESULT = 2;
    public static final String FILTER_LOCATION_VALUE_SUCCESS_ZERO = "Random";

    //FilterByName
    public static final String FILTER_NAME_VALUE_SUCCESS = "Exit";
    public static final int FILTER_NAME_VALUE_SUCCESS_RESULT = 2;
    public static final String FILTER_NAME_VALUE_SUCCESS1 = "Exit1";
    public static final int FILTER_NAME_VALUE_SUCCESS_RESULT1 = 1;
    public static final String FILTER_NAME_VALUE_SUCCESS_ZERO = "Random";

    //FilterByAll
    public static final String FILTER_ALL_VALUE_SUCCESS_ZERO = "RandomFilter";

    //SERVICE

    //FindAll()
    public static final int FIND_ALL_CULTURAL_OFFERS_COUNT = 2;

    //FindAll(Pageable)
    public static final int PAGE_SIZE = 1;
    public static final int FIND_ALL_CULTURAL_OFFERS_COUNT_PAGED = 1;

    //FindOne()
    public static final int FIND_ONE_CULTURAL_OFFER_EXISTS = 1;
    public static final int FIND_ONE_CULTURAL_OFFER_NOT_EXISTS = 4;

    //SaveOne()
    /**
     * {
     *     "description": "somethingh",
     *     "endDate" : "2019-02-04",
     *     "name" : "nothingaaa",
     *     "startDate" : "2019-02-03",
     *     "location" : 1,
     *     "subcategory" : 1,
     *     "pictures" : ["http://dummyimage.com/162x198.jpg", "http://dummyimage.com/127x178.png" , "DAMIIMAGDZ"]
     * }
     */
    public static final String SAVE_ONE_CULTURAL_OFFER_DESCRIPTION = "This is a description";
    public static final String SAVE_ONE_CULTURAL_OFFER_ENDDATE = "2019-02-04";
    public static final String SAVE_ONE_CULTURAL_OFFER_NAME = "OfferName";
    public static final String SAVE_ONE_CULTURAL_OFFER_STARTDATE = "2019-02-03";
    public static final int SAVE_ONE_CULTURAL_OFFER_LOCATION = 1;
    public static final int SAVE_ONE_CULTURAL_OFFER_SUBCATEGORY = 1;
    public static final String SAVE_ONE_CULTURAL_OFFER_PICTURE1 = "http://dummyimage.com/162x198.jpg";
    public static final String SAVE_ONE_CULTURAL_OFFER_PICTURE2 = "http://dummyimage.com/127x178.png";


}
