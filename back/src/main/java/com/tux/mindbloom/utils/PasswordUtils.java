package com.tux.mindbloom.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class PasswordUtils {
  private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
  private static final int SALT_LENGTH = 32; // 16 octets pour le sel

  private PasswordUtils() {
  }

  /**
   * Decrypt the password, usually sent from the frontend.
   *
   * @param encryptedPassword The password to decrypt
   * @return The decrypted password
   */
  public static String decryptPassword(String encryptedPassword) {
    // Séparer l'IV du texte chiffré
    if (encryptedPassword == null || !encryptedPassword.contains(":")) {
      throw new IllegalArgumentException("Invalid encrypted password format");
    }
    if (PasswordUtils.getSecretKey() == null) {
      throw new IllegalStateException("Secret key for decryption is not configured");
    }

    String[] parts = encryptedPassword.split(":");
    String ivHex = parts[0];
    String cipherText = parts[1];

    // Convertir l'IV de la chaîne hexadécimale en bytes
    IvParameterSpec iv = new IvParameterSpec(hexStringToByteArray(ivHex));

    // Initialiser le Cipher avec l'IV
    SecretKeySpec keySpec = new SecretKeySpec(PasswordUtils.getSecretKey().getBytes(StandardCharsets.UTF_8), "AES");
    try {
      Cipher cipher = Cipher.getInstance(ALGORITHM);
      cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);

      // Déchiffrer les données chiffrées
      byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(cipherText));
      String decryptedPasswordWithSalt = new String(decryptedBytes);

      // Extraire le mot de passe sans le sel (si applicable)
      return decryptedPasswordWithSalt.substring(0, decryptedPasswordWithSalt.length() - SALT_LENGTH);
    } catch (Exception e) {
      throw new IllegalStateException("Error while decrypting the password", e);
    }
  }

  // Méthode pour convertir une chaîne hexadécimale en tableau de bytes
  private static byte[] hexStringToByteArray(String s) {
    int len = s.length();
    byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
      data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
        + Character.digit(s.charAt(i + 1), 16));
    }
    return data;
  }

  public static String getSecretKey() {
    return "your-secret-keyy";
  }
}
