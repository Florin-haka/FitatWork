package de.info3.navigation.ui.notifications;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import java.util.Objects;

import de.info3.navigation.AppDatabase;
import de.info3.navigation.DailyTipDao;
import de.info3.navigation.DateDao;
import de.info3.navigation.ExerciceDao;
import de.info3.navigation.MainActivity;
import de.info3.navigation.Profile;
import de.info3.navigation.ProfileDao;
import de.info3.navigation.R;
import de.info3.navigation.Setting;
import de.info3.navigation.SettingDao;
import de.info3.navigation.Training;
import de.info3.navigation.databinding.FragmentNotificationsBinding;

//einstellungen und profil

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;
    private Profile profile;
    private Setting setting;
    private String linkProfilePicture;
    private String profileName;
    private boolean showDaylitip;
    private int levelpoints;
    private int bonuspoints;
    private int level=0;
    private char[] bodyPartsUsable;


    private boolean[] bodyParts=new boolean[5];


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

        return root;

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppDatabase db = Room.databaseBuilder(requireContext(),
                AppDatabase.class, "fitAtWorkDatabase").allowMainThreadQueries().build();
        ProfileDao profileDao = db.profileDao();
        SettingDao settingDao = db.settingDao();
                                                                    //lädt aktuelle pofil und einstellungs daten
        this.profile=profileDao.getProfile().get(0);
        this.setting=settingDao.getSettings().get(0);
        this.linkProfilePicture=profile.getLinkProfileFoto();
        this.profileName=profile.getUserName();
        this.levelpoints=profile.getLevelPoints();
        this.bonuspoints=profile.getBonusPoints();
        if(this.levelpoints<34){
            this.level=1;
        }
        else if(this.levelpoints>65){
            this.level=3;
        }
        else{
            this.level=2;
        }
        String bodyParts=this.setting.getBodyPartsUsable();

        bodyPartsUsable = bodyParts.toCharArray();
        for(int i=0;i<bodyPartsUsable.length-1;i++){
            if(bodyPartsUsable[i]=='0'){
                this.bodyParts[i]=false;
            }
            else{
                this.bodyParts[i]=true;
            }
        }
        TextView profileNameView=getActivity().findViewById(R.id.profile_name);
        profileNameView.setText(this.profileName);
        TextView levelView=getActivity().findViewById(R.id.txt_vie_level_show);
        levelView.setText(String.valueOf(this.level));
        TextView bonusPointsView=getActivity().findViewById(R.id.txt_view_bonuspoints_show);
        bonusPointsView.setText(String.valueOf(this.bonuspoints));
        Switch switcherShowDailyTip=getActivity().findViewById(R.id.show_daily_tip);


         final int[] counter = {1};
        switcherShowDailyTip.setChecked(true);
        if(!setting.isShowDailyTip()){
            counter[0]=0;
            switcherShowDailyTip.setChecked(false);
        }
        switcherShowDailyTip.setOnClickListener(new View.OnClickListener() { //daily tip beim appstart aus und einschalten
            @Override
            public void onClick(View view) {


                if(setting.isShowDailyTip()){

                counter[0] = 0;
                setting.setShowDailyTip(false);
                switcherShowDailyTip.setChecked(false);

                }
                else{ counter[0] = 1;
                setting.setShowDailyTip(true);
                switcherShowDailyTip.setChecked(true);
                }
                settingDao.insertSetting(setting);
            }
        });


        Switch bodyPart1=getActivity().findViewById(R.id.switch1);
        Switch bodyPart2=getActivity().findViewById(R.id.switch2);
        Switch bodyPart3=getActivity().findViewById(R.id.switch3);
        Switch bodyPart4=getActivity().findViewById(R.id.switch4);
        Switch bodyPart5=getActivity().findViewById(R.id.switch5);
        Button changeProfileName=getActivity().findViewById(R.id.change_name);
        EditText newName=getActivity().findViewById(R.id.eidt_username);
        Button okButton=getActivity().findViewById(R.id.button_ok);
        changeProfileName.setOnClickListener(new View.OnClickListener() { //ermöglicht das profil namen ändern
            @Override
            public void onClick(View view) {
                newName.setVisibility(View.VISIBLE);
                okButton.setVisibility(View.VISIBLE);
                bodyPart1.setVisibility(View.INVISIBLE);
                bodyPart2.setVisibility(View.INVISIBLE);
                bodyPart3.setVisibility(View.INVISIBLE);
                bodyPart4.setVisibility(View.INVISIBLE);
                bodyPart5.setVisibility(View.INVISIBLE);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String newUserName=newName.getText().toString();
                        newName.setVisibility(View.INVISIBLE);
                        okButton.setVisibility(View.INVISIBLE);
                        AppDatabase db = Room.databaseBuilder(requireContext(),
                                AppDatabase.class, "fitAtWorkDatabase").allowMainThreadQueries().build();
                        ProfileDao profileDao=db.profileDao();
                        Profile profile=profileDao.getProfile().get(0);
                        profile.setUserName(newUserName);
                        profileDao.insertProfile(profile);
                        profileNameView.setText(newUserName);
                        bodyPart1.setVisibility(View.VISIBLE);
                        bodyPart2.setVisibility(View.VISIBLE);
                        bodyPart3.setVisibility(View.VISIBLE);
                        bodyPart4.setVisibility(View.VISIBLE);
                        bodyPart5.setVisibility(View.VISIBLE);

                    }
                });
            }
        });


        bodyPart1.setChecked(true);             //einzelene körperteile konne aktiviert oder deaktiviert werden
        bodyPart2.setChecked(true);
        bodyPart3.setChecked(true);
        bodyPart4.setChecked(true);
        bodyPart5.setChecked(true);
        final int[] counter1={1};
        final int[] counter2={1};
        final int[] counter3={1};
        final int[] counter4={1};
        final int[] counter5={1};

        if(bodyPartsUsable[0]=='0'){
            bodyPart1.setChecked(false);
            counter1[0]=0;
        }
        if(bodyPartsUsable[1]=='0'){
            bodyPart2.setChecked(false);
            counter2[0]=0;
        }
        if(bodyPartsUsable[2]=='0'){
            bodyPart3.setChecked(false);
            counter3[0]=0;
        }
        if(bodyPartsUsable[3]=='0'){
            bodyPart4.setChecked(false);
            counter4[0]=0;
        }
        if(bodyPartsUsable[4]=='0'){
            bodyPart5.setChecked(false);
            counter5[0]=0;
        }
        bodyPart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bodyPartsUsable[0]=='1'){

                    counter1[0] = 0;
                    bodyPartsUsable[0]='0';
                    bodyPart1.setChecked(false);

                }
                else{ counter1[0] = 1;
                    bodyPartsUsable[0]='1';
                    bodyPart1.setChecked(true);
                }

            }
        });
        bodyPart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bodyPartsUsable[1]=='1'){

                    counter2[0] = 0;
                    bodyPartsUsable[1]='0';
                    bodyPart2.setChecked(false);

                }
                else{ counter2[0] = 1;
                    bodyPartsUsable[1]='1';
                    bodyPart2.setChecked(true);
                }

            }
        });
        bodyPart3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bodyPartsUsable[2]=='1'){

                    counter3[0] = 0;
                    bodyPartsUsable[2]='0';
                    bodyPart3.setChecked(false);

                }
                else{ counter3[0] = 1;
                    bodyPartsUsable[2]='1';
                    bodyPart3.setChecked(true);
                }

            }
        });
        bodyPart4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bodyPartsUsable[3]=='1'){

                    counter4[0] = 0;
                    bodyPartsUsable[3]='0';
                    bodyPart4.setChecked(false);

                }
                else{ counter4[0] = 1;
                    bodyPartsUsable[3]='1';
                    bodyPart4.setChecked(true);
                }

            }
        });
        bodyPart5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bodyPartsUsable[4]=='1'){

                    counter5[0] = 0;
                    bodyPartsUsable[4]='0';
                    bodyPart5.setChecked(false);

                }
                else{ counter5[0] = 1;
                    bodyPartsUsable[4]='1';
                    bodyPart5.setChecked(true);
                }

            }
        });

        ImageButton infoBodyParts=getActivity().findViewById(R.id.button_Info_bodyparts);
        infoBodyParts.setOnClickListener(new View.OnClickListener() {           //zeigt informationen an
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setMessage("Diese Einstellungen bilden zusammen mit dem Level einen Filter für die Auswahl einer zufälligen Übung.") //Strings in Ressourcen auslagern
                        .setTitle("Gut zu wissen:");


                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        Button reset=getActivity().findViewById(R.id.button_reset);
        reset.setBackgroundColor(Color.RED);
        reset.setOnClickListener(new View.OnClickListener() {  //setzt bonuspunkte zurück
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setMessage("Möchtest du die Bonuspunkte wirklich zurücksetzen? Das kann nicht rückgängig gemacht werden!") //Strings in Ressourcen auslagern
                        .setTitle("WARNUNG!");

                builder.setPositiveButton("zurücksetzen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AppDatabase db = Room.databaseBuilder(requireContext(),
                                AppDatabase.class, "fitAtWorkDatabase").allowMainThreadQueries().build();
                        ProfileDao profileDao=db.profileDao();
                        Profile profile=profileDao.getProfile().get(0);
                        profile.setBonusPoints(0);
                        profileDao.insertProfile(profile);
                        bonusPointsView.setText("0");
                    }
                });


                builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    @Override
    public void onDestroyView() {   //speicher änderungen ab
        super.onDestroyView();
        binding = null;
        StringBuilder bodyparts= new StringBuilder();
        for (char c : bodyPartsUsable) {
            bodyparts.append(c);
        }
        setting.setBodyPartsUsable(bodyparts.toString());
        AppDatabase db = Room.databaseBuilder(requireContext(),
                AppDatabase.class, "fitAtWorkDatabase").allowMainThreadQueries().build();
        SettingDao settingDao = db.settingDao();
        settingDao.insertSetting(setting);
    }
}