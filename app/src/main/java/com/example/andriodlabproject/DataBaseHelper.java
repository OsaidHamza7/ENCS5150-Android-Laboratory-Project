package com.example.andriodlabproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Blob;

public class DataBaseHelper extends android.database.sqlite.SQLiteOpenHelper {

    private static final String dbName = "CarRentalDB";
    private static final int dbVersion = 1;

    private static final String CREATE_USER_TABLE = "CREATE TABLE User (" +
            "FirstName TEXT," +
            "LastName TEXT," +
            "Gender TEXT," +
            "Email TEXT PRIMARY KEY," +
            "Password TEXT," +
            "Country TEXT," +
            "City TEXT," +
            "PhoneNumber TEXT," +
            "Permission TEXT," +
            "ProfilePicture BLOB);";

    private static final String CREATE_CAR_TABLE = "CREATE TABLE Car (" +
            "CarID INTEGER PRIMARY KEY," +
            "FactoryName TEXT," +
            "Type TEXT," +
            "Price REAL," +
            "FuelType TEXT," +
            "TransmissionType TEXT," +
            "Mileage REAL," +
            "Image INTEGER);";

    private static final String CREATE_RESERVATION_TABLE = "CREATE TABLE Reservation (" +
            "ReservationID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "UserID INTEGER," +
            "CarID INTEGER," +
            "ReservationDate TEXT," +
            "ReservationTime TEXT," +
            "FOREIGN KEY(UserID) REFERENCES User(UserID)," +
            "FOREIGN KEY(CarID) REFERENCES Car(CarID));";

    private static final String CREATE_FAVORITES_TABLE = "CREATE TABLE Favorites (" +
            "FavoriteID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "UserID INTEGER," +
            "CarID INTEGER," +
            "FOREIGN KEY(UserID) REFERENCES User(UserID)," +
            "FOREIGN KEY(CarID) REFERENCES Car(CarID));";

    private static final String CREATE_SPECIAL_OFFERS_TABLE = "CREATE TABLE SpecialOffers (" +
            "OfferID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "CarID INTEGER," +
            "StartDate TEXT," +
            "EndDate TEXT," +
            "FOREIGN KEY(CarID) REFERENCES Car(CarID));";



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_CAR_TABLE);
        db.execSQL(CREATE_RESERVATION_TABLE);
        db.execSQL(CREATE_FAVORITES_TABLE);
        //db.execSQL(CREATE_SPECIAL_OFFERS_TABLE);

        // add a static admin user using insertInto query
        db.execSQL("INSERT INTO User (FirstName, LastName, Gender, Email, Password, Country, City, PhoneNumber, Permission) VALUES ('Admin','Admin','Male','admin@gmail.com','"+User.hashPassword("admin@123")+ "','Palestine','Tulkarm','0599999999','Admin')");

    }

    // create a new user account in the database assuming that there is User class
    public boolean insertUser(User user){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FirstName",user.getFirstName());
        contentValues.put("LastName",user.getLastName());
        contentValues.put("Gender", user.getGender());
        contentValues.put("Email", user.getEmail());
        contentValues.put("Password", user.getPassword());
        contentValues.put("Country", user.getCountry());
        contentValues.put("City", user.getCity());
        contentValues.put("PhoneNumber", user.getPhoneNumber());
        contentValues.put("Permission", user.getPermission());
        contentValues.put("ProfilePicture", user.getProfilePicture());
        if (sqLiteDatabase.insert("User",null,contentValues) == -1)
            return false;
        else
            return true;

    }

    // get all users from the database
    public Cursor getAllUsers(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM User",null);
    }

    // get user by email
    public Cursor getUserByEmail(String email){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM User WHERE Email = '"+email+"'",null);
    }

    // Update user info (First name, last name, and phone number)
    public void updateUserInfo(User user){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FirstName",user.getFirstName());
        contentValues.put("LastName",user.getLastName());
        contentValues.put("PhoneNumber", user.getPhoneNumber());
        sqLiteDatabase.update("User",contentValues,"Email = '"+user.getEmail()+"'",null);
    }

    // Update the user Password
    public void updateUserPassword(String email, String password){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Password",password);

        sqLiteDatabase.update("User",contentValues,"Email = '"+email+"'",null);

    }

    // update the user profile image
    public void updateUserProfilePicture(String email, byte[] profilePicture){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ProfilePicture",profilePicture);
        sqLiteDatabase.update("User",contentValues,"Email = '"+email+"'",null);
    }

    // delete user by email
    public void deleteUserByEmail(String email){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete("User","Email = '"+email+"'",null);
    }

    // add a new car to the database
    public boolean insertCar(Car car){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("CarID",car.getID());
        contentValues.put("FactoryName",car.getFactoryName());
        contentValues.put("Type",car.getType());
        contentValues.put("Price", car.getPrice());
        contentValues.put("FuelType", car.getFuelType());
        contentValues.put("TransmissionType", car.getTransmission());
        contentValues.put("Mileage", car.getMileage());
        contentValues.put("Image", car.getImgCar());
        if (sqLiteDatabase.insert("Car",null,contentValues) == -1)
            return false;
        else
            return true;

    }

    // get all cars from the database
    public Cursor getAllCars(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM Car",null);
    }

    // add a new reservation to the database
    public boolean insertReservation(Reservation reservation){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("UserID",reservation.getUserID());
        contentValues.put("CarID",reservation.getCarID());
        contentValues.put("ReservationDate", reservation.getReservationDate());
        contentValues.put("ReservationTime", reservation.getReservationTime());
        if (sqLiteDatabase.insert("Reservation",null,contentValues) == -1)
            return false;
        else
            return true;

    }

    // get all reservations from the database
    public Cursor getAllReservations(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM Reservation",null);
    }

    // get reservations by user email
    public Cursor getReservationsByEmail(String email){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM Reservation WHERE Email = '"+email+"'",null);
    }

    // add a new favorite to the database
    public boolean insertFavorite(int userID, int carID){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("UserID", userID);
        contentValues.put("CarID", carID);
        if (sqLiteDatabase.insert("Favorites",null,contentValues) == -1)
            return false;
        else
            return true;

    }

    // get favorites by user email
    public Cursor getFavoritesByEmail(String email){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM Favorites WHERE Email = '"+email+"'",null);
    }







    public DataBaseHelper(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }



}
