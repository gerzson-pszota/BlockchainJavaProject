import java.util.Date;

public class Block {

  public String hash; // digital signature
  public String previousHash; // previous block's hash
  private String data; // block data
  private Long timeStamp; // as number of milliseconds since 1/1/1970

  public Block(String data, String previousHash) {
    this.data = data;
    this.previousHash = previousHash;
    this.timeStamp = new Date().getTime();
    this.hash = calculateHash();
  }

  public String calculateHash() {
    return StringUtil.applySha256(previousHash + Long.toString(timeStamp) + data);
  }
}
