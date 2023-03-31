package com.ulca.benchmark.util;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptDcryptService {

	public static String encrypt(String plaintext, String key) {
		try {
			// Generate a random 16-byte initialization vector
			byte initVector[] = new byte[16];
			(new Random()).nextBytes(initVector);
			IvParameterSpec iv = new IvParameterSpec(initVector);

			// prep the key
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			// prep the AES Cipher
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			// Encode the plaintext as array of Bytes
			byte[] cipherbytes = cipher.doFinal(plaintext.getBytes());

			// Build the output message initVector + cipherbytes -> base64
			byte[] messagebytes = new byte[initVector.length + cipherbytes.length];

			System.arraycopy(initVector, 0, messagebytes, 0, 16);
			System.arraycopy(cipherbytes, 0, messagebytes, 16, cipherbytes.length);

			// Return the cipherbytes as a Base64-encoded string
			return Base64.getEncoder().encodeToString(messagebytes);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	// Base64-encoded String ciphertext -> String plaintext
	public static String decrypt(String ciphertext, String key) {
		try {
			byte[] cipherbytes = Base64.getDecoder().decode(ciphertext);

			byte[] initVector = Arrays.copyOfRange(cipherbytes, 0, 16);

			byte[] messagebytes = Arrays.copyOfRange(cipherbytes, 16, cipherbytes.length);

			IvParameterSpec iv = new IvParameterSpec(initVector);
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

			// Convert the ciphertext Base64-encoded String back to bytes, and
			// then decrypt
			byte[] byte_array = cipher.doFinal(messagebytes);

			// Return plaintext as String
			return new String(byte_array, StandardCharsets.UTF_8);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/*
	 * public static void main(String[] args) { // TODO Auto-generated method stub
	 * 
	 * String key = "TjWnZr4u7xD*G-KaPdRgUkXp2s5v8ntu"; String message = "Parshant";
	 * // either plaintext or encrypted String encryptedVal
	 * ="DGV6BZDcTUa1ZWf8fvYHrLN59B2zEra0sDjEZEdQl3k="; // String encrypted =
	 * encrypt(message, key);
	 * 
	 * // System.out.println(encrypted);
	 * 
	 * System.out.println(decrypt(encryptedVal, key));
	 * 
	 * }
	 */
}
