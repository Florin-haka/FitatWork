package de.info3.navigation.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import java.util.List;

import de.info3.navigation.AppDatabase;
import de.info3.navigation.Notiz;
import de.info3.navigation.NotizDao;
import de.info3.navigation.R;
import de.info3.navigation.databinding.FragmentHomeBinding;

//erstellt und speichert notizen

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppDatabase db = Room.databaseBuilder(getContext(),AppDatabase.class, "Notizen-Database").allowMainThreadQueries().build();
        NotizDao notizDao = db.notizDao();
        //List<Notiz> notizen = notizDao.getAll();



        Button b11 = getActivity().findViewById(R.id.b11);
        EditText et11 = getActivity().findViewById(R.id.et11);
        TextView tv11 = getActivity().findViewById(R.id.tv11);

        b11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {        //erstellt und speicher notiz
                b11.setBackgroundColor(Color.GRAY);
                et11.setVisibility(View.VISIBLE);
                String b11string = et11.getText().toString();
                Notiz insertnotiz = new Notiz(11,b11string,11);
                notizDao.inserttoID(insertnotiz);



            }
        });

        b11.setOnLongClickListener(new View.OnLongClickListener() {  //zeigt notiz an
            @Override
            public boolean onLongClick(View view) {
                String b11string = et11.getText().toString();
                tv11.setVisibility(View.VISIBLE);
                int number = 11;
                List<Notiz> notizen = notizDao.loadNotizonkeycode(number);
                Notiz notiz = notizen.get(0);
                String string = notiz.getNotiztext().toString();

                if (string.isEmpty()) {
                    tv11.setText(" Keine Notiz gefunden");
                    tv11.setVisibility(View.VISIBLE);
                } else {
                    tv11.setText(string);
                    tv11.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });

        Button b12 = getActivity().findViewById(R.id.b12);
        EditText et12 = getActivity().findViewById(R.id.et12);
        TextView tv12 = getActivity().findViewById(R.id.tv12);

        b12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b12.setBackgroundColor(Color.GRAY);
                et12.setVisibility(View.VISIBLE);
                String b12string = et12.getText().toString();
                Notiz insertnotiz = new Notiz(12, b12string,12);
                notizDao.inserttoID(insertnotiz);



            }
        });
        b12.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String b12string = et12.getText().toString();
               tv12.setVisibility(View.VISIBLE);

                int number = 12;
                List<Notiz> notizen = notizDao.loadNotizonkeycode(number);
                Notiz notiz = notizen.get(0);
                String string = notiz.getNotiztext().toString();

                if (string.isEmpty()) {
                    tv12.setText(" Keine Notiz gefunden");
                    tv12.setVisibility(View.VISIBLE);
                } else {
                    tv12.setText(string);
                    tv12.setVisibility(View.VISIBLE);
                }




                return true;
            }
        });

        Button b13 = getActivity().findViewById(R.id.b13);
        EditText et13 = getActivity().findViewById(R.id.et13);
        TextView tv13 = getActivity().findViewById(R.id.tv13);

        b13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b13.setBackgroundColor(Color.GRAY);
                et13.setVisibility(View.VISIBLE);
                String b13string = et13.getText().toString();
                Notiz insertnotiz = new Notiz(13, b13string,13);
                notizDao.inserttoID(insertnotiz);
            }
        });
        b13.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String b13string = et13.getText().toString();
                tv13.setVisibility(View.VISIBLE);

                int number = 13;
                List<Notiz> notizen = notizDao.loadNotizonkeycode(number);
                Notiz notiz = notizen.get(0);
                String string = notiz.getNotiztext().toString();

                if (string.isEmpty()) {
                    tv13.setText(" Keine Notiz gefunden");
                    tv13.setVisibility(View.VISIBLE);
                } else {
                    tv13.setText(string);
                    tv13.setVisibility(View.VISIBLE);
                }

                return true;
            }
        });

        Button b14 = getActivity().findViewById(R.id.b14);
        EditText et14 = getActivity().findViewById(R.id.et14);
        TextView tv14 = getActivity().findViewById(R.id.tv14);

        b14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b14.setBackgroundColor(Color.GRAY);
                et14.setVisibility(View.VISIBLE);
                String b14string = et14.getText().toString();
                Notiz insertnotiz = new Notiz(14,b14string,14);
                notizDao.inserttoID(insertnotiz);
            }
        });
        b14.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String b14string = et14.getText().toString();

                tv14.setVisibility(View.VISIBLE);

                int number = 14;
                List<Notiz> notizen = notizDao.loadNotizonkeycode(number);
                Notiz notiz = notizen.get(0);
                String string = notiz.getNotiztext().toString();

                if (string.isEmpty()) {
                    tv14.setText(" Keine Notiz gefunden");
                    tv14.setVisibility(View.VISIBLE);
                } else {
                    tv14.setText(string);
                    tv14.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
        Button b15 = getActivity().findViewById(R.id.b15);
        EditText et15 = getActivity().findViewById(R.id.et15);
        TextView tv15 = getActivity().findViewById(R.id.tv15);

        b15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b15.setBackgroundColor(Color.GRAY);
                et15.setVisibility(View.VISIBLE);
                String b15string = et15.getText().toString();
                Notiz insertnotiz = new Notiz(15,b15string,15);
                notizDao.inserttoID(insertnotiz);
            }
        });
        b15.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String b15string = et15.getText().toString();

                tv15.setVisibility(View.VISIBLE);
                int number = 15;
                List<Notiz> notizen = notizDao.loadNotizonkeycode(number);
                Notiz notiz = notizen.get(0);
                String string = notiz.getNotiztext().toString();

                if (string.isEmpty()) {
                    tv15.setText(" Keine Notiz gefunden");
                    tv15.setVisibility(View.VISIBLE);
                } else {
                    tv15.setText(string);
                    tv14.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
        Button b16 = getActivity().findViewById(R.id.b16);
        EditText et16 = getActivity().findViewById(R.id.et16);
        TextView tv16 = getActivity().findViewById(R.id.tv16);

        b16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b16.setBackgroundColor(Color.GRAY);
                et16.setVisibility(View.VISIBLE);
                String b16string = et16.getText().toString();
                //tv12.setText(b11string);
                Notiz insertnotiz = new Notiz(16,b16string,16);
                notizDao.inserttoID(insertnotiz);
            }
        });
        b16.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String b16string = et16.getText().toString();

                tv16.setVisibility(View.VISIBLE);
                int number = 16;
                List<Notiz> notizen = notizDao.loadNotizonkeycode(number);
                Notiz notiz = notizen.get(0);
                String string = notiz.getNotiztext().toString();

                if (string.isEmpty()) {
                    tv16.setText(" Keine Notiz gefunden");
                    tv16.setVisibility(View.VISIBLE);
                } else {
                    tv16.setText(string);
                    tv16.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
        Button b17 = getActivity().findViewById(R.id.b17);
        EditText et17 = getActivity().findViewById(R.id.et17);
        TextView tv17 = getActivity().findViewById(R.id.tv17);

        b17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b17.setBackgroundColor(Color.GRAY);
                et17.setVisibility(View.VISIBLE);
                String b17string = et17.getText().toString();
                //tv12.setText(b11string);
                Notiz insertnotiz = new Notiz(17,b17string,17);
                notizDao.inserttoID(insertnotiz);
            }
        });
        b17.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String b17string = et17.getText().toString();

                tv17.setVisibility(View.VISIBLE);
                int number = 17;
                List<Notiz> notizen = notizDao.loadNotizonkeycode(number);
                Notiz notiz = notizen.get(0);
                String string = notiz.getNotiztext().toString();

                if (string.isEmpty()) {
                    tv17.setText(" Keine Notiz gefunden");
                    tv17.setVisibility(View.VISIBLE);
                } else {
                    tv17.setText(string);
                    tv17.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });

        Button b21 = getActivity().findViewById(R.id.b21);
        EditText et21 = getActivity().findViewById(R.id.et21);
        TextView tv21 = getActivity().findViewById(R.id.tv21);

        b21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b21.setBackgroundColor(Color.GRAY);
                et21.setVisibility(View.VISIBLE);
                String b21string = et21.getText().toString();
                Notiz insertnotiz = new Notiz(21,b21string,21);
                notizDao.inserttoID(insertnotiz);

            }
        });
        b21.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String b21string = et21.getText().toString();

                tv21.setVisibility(View.VISIBLE);
                int number = 21;
                List<Notiz> notizen = notizDao.loadNotizonkeycode(number);
                Notiz notiz = notizen.get(0);
                String string = notiz.getNotiztext().toString();

                if (string.isEmpty()) {
                    tv21.setText(" Keine Notiz gefunden");
                    tv21.setVisibility(View.VISIBLE);
                } else {
                    tv21.setText(string);
                    tv21.setVisibility(View.VISIBLE);
                }

                return true;
            }
        });

        Button b22 = getActivity().findViewById(R.id.b22);
        EditText et22 = getActivity().findViewById(R.id.et22);
        TextView tv22 = getActivity().findViewById(R.id.tv22);

        b22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b22.setBackgroundColor(Color.GRAY);
                et22.setVisibility(View.VISIBLE);
                String b22string = et22.getText().toString();
                //tv12.setText(b11string);
                Notiz insertnotiz = new Notiz(22,b22string,22);
                notizDao.inserttoID(insertnotiz);

            }
        });
        b22.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String b22string = et22.getText().toString();
                tv22.setVisibility(View.VISIBLE);
                int number = 22;
                List<Notiz> notizen = notizDao.loadNotizonkeycode(number);
                Notiz notiz = notizen.get(0);
                String string = notiz.getNotiztext().toString();

                if (string.isEmpty()) {
                    tv22.setText(" Keine Notiz gefunden");
                    tv22.setVisibility(View.VISIBLE);
                } else {
                    tv22.setText(string);
                    tv22.setVisibility(View.VISIBLE);
                }



                return true;
            }
        });

        Button b23 = getActivity().findViewById(R.id.b23);
        EditText et23 = getActivity().findViewById(R.id.et23);
        TextView tv23 = getActivity().findViewById(R.id.tv23);

        b23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b23.setBackgroundColor(Color.GRAY);
                et23.setVisibility(View.VISIBLE);
                String b23string = et23.getText().toString();
                //tv12.setText(b11string);
                Notiz insertnotiz = new Notiz(23,b23string,23);
                notizDao.inserttoID(insertnotiz);
            }
        });
        b23.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String b23string = et23.getText().toString();
                tv23.setVisibility(View.VISIBLE);
                int number = 23;
                List<Notiz> notizen = notizDao.loadNotizonkeycode(number);
                Notiz notiz = notizen.get(0);
                String string = notiz.getNotiztext().toString();

                if (string.isEmpty()) {
                    tv23.setText(" Keine Notiz gefunden");
                    tv23.setVisibility(View.VISIBLE);
                } else {
                    tv23.setText(string);
                    tv23.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });

        Button b24 = getActivity().findViewById(R.id.b24);
        EditText et24 = getActivity().findViewById(R.id.et24);
        TextView tv24 = getActivity().findViewById(R.id.tv24);

        b24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b24.setBackgroundColor(Color.GRAY);
                et24.setVisibility(View.VISIBLE);
                String b24string = et24.getText().toString();
                //tv12.setText(b11string);
                Notiz insertnotiz = new Notiz(24,b24string,24);
                notizDao.inserttoID(insertnotiz);
            }
        });
        b24.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String b24string = et24.getText().toString();
                tv24.setVisibility(View.VISIBLE);
                int number = 24;
                List<Notiz> notizen = notizDao.loadNotizonkeycode(number);
                Notiz notiz = notizen.get(0);
                String string = notiz.getNotiztext().toString();

                if (string.isEmpty()) {
                    tv24.setText(" Keine Notiz gefunden");
                    tv24.setVisibility(View.VISIBLE);
                } else {
                    tv24.setText(string);
                    tv24.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
        Button b25 = getActivity().findViewById(R.id.b25);
        EditText et25 = getActivity().findViewById(R.id.et25);
        TextView tv25 = getActivity().findViewById(R.id.tv25);

        b25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b25.setBackgroundColor(Color.GRAY);
                et25.setVisibility(View.VISIBLE);
                String b25string = et25.getText().toString();
                //tv12.setText(b11string);
                Notiz insertnotiz = new Notiz(25,b25string,25);
                notizDao.inserttoID(insertnotiz);
            }
        });
        b25.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String b25string = et25.getText().toString();
                tv25.setVisibility(View.VISIBLE);
                int number = 25;
                List<Notiz> notizen = notizDao.loadNotizonkeycode(number);
                Notiz notiz = notizen.get(0);
                String string = notiz.getNotiztext().toString();

                if (string.isEmpty()) {
                    tv25.setText(" Keine Notiz gefunden");
                    tv25.setVisibility(View.VISIBLE);
                } else {
                    tv25.setText(string);
                    tv25.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
        Button b26 = getActivity().findViewById(R.id.b26);
        EditText et26 = getActivity().findViewById(R.id.et26);
        TextView tv26 = getActivity().findViewById(R.id.tv26);

        b26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b26.setBackgroundColor(Color.GRAY);
                et26.setVisibility(View.VISIBLE);
                String b26string = et26.getText().toString();
                //tv12.setText(b11string);
                Notiz insertnotiz = new Notiz(26,b26string,26);
                notizDao.inserttoID(insertnotiz);
            }
        });
        b26.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String b26string = et26.getText().toString();
                tv26.setVisibility(View.VISIBLE);
                int number = 26;
                List<Notiz> notizen = notizDao.loadNotizonkeycode(number);
                Notiz notiz = notizen.get(0);
                String string = notiz.getNotiztext().toString();

                if (string.isEmpty()) {
                    tv26.setText(" Keine Notiz gefunden");
                    tv26.setVisibility(View.VISIBLE);
                } else {
                    tv26.setText(string);
                    tv26.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
        Button b27 = getActivity().findViewById(R.id.b27);
        EditText et27 = getActivity().findViewById(R.id.et27);
        TextView tv27 = getActivity().findViewById(R.id.tv27);

        b27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b27.setBackgroundColor(Color.GRAY);
                et27.setVisibility(View.VISIBLE);
                String b27string = et27.getText().toString();
                //tv12.setText(b11string);
                Notiz insertnotiz = new Notiz(27,b27string,27);
                notizDao.inserttoID(insertnotiz);
            }
        });
        b27.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String b27string = et27.getText().toString();
                tv27.setVisibility(View.VISIBLE);
                int number = 27;
                List<Notiz> notizen = notizDao.loadNotizonkeycode(number);
                Notiz notiz = notizen.get(0);
                String string = notiz.getNotiztext().toString();

                if (string.isEmpty()) {
                    tv27.setText(" Keine Notiz gefunden");
                    tv27.setVisibility(View.VISIBLE);
                } else {
                    tv27.setText(string);
                    tv27.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
        Button b31 = getActivity().findViewById(R.id.b31);
        EditText et31 = getActivity().findViewById(R.id.et31);
        TextView tv31 = getActivity().findViewById(R.id.tv31);

        b31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b31.setBackgroundColor(Color.GRAY);
                et31.setVisibility(View.VISIBLE);
                String b31string = et31.getText().toString();
                Notiz insertnotiz = new Notiz(31,b31string,31);
                notizDao.inserttoID(insertnotiz);

            }
        });
        b31.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String b31string = et31.getText().toString();
                tv31.setVisibility(View.VISIBLE);
                int number = 31;
                List<Notiz> notizen = notizDao.loadNotizonkeycode(number);
                Notiz notiz = notizen.get(0);
                String string = notiz.getNotiztext().toString();

                if (string.isEmpty()) {
                    tv31.setText(" Keine Notiz gefunden");
                    tv31.setVisibility(View.VISIBLE);
                } else {
                    tv31.setText(string);
                    tv31.setVisibility(View.VISIBLE);
                }

                return true;
            }
        });

        Button b32 = getActivity().findViewById(R.id.b32);
        EditText et32 = getActivity().findViewById(R.id.et32);
        TextView tv32 = getActivity().findViewById(R.id.tv32);

        b32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b32.setBackgroundColor(Color.GRAY);
                et32.setVisibility(View.VISIBLE);
                String b32string = et32.getText().toString();
                //tv12.setText(b11string);
                Notiz insertnotiz = new Notiz(32,b32string,32);
                notizDao.inserttoID(insertnotiz);


            }
        });
        b32.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String b32string = et32.getText().toString();
                tv32.setVisibility(View.VISIBLE);
                int number = 32;
                List<Notiz> notizen = notizDao.loadNotizonkeycode(number);
                Notiz notiz = notizen.get(0);
                String string = notiz.getNotiztext().toString();

                if (string.isEmpty()) {
                    tv32.setText(" Keine Notiz gefunden");
                    tv32.setVisibility(View.VISIBLE);
                } else {
                    tv32.setText(string);
                    tv32.setVisibility(View.VISIBLE);
                }



                return true;
            }
        });

        Button b33 = getActivity().findViewById(R.id.b33);
        EditText et33 = getActivity().findViewById(R.id.et33);
        TextView tv33 = getActivity().findViewById(R.id.tv33);

        b33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b33.setBackgroundColor(Color.GRAY);
                et33.setVisibility(View.VISIBLE);
                String b33string = et33.getText().toString();
                //tv12.setText(b11string);
                Notiz insertnotiz = new Notiz(33,b33string,33);
                notizDao.inserttoID(insertnotiz);
            }
        });
        b33.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String b33string = et33.getText().toString();
                tv33.setVisibility(View.VISIBLE);
                int number = 33;
                List<Notiz> notizen = notizDao.loadNotizonkeycode(number);
                Notiz notiz = notizen.get(0);
                String string = notiz.getNotiztext().toString();

                if (string.isEmpty()) {
                    tv33.setText(" Keine Notiz gefunden");
                    tv33.setVisibility(View.VISIBLE);
                } else {
                    tv33.setText(string);
                    tv33.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });

        Button b34 = getActivity().findViewById(R.id.b34);
        EditText et34 = getActivity().findViewById(R.id.et34);
        TextView tv34 = getActivity().findViewById(R.id.tv34);

        b34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b34.setBackgroundColor(Color.GRAY);
                et34.setVisibility(View.VISIBLE);
                String b34string = et34.getText().toString();
                //tv12.setText(b11string);
                Notiz insertnotiz = new Notiz(34,b34string,34);
                notizDao.inserttoID(insertnotiz);
            }
        });
        b34.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String b34string = et34.getText().toString();
                tv34.setVisibility(View.VISIBLE);
                int number = 34;
                List<Notiz> notizen = notizDao.loadNotizonkeycode(number);
                Notiz notiz = notizen.get(0);
                String string = notiz.getNotiztext().toString();

                if (string.isEmpty()) {
                    tv34.setText(" Keine Notiz gefunden");
                    tv34.setVisibility(View.VISIBLE);
                } else {
                    tv34.setText(string);
                    tv34.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
        Button b35 = getActivity().findViewById(R.id.b35);
        EditText et35 = getActivity().findViewById(R.id.et35);
        TextView tv35 = getActivity().findViewById(R.id.tv35);

        b35.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b35.setBackgroundColor(Color.GRAY);
                et35.setVisibility(View.VISIBLE);
                String b35string = et35.getText().toString();
                //tv12.setText(b11string);
                Notiz insertnotiz = new Notiz(35,b35string,35);
                notizDao.inserttoID(insertnotiz);
            }
        });
        b35.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String b35string = et35.getText().toString();
                tv35.setVisibility(View.VISIBLE);
                int number = 35;
                List<Notiz> notizen = notizDao.loadNotizonkeycode(number);
                Notiz notiz = notizen.get(0);
                String string = notiz.getNotiztext().toString();

                if (string.isEmpty()) {
                    tv35.setText(" Keine Notiz gefunden");
                    tv35.setVisibility(View.VISIBLE);
                } else {
                    tv35.setText(string);
                    tv35.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
        Button b36 = getActivity().findViewById(R.id.b36);
        EditText et36 = getActivity().findViewById(R.id.et36);
        TextView tv36 = getActivity().findViewById(R.id.tv36);

        b36.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b36.setBackgroundColor(Color.GRAY);
                et36.setVisibility(View.VISIBLE);
                String b36string = et36.getText().toString();
                //tv12.setText(b11string);
                Notiz insertnotiz = new Notiz(36,b36string,36);
                notizDao.inserttoID(insertnotiz);
            }
        });
        b36.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String b36string = et36.getText().toString();
                tv36.setVisibility(View.VISIBLE);
                int number = 36;
                List<Notiz> notizen = notizDao.loadNotizonkeycode(number);
                Notiz notiz = notizen.get(0);
                String string = notiz.getNotiztext().toString();

                if (string.isEmpty()) {
                    tv36.setText(" Keine Notiz gefunden");
                    tv36.setVisibility(View.VISIBLE);
                } else {
                    tv36.setText(string);
                    tv36.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
        Button b37 = getActivity().findViewById(R.id.b37);
        EditText et37 = getActivity().findViewById(R.id.et37);
        TextView tv37 = getActivity().findViewById(R.id.tv37);

        b37.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b37.setBackgroundColor(Color.GRAY);
                et37.setVisibility(View.VISIBLE);
                String b37string = et37.getText().toString();
                //tv12.setText(b11string);
                Notiz insertnotiz = new Notiz(37,b37string,37);
                notizDao.inserttoID(insertnotiz);
            }
        });
        b37.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String b37string = et37.getText().toString();
                tv37.setVisibility(View.VISIBLE);
                int number = 37;
                List<Notiz> notizen = notizDao.loadNotizonkeycode(number);
                Notiz notiz = notizen.get(0);
                String string = notiz.getNotiztext().toString();

                if (string.isEmpty()) {
                    tv37.setText(" Keine Notiz gefunden");
                    tv37.setVisibility(View.VISIBLE);
                } else {
                    tv37.setText(string);
                    tv37.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
        Button b41 = getActivity().findViewById(R.id.b41);
        EditText et41 = getActivity().findViewById(R.id.et41);
        TextView tv41 = getActivity().findViewById(R.id.tv41);

        b41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b41.setBackgroundColor(Color.GRAY);
                et41.setVisibility(View.VISIBLE);
                String b41string = et41.getText().toString();
                Notiz insertnotiz = new Notiz(41,b41string,41);
                notizDao.inserttoID(insertnotiz);

            }
        });
        b41.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String b41string = et41.getText().toString();
                tv41.setVisibility(View.VISIBLE);
                int number = 41;
                List<Notiz> notizen = notizDao.loadNotizonkeycode(number);
                Notiz notiz = notizen.get(0);
                String string = notiz.getNotiztext().toString();

                if (string.isEmpty()) {
                    tv41.setText(" Keine Notiz gefunden");
                    tv41.setVisibility(View.VISIBLE);
                } else {
                    tv41.setText(string);
                    tv41.setVisibility(View.VISIBLE);
                }

                return true;
            }
        });

        Button b42 = getActivity().findViewById(R.id.b42);
        EditText et42 = getActivity().findViewById(R.id.et42);
        TextView tv42 = getActivity().findViewById(R.id.tv42);

        b42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b42.setBackgroundColor(Color.GRAY);
                et42.setVisibility(View.VISIBLE);
                String b42string = et42.getText().toString();
                //tv12.setText(b11string);
                Notiz insertnotiz = new Notiz(42,b42string,42);
                notizDao.inserttoID(insertnotiz);


            }
        });
        b42.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String b42string = et42.getText().toString();
                tv42.setVisibility(View.VISIBLE);
                int number = 42;
                List<Notiz> notizen = notizDao.loadNotizonkeycode(number);
                Notiz notiz = notizen.get(0);
                String string = notiz.getNotiztext().toString();

                if (string.isEmpty()) {
                    tv42.setText(" Keine Notiz gefunden");
                    tv42.setVisibility(View.VISIBLE);
                } else {
                    tv42.setText(string);
                    tv42.setVisibility(View.VISIBLE);
                }



                return true;
            }
        });

        Button b43 = getActivity().findViewById(R.id.b43);
        EditText et43 = getActivity().findViewById(R.id.et43);
        TextView tv43 = getActivity().findViewById(R.id.tv43);

        b43.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b43.setBackgroundColor(Color.GRAY);
                et43.setVisibility(View.VISIBLE);
                String b43string = et43.getText().toString();
                //tv12.setText(b11string);
                Notiz insertnotiz = new Notiz(43,b43string,43);
                notizDao.inserttoID(insertnotiz);
            }
        });
        b43.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String b43string = et43.getText().toString();
                tv43.setVisibility(View.VISIBLE);
                int number = 43;
                List<Notiz> notizen = notizDao.loadNotizonkeycode(number);
                Notiz notiz = notizen.get(0);
                String string = notiz.getNotiztext().toString();

                if (string.isEmpty()) {
                    tv43.setText(" Keine Notiz gefunden");
                    tv43.setVisibility(View.VISIBLE);
                } else {
                    tv43.setText(string);
                    tv43.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });

        Button b44 = getActivity().findViewById(R.id.b44);
        EditText et44 = getActivity().findViewById(R.id.et44);
        TextView tv44 = getActivity().findViewById(R.id.tv44);

        b44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b44.setBackgroundColor(Color.GRAY);
                et44.setVisibility(View.VISIBLE);
                String b44string = et44.getText().toString();
                //tv12.setText(b11string);
                Notiz insertnotiz = new Notiz(44,b44string,44);
                notizDao.inserttoID(insertnotiz);
            }
        });
        b44.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String b44string = et44.getText().toString();
                tv44.setVisibility(View.VISIBLE);
                int number = 44;
                List<Notiz> notizen = notizDao.loadNotizonkeycode(number);
                Notiz notiz = notizen.get(0);
                String string = notiz.getNotiztext().toString();

                if (string.isEmpty()) {
                    tv44.setText(" Keine Notiz gefunden");
                    tv44.setVisibility(View.VISIBLE);
                } else {
                    tv44.setText(string);
                    tv44.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
        Button b45 = getActivity().findViewById(R.id.b45);
        EditText et45 = getActivity().findViewById(R.id.et45);
        TextView tv45 = getActivity().findViewById(R.id.tv45);

        b45.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b45.setBackgroundColor(Color.GRAY);
                et45.setVisibility(View.VISIBLE);
                String b45string = et45.getText().toString();
                //tv12.setText(b11string);
                Notiz insertnotiz = new Notiz(45,b45string,45);
                notizDao.inserttoID(insertnotiz);
            }
        });
        b45.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String b45string = et45.getText().toString();
                tv45.setVisibility(View.VISIBLE);
                int number = 45;
                List<Notiz> notizen = notizDao.loadNotizonkeycode(number);
                Notiz notiz = notizen.get(0);
                String string = notiz.getNotiztext().toString();

                if (string.isEmpty()) {
                    tv45.setText(" Keine Notiz gefunden");
                    tv45.setVisibility(View.VISIBLE);
                } else {
                    tv45.setText(string);
                    tv45.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
        Button b46 = getActivity().findViewById(R.id.b46);
        EditText et46 = getActivity().findViewById(R.id.et46);
        TextView tv46 = getActivity().findViewById(R.id.tv46);

        b46.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b46.setBackgroundColor(Color.GRAY);
                et46.setVisibility(View.VISIBLE);
                String b46string = et46.getText().toString();
                //tv12.setText(b11string);
                Notiz insertnotiz = new Notiz(46,b46string,46);
                notizDao.inserttoID(insertnotiz);
            }
        });
        b46.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String b46string = et46.getText().toString();
                tv46.setVisibility(View.VISIBLE);
                int number = 46;
                List<Notiz> notizen = notizDao.loadNotizonkeycode(number);
                Notiz notiz = notizen.get(0);
                String string = notiz.getNotiztext().toString();

                if (string.isEmpty()) {
                    tv46.setText(" Keine Notiz gefunden");
                    tv46.setVisibility(View.VISIBLE);
                } else {
                    tv46.setText(string);
                    tv46.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
        Button b47 = getActivity().findViewById(R.id.b47);
        EditText et47 = getActivity().findViewById(R.id.et47);
        TextView tv47 = getActivity().findViewById(R.id.tv47);

        b47.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b47.setBackgroundColor(Color.GRAY);
                et47.setVisibility(View.VISIBLE);
                String b47string = et47.getText().toString();
                //tv12.setText(b11string);
                Notiz insertnotiz = new Notiz(47,b47string,47);
                notizDao.inserttoID(insertnotiz);
            }
        });
        b47.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String b47string = et47.getText().toString();
                tv47.setVisibility(View.VISIBLE);
                int number = 47;
                List<Notiz> notizen = notizDao.loadNotizonkeycode(number);
                Notiz notiz = notizen.get(0);
                String string = notiz.getNotiztext().toString();

                if (string.isEmpty()) {
                    tv47.setText(" Keine Notiz gefunden");
                    tv47.setVisibility(View.VISIBLE);
                } else {
                    tv47.setText(string);
                    tv47.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
        Button b51 = getActivity().findViewById(R.id.b51);
        EditText et51 = getActivity().findViewById(R.id.et51);
        TextView tv51 = getActivity().findViewById(R.id.tv51);

        b51.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b51.setBackgroundColor(Color.GRAY);
                et51.setVisibility(View.VISIBLE);
                String b51string = et51.getText().toString();
                Notiz insertnotiz = new Notiz(51,b51string,51);
                notizDao.inserttoID(insertnotiz);

            }
        });
        b51.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String b51string = et51.getText().toString();
                tv51.setVisibility(View.VISIBLE);
                int number = 51;
                List<Notiz> notizen = notizDao.loadNotizonkeycode(number);
                Notiz notiz = notizen.get(0);
                String string = notiz.getNotiztext().toString();

                if (string.isEmpty()) {
                    tv51.setText(" Keine Notiz gefunden");
                    tv51.setVisibility(View.VISIBLE);
                } else {
                    tv51.setText(string);
                    tv51.setVisibility(View.VISIBLE);
                }

                return true;
            }
        });

        Button b52 = getActivity().findViewById(R.id.b52);
        EditText et52 = getActivity().findViewById(R.id.et52);
        TextView tv52 = getActivity().findViewById(R.id.tv52);

        b52.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b52.setBackgroundColor(Color.GRAY);
                et52.setVisibility(View.VISIBLE);
                String b52string = et52.getText().toString();
                //tv12.setText(b11string);
                Notiz insertnotiz = new Notiz(52,b52string,52);
                notizDao.inserttoID(insertnotiz);

            }
        });
        b52.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String b52string = et52.getText().toString();
                tv52.setVisibility(View.VISIBLE);
                int number = 52;
                List<Notiz> notizen = notizDao.loadNotizonkeycode(number);
                Notiz notiz = notizen.get(0);
                String string = notiz.getNotiztext().toString();

                if (string.isEmpty()) {
                    tv52.setText(" Keine Notiz gefunden");
                    tv52.setVisibility(View.VISIBLE);
                } else {
                    tv52.setText(string);
                    tv52.setVisibility(View.VISIBLE);
                }



                return true;
            }
        });

        Button b53 = getActivity().findViewById(R.id.b53);
        EditText et53 = getActivity().findViewById(R.id.et53);
        TextView tv53 = getActivity().findViewById(R.id.tv53);

        b53.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b53.setBackgroundColor(Color.GRAY);
                et53.setVisibility(View.VISIBLE);
                String b53string = et53.getText().toString();
                //tv12.setText(b11string);
                Notiz insertnotiz = new Notiz(53,b53string,53);
                notizDao.inserttoID(insertnotiz);
            }
        });
        b53.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String b53string = et53.getText().toString();
                tv53.setVisibility(View.VISIBLE);
                int number = 53;
                List<Notiz> notizen = notizDao.loadNotizonkeycode(number);
                Notiz notiz = notizen.get(0);
                String string = notiz.getNotiztext().toString();

                if (string.isEmpty()) {
                    tv53.setText(" Keine Notiz gefunden");
                    tv53.setVisibility(View.VISIBLE);
                } else {
                    tv53.setText(string);
                    tv53.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });

        Button b54 = getActivity().findViewById(R.id.b54);
        EditText et54 = getActivity().findViewById(R.id.et54);
        TextView tv54 = getActivity().findViewById(R.id.tv54);

        b54.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b54.setBackgroundColor(Color.GRAY);
                et54.setVisibility(View.VISIBLE);
                String b54string = et54.getText().toString();
                //tv12.setText(b11string);
                Notiz insertnotiz = new Notiz(54,b54string,54);
                notizDao.inserttoID(insertnotiz);
            }
        });
        b54.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String b54string = et54.getText().toString();
                tv54.setVisibility(View.VISIBLE);
                int number = 54;
                List<Notiz> notizen = notizDao.loadNotizonkeycode(number);
                Notiz notiz = notizen.get(0);
                String string = notiz.getNotiztext().toString();

                if (string.isEmpty()) {
                    tv54.setText(" Keine Notiz gefunden");
                    tv54.setVisibility(View.VISIBLE);
                } else {
                    tv54.setText(string);
                    tv54.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
        Button b55 = getActivity().findViewById(R.id.b55);
        EditText et55 = getActivity().findViewById(R.id.et55);
        TextView tv55 = getActivity().findViewById(R.id.tv55);

        b55.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b55.setBackgroundColor(Color.GRAY);
                et55.setVisibility(View.VISIBLE);
                String b55string = et55.getText().toString();
                //tv12.setText(b11string);
                Notiz insertnotiz = new Notiz(55,b55string,55);
                notizDao.inserttoID(insertnotiz);
            }
        });
        b55.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String b55string = et55.getText().toString();
                tv55.setVisibility(View.VISIBLE);
                int number = 55;
                List<Notiz> notizen = notizDao.loadNotizonkeycode(number);
                Notiz notiz = notizen.get(0);
                String string = notiz.getNotiztext().toString();

                if (string.isEmpty()) {
                    tv55.setText(" Keine Notiz gefunden");
                    tv55.setVisibility(View.VISIBLE);
                } else {
                    tv55.setText(string);
                    tv55.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
        Button b56 = getActivity().findViewById(R.id.b56);
        EditText et56 = getActivity().findViewById(R.id.et56);
        TextView tv56 = getActivity().findViewById(R.id.tv56);

        b56.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b56.setBackgroundColor(Color.GRAY);
                et56.setVisibility(View.VISIBLE);
                String b56string = et56.getText().toString();
                //tv12.setText(b11string);
                Notiz insertnotiz = new Notiz(56,b56string,56);
                notizDao.inserttoID(insertnotiz);
            }
        });
        b56.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String b56string = et56.getText().toString();
                tv56.setVisibility(View.VISIBLE);
                int number = 56;
                List<Notiz> notizen = notizDao.loadNotizonkeycode(number);
                Notiz notiz = notizen.get(0);
                String string = notiz.getNotiztext().toString();

                if (string.isEmpty()) {
                    tv56.setText(" Keine Notiz gefunden");
                    tv56.setVisibility(View.VISIBLE);
                } else {
                    tv56.setText(string);
                    tv56.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
        Button b57 = getActivity().findViewById(R.id.b57);
        EditText et57 = getActivity().findViewById(R.id.et57);
        TextView tv57 = getActivity().findViewById(R.id.tv57);

        b57.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b57.setBackgroundColor(Color.GRAY);
                et57.setVisibility(View.VISIBLE);
                String b57string = et57.getText().toString();
                //tv12.setText(b11string);
                Notiz insertnotiz = new Notiz(57,b57string,57);
                notizDao.inserttoID(insertnotiz);
            }
        });
        b57.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String b57string = et57.getText().toString();
                tv57.setVisibility(View.VISIBLE);
                int number = 57;
                List<Notiz> notizen = notizDao.loadNotizonkeycode(number);
                Notiz notiz = notizen.get(0);
                String string = notiz.getNotiztext().toString();

                if (string.isEmpty()) {
                    tv57.setText(" Keine Notiz gefunden");
                    tv57.setVisibility(View.VISIBLE);
                } else {
                    tv57.setText(string);
                    tv57.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });

        Button infobutton = getActivity().findViewById(R.id.infobutton);
        TextView infotv = getActivity().findViewById(R.id.infotext);
        infobutton.setOnClickListener(new View.OnClickListener() { //zeigt inf an wie die activity funktioniert
            @Override
            public void onClick(View view) {
                infotv.setVisibility(View.VISIBLE);
                infotv.setText("Notiz erstellen: Datum antippen                                           "              +
                        "Notiz speichern: nochmal antippen                                                " +
                        "Notiz ansehen: lang auf das Datum tippen"
                );
            }
        });

        Button deletb = getActivity().findViewById(R.id.deleteb);
        deletb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et11.setVisibility(View.INVISIBLE);
                et12.setVisibility(View.INVISIBLE);
                et13.setVisibility(View.INVISIBLE);
                et14.setVisibility(View.INVISIBLE);
                et15.setVisibility(View.INVISIBLE);
                et16.setVisibility(View.INVISIBLE);
                et17.setVisibility(View.INVISIBLE);
                et21.setVisibility(View.INVISIBLE);
                et22.setVisibility(View.INVISIBLE);
                et23.setVisibility(View.INVISIBLE);
                et24.setVisibility(View.INVISIBLE);
                et25.setVisibility(View.INVISIBLE);
                et26.setVisibility(View.INVISIBLE);
                et27.setVisibility(View.INVISIBLE);
                et31.setVisibility(View.INVISIBLE);
                et32.setVisibility(View.INVISIBLE);
                et33.setVisibility(View.INVISIBLE);
                et34.setVisibility(View.INVISIBLE);
                et35.setVisibility(View.INVISIBLE);
                et36.setVisibility(View.INVISIBLE);
                et37.setVisibility(View.INVISIBLE);
                et41.setVisibility(View.INVISIBLE);
                et42.setVisibility(View.INVISIBLE);
                et43.setVisibility(View.INVISIBLE);
                et44.setVisibility(View.INVISIBLE);
                et45.setVisibility(View.INVISIBLE);
                et46.setVisibility(View.INVISIBLE);
                et47.setVisibility(View.INVISIBLE);
                et51.setVisibility(View.INVISIBLE);
                et52.setVisibility(View.INVISIBLE);
                et53.setVisibility(View.INVISIBLE);
                et54.setVisibility(View.INVISIBLE);
                et55.setVisibility(View.INVISIBLE);
                et56.setVisibility(View.INVISIBLE);
                et57.setVisibility(View.INVISIBLE);

                tv11.setVisibility(View.INVISIBLE);
                tv12.setVisibility(View.INVISIBLE);
                tv13.setVisibility(View.INVISIBLE);
                tv14.setVisibility(View.INVISIBLE);
                tv15.setVisibility(View.INVISIBLE);
                tv16.setVisibility(View.INVISIBLE);
                tv17.setVisibility(View.INVISIBLE);
                tv21.setVisibility(View.INVISIBLE);
                tv22.setVisibility(View.INVISIBLE);
                tv23.setVisibility(View.INVISIBLE);
                tv24.setVisibility(View.INVISIBLE);
                tv25.setVisibility(View.INVISIBLE);
                tv26.setVisibility(View.INVISIBLE);
                tv27.setVisibility(View.INVISIBLE);
                tv31.setVisibility(View.INVISIBLE);
                tv32.setVisibility(View.INVISIBLE);
                tv33.setVisibility(View.INVISIBLE);
                tv34.setVisibility(View.INVISIBLE);
                tv35.setVisibility(View.INVISIBLE);
                tv36.setVisibility(View.INVISIBLE);
                tv37.setVisibility(View.INVISIBLE);
                tv41.setVisibility(View.INVISIBLE);
                tv42.setVisibility(View.INVISIBLE);
                tv43.setVisibility(View.INVISIBLE);
                tv44.setVisibility(View.INVISIBLE);
                tv45.setVisibility(View.INVISIBLE);
                tv46.setVisibility(View.INVISIBLE);
                tv47.setVisibility(View.INVISIBLE);
                tv51.setVisibility(View.INVISIBLE);
                tv52.setVisibility(View.INVISIBLE);
                tv53.setVisibility(View.INVISIBLE);
                tv54.setVisibility(View.INVISIBLE);
                tv55.setVisibility(View.INVISIBLE);
                tv56.setVisibility(View.INVISIBLE);
                tv57.setVisibility(View.INVISIBLE);
                infotv.setVisibility(View.INVISIBLE);

            }
        });



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}