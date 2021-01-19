package com.example.tuabogado;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import addeditlawyer.AddEditLawyerActivity;
import data.LawyersContract;
import data.LawyersDbHelper;
import lawyerdetail.LawyerDetailActivity;
import lawyerdetail.LawyerDetailFragment;
import lawyers.LawyersActivity;
import lawyers.LawyersCursorAdapter;

public class LawyersFragment extends Fragment {

    public static final int REQUEST_UPDATE_DELETE_LAWYER = 2;

    private LawyersDbHelper mLawyersDbHelper;

    private ListView mLawyersList;
    private LawyersCursorAdapter mLawyersAdapter;
    private FloatingActionButton mAddButton;

    public LawyersFragment() {
        // Required empty public constructor
    }

    public static LawyersFragment newInstance() {
        return new LawyersFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_lawyers, container, false);

        // Referencias de nuestra Interfaz de Usuario
        mLawyersList = (ListView) root.findViewById(R.id.lawyers_list);
        mLawyersAdapter = new LawyersCursorAdapter(getActivity(), null);
        mAddButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        //Asignación
        mLawyersList.setAdapter(mLawyersAdapter);
        
        /** EVENTOS */
        mLawyersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor currentItem = (Cursor) mLawyersAdapter.getItem(position);
                String currentLawyerId = currentItem.getString(
                        currentItem.getColumnIndex(LawyersContract.LawyerEntry.ID));
                
                showDetailScreen(currentLawyerId);
            }
        });

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddScreen();
            }
        });
        /** FIN EVENTOS*/

        //Instancia del Helper
        mLawyersDbHelper = new LawyersDbHelper(getActivity());

        //Cargamos los datos
        loadLawyers();

        return root;
    }

    private void showAddScreen() {
        Intent intent = new Intent(getActivity(), AddEditLawyerActivity.class);
        startActivityForResult(intent, AddEditLawyerActivity.REQUEST_ADD_LAWYER);
    }

    private void showDetailScreen(String lawyerId) {
        Intent intent = new Intent(getActivity(), LawyerDetailActivity.class);
        intent.putExtra(LawyersActivity.EXTRA_LAWYER_ID, lawyerId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_LAWYER);
    }

    private void loadLawyers() {
        //Acciones para la carga de Datos
        new LawyersLoadTask().execute();
    }

    /** Para que la lista se actulice en caso de que haya habido
     * alguna modificación u alta*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(Activity.RESULT_OK == resultCode){
            switch (requestCode){
                case AddEditLawyerActivity.REQUEST_ADD_LAWYER:
                    showSuccessfullSavedMessage();
                    loadLawyers();
                    break;
                case REQUEST_UPDATE_DELETE_LAWYER:
                    loadLawyers();
                    break;
            }
        }

    }

    private void showSuccessfullSavedMessage() {
        Toast.makeText(getActivity(),
                "Alta de Abogado realizada correctamente", Toast.LENGTH_SHORT).show();
    }

    /** Tarea Asíncrona para la carga de los abogados*/
    private class LawyersLoadTask extends AsyncTask<Void, Void, Cursor>{

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mLawyersDbHelper.getAllLawyers();
        }

        @Override
        protected void onPostExecute(Cursor cursor){
            if(cursor != null && cursor.getCount() > 0){
                mLawyersAdapter.swapCursor(cursor);
            }else {
                //Mostramos que la lista está vacía - empty state
            }
        }
    }
}