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
    
    //Subscribe
    public static final long CHECK_IF_SUBSCRIBE_EXISTS = 0;
    public static final int CHECK_IF_SUBSCRIBE_EXISTS_CULTURAL_OFFER = 1;
    public static final int CHECK_IF_SUBSCRIBE_EXISTS_REGISTERED_SUCCESS = 2;
    public static final int CHECK_IF_SUBSCRIBE_EXISTS_REGISERED_FAIL = 4;

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
    public static final String SAVE_ONE_CULTURAL_OFFER_DESCRIPTION = "This is a description";
    public static final String SAVE_ONE_CULTURAL_OFFER_ENDDATE = "2019-02-04";
    public static final String SAVE_ONE_CULTURAL_OFFER_NAME = "OfferName";
    public static final String SAVE_ONE_CULTURAL_OFFER_NAME_FAIL = "Exit";
    public static final String SAVE_ONE_CULTURAL_OFFER_STARTDATE = "2019-02-03";
    public static final int SAVE_ONE_CULTURAL_OFFER_LOCATION = 1;
    public static final int SAVE_ONE_CULTURAL_OFFER_LOCATION_FAIL = 7;
    public static final int SAVE_ONE_CULTURAL_OFFER_SUBCATEGORY = 1;
    public static final int SAVE_ONE_CULTURAL_OFFER_SUBCATEGORY_FAIL = 6;
    public static final String SAVE_ONE_CULTURAL_OFFER_PICTURE1 = "http://dummyimage.com/162x198.jpg";
    public static final String SAVE_ONE_CULTURAL_OFFER_PICTURE2 = "http://dummyimage.com/127x178.png";

    //update()
    public static final int UPDATE_ONE_CULTURAL_OFFER_ID = 1;
    public static final int UPDATE_ONE_CULTURAL_OFFER_ID_FAIL = 5;
    public static final String UPDATE_ONE_CULTURAL_OFFER_DESCRIPTION = "This is a description";
    public static final String UPDATE_ONE_CULTURAL_OFFER_ENDDATE = "2019-02-04";
    public static final String UPDATE_ONE_CULTURAL_OFFER_NAME = "OfferName1000";
    public static final String UPDATE_ONE_CULTURAL_OFFER_NAME_FAIL = "Exit1";
    public static final String UPDATE_ONE_CULTURAL_OFFER_STARTDATE = "2019-02-03";
    public static final int UPDATE_ONE_CULTURAL_OFFER_LOCATION = 1;
    public static final int UPDATE_ONE_CULTURAL_OFFER_SUBCATEGORY = 1;
    public static final String UPDATE_ONE_CULTURAL_OFFER_PICTURE1 = "http://dummyimage.com/162x198.jpg";
    public static final String UPDATE_ONE_CULTURAL_OFFER_PICTURE2 = "http://dummyimage.com/127x178.png";
    public static final int UPDATE_PICTURE_SIZE = 2;

    //delete()
    public static final int DELETE_CULTURAL_OFFER_ID = 1;
    public static final int DELETE_CULTURAL_OFFER_ID_FAIL = 5;
    public static final int DELETE_EXPECTED_SIZE = 1;

    //getCulturalOfferReferencingCount()
    public static final int REFERENCE_COUNT_ID_VALID = 1;
    public static final int REFERENCE_COUNT_VALID = 2;
    public static final int REFERENCE_COUNT_ID_VALID_ZERO = 2;
    public static final int REFERENCE_COUNT_ID_INVALID = 2;

    //filter()
    public static final String FILTER_CATEGORY = "category";
    public static final String FILTER_SUBCATEGORY = "subcategory";
    public static final String FILTER_LOCATION = "location";
    public static final String FILTER_NAME = "name";
    public static final String FILTER_EMPTY = "";
    public static final String FILTER_INVALID = "description";

    public static final String FILTER_CATEGORY_VALUE_FOUND = "Category1";
    public static final int FILTER_CATEGORY_VALUE_FOUND_NUMBER = 2;
    public static final String FILTER_SUBCATEGORY_VALUE_FOUND  = "Subcategory1";
    public static final int  FILTER_SUBCATEGORY_VALUE_FOUND_NUMBER = 2;
    public static final String FILTER_LOCATION_VALUE_FOUND  = "Novi Sad";
    public static final int  FILTER_LOCATION_VALUE_FOUND_NUMBER  = 2;
    public static final String FILTER_NAME_VALUE_FOUND  = "Exit";
    public static final int FILTER_NAME_VALUE_FOUND_NUMBER = 2;
    public static final String FILTER_EMPTY_FOUND  = "Exit1";
    public static final int FILTER_EMPTY_FOUND_NUMBER = 2;
    public static final String FILTER_INVALID_VALUE_FOUND  = "Exit1";
    public static final int FILTER_INVALID_VALUE_FOUND_NUMBER = 1;


    public static final String FILTER_CATEGORY_VALUE_NOT_FOUND  = "Category5";
    public static final String FILTER_LOCATION_VALUE_NOT_FOUND = "Beograd";
    public static final String FILTER_NAME_VALUE_NOT_FOUND = "Exit3";
    public static final String FILTER_INVALID_VALUE_EMPTY = "";
    public static final int FILTER_EMPTY_VALUE_FOUND = 1;
    
    //subscribe()
    public static final int CULTURAL_OFFER_ID = 1;
    public static final String CULTURAL_OFFER_NAME = "Exit";
    public static final int REGISTERED_ID_NOT_SUBSCRIBED = 6;
    public static final int REGISTERED_ID_ALREADY_SUBSCRIBED = 3;
    public static final String REGISTERED_ID_NOT_SUBSCRIBED_NAME = "vuleR";
    public static final String REGISTERED_ID_ALREADY_SUBSCRIBED_NAME = "nijeMico";

    //CONTROLLER
    public static final int OLD_CULTURAL_OFFER_ID = 1;
    public static final String OLD_CULTURAL_OFFER_DESCRIPTION = "Proslava 20. godišnjice EXIT festivala biće održana u njegovom poznatom punom formatu od 8. do 11. jula 2021. na Petrovaradinskoj tvrđavi.";
    public static final String OLD_CULTURAL_OFFER_ENDDATE = "2020-07-08";
    public static final String OLD_CULTURAL_OFFER_NAME = "Exit";
    public static final String OLD_CULTURAL_OFFER_STARTDATE = "2020-07-11";
    public static final int OLD_CULTURAL_OFFER_LOCATION = 1;
    public static final int OLD_CULTURAL_OFFER_SUBCATEGORY = 1;
    public static final int EXPECTED_OFFERS_BY_SUBCATEGORY = 2;




}
