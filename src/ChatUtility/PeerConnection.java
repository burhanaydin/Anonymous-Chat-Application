/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatUtility;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.security.GeneralSecurityException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.Position;
import org.json.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import uiElements.MainUI;

/**
 *
 * @author pcc
 */
public class PeerConnection {

    private static final byte[] messageReceived = new byte[1500];

    public static native void sender(int argc, String dest_Ip, String messageSent);
    
    public String peerToPeerPort = null;
    public String peerToPeerIP = null;
    public String broadcastIP = "255.255.255.255";
    public String peerToPeerNick = null;
    public boolean peerToPeerFlag = false;
    public int splitFactor = 5;

    public Encryption encryption = null;
    public domXMLParser xmlParser = null;
    public static String encrypted = "";
    private String decryptedMessage = "";
    public JSONObject jsonObjectSent = null;
    public JSONObject jsonObjectReceived = null;
    public JSONObject jsonObjectIntermediary = null;
    
    public peerNode peer = null;
    
    public String[] messageToSend = {"","","","",""}; 
    public List<HashMap<String,String>> incomingMessageList = null;
    
    private DatagramSocket socket = null;
    private DatagramPacket packet = null;
    
    public static Thread accepting = null;

    public PeerConnection() {

        incomingMessageList = new ArrayList<HashMap<String,String>>();
        encryption = new Encryption();
        xmlParser = new domXMLParser();

    }
    static {	
        System.loadLibrary("Send");
    }

    public DatagramSocket getSocket() {
        return socket;
    }

    public DatagramPacket getPacket() {
        return packet;
    }

    public void setPeerToPeerIP(String peerToPeerIP) {
        this.peerToPeerIP = peerToPeerIP;
    }

    public void setPeerToPeerPort(String peerToPeerPort) {
        this.peerToPeerPort = peerToPeerPort;
    }

    public void setPeerToPeerNick(String peerToPeerNick) {
        this.peerToPeerNick = peerToPeerNick;
    }

    public void setPeerToPeerFlag(boolean peerToPeerFlag) {
        this.peerToPeerFlag = peerToPeerFlag;
    }
    
    public void setEncryption(Encryption encryption) {
        this.encryption = encryption;
    }

    public  void setEncrypted(String encrypted) {
        PeerConnection.encrypted = encrypted;
    }

    public Encryption getEncryption() {
        return encryption;
    }

    public static String isEncrypted() {
        return encrypted;
    }
 
    public boolean isPeerToPeerFlag() {
        return peerToPeerFlag;
    }
    public List<HashMap<String, String>> getIncomingMessageList() {
        return Collections.unmodifiableList(incomingMessageList);
    }
    public HashMap<String,String> getMessage() {

        HashMap<String,String> incoming = incomingMessageList.get(0);
        incomingMessageList.remove(0);
        return incoming;

    }

    public void sendMessage(String protocol, String scope, String mode, String subnetid, String encrypted,  String message) {
        this.messageToSend[0] = scope;
        this.messageToSend[1] = mode;
        this.messageToSend[2] = subnetid;
        this.messageToSend[3] = encrypted;
        if(encrypted.equalsIgnoreCase("Encrypted")) {
            this.messageToSend[4] =  encryption.encrypt(message);
        } else {
            this.messageToSend[4] = message;
        }
 
        dispatch(protocol);

    }

    public void dispatch(String protocol) {
        List<peerNode> peers = xmlParser.reader(this.messageToSend[0], this.messageToSend[1], messageToSend[2]);
        Iterator<peerNode> it = peers.iterator();

        if(!protocol.equalsIgnoreCase("/Join")){
            MainUI.peerMe.privateKey = null;
            MainUI.peerMe.publicKey = null;
        }else{
            if (encryption.isKeyPresent()) {
                try {

                    MainUI.peerMe.privateKey = Encryption.fromPrivateKey(encryption.getPrivateKey());
                    MainUI.peerMe.publicKey = Encryption.fromPublicKey(encryption.getPublicKey());

                } catch (GeneralSecurityException ex) {
                    Logger.getLogger(PeerConnection.class.getName()).log(Level.SEVERE, null, ex);
                }

            } 
        }
        if(!peerToPeerFlag){
            
            while (it.hasNext()) {
                peer = it.next();
                jsonObjectSent = jsonBuilder(protocol, this.messageToSend[0], this.messageToSend[1]
                        ,messageToSend[2], messageToSend[3], MainUI.peerMe, messageToSend[4]);
                
                sending( peer.ip, jsonObjectSent.toString());
            }
            
            jsonObjectSent = jsonBuilder(protocol, this.messageToSend[0], this.messageToSend[1]
                        , messageToSend[2], messageToSend[3], MainUI.peerMe, messageToSend[4]);
            
            sending( broadcastIP, jsonObjectSent.toString());
        } 
        else if(peerToPeerFlag){

            jsonObjectSent = jsonBuilder(protocol, this.messageToSend[0], this.messageToSend[1]
                    , messageToSend[2], messageToSend[3], MainUI.peerMe, messageToSend[4]);
            
            sending( peerToPeerIP, jsonObjectSent.toString());
            sending( broadcastIP, jsonObjectSent.toString());
        }
    }

    public void accept() {
        try {
            if(PeerConnection.accepting == null) {
                socket = new DatagramSocket(7777);
            }

            packet = new DatagramPacket(messageReceived, messageReceived.length);
           
        } catch (SocketException ex) {
            Logger.getLogger(PeerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        accepting = new Thread(new Runnable() {
            
            public void run() {

                for (;;) {
                    
                    try {
                        
                        StringBuilder sBuilder = new StringBuilder();
                        Pattern pat = Pattern.compile("[0-9]");
                        Matcher mat;
                        for( int i=0 ; i < splitFactor * 2; i++){
                           socket.receive(packet);
                           mat = pat.matcher(messageReceived.toString());
                           if(!mat.matches()){
                               sBuilder.append(messageReceived.toString());
                           }
                        }
                        
                        jsonObjectReceived = (JSONObject) new JSONParser().parse(sBuilder.toString());

                        String protocol = jsonObjectReceived.getString("Protocol");
                        String scope = jsonObjectReceived.getString("Scope");
                        String mode = jsonObjectReceived.getString("Mode");
                        String subnetid = jsonObjectReceived.getString("SubnetID");
                        String encrypted = jsonObjectReceived.getString("Encrypted");

                        JSONArray jsonArray = jsonObjectReceived.getJSONArray("Peer");
                        String nick = jsonArray.getString(0);
                        String ip = jsonArray.getString(1);
                        String port = jsonArray.getString(2);
                        String publicKey = jsonArray.getString(3);
                        String privateKey = jsonArray.getString(4);

                        String message = jsonObjectReceived.getString("Message");

                        if (protocol.equalsIgnoreCase("/Join")) {
                            
                            xmlParser.writer(scope, mode, subnetid, nick, ip, port, publicKey, privateKey);
                            decryptedMessage = " has now joined the chat room\n";
                            
                            if(scope.equalsIgnoreCase("Global")){
                                MainUI.getListModel1().addElement(nick);
                                MainUI.getjList1().setModel(MainUI.getListModel1());
                            }else if(scope.equalsIgnoreCase("Local")){
                                MainUI.getListModel2().addElement(nick);
                                MainUI.getjList2().setModel(MainUI.getListModel2());
                            }
                            
                        } else if (protocol.equalsIgnoreCase("/Quit")) {
                            
                            xmlParser.removePeer(scope, mode, subnetid, nick);
                            decryptedMessage = " has now leaved the chat room\n";
                            
                            if(scope.equalsIgnoreCase("Global")){
                                MainUI.getListModel1().removeElementAt(
                                        MainUI.getjList1().getNextMatch(nick, 0, Position.Bias.Forward));
                                MainUI.getjList1().setModel(MainUI.getListModel1());
                            }else if(scope.equalsIgnoreCase("Local")){
                                MainUI.getListModel2().removeElementAt( 
                                        MainUI.getjList2().getNextMatch(nick, 0, Position.Bias.Forward));
                                MainUI.getjList2().setModel(MainUI.getListModel2());
                            }
                            
                        } else if (protocol.equalsIgnoreCase("/Local")
                                || protocol.equalsIgnoreCase("/Global")) {
                            xmlParser.update(scope, mode, subnetid, nick);
                            continue;
                        }
                        else if(protocol.equalsIgnoreCase("Messaging")){
                            
                            if (encrypted.equalsIgnoreCase("Encrypted") ) {
                            
                                 decryptedMessage = encryption.decrypt(message.getBytes() 
                                                                    ,xmlParser.getKeyByNickName(nick, "PrivateKey"));
                            } else {
                                 decryptedMessage = message;
                            }
                        }
                        HashMap<String,String> newMessage = new HashMap<>();
                        newMessage.put(nick, decryptedMessage);
                        incomingMessageList.add(incomingMessageList.size(), newMessage);

                    } catch (ParseException | IOException ex) {
                        Logger.getLogger(PeerConnection.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (JSONException e) {
						// TODO Auto-generated catch block
			e.printStackTrace();
		    }
                }

            }
        });
        accepting.start();
    }

    public JSONObject jsonBuilder(String protocol, String scope, String mode, String subnetid, String encrypted, peerNode peer, String message) {

        jsonObjectIntermediary = new JSONObject();
        try {

            jsonObjectIntermediary.put("Protocol", protocol);
            jsonObjectIntermediary.put("Scope", scope);
            jsonObjectIntermediary.put("Mode", mode);
            jsonObjectIntermediary.put("SubnetID", subnetid);
            jsonObjectIntermediary.put("Encrypted", encrypted);

            JSONArray jsonArray = new JSONArray();
            jsonArray.put(0, peer.nick);
            jsonArray.put(1, peer.ip);
            jsonArray.put(2, peer.port);
            jsonArray.put(3, peer.publicKey);
            jsonArray.put(4, peer.privateKey);

            jsonObjectIntermediary.put("Peer", jsonArray);

            jsonObjectIntermediary.put("Message", message);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return jsonObjectIntermediary;
    }
    public void sending(String ip, String message){
        int i=0;
        for(String s:splitMessage(message)){
            sender(2, ip, String.valueOf(i++));
            sender(2, ip, s);
        }
    }
    public String[] splitMessage(String Message){
        
        int  start = 0, end = 0;
        String [] splittedMessage = new String[splitFactor];
        for( int i=0 ; i < splitFactor; i++){
            end = start + Message.length()/splitFactor;
            if(end > Message.length()) {
                end = Message.length();
            }
            splittedMessage[i] = Message.substring( start, end);
            start = end;
        }   
        return splittedMessage;
    }
}
