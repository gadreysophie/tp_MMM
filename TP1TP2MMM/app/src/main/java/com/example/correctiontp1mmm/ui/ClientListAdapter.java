package com.example.correctiontp1mmm.ui;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.correctiontp1mmm.R;
import com.example.correctiontp1mmm.data.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientListAdapter extends RecyclerView.Adapter<ClientListAdapter.ClientHolder> {
    private List<Client> clients = new ArrayList<>();
    public List<Client> getClients () {return clients;}

    public void updateContact (Client client){
        Client c = clients.stream()
                .filter(customer -> client.getUid().equals(customer.getUid()))
                .findAny().
                orElse(null);
        int index = clients.indexOf(c);
        clients.set(index, client);
    }


    @NonNull
    @Override
    public ClientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.client_item, parent, false);
        return new ClientHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientHolder holder, int position) {
        Client currentUser = clients.get(position);
        holder.firstName.setText(currentUser.getFirstName());
        holder.lastName.setText(currentUser.getLastName());
        holder.city.setText(currentUser.getCity());
    }

    @Override
    public int getItemCount() {
        return clients.size();
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
        notifyDataSetChanged();
    }

    public Client getClientAt(int adapterPosition) {
        return clients.get(adapterPosition);
    }

    public void removeContactWithId(String key) {
    }

    class ClientHolder extends RecyclerView.ViewHolder {
        private TextView firstName;
        private TextView lastName;
        private TextView city;


        public ClientHolder(View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.firstName);
            lastName = itemView.findViewById(R.id.lastName);
            city = itemView.findViewById(R.id.city);
        }
    }
}