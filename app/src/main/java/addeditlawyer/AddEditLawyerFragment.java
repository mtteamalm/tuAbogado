package addeditlawyer;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tuabogado.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import data.Lawyer;
import data.LawyersDbHelper;
import lawyerdetail.LawyerDetailFragment;

/**
 * Vista de creación/edición de abogado
 */
public class AddEditLawyerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_LAWYER_ID = "arg_lawyer_id";


    // TODO: Rename and change types of parameters
    private String mLawyerId;

    private LawyersDbHelper mLawyersDbHelper;

    private FloatingActionButton mSaveButton;
    private TextInputEditText mNameField;
    private TextInputEditText mPhoneNumberField;
    private TextInputEditText mSpecialtyField;
    private TextInputEditText mBioField;

    private TextInputLayout mNameLabel;
    private TextInputLayout mPhoneNumberLabel;
    private TextInputLayout mSpecialtyLabel;
    private TextInputLayout mBioLabel;

    public AddEditLawyerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param mLawyerId Parameter 1.
     * @return A new instance of fragment AddEditLawyerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddEditLawyerFragment newInstance(String mLawyerId) {
        AddEditLawyerFragment fragment = new AddEditLawyerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LAWYER_ID, mLawyerId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLawyerId = getArguments().getString(ARG_LAWYER_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_edit_lawyer, container, false);

        //Referencias de nuestra Interfaz
        mSaveButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mNameField = (TextInputEditText) root.findViewById(R.id.et_name);
        mPhoneNumberField = (TextInputEditText) root.findViewById(R.id.et_phone_number);
        mSpecialtyField = (TextInputEditText) root.findViewById(R.id.et_specialty);
        mBioField = (TextInputEditText) root.findViewById(R.id.et_bio);

        mNameLabel = (TextInputLayout) root.findViewById(R.id.til_name);
        mPhoneNumberLabel = (TextInputLayout) root.findViewById(R.id.til_phone_number);
        mSpecialtyLabel = (TextInputLayout) root.findViewById(R.id.til_specialty);
        mBioLabel = (TextInputLayout) root.findViewById(R.id.til_bio);

        //Eventos
        mSaveButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                addEditLawyer();
            }
        });

        mLawyersDbHelper = new LawyersDbHelper(getActivity());

        //Cargamos los datos (cuando sea necesario)
        if(mLawyerId != null){
            loadLawyer();
        }

        return root;
    }

    private void addEditLawyer() {
        boolean error = false;

        String name = mNameField.getText().toString();
        String phoneNumber = mPhoneNumberField.getText().toString();
        String specialty = mSpecialtyField.getText().toString();
        String bio = mBioField.getText().toString();

        if (TextUtils.isEmpty(name)) {
            mNameLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(phoneNumber)) {
            mPhoneNumberLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(specialty)) {
            mSpecialtyLabel.setError(getString(R.string.field_error));
            error = true;
        }


        if (TextUtils.isEmpty(bio)) {
            mBioLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (error) {
            return;
        }

        Lawyer lawyer = new Lawyer(name, specialty, phoneNumber, bio, "");

        new AddEditLawyerTask().execute(lawyer);
    }

    private void loadLawyer() {
        //Acciones
        new GetLawyerByIdTask().execute();
    }

    private class AddEditLawyerTask extends AsyncTask<Lawyer, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Lawyer... lawyers) {
            if(mLawyerId != null){
                return mLawyersDbHelper.updateLawyer(lawyers[0], mLawyerId) > 0;
            }else{
                return mLawyersDbHelper.saveLawyer(lawyers[0]) > 0;
            }
        }

        @Override
        protected void onPostExecute(Boolean result){
            showLawyersScreen(result);
        }
    }

    private void showLawyersScreen(Boolean requery) {
        if(!requery){
            showAddEditError();
            getActivity().setResult(Activity.RESULT_CANCELED);
        }else{
            getActivity().setResult(Activity.RESULT_OK);
        }
    }

    private void showAddEditError() {
        Toast.makeText(getActivity(), "Error al añadir nueva información", Toast.LENGTH_SHORT).show();
    }

    private class GetLawyerByIdTask extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected Cursor doInBackground(Void... voids) {
            return mLawyersDbHelper.getLawyerById(mLawyerId);
        }

        @Override
        protected void onPostExecute(Cursor cursor){
            if(cursor != null && cursor.moveToLast()){
                showLawyer(new Lawyer(cursor));
            }else{
                showLoadError();
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        }
    }

    private void showLoadError() {
        Toast.makeText(getActivity(), "Error al editar el abogado", Toast.LENGTH_SHORT).show();
    }

    private void showLawyer(Lawyer lawyer) {
        mNameField.setText(lawyer.getName());
        mPhoneNumberField.setText(lawyer.getPhoneNumber());
        mSpecialtyField.setText(lawyer.getSpecialty());
        mBioField.setText(lawyer.getBio());
    }


}