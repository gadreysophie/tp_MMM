package com.example.correctiontp1mmm.data;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "client_table")
public class Client {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @ColumnInfo(name = "last_name")
    private String lastName;

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "date_name")
    private String date;

    @ColumnInfo(name = "city_name")
    private String city;

    public Client(String lastName, String firstName, String date, String city) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.date = date;
        this.city = city;
    }


}
