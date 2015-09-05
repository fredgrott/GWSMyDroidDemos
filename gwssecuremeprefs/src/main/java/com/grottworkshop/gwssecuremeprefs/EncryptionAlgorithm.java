package com.grottworkshop.gwssecuremeprefs;

/**
 * Encryption algorithm.
 * @author NoTiCe
 * Created by fgrott on 9/5/2015.
 */
public interface EncryptionAlgorithm {
    /**
     * Encrypts the given bytes.
     * @param bytes
     *            The bytes to encrypt.
     * @return The encrypted bytes.
     * @throws EncryptionException When the encryiption fails.
     */
    byte[] encrypt(byte[] bytes) throws EncryptionException;

    /**
     * Decrypts the given bytes.
     * @param bytes
     *            The bytes to decrypt.
     * @return The decrypted bytes.
     * @throws EncryptionException When the encryiption fails.
     */
    byte[] decrypt(byte[] bytes) throws EncryptionException;
}
