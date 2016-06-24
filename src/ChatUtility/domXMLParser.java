/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatUtility;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import uiElements.MainUI;

/**
 *
 * @author burhan
 */
public class domXMLParser {
    
    private DocumentBuilderFactory dBFactory = null;
    private DocumentBuilder dBuilder = null;
    private Document doc = null;
    private static final Path filePath = Paths.get("Connections.xml"); 
    
    public domXMLParser(){
        
        dBFactory = DocumentBuilderFactory.newInstance();
        try {
            dBuilder = dBFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(domXMLParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getKeyByNickName(String nick, String whichKey){
        
        try{
            
            doc = dBuilder.parse(filePath.toString());
            doc.normalize();
            NodeList rootNodes = doc.getElementsByTagName("Connections");
            Node rootNode = rootNodes.item(0);
            Element rootElement = (Element) rootNode;
            NodeList connectionsList = rootElement.getElementsByTagName("Peer");
            for (int i=0; i < connectionsList.getLength(); i++){
                Node theNode = connectionsList.item(i);
                Element peerElement = (Element) theNode;
                if(peerElement.getChildNodes().item(0).getNodeValue().equalsIgnoreCase(nick)){
                    if(whichKey.equalsIgnoreCase("PrivateKey")) {
                        return peerElement.getChildNodes().item(4).getNodeValue();
                    } else if(whichKey.equalsIgnoreCase("PublicKey")) {
                        return peerElement.getChildNodes().item(3).getNodeValue();
                    }
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    public List<peerNode> reader(String scope, String mode, String subnetid){
        
        peerNode n = null;
        List<peerNode> peerList = new ArrayList<>();
        List<String> subnetidList = new ArrayList<>();
        try {
            
            doc = dBuilder.parse(filePath.toString());
            doc.normalize();
            NodeList rootNodes = doc.getElementsByTagName("Connections");
            Node rootNode = rootNodes.item(0);
            Element rootElement = (Element) rootNode;
            NodeList connectionsList = rootElement.getElementsByTagName("Peer");
            
            for (int i=0; i < connectionsList.getLength(); i++){
                Node theNode = connectionsList.item(i);
                Element peerElement = (Element) theNode;
                if(scope.equalsIgnoreCase("Local") && mode.equalsIgnoreCase("Client")){
                    if( peerElement.getAttribute("Scope").equalsIgnoreCase("Local")
                             && peerElement.getAttribute("SubnetID").equalsIgnoreCase(subnetid)){
                          n = new peerNode();
                          n.nick=peerElement.getChildNodes().item(0).getNodeValue();
                          n.ip=peerElement.getChildNodes().item(1).getNodeValue();
                          n.port=peerElement.getChildNodes().item(2).getNodeValue();
                          n.publicKey=peerElement.getChildNodes().item(3).getNodeValue();
                          n.privateKey=peerElement.getChildNodes().item(4).getNodeValue();
                          peerList.add(n);
                    }
                }else if(scope.equalsIgnoreCase("Local") && mode.equalsIgnoreCase("Gateway")){
                       if( peerElement.getAttribute("Scope").equalsIgnoreCase("Local")
                             && !subnetidList.contains(peerElement.getAttribute("SubnetID"))){                       
                          n = new peerNode();
                          n.nick=peerElement.getChildNodes().item(0).getNodeValue();
                          n.ip=peerElement.getChildNodes().item(1).getNodeValue();
                          n.port=peerElement.getChildNodes().item(2).getNodeValue();
                          n.publicKey=peerElement.getChildNodes().item(3).getNodeValue();
                          n.privateKey=peerElement.getChildNodes().item(4).getNodeValue();
                          peerList.add(n);
                          subnetidList.add(peerElement.getAttribute("SubnetID"));
                       }
                }else if(scope.equalsIgnoreCase("Global") && mode.equalsIgnoreCase("Client")){
                       if( peerElement.getAttribute("Scope").equalsIgnoreCase("Local")
                             && peerElement.getAttribute("SubnetID").equalsIgnoreCase(subnetid)){                       
                          n = new peerNode();
                          n.nick=peerElement.getChildNodes().item(0).getNodeValue();
                          n.ip=peerElement.getChildNodes().item(1).getNodeValue();
                          n.port=peerElement.getChildNodes().item(2).getNodeValue();
                          n.publicKey=peerElement.getChildNodes().item(3).getNodeValue();
                          n.privateKey=peerElement.getChildNodes().item(4).getNodeValue();
                          peerList.add(n);
                       }
                }else if(scope.equalsIgnoreCase("Global") && mode.equalsIgnoreCase("Gateway")){
                       if( !subnetidList.contains(peerElement.getAttribute("SubnetID"))){                       
                          n = new peerNode();
                          n.nick=peerElement.getChildNodes().item(0).getNodeValue();
                          n.ip=peerElement.getChildNodes().item(1).getNodeValue();
                          n.port=peerElement.getChildNodes().item(2).getNodeValue();
                          n.publicKey=peerElement.getChildNodes().item(3).getNodeValue();
                          n.privateKey=peerElement.getChildNodes().item(4).getNodeValue();
                          peerList.add(n);
                          subnetidList.add(peerElement.getAttribute("SubnetID"));
                       }
                }
            }
        } catch (SAXException | IOException ex) {
            Logger.getLogger(domXMLParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        n = null;
        return peerList;
    }
   
    public void writer(String scope,String mode,String subnetid, String nick, String ip, String port, String publicKey, String privateKey){
       
        try {
          
            doc = dBuilder.parse(filePath.toString());
            NodeList rootNodes = doc.getElementsByTagName("Connections");
            Node rootNode = rootNodes.item(0);
            Element rootElement = (Element) rootNode;
            
            Element peerElement = doc.createElement("Peer");
            peerElement.setAttribute("Scope", scope);
            peerElement.setAttribute("Mode", mode);
            peerElement.setAttribute("SubentID", subnetid);
            
            Element nickElement = doc.createElement("nick");
            nickElement.appendChild(doc.createTextNode(nick));
            Element ipElement = doc.createElement("ip");
            ipElement.appendChild(doc.createTextNode(ip));
            Element portElement = doc.createElement("port");
            portElement.appendChild(doc.createTextNode(port));
            Element publicKeyElement = doc.createElement("publicKey");
            publicKeyElement.appendChild(doc.createTextNode(publicKey));
            Element privateKeyElement = doc.createElement("privateKey");
            privateKeyElement.appendChild(doc.createTextNode(privateKey));
            
            peerElement.appendChild(nickElement);
            peerElement.appendChild(ipElement);
            peerElement.appendChild(portElement);
            peerElement.appendChild(publicKeyElement);
            peerElement.appendChild(privateKeyElement);
            rootElement.appendChild(peerElement);
            
            doc.appendChild(rootElement);
            OutputFormat outFormat = new OutputFormat(doc);
            outFormat.setIndenting(true);
            
            FileOutputStream fos = new FileOutputStream(filePath.toString());
            XMLSerializer serializer = new XMLSerializer( fos, outFormat);
            serializer.serialize(doc);
            
            if(scope.equalsIgnoreCase("Global")){
                  MainUI.getjList1().add(new JMenuItem(nick));
            }
            else if(scope.equalsIgnoreCase("Local")){
                  MainUI.getjList2().add(new JMenuItem(nick));
            }      
            
            
        } catch (SAXException ex) {
            Logger.getLogger(domXMLParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(domXMLParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void removePeer(String scope, String mode,String subnetid, String nick){
        try {
            
            doc = dBuilder.parse(filePath.toString());
            doc.normalize();
            NodeList rootNodes = doc.getElementsByTagName("Connections");
            Node rootNode = rootNodes.item(0);
            Element rootElement = (Element) rootNode;
            NodeList connectionsList = rootElement.getElementsByTagName("Peer");
            
            for (int i=0; i < connectionsList.getLength(); i++){
                Node theNode = connectionsList.item(i);
                Element peerElement = (Element) theNode;
                if(peerElement.getChildNodes().item(0).getTextContent().equalsIgnoreCase(nick)
                        && peerElement.getAttribute("Scope").equalsIgnoreCase(scope)
                        && peerElement.getAttribute("Mode").equalsIgnoreCase(mode)
                        && peerElement.getAttribute("SubnetID").equalsIgnoreCase(subnetid)){
                      rootNode.removeChild(theNode);
                }
            }
            Element root = (Element) rootNode;
            doc.appendChild(root);
            OutputFormat outFormat = new OutputFormat(doc);
            outFormat.setIndenting(true);
            
            FileOutputStream fos = new FileOutputStream(filePath.toString());
            XMLSerializer serializer = new XMLSerializer( fos, outFormat);
            serializer.serialize(doc);
            
            if(scope.equalsIgnoreCase("Global")){
                  MainUI.getjList1().add(new JMenuItem(nick));
            }
            else if(scope.equalsIgnoreCase("Local")){
                  MainUI.getjList2().add(new JMenuItem(nick));
            } 
            
        } catch (SAXException | IOException ex) {
            Logger.getLogger(domXMLParser.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void update(String scope, String mode, String subnetid, String nick ){
        
        try {
            
            doc = dBuilder.parse(filePath.toString());
            doc.normalize();
            NodeList rootNodes = doc.getElementsByTagName("Connections");
            Node rootNode = rootNodes.item(0);
            Element rootElement = (Element) rootNode;
            NodeList connectionsList = rootElement.getElementsByTagName("Peer");
            
            for (int i=0; i < connectionsList.getLength(); i++){
                Node theNode = connectionsList.item(i);
                Element peerElement = (Element) theNode;
                if(peerElement.getChildNodes().item(0).getTextContent().equalsIgnoreCase(nick)
                        && peerElement.getAttribute("Scope").equalsIgnoreCase(scope)
                        && peerElement.getAttribute("Mode").equalsIgnoreCase(mode)
                        && peerElement.getAttribute("SubnetID").equalsIgnoreCase(subnetid)){
                                 
                    Element newPeerElement = doc.createElement("Peer");
                    newPeerElement.setAttribute("Scope", peerElement.getAttribute("Scope"));
                    newPeerElement.setAttribute("Mode", peerElement.getAttribute("Mode"));

                    Element nickElement = doc.createElement("nick");
                    nickElement.appendChild(doc.createTextNode(nick));
                    Element ipElement = doc.createElement("ip");
                    ipElement.appendChild(doc.createTextNode(peerElement.getChildNodes().item(1).getNodeValue()));
                    Element portElement = doc.createElement("port");
                    portElement.appendChild(doc.createTextNode(peerElement.getChildNodes().item(2).getNodeValue()));
                    Element publicKeyElement = doc.createElement("publicKey");
                    publicKeyElement.appendChild(doc.createTextNode(peerElement.getChildNodes().item(3).getNodeValue()));
                    Element privateKeyElement = doc.createElement("privateKey");
                    privateKeyElement.appendChild(doc.createTextNode(peerElement.getChildNodes().item(4).getNodeValue()));

                    newPeerElement.appendChild(nickElement);
                    newPeerElement.appendChild(ipElement);
                    newPeerElement.appendChild(portElement);
                    newPeerElement.appendChild(publicKeyElement);
                    newPeerElement.appendChild(privateKeyElement);
                    
                    rootNode.replaceChild(newPeerElement,theNode);
                }
            }
            Element root = (Element) rootNode;
            doc.appendChild(root);
            OutputFormat outFormat = new OutputFormat(doc);
            outFormat.setIndenting(true);
            
            FileOutputStream fos = new FileOutputStream(filePath.toString());
            XMLSerializer serializer = new XMLSerializer( fos, outFormat);
            serializer.serialize(doc);
            
            if(scope.equalsIgnoreCase("Global")){
                  MainUI.getjList1().add(new JMenuItem(nick));
            }
            else if(scope.equalsIgnoreCase("Local")){
                  MainUI.getjList2().add(new JMenuItem(nick));
            } 
            
        } catch (SAXException ex) {
            Logger.getLogger(domXMLParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(domXMLParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

