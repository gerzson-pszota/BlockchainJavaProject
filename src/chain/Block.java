package chain;

import java.util.Date;

public class Block {

  public String hash; // digital signature
  public String previousHash; // previous block's hash
  private String data; // block data
  private long timeStamp; // as number of milliseconds since 1/1/1970
  private int nonce;

  public Block(String data, String previousHash) {
    this.data = data;
    this.previousHash = previousHash;
    this.timeStamp = new Date().getTime();
    this.hash = calculateHash();
  }

  public String calculateHash() {
    return StringUtil.applySha256(
        previousHash + Long.toString(timeStamp) + Integer.toString(nonce) + data);
  }

  // I use 4–6 for testing. (At the time of coding, Litecoin’s difficulty is around 32.67 Million)
  public void mineBlock(int difficulty) {
    String target =
        new String(new char[difficulty])
            .replace('\0', '0'); // Create a string with difficulty * "0"
    while (!hash.substring(0, difficulty).equals(target)) {
      nonce++;
      hash = calculateHash();
    }
    System.out.println("chain.Block mined!!! : " + hash);
  }
}
