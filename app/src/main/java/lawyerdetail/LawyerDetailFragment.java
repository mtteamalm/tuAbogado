package lawyerdetail;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tuabogado.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import data.Lawyer;
import data.LawyersDbHelper;

/**
 * Vista para el detalle del abogado
 */
public class LawyerDetailFragment extends Fragment {

    private String mLawyerId;

    private CollapsingToolbarLayout mCollapsingView;
    private ImageView mAvatar;
    private TextView mPhoneNumber;
    private TextView mSpecialty;
    private TextView mBio;

    private LawyersDbHelper mLawyersDbHelper;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_LAWYER_ID = "lawyerId";


    public LawyerDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param LawyerId Parameter 1.
     * @return A new instance of fragment LawyerDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LawyerDetailFragment newInstance(String LawyerId) {
        LawyerDetailFragment fragment = new LawyerDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LAWYER_ID, LawyerId);
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
        View root = inflater.inflate(R.layout.fragment_lawyer_detail, container, false);

        //Asignamos los valores de nuestros atributos
        mCollapsingView = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        mAvatar = (ImageView) getActivity().findViewById(R.id.ImageViewAvatar);
        mPhoneNumber = (TextView) root.findViewById(R.id.TextViewPhoneNumber);
        mSpecialty = (TextView) root.findViewById(R.id.TextViewSpecialty);
        mBio = (TextView) root.findViewById(R.id.TextViewBio);

        mLawyersDbHelper =  new LawyersDbHelper(getActivity());

        //Método para cargar un abogado que a su vez usara una Tarea asíncrona
        loadLawyer();

        return root;
    }

    private void loadLawyer() {
        new GetLawyerByIdTask().execute();
    }

    /** MÉTODOS QUE USAREMOS DENTRO DE NUESTRA TAREA*/
    private void showLawyer(Lawyer lawyer){
        mCollapsingView.setTitle(lawyer.getName().toString());
        Glide.with(this)
                .load(Uri.parse("file:///android_asset/" + lawyer.getAvatarUri()))
                .centerCrop()
                .into(mAvatar);
        mPhoneNumber.setText(lawyer.getPhoneNumber());
        mSpecialty.setText(lawyer.getSpecialty());
        mBio.setText(lawyer.getBio());
    }

    private void showLoadError(){
        Toast.makeText(getActivity(), "Error al cargar la información solicitada",
                Toast.LENGTH_SHORT).show();
    }

    /** Tarea Asíncrona para la carga de un abogado por su Id */
    private class GetLawyerByIdTask extends AsyncTask<Void, Void, Cursor>{

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mLawyersDbHelper.getLawyerById(mLawyerId);
        }

        /** También sobreescribimos el método onPostExecute*/
        @Override
        protected void onPostExecute(Cursor cursor){
            if(cursor != null && cursor.moveToLast()){
                showLawyer(new Lawyer(cursor));
            }else {
                showLoadError();
            }
        }
    }
}