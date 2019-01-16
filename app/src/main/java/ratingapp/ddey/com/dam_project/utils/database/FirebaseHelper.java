package ratingapp.ddey.com.dam_project.utils.database;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class FirebaseHelper {
    private static final String STORAGE_PATH = "Images";
    private static final String RESULTS = "Results";


    private DatabaseReference imagesReference;
    private DatabaseReference resultsReference;
    private FirebaseDatabase helper;
    private static FirebaseHelper firebaseHelper;
    private FirebaseStorage storage;

    public FirebaseHelper() {
        helper = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    public static FirebaseHelper getInstance() {
        if (firebaseHelper == null) {
            synchronized (FirebaseHelper.class) {
                if (firebaseHelper == null) {
                    firebaseHelper = new FirebaseHelper();
                }
            }
        }
        return firebaseHelper;
    }

    public void openConnection() {
        resultsReference = helper.getReference(RESULTS);
        imagesReference = helper.getReference(STORAGE_PATH);
    }

    public DatabaseReference getImagesReference() {
        return imagesReference;
    }

    public void setImagesReference(DatabaseReference imagesReference) {
        this.imagesReference = imagesReference;
    }

    public DatabaseReference getResultsReference() {
        return resultsReference;
    }

    public void setResultsReference(DatabaseReference resultsReference) {
        this.resultsReference = resultsReference;
    }

    public FirebaseDatabase getHelper() {
        return helper;
    }

    public void setHelper(FirebaseDatabase helper) {
        this.helper = helper;
    }

    public static FirebaseHelper getFirebaseHelper() {
        return firebaseHelper;
    }

    public static void setFirebaseHelper(FirebaseHelper firebaseHelper) {
        FirebaseHelper.firebaseHelper = firebaseHelper;
    }

    public FirebaseStorage getStorage() {
        return storage;
    }

    public void setStorage(FirebaseStorage storage) {
        this.storage = storage;
    }
}
