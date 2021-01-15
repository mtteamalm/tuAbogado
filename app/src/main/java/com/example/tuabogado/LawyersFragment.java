package com.example.tuabogado;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import data.LawyersDbHelper;
import lawyers.LawyersCursorAdapter;

public class LawyersFragment extends Fragment {

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

        //Instancia del Helper
        mLawyersDbHelper = new LawyersDbHelper(getActivity());

        //Cargamos los datos
        loadLawyers();

        return root;
    }

    private void loadLawyers() {
        //Acciones para la carga de Datos
        new LawyersLoadTask().execute();
    }

    /** Para que la lista se actulice en caso de que haya habido
     * alguna modificación u alta*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

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