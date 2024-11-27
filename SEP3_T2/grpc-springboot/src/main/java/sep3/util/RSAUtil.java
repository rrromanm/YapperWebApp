package sep3.util;

import java.math.BigInteger;

public class RSAUtil {
    private int prime1;
    private int prime2;
    private int modulus;
    private int publicExponent;
    private int privateExponent;

    public RSAUtil(int prime1, int prime2, int publicExponent) {
        this.prime1 = prime1;
        this.prime2 = prime2;
        this.modulus = prime1 * prime2;
        this.publicExponent = publicExponent;
        this.privateExponent = this.calculatePrivateExponent();
    }

    private int calculatePrivateExponent() {
        int phi = (prime1 - 1) * (prime2 - 1);
        BigInteger eBigInt = BigInteger.valueOf(publicExponent);
        BigInteger phiBigInt = BigInteger.valueOf(phi);
        BigInteger dBigInt = eBigInt.modInverse(phiBigInt);
        return dBigInt.intValue();
    }

    public String encrypt(String message) {
        StringBuilder encryptedMessage = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            char character = message.charAt(i);
            BigInteger m = BigInteger.valueOf((int) character);
            BigInteger encrypted = m.modPow(BigInteger.valueOf(publicExponent), BigInteger.valueOf(modulus));
            encryptedMessage.append(encrypted).append(" ");
        }
        return encryptedMessage.toString().trim();
    }

    public String decrypt(String encryptedMessage) {
        StringBuilder decryptedMessage = new StringBuilder();
        String[] encryptedChars = encryptedMessage.split(" ");
        for (String encryptedChar : encryptedChars) {
            BigInteger encrypted = new BigInteger(encryptedChar);
            BigInteger decrypted = encrypted.modPow(BigInteger.valueOf(privateExponent), BigInteger.valueOf(modulus));
            char decryptedChar = (char) decrypted.intValue();
            decryptedMessage.append(decryptedChar);
        }
        return decryptedMessage.toString();
    }
}
