package data;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.UUID;

/*ENTIDAD ABOGADO*/
public class Lawyer {

    /*ATRIBUTOS*/
    private String id;
    private String name;
    private String specialty;
    private String phoneNumber;
    private String bio;
    private String avatarUri;

    /*CONSTRUCTOR*/
    /*Generamos el método constructor sin pasarle el ID porque se lo vamos a
    * asignar de manera aleatoria haciendo uso de la UUID*/
    public Lawyer(String name, String specialty, String phoneNumber, String bio, String avatarUri) {
        this.id = UUID.randomUUID().toString(); //Generamos la id automáticamente.
        this.name = name;
        this.specialty = specialty;
        this.phoneNumber = phoneNumber;
        this.bio = bio;
        this.avatarUri = avatarUri;
    }
    /*MÉTODOS GETTER*/
    public String getName() {
        return name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getBio() {
        return bio;
    }

    public String getAvatarUri() {
        return avatarUri;
    }

    /*Conversor a pares clave-valor*/
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(LawyersContract.LawyerEntry.ID, id);
        values.put(LawyersContract.LawyerEntry.NAME, name);
        values.put(LawyersContract.LawyerEntry.SPECIALTY, specialty);
        values.put(LawyersContract.LawyerEntry.PHONE_NUMBER, phoneNumber);
        values.put(LawyersContract.LawyerEntry.BIO, bio);
        values.put(LawyersContract.LawyerEntry.AVATAR_URI, avatarUri);
        return values;
    }



}
