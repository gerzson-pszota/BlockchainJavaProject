import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class Wallet {
  public PrivateKey privateKey;
  public PublicKey publicKey;

  // this method uses Java.security.KeyPairGenerator to generate an Elliptic Curve KeyPair
  // it makes and sets Public and Private keys
  public void generateKeyPair() {
    try {
      KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECFSA", "BC");
      SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
      ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
      // initialize the key generator and generate a KeyPair
      keyGen.initialize(ecSpec, random);
      KeyPair keyPair = keyGen.generateKeyPair();
      // set the public and private keys from the KeyPair
      privateKey = keyPair.getPrivate();
      publicKey = keyPair.getPublic();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
