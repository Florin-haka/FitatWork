package de.info3.navigation.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import de.info3.navigation.MainActivity;
import de.info3.navigation.Profile;
import de.info3.navigation.R;
import de.info3.navigation.Training;
import de.info3.navigation.databinding.FragmentNotificationsBinding;


public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;

    }
    //In der OnViewCreated aktzeptiert Android Studio es, den Button zu finden.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    Button button = getActivity().findViewById(R.id.button_profil);
    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getContext(), Training.class); //Um den Intent korrekt zu befüllen, ist anstelle von "MainActivity.this" "getContext()" einzugeben
            startActivity(intent);
            button.setText("Test");
        }
    });
        final int[] counter = {1};
        Switch switcher = getActivity().findViewById(R.id.switch1);
        switcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(counter[0] ==1){
                switcher.setText("!Hola!");
                counter[0] = 0;}else{switcher.setText("Switcher"); counter[0] = 1;}
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}