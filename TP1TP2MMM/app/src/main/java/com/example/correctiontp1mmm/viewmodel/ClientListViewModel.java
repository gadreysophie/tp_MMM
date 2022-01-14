package com.example.correctiontp1mmm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.correctiontp1mmm.data.BasicRepository;
import com.example.correctiontp1mmm.data.Client;
import com.example.correctiontp1mmm.data.ClientList;
import com.example.correctiontp1mmm.data.IRepository;
import com.example.correctiontp1mmm.data.RoomRepository;

import java.util.List;


// This is the VIEWMODEL which interfaces with the list of clients (our MODEL)
// This list of clients is transformed into a LiveData (which enables a notification on the VIEW each time a chang occurs)
public class ClientListViewModel extends AndroidViewModel {
    private LiveData<List<Client>> allClients = new MutableLiveData<>();


    private IRepository repository;
    private static boolean intialized = false;


    public boolean isIntialized() {
        return intialized;
    }

    public void setIntialized(boolean intialized) {
        this.intialized = intialized;
    }


    public ClientListViewModel(@NonNull Application application) {
        super(application);

        if (!isIntialized()) {

            ClientList l = new ClientList();
            l.add(new Client("Bilal","Enki","02/02/2021","Paris"));
            l.add(new Client("Bajram","Denis","02/02/2021","Angouleme"));
            repository = new BasicRepository(l);

            // or use ROOM
            //repository = new RoomRepository(application);
        }

        allClients = repository.getAllClients();
    }

    public void setRepository(IRepository r) {
        repository = r;
        allClients = repository.getAllClients();

    }

    public void insert(Client client) {
        repository.insertClient(client);
    }

    public void delete(Client client) {
        repository.deleteClient(client);
    }

    public LiveData<List<Client>> getAllUsers() {
        return allClients;
    }
}
