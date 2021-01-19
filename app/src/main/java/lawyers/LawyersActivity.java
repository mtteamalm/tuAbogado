package lawyers;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.appcompat.widget.Toolbar;

import com.example.tuabogado.LawyersFragment;
import com.example.tuabogado.R;

public class LawyersActivity extends AppCompatActivity {

    //Definimos un campo extra que nos será necesario para identificar a los abogados en el CRUD
    public static final String EXTRA_LAWYER_ID = "extra_lawyer_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyers);
        //Insertamos el Toolbar que añadimos en activity_lawyer.xml
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Añadimos nuestro fragmento
        LawyersFragment fragment = (LawyersFragment) getSupportFragmentManager()
                .findFragmentById(R.id.lawyers_container);
        if (fragment == null){
            fragment = LawyersFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.lawyers_container, fragment)
                    .commit();
        }
    }

}