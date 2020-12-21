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
	public static final int OFFER_ID_CREATE = 1;
	public static final String OFFER_NAME_CREATE = "Exit";
	public static final String OFFER_DESCRIPTION_CREATE = "Proslava 20. godišnjice EXIT festivala biće održana u njegovom poznatom punom formatu od 8. do 11. jula 2021. na Petrovaradinskoj tvrđavi.";
	public static final String OFFER_STARTDATE_CREATE = "2020-07-08";
	public static final String OFFER_ENDDATE_CREATE = "2020-07-11";

	public static final int NEWSLETTER_ID_UPDATE = 1;
	public static final String NEWSLETTER_NAME_UPDATE = "new newsletter";
	public static final String NEWSLETTER_OLD_NAME = "Newsletter1";
	public static final int NEWSLETTER_ID_DELETE = 2;
	public static final String DELETED_NAME = "Newsletter2";
	
	public static final int OFFER_ID_NOT_EXIST = 888;
	
	//JUnit
	public static final int REGISTERED_ID_CREATE = 0;
	public static final String REGISTERED_EMAIL_CREATE = "mico@reg.com";
	public static final String REGISTERED_USERNAME_CREATE = "micoR";
	public static final String REGISTERED_PASSWORD_CREATE = "$2y$12$NFN7DJUX1lFfaDX1tc9/6uBtgls9SZOU9iwjhrlXJc0xr471vgKAK";
	public static final String REGISTERED_NOT_SUBSCRIBED_EMAIL_CREATE = "mico1@reg.com";
	public static final String REGISTERED_NOT_SUBSCRIBED_USERNAME_CREATE = "micoR1";
	
	public static final String NEWSLETTER_NAME_CREATE_1 = "Newsletter1";
	public static final String NEWSLETTER_DESCRIPTION_CREATE_1 = "Nulla ac enim.";
	public static final String NEWSLETTER_PUBLISHED_DATE_CREATE_1 = "2020-03-01";
	
	public static final String NEWSLETTER_NAME_CREATE_2 = "Newsletter2";
	public static final String NEWSLETTER_DESCRIPTION_CREATE_2 = "Pellentesque ultrices mattis odio.";
	public static final String NEWSLETTER_PUBLISHED_DATE_CREATE_2 = "2020-11-19";
	
	public static final int PICTURE_ID_CREATE_NEW = 2;
	public static final String PICTURE_PICTURE_CREATE_NEW = "http://dummyimage.com/100x100.bmp";

	//controller
    public static final String DB_USERNAME_REG = "micoR";
    public static final String DB_USERNAME_ADMIN = "mico";
    public static final String DB_PASSWORD_RAW = "123qweASD";
    
    public static final int CULTURAL_OFFER_NO_NEWSLETTER = 0;
    
    public static final int NEW_ID = 0;
    public static final String NEW_NAME = "new name";
    public static final String NEW_DESCRIPTION_C = "new description";
    public static final int NEW_OFFER = 1;
    public static final String NEW_DATE = "2020-03-01";
    public static final String NEW_PIC = "picture";
    public static final String WRONG_DATE = "2021-03-01";
    public static final String BLANK = "";
    public static final int DELETE_ID = 3;
    public static final String RESTORE_NAME = "Newsletter3";
    public static final String RESTORE_DATE = "2020-01-31";
    public static final int RESTORE_OFFER = 1;
    public static final String RESTORE_DESCRIPTION = "Etiam faucibus cursus urna. Ut tellus.";
    public static final String RESTORE_PICTURE = "http://dummyimage.com/219x161.bmp";
    public static final int UPDATE_ID = 2;
    public static final String RESTORE_NAME_UPDATE = "Newsletter2";
    public static final String RESTORE_DATE_UPDATE = "2020-11-19";
    public static final int RESTORE_OFFER_UPDATE = 1;
    public static final String RESTORE_DESCRIPTION_UPDATE = "Pellentesque ultrices mattis odio.";
    public static final String RESTORE_PICTURE_UPDATE = "http://dummyimage.com/129x140.bmp";



	


}
