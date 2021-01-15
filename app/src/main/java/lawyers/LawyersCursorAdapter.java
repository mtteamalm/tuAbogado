package lawyers;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.tuabogado.R;

import data.LawyersContract;

/** Adaptador de Abogados*/
public class LawyersCursorAdapter extends CursorAdapter {

    /** Constructor */
    public LawyersCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    /**Implementación de los métodos*/
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_lawyer, parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        //Referencias de nuestra Interfaz de Usuario
        TextView nameText = (TextView) view.findViewById(R.id.TextViewName);
        final ImageView avatarImage = (ImageView) view.findViewById(R.id.ImageViewAvatar);

        //Obtenemos los valores
        String name = cursor.getString(cursor.getColumnIndex(LawyersContract.LawyerEntry.NAME));
        String avatarUri = cursor.getString(cursor.getColumnIndex(LawyersContract.LawyerEntry.AVATAR_URI));

        //Asignamos los valores
        nameText.setText(name);

        //Hacemos uso de la librería externa Glide para ello. Hay un video en openwebinars de esto
        Glide
                .with(context)
                .asBitmap()
                .load(Uri.parse("file:///android_asset/" + avatarUri))
                .error(R.drawable.ic_baseline_account_circle_24_white)
                .centerCrop()
                .into(new BitmapImageViewTarget(avatarImage){
                    @Override
                    protected void setResource(Bitmap resource){
                        RoundedBitmapDrawable drawable
                                = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        drawable.setCircular(true);
                        avatarImage.setImageDrawable(drawable);
                    }
                });
    }
}
