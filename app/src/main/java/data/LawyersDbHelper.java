
package data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class LawyersDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Lawyers.db";

    public LawyersDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //CREACIÓN DE LA BASE DE DATOS
        db.execSQL("CREATE TABLE " + LawyersContract.LawyerEntry.TABLE_NAME + " ("
                + LawyersContract.LawyerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + LawyersContract.LawyerEntry.ID + " TEXT NOT NULL,"
                + LawyersContract.LawyerEntry.NAME + " TEXT NOT NULL,"
                + LawyersContract.LawyerEntry.SPECIALTY + " TEXT NOT NULL,"
                + LawyersContract.LawyerEntry.PHONE_NUMBER + " TEXT NOT NULL,"
                + LawyersContract.LawyerEntry.BIO + " TEXT NOT NULL,"
                + LawyersContract.LawyerEntry.AVATAR_URI + " TEXT,"
                + "UNIQUE (" + LawyersContract.LawyerEntry.ID + "))");
        //FIN CREACIÓN DE LA BASE DE DATOS

        //INSERTAR DATOS
        // Insertar datos ficticios para prueba inicial
        mockData(db);
        //FIN INSERTAR DATOS
    }

    private void mockData(SQLiteDatabase sqLiteDatabase) {
        mockLawyer(sqLiteDatabase, new Lawyer("Carlos Perez", "Abogado penalista",
                "300 200 1111", "Gran profesional con experiencia de 5 años en casos penales.",
                "carlos_perez.jpg"));
        mockLawyer(sqLiteDatabase, new Lawyer("Daniel Samper", "Abogado accidentes de tráfico",
                "300 200 2222", "Gran profesional con experiencia de 5 años en accidentes de tráfico.",
                "daniel_samper.jpg"));
        mockLawyer(sqLiteDatabase, new Lawyer("Lucia Aristizabal", "Abogado de derechos laborales",
                "300 200 3333", "Gran profesional con más de 3 años de experiencia en defensa de los trabajadores.",
                "lucia_aristizabal.jpg"));
        mockLawyer(sqLiteDatabase, new Lawyer("Marina Acosta", "Abogado de familia",
                "300 200 4444", "Gran profesional con experiencia de 5 años en casos de familia.",
                "marina_acosta.jpg"));
        mockLawyer(sqLiteDatabase, new Lawyer("Olga Ortiz", "Abogado de administración pública",
                "300 200 5555", "Gran profesional con experiencia de 5 años en casos en expedientes de urbanismo.",
                "olga_ortiz.jpg"));
        mockLawyer(sqLiteDatabase, new Lawyer("Pamela Briger", "Abogado fiscalista",
                "300 200 6666", "Gran profesional con experiencia de 5 años en casos de derecho financiero",
                "pamela_briger.jpg"));
        mockLawyer(sqLiteDatabase, new Lawyer("Rodrigo Benavidez", "Abogado Mercantilista",
                "300 200 1111", "Gran profesional con experiencia de 5 años en redacción de contratos mercantiles",
                "rodrigo_benavidez.jpg"));
        mockLawyer(sqLiteDatabase, new Lawyer("Tom Bonz", "Abogado penalista",
                "300 200 1111", "Gran profesional con experiencia de 5 años en casos penales.",
                "tom_bonz.jpg"));
    }

    private long mockLawyer(SQLiteDatabase db, Lawyer lawyer) {
        return db.insert(
                LawyersContract.LawyerEntry.TABLE_NAME,
                null,
                lawyer.toContentValues());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //De momento no ponemos nada
    }

    /*Metodo para la inserción de un nuevo abogado*/
    public long saveLawyer(Lawyer lawyer){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(DATABASE_NAME, null, lawyer.toContentValues());
    }

    /*Método para obtener todos los abogados*/
    public Cursor getAllLawyers(){
        return getReadableDatabase()
                .query(
                        LawyersContract.LawyerEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    /*Búsqueda de abogado por ID*/
    public Cursor getLawyerById(String lawyerId){
        Cursor c = getReadableDatabase().query(
                LawyersContract.LawyerEntry.TABLE_NAME,
                null,
                LawyersContract.LawyerEntry.ID + "LIKE ?",
                new String[]{lawyerId},
                null,
                null,
                null);
        /*Retormanos el cursor resultado de nuestra consulta*/
        return c;

    }
}
