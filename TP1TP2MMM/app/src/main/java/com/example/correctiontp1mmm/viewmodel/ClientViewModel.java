package com.example.correctiontp1mmm.viewmodel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.correctiontp1mmm.data.Client;

// This extends BaseObservable so it can be directly linked with the UI through data binding
public class ClientViewModel extends BaseObservable {

    private Client client;


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }


    public ClientViewModel(Client client) {
        this.client = client;
    }

    @Bindable
    public String getLastName() {
        return client.getLastName();
    }

    public void setLastName(String lastName) {
        client.setLastName(lastName);
    }


    @Bindable
    public String getFirstName() {
        return client.getFirstName();
    }

    public void setFirstName(String firstName) {
        client.setFirstName(firstName);
    }


    @Bindable
    public String getDate() {
        return client.getDate();
    }

    public void setDate(String date) {
        client.setDate(date);
    }


    @Bindable
    public String getCity() {
        return client.getCity();
    }

    public void setCity(String city) {
        client.setCity(city);
    }
}
