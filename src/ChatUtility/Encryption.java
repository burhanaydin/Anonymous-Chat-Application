
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatUtility;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
/**
 *
 * @author pcc
 */
public class Encryption {
    
    private static final String ALGORITHM ="RSA";
    private static final Path PRIVATE_KEY_FILE = Paths.get("PrivateKeyFile.txt");
    private static final Path PUBLIC_KEY_FILE = Paths.get("PublicKeyFile.txt");
    private File privateKeyFile = null;
    private File publicKeyFile = null;
    private KeyPairGenerator keyGenerator = null;
    private KeyPair pair = null;
    private Cipher cipher = null;
    private PublicKey publicKey = null;
    private PrivateKey privateKey = null;
    private DataInputStream is1 = null;
    private DataInputStream is2 = null;

    public Encryption(){
            privateKeyFile = new File(PRIVATE_KEY_FILE.toString());
            publicKeyFile = new File(PUBLIC_KEY_FILE.toString());
    }
    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }
    public void closeStreams(){
        try {
            is1.close();
            is2.close();
        } catch (IOException ex) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void generateKey(){
        
        try{
            this.keyGenerator = KeyPairGenerator.getInstance(ALGORITHM);
            keyGenerator.initialize(1024);
            this.pair = keyGenerator.generateKeyPair();
            

            if(privateKeyFile.getParentFile() != null){
                privateKeyFile.getParentFile().mkdirs();
            }
            privateKeyFile.createNewFile();
            if(publicKeyFile.getParentFile() != null){
                publicKeyFile.getParentFile().mkdirs();
            }
            publicKeyFile.createNewFile();
            
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(privateKeyFile));
            os.write(pair.getPrivate().getEncoded());
            this.privateKey = pair.getPrivate();
            os.close();
            
            ObjectOutputStream os2 = new ObjectOutputStream(new FileOutputStream(publicKeyFile));
            os2.write(pair.getPublic().getEncoded());
            this.publicKey = pair.getPublic();       
            os2.close();
            
        } catch (NoSuchAlgorithmException | IOException ex) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public boolean isKeyPresent(){
        
        File prKeyFile = new File(PRIVATE_KEY_FILE.toString());
        File pubKeyFile = new File(PUBLIC_KEY_FILE.toString());
        return !prKeyFile.exists() || !pubKeyFile.exists();
   
    }
    public String encrypt(String message){
        byte [] encryptedMessage = new byte[1024];
        
        
        try{
            is1 = new DataInputStream(new FileInputStream(publicKeyFile)); 
            byte [] encodedPublicKey = new byte[is1.available()];
            is1.readFully(encodedPublicKey); 
           // encodedPublicKey.toString().replaceAll("-----END PUBLIC KEY-----", "");
           // encodedPublicKey.toString().replaceAll("-----BEGIN PUBLIC KEY-----", "");
           // encodedPublicKey = Base64.getEncoder().encode(encodedPublicKey);
            // create public key
           // X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
            //KeyFactory kf = KeyFactory.getInstance("RSA");            
            //PublicKey pk = kf.generatePublic(publicKeySpec);
            this.cipher = Cipher.getInstance(ALGORITHM);
            this.cipher.init(Cipher.ENCRYPT_MODE, this.publicKey);
            if(message != null) {
                encryptedMessage = cipher.doFinal(message.getBytes());
            }else{
                return "";
            }
            
        }catch(NoSuchAlgorithmException | NoSuchPaddingException |
              InvalidKeyException | IllegalBlockSizeException | BadPaddingException e){
        } catch (IOException  ex) {
             Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        
        return new String(encryptedMessage);
    }
    public  String decrypt(byte [] message, String privateKey){
        byte [] decryptedMessage = null;
        byte [] encodedPrivateKey = null;
        PrivateKey pk = null;
        
        try{
            if(privateKey == null){
                is2 = new DataInputStream(new FileInputStream(privateKeyFile));            
                is2.readFully(encodedPrivateKey);
              // create public key
                PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
                KeyFactory kf = KeyFactory.getInstance("RSA");
                pk = kf.generatePrivate(privateKeySpec);
            }else{
                pk = toPrivateKey(privateKey);
            }           
            this.cipher = Cipher.getInstance(ALGORITHM);
            this.cipher.init(Cipher.DECRYPT_MODE,this.privateKey);
            decryptedMessage = cipher.doFinal(message);
            
        }catch(NoSuchAlgorithmException | NoSuchPaddingException |
                InvalidKeyException | IllegalBlockSizeException | BadPaddingException e){
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralSecurityException ex) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new String(decryptedMessage);
    }
    
    public static PrivateKey toPrivateKey(String key64) throws GeneralSecurityException {
        byte[] clear = Base64.getDecoder().decode(key64);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(clear);
        KeyFactory fact = KeyFactory.getInstance("RSA");
        PrivateKey priv = fact.generatePrivate(keySpec);
        Arrays.fill(clear, (byte) 0);
        return priv;
    }

    public static PublicKey toPublicKey(String stored) throws GeneralSecurityException {
        byte[] clear = Base64.getDecoder().decode(stored);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(clear);
        KeyFactory fact = KeyFactory.getInstance("RSA");
        Arrays.fill(clear, (byte) 0);
        return fact.generatePublic(spec);
    }

    public static String fromPrivateKey(PrivateKey priv) throws GeneralSecurityException {
        KeyFactory fact = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec spec = fact.getKeySpec(priv, PKCS8EncodedKeySpec.class);
        return Base64.getEncoder().encodeToString(spec.getEncoded());
    }
    
    public static String fromPublicKey(PublicKey publ) throws GeneralSecurityException {
        KeyFactory fact = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec spec = fact.getKeySpec(publ,X509EncodedKeySpec.class);
        return Base64.getEncoder().encodeToString(spec.getEncoded());
    }  
}
