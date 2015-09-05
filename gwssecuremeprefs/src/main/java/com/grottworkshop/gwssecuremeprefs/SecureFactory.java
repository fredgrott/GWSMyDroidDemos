package com.grottworkshop.gwssecuremeprefs;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;


/**
 * A factory class to ease the creation of the SecureSharedPreferences instance.
 * @author NoTiCe
 * Created by fgrott on 9/5/2015.
 */
public final class SecureFactory {
    private static final String INITIALIZATION_ERROR = "Can not initialize SecureSharedPreferences";
    public static final int VERSION_1 = 1;
    public static final int LATEST_VERSION = VERSION_1;
    //private static final Logger LOGGER = LoggerFactory.getLogger(SecureFactory.class);

    /**
     * Hidden util constructor.
     */
    private SecureFactory() {
    }

    /**
     * Creates the {@link SecureSharedPreferences} instance with a given original and an {@link EncryptionAlgorithm}.
     * This function does a version check and the required migrations when the local structure is outdated or not encrypted yet.
     * @param original The original {@link SharedPreferences}, which can be also a {@link SecureSharedPreferences} instance.
     * @param encryption The {@link EncryptionAlgorithm} to use.
     * @return A {@link SecureSharedPreferences} instance.
     */
    public static SecureSharedPreferences getPreferences(SharedPreferences original, EncryptionAlgorithm encryption) {
        SecureSharedPreferences sharedPreferences;
        if (original instanceof SecureSharedPreferences) {
            sharedPreferences = (SecureSharedPreferences) original;
        } else {
            sharedPreferences = new SecureSharedPreferences(original, encryption);
        }
        if (SecureUtils.getVersion(sharedPreferences) < VERSION_1) {
            //Log.i("Initial migration to Secure storage.");
            SecureUtils.migrateData(original, sharedPreferences, VERSION_1);
        }
        return sharedPreferences;
    }

    /**
     * Creates the {@link SecureSharedPreferences} instance with a given original and an {@link EncryptionAlgorithm}.
     * This function does a version check and the required migrations when the local structure is outdated or not encrypted yet.
     * @param original The original {@link SharedPreferences}, which can be also a {@link SecureSharedPreferences} instance.
     * @param password The password to use. This will use the {@link Encryption} implementation of the {@link EncryptionAlgorithm}.
     * @return A {@link SecureSharedPreferences} instance.
     * @throws SecurityException When the {@link EncryptionAlgorithm} can not be initialized.
     */
    public static SecureSharedPreferences getPreferences(SharedPreferences original, String password) throws SecurityException, NoSuchAlgorithmException, NoSuchPaddingException {
        try {
            EncryptionAlgorithm encryption = new Encryption(password);
            return getPreferences(original, encryption);
        } catch (UnsupportedEncodingException e) {
            throw new SecurityException(INITIALIZATION_ERROR, e);
        }
    }

    /**
     * Creates a {@link SecureSharedPreferences} instance.
     * @param context The current context.
     * @param preferencesName The name of the {@link SharedPreferences}.
     * @param password The password
     * @return The initialized {@link SecureSharedPreferences}.
     */
    public static SecureSharedPreferences getPreferences(Context context, String preferencesName, String password) throws NoSuchAlgorithmException, NoSuchPaddingException {
        try {
            return getPreferences(context, preferencesName, new Encryption(password));
        } catch (UnsupportedEncodingException e) {
            throw new SecurityException(INITIALIZATION_ERROR, e);
        }
    }

    /**
     * Creates a {@link SecureSharedPreferences} instance.
     * @param context The current context.
     * @param preferencesName The name of the {@link SharedPreferences}.
     * @param encryption The {@link EncryptionAlgorithm} to use.
     * @return The initialized {@link SecureSharedPreferences}.
     */
    public static SecureSharedPreferences getPreferences(Context context, String preferencesName, EncryptionAlgorithm encryption) {
        return getPreferences(context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE), encryption);
    }
}
