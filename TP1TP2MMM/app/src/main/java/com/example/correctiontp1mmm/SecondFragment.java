package com.example.correctiontp1mmm;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.correctiontp1mmm.data.Client;
import com.example.correctiontp1mmm.databinding.FragmentSecondBinding;
import com.example.correctiontp1mmm.viewmodel.ClientListViewModel;
import com.example.correctiontp1mmm.viewmodel.ClientViewModel;

public class SecondFragment extends Fragment {

    FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate ( inflater ,
                R.layout.fragment_second , container , false );

        // create a default client (empty data)
        binding.setViewModel(new ClientViewModel(new Client("","","","")));

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // we get a reference on the view model of a client
                ClientViewModel vm = binding.getViewModel();

                Log.i("CLICK","CLIENT = "+vm.getClient().getFirstName()+" "+ vm.getClient().getLastName());


                // we get a reference on the viewmodel of the ClientList
                ClientListViewModel clvm = new ViewModelProvider(requireActivity()).get(ClientListViewModel.class);

                // we extract the client and give it to the client list
                clvm.insert(vm.getClient());

                // and we move back to the first fragment
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }
}