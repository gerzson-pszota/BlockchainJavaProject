package transaction;

import chain.StringUtil;

import java.security.*;
import java.util.ArrayList;

public class Transaction {

  public String transactionId; // this is also the hash of the transaction.
  public PublicKey sender; // senders address/public key.
  public PublicKey reciepient; // Recipients address/public key.
  public float value;
  public byte[] signature; // this is to prevent anybody else from spending funds in our wallet.

  public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
  public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();

  private static int sequence = 0; // a rough count of how many transactions have been generated.

  public Transaction(
      PublicKey from, PublicKey to, float value, ArrayList<TransactionInput> inputs) {
    this.sender = from;
    this.reciepient = to;
    this.value = value;
    this.inputs = inputs;
  }

  // signs all the data we don't wish to be tampered with.
  public void generateSignature(PrivateKey privateKey) {
    String data =
        StringUtil.getStringFromKey(sender)
            + StringUtil.getStringFromKey(reciepient)
            + Float.toString(value);
    signature = StringUtil.applyECDSASig(privateKey, data);
  }

  // verifies the data we signed hasn't been tampered with
  public boolean verifiySignature() {
    String data =
        StringUtil.getStringFromKey(sender)
            + StringUtil.getStringFromKey(reciepient)
            + Float.toString(value);
    return StringUtil.verifyECDSASig(sender, data, signature);
  }

  // this calculates the transaction hash (which will be used as its Id)
  private String calculateHash() {
    sequence++;
    return StringUtil.applySha256(
        StringUtil.getStringFromKey(sender)
            + StringUtil.getStringFromKey(reciepient)
            + Float.toString(value)
            + sequence);
  }
}
