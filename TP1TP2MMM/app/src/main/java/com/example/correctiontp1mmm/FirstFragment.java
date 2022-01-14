package com.example.correctiontp1mmm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.correctiontp1mmm.data.Client;
import com.example.correctiontp1mmm.data.ClientList;
import com.example.correctiontp1mmm.data.IRepository;
import com.example.correctiontp1mmm.ui.ClientListAdapter;
import com.example.correctiontp1mmm.viewmodel.ClientListViewModel;

import java.util.List;

public class FirstFragment extends Fragment {

    private ClientListViewModel viewModel;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.myclients);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        // on créée une instance d'adapter
        final ClientListAdapter adapter = new ClientListAdapter();
        recyclerView.setAdapter(adapter);

        // on crée une instance de notre ViewModel

        viewModel = new ViewModelProvider(requireActivity()).get(ClientListViewModel.class);



        // et on ajoute un observer sur les clients...
        viewModel.getAllUsers().observe(getViewLifecycleOwner(), new Observer<List<Client>>() {
            @Override
            public void onChanged(@Nullable List<Client> clients) {
                adapter.setClients(clients);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.delete(adapter.getClientAt(viewHolder.getAdapterPosition()));
                // notification...
            }
        }).attachToRecyclerView(recyclerView);


        view.findViewById(R.id.addClient).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }
}