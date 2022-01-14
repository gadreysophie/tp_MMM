package com.example.correctiontp1mmm.data;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
import com.example.correctiontp1mmm.R;

import java.util.ArrayList;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder>
        implements Filterable {
    private Context context;
    List<Client> contactList;
    List<Client> contactListFiltered;
    private ContactsAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, lastname;
        public ImageView thumbnail;
        public TextView city;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.firstName);
            lastname = view.findViewById(R.id.lastName);
            //thumbnail = view.findViewById(R.id.city);
            city = view.findViewById(R.id.city);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(contactList.get(getAdapterPosition()));
                }
            });

        }
    }


    public ContactsAdapter(Context context, List<Client> contactList, ContactsAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.client_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, final int position) {
        final Client contact = contactListFiltered.get(position);

            if (contact.getFirstName() != null) {
                viewHolder.name.setText(contact.getFirstName());
                viewHolder.lastname.setVisibility(TextView.VISIBLE);
                viewHolder.thumbnail.setVisibility(ImageView.GONE);
            }
            /*
            if (contact.getImage() != null) {
                String imageUrl = contact.getImage();
                Log.i("TAG","image="+imageUrl);
                Glide.with(viewHolder.thumbnail.getContext())
                        .load(contact.getImage())
                        .apply(RequestOptions.circleCropTransform())
                        .into(viewHolder.thumbnail);

                viewHolder.thumbnail.setVisibility(ImageView.VISIBLE);
                viewHolder.lastname.setVisibility(TextView.GONE);
            }

             */

            if (contact.getLastName() != null) {
                viewHolder.lastname.setText(contact.getLastName());
            }
            viewHolder.city.setText(""+contact.getCity());
    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    public void removeContactWithId(String uid) {

        contactListFiltered.removeIf(contact ->  (contact.getUid().equals(uid
        )) );
        contactList.removeIf(contact ->  (contact.getUid().equals(uid) ));
    }

    public void updateContact(Client updatedContact) {

        Client oldContact = contactListFiltered.stream()
                .filter(c -> (updatedContact.getUid().equals(c.getUid())))
                .findFirst()
                .orElse(null);
        if (oldContact != null) {
            contactListFiltered.set(contactListFiltered.indexOf(oldContact), updatedContact);
            Log.i("TAG","updated likes from DB for "+updatedContact.getFirstName()+" = "+ updatedContact.getCity());
        }

        oldContact = contactList.stream()
                .filter(c -> (updatedContact.getUid().equals(c.getUid())))
                .findFirst()
                .orElse(null);
        if (oldContact != null)
            contactList.set(contactList.indexOf(oldContact),updatedContact);

    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = contactList;
                } else {
                    List<Client> filteredList = new ArrayList<>();
                    for (Client row : contactList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getFirstName().toLowerCase().contains(charString.toLowerCase()) || row.getLastName().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<Client>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactsAdapterListener {
        void onContactSelected(Client contact);
    }
}