package layout;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;

import hk.edu.ouhk.ronnie.hongkongtoilet.MainActivity;
import hk.edu.ouhk.ronnie.hongkongtoilet.R;
import hk.edu.ouhk.ronnie.hongkongtoilet.ToiletApplication;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

    RadioGroup language_group;
    Spinner row_spinner;
    ToiletApplication application;
    public SettingFragment() {
        // Required empty public constructor
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        menu.findItem(R.id.action_update).setEnabled(false);
        menu.findItem(R.id.action_update).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_setting, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.app_name));
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle(getString(R.string.action_settings));
        application=(ToiletApplication)getActivity().getApplicationContext();
        language_group=(RadioGroup)view.findViewById(R.id.languageGroup);
        row_spinner=(Spinner)view.findViewById(R.id.row_spinner);
        String[] mItems = getResources().getStringArray(R.array.row_no);
        ArrayAdapter<String> _Adapter=new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item, mItems);
        row_spinner.setAdapter(_Adapter);
        initSetting();
        language_group.setOnCheckedChangeListener(listener);
        row_spinner.setOnItemSelectedListener(itemSelectedListener);
        return view;
    }
    private void initSetting() {
        String l=application.getAppLanguage(getContext()).getCountry();
        int i=application.getRowNo();
        row_spinner.setSelection(i);

        switch (l)
        {
            case "TW":
                language_group.check(R.id.radioTraditonal);
                break;
            case "CN":
                language_group.check(R.id.radioSimplified);
                break;
            default:
                language_group.check(R.id.radioEnglish);
                break;
        }
    }
    private RadioGroup.OnCheckedChangeListener listener=new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch (i)
            {
                case R.id.radioEnglish:
                    application.updateLanguage(getContext(),"ENG");
                    break;
                case R.id.radioTraditonal:
                    application.updateLanguage(getContext(),"ZH");
                    break;
                case R.id.radioSimplified:
                    application.updateLanguage(getContext(),"CH");
                    break;
            }
            restartActivity();
        }
    };
    private Spinner.OnItemSelectedListener itemSelectedListener= new Spinner.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            application.updateRowNo(i);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    private void restartActivity() {
        Intent intent = getActivity().getIntent();
        getActivity().finish();
        startActivity(intent);
    }
}
