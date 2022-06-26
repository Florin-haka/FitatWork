package de.info3.navigation.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.info3.navigation.AppDatabase;
import de.info3.navigation.Exercice;
import de.info3.navigation.ExerciceDao;
import de.info3.navigation.MainActivityTrackingApp;
import de.info3.navigation.PickExercice;
import de.info3.navigation.Profile;
import de.info3.navigation.ProfileDao;
import de.info3.navigation.Setting;
import de.info3.navigation.SettingDao;
import de.info3.navigation.Training;
import de.info3.navigation.databinding.FragmentDashboardBinding;

//ist eigntlich home fragment nur falsch benannt :D

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;
    int progress = 50;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Button trackingButton =binding.buttonTracking;
        Button trainingButton = binding.buttonTraining;
        Button randomButton=binding.buttonRandom;
        ProgressBar progressBar = binding.progressBar;

        AppDatabase db = Room.databaseBuilder(requireContext(),
                AppDatabase.class, "fitAtWorkDatabase").allowMainThreadQueries().build();
        ProfileDao profileDao=db.profileDao();
        progress=profileDao.getProfile().get(0).getLevelPoints();
        progressBar.setProgress(progress);


        trackingButton.setOnClickListener(new View.OnClickListener(){  //startet tracking activity
            @Override
            public void onClick(View view){
                Intent intent=new Intent(getContext(), MainActivityTrackingApp.class);
                startActivity(intent);
            }
        });

        trainingButton.setOnClickListener(new View.OnClickListener() {      //startet übungs auswahl activity
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PickExercice.class);
                startActivity(intent);
            }
        });

        randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDatabase db = Room.databaseBuilder(requireContext(),
                        AppDatabase.class, "fitAtWorkDatabase").allowMainThreadQueries().build();
                ExerciceDao exerciceDao=db.exerciceDao();
                ProfileDao profileDao=db.profileDao();
                SettingDao settingDao=db.settingDao();
                List<Exercice> wrongExercices=new ArrayList<>();
                List<Exercice> allExercices=exerciceDao.getAllExercices();
                wrongExercices.add(allExercices.get(0));
                Profile profile=profileDao.getProfile().get(0);
                Setting setting=settingDao.getSettings().get(0);
                boolean suceeded=false;
                int iteration=0;
                while(allExercices.size()!=wrongExercices.size()){          //wählt nur übungen aus die zum level der person und dernen körperlichen einschränkungen passen
                    iteration++;
                    Random random=new Random();
                    int id=random.nextInt(allExercices.size()-1);
                    if(id>0){
                        Exercice exercice =allExercices.get(id);
                        boolean exerciceIsWrong=false;
                        int level;
                        if(profile.getLevelPoints()<34){
                            level=1;
                        }
                        else if(profile.getLevelPoints()>65){
                            level=3;
                        }
                        else{
                            level=2;
                        }
                        if(level!=exercice.getLevel()){
                            exerciceIsWrong=true;
                        }
                        char[] bodypartsUsable=setting.getBodyPartsUsable().toCharArray();
                        char[] bodypartsUsed=exercice.getBodyPart().toCharArray();
                        for(int i=0;i<4;i++){
                            if(bodypartsUsable[i]=='0'&bodypartsUsed[i]=='1'){
                                exerciceIsWrong=true;
                            }
                        }

                        if(exerciceIsWrong){
                            boolean isAlreadyInWasteList=false;
                            for(int j=0;j<wrongExercices.size();j++) {
                                if(wrongExercices.get(j).getId()==exercice.getId()){
                                    isAlreadyInWasteList=true;
                                }

                            }
                            if(!isAlreadyInWasteList){
                                wrongExercices.add(exercice);
                            }
                        }
                        else{
                            Intent intent=new Intent(getContext(),Training.class);
                            intent.putExtra("id",exercice.getId());
                            startActivity(intent);
                            suceeded=true;
                            break;
                        }



                    }
                    if(iteration>100){
                        break;
                    }
                }

                for(int k=0;k<allExercices.size();k++){   //anzahl zufälliger durchläufe wird auf 100 begrenzt, dann wird die Liste abgearbeitet


                    int id=k;
                    if(id>0){
                        Exercice exercice =allExercices.get(id);
                        boolean exerciceIsWrong=false;
                        int level;
                        if(profile.getLevelPoints()<34){
                            level=1;
                        }
                        else if(profile.getLevelPoints()>65){
                            level=3;
                        }
                        else{
                            level=2;
                        }
                        if(level!=exercice.getLevel()){
                            exerciceIsWrong=true;
                        }
                        char[] bodypartsUsable=setting.getBodyPartsUsable().toCharArray();
                        char[] bodypartsUsed=exercice.getBodyPart().toCharArray();
                        for(int i=0;i<4;i++){
                            if(bodypartsUsable[i]=='0'&bodypartsUsed[i]=='1'){
                                exerciceIsWrong=true;
                            }
                        }

                        if(exerciceIsWrong){
                            boolean isAlreadyInWasteList=false;
                            for(int j=0;j<wrongExercices.size();j++) {
                                if(wrongExercices.get(j).getId()==exercice.getId()){
                                    isAlreadyInWasteList=true;
                                }

                            }
                            if(!isAlreadyInWasteList){
                                wrongExercices.add(exercice);
                            }
                        }
                        else{
                            Intent intent=new Intent(getContext(),Training.class);
                            intent.putExtra("id",exercice.getId());
                            startActivity(intent);
                            suceeded=true;
                            break;
                        }



                    }

                }

                if(!suceeded){
                    Toast.makeText(getContext(),"Keine passende Übung gefunden",Toast.LENGTH_LONG).show();
                }


            }
        });

              return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}