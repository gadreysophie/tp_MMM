package com.example.correctiontp1mmm.data;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.correctiontp1mmm.ui.ClientListAdapter;

import java.util.Arrays;
import java.util.List;

public class FirebaseRepository implements IRepository {

    public static final String ANONYMOUS = "anonymous";
    private String mUsername = ANONYMOUS;
    public static final int RC_SIGN_IN = 1;

    // STEP 1 : make a reference to the database...
    private FirebaseDatabase mFireDataBase;
    private DatabaseReference mContactsDatabaseReference;

    //STEP 4: child event lister.
    private ChildEventListener mChildEventListener;

    ClientListAdapter clientListAdapter;

    //STEP 7: Auth
    private FirebaseAuth mFireBaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStatListener;

    Activity activity;

    public FirebaseRepository(Application application) {
    }

    public FirebaseRepository(Activity activity, ClientListAdapter clientListAdapter){
        this.clientListAdapter = clientListAdapter;
        this.activity = activity;

        mFireDataBase = FirebaseDatabase.getInstance();

        mContactsDatabaseReference = mFireDataBase.getReference().child("clients");

        // STEP 7: Adding authentication
        mFireBaseAuth = FirebaseAuth.getInstance();
        activateAutentication();
    }

    // STEP 7: Activate Autentication
    public void activateAutentication() {
        mAuthStatListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // signed in
                    onSignedIn(user.getDisplayName());
                } else {
                    // not signed int
                    onSignedOut();

                    List<AuthUI.IdpConfig> providers = Arrays.asList(
                            new AuthUI.IdpConfig.EmailBuilder().build(),
                            new AuthUI.IdpConfig.GoogleBuilder().build());


                    startActivityForResult(AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(providers)
                                    .build(),
                            RC_SIGN_IN);

                }
            }
        };
        mFireBaseAuth.addAuthStateListener(mAuthStatListener);
    }
    void onSignedIn(String name) {
        mUsername = name;
        enableUpdatesFromDB();
    }

    void onSignedOut() {
        mUsername = ANONYMOUS;
        contacts.clear();
        if (mChildEventListener != null) {
            mContactsDatabaseReference.removeEventListener(mChildEventListener) ;
        }
    }

    // STEP 4: listen to any change on the DB
    public void enableUpdatesFromDB() {
        if (mUsername != null) {
            Log.i("enableUpdatesFromDB", "username ok");
            if (!mUsername.equals(ANONYMOUS)) {
                Log.i("enableUpdatesFromDB", "username non anonymous");

                if (mChildEventListener == null) {
                    mChildEventListener = new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Contact contact = dataSnapshot.getValue(Contact.class);
                            // don't forget to set the key to identify the Contact!
                            contact.setUid(dataSnapshot.getKey());
                            contacts.add(contact);
                            mAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                            Contact contact = dataSnapshot.getValue(Contact.class);
                            contact.setUid(dataSnapshot.getKey());
                            mAdapter.updateContact(contact);
                            mAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                            //Contact msg = dataSnapshot.getValue(Contact.class);
                            // don't forget to set the key to identify the Contact!
                            mAdapter.removeContactWithId(dataSnapshot.getKey());
                            mAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    };
                    mContactsDatabaseReference.addChildEventListener(mChildEventListener);
                }
            }
        } else
            Log.i("enableUpdatesFromDB", "no username ok");

    }




    @Override
    public void insertClient(Client client) {
        mContactsDatabaseReference.push().setValue(client);
    }

    @Override
    public void deleteClient(Client client) {
        mContactsDatabaseReference.child(client.getUid()).removeValue();
    }

    @Override
    public void deleteAllClients() {

    }

    @Override
    public LiveData<List<Client>> getAllClients() {
        return new MutableLiveData<>();
    }
}
