package com.project.tim7.constants;

public class NewsletterConstants {
	//common
    public static final long FIND_ALL_NUMBER_OF_ITEMS = 3;
    public static final Integer PAGEABLE_PAGE = 0;
    public static final Integer PAGEABLE_SIZE = 2;
	public static final int EXPECTED_PAGES = 2;
	public static final int EXPECTED_PAGES_NULL = 0;
    
	//repository
	public static final int SUBSCRIBED_REGISTERED_ID = 2;
	public static final int SUBSCRIBED_REGISTERED_ID_NULL = 3;
	public static final int DB_NEWSLETTERS = 3;
	public static final int CULTURAL_OFFER_ID = 1;
	public static final int CULTURAL_OFFER_ID_NULL = 2;
	public static final int DB_NEWSLETTERS_NULL = 0;
	
	//service
	public static final int NEWSLETTER_ID = 1;
	public static final int NEWSLETTER_ID_NOT_EXIST = 888;
	
	//creating newsletter data
	public static final int NEWSLETTER_ID_CREATE = 0;
	public static final String NEWSLETTER_NAME_CREATE = "new newsletter";
	public static final String NEWSLETTER_DESCRIPTION_CREATE = "Nulla ac enim.";
	public static final String NEWSLETTER_PUBLISHED_DATE_CREATE = "2020-03-01";
	public static final int PICTURE_ID_CREATE = 1;
	public static final String PICTURE_PICTURE_CREATE = "http://dummyimage.com/129x140.bmp";
	/*public static final int CATEGORY_ID_CREATE = 1;
	public static final int SUBCATEGORY_ID_CREATE = 1;
	public static final String CATEGORY_NAME_CREATE = "Category1";
	public static final String SUBCATEGORY_NAME_CREATE = "Subcategory1";
	public static final int LOCATION_ID_CREATE = 1;
	public static final String LOCATION_NAME_CREATE = "Novi Sad";
	public static final double LOCATION_LATITUDE_CREATE = 45.25167;
	public static final double LOCATION_LONGITUDE_CREATE = 19.83694;*/
	public static final int OFFER_ID_CREATE = 1;
	public static final String OFFER_NAME_CREATE = "Exit";
	public static final String OFFER_DESCRIPTION_CREATE = "Proslava 20. godišnjice EXIT festivala biće održana u njegovom poznatom punom formatu od 8. do 11. jula 2021. na Petrovaradinskoj tvrđavi.";
	public static final String OFFER_STARTDATE_CREATE = "2020-07-08";
	public static final String OFFER_ENDDATE_CREATE = "2020-07-11";
	public static final int NEWSLETTER_CREATED_EXPECTED_ID = 4;

	public static final int NEWSLETTER_ID_UPDATE = 1;
	public static final String NEWSLETTER_NAME_UPDATE = "new newsletter";
	public static final String NEWSLETTER_OLD_NAME = "Exit";
	public static final int NEWSLETTER_ID_DELETE = 2;
	public static final String DELETED_NAME = "Exit1";
	
	public static final int OFFER_ID_NOT_EXIST = 888;


}
