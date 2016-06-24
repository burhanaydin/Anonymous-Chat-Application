#include <jni.h>
#include "ChatUtility_PeerConnection.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <unistd.h>
#include <stdbool.h>
#include <arpa/inet.h>
#include <linux/if_packet.h>
#include <linux/ip.h>
#include <linux/udp.h>
#include <net/if.h>
#include <netinet/ether.h>
#include <sys/ioctl.h>
#include <sys/socket.h>

#define DEST_MAC0	0xFF
#define DEST_MAC1	0xFF
#define DEST_MAC2	0xFF
#define DEST_MAC3	0xFF
#define DEST_MAC4	0xFF
#define DEST_MAC5	0xFF

#define ETHER_TYPE	0x0800

#define DEFAULT_IF	"eth0"
#define BUF_SIZE	1500

JNIEXPORT void JNICALL Java_ChatUtility_PeerConnection_receiver
  (JNIEnv *env, jclass jClass, jint arg1, jstring arg2){
	  
	  
	char sender[INET6_ADDRSTRLEN];
	int sockfd, ret, i;
	int sockopt;
	ssize_t numbytes;
	struct ifreq ifopts;
	struct sockaddr_storage their_addr;
	uint8_t buf[BUF_SIZE];
	char ifName[IFNAMSIZ];
	int packet_Ids[100];
	int packet_Id_Index = 0;
	bool packet_Id_flag = false;

 
        
	/* Header Structures */
	struct ether_header *eh = (struct ether_header *) buf;
	struct iphdr *iph = (struct iphdr *) (buf + sizeof(struct ether_header));
	struct udphdr *udph = (struct udphdr *) (buf + sizeof(struct iphdr) + sizeof(struct ether_header));

	/* Open PF_PACKET socket, listening for EtherType ETHER_TYPE */
	if ((sockfd = socket(PF_PACKET, SOCK_RAW, htons(ETHER_TYPE))) == -1) {
		perror("listener: socket");
	
	}
	
	/* Get interface name */
	strcpy(ifName, DEFAULT_IF);
	
	/* Set interface to promiscuous mode */
	strncpy(ifopts.ifr_name, ifName, IFNAMSIZ - 1);
	ioctl(sockfd, SIOCGIFFLAGS, &ifopts);
	ifopts.ifr_flags |= IFF_PROMISC;
	ioctl(sockfd, SIOCSIFFLAGS, &ifopts);

	/* Allow the socket to be reused */
	if (setsockopt(sockfd, SOL_SOCKET, SO_REUSEADDR, &sockopt, sizeof sockopt) == -1) {
		perror("setsockopt");
		close(sockfd);
		exit(EXIT_FAILURE);
	}

	/* Bind to device */
	if (setsockopt(sockfd, SOL_SOCKET, SO_BINDTODEVICE, ifName, IFNAMSIZ - 1) == -1) {
		perror("SO_BINDTODEVICE");
		close(sockfd);
		exit(EXIT_FAILURE);
	}

	while (1) {
		numbytes = recvfrom(sockfd, buf, BUF_SIZE, 0, NULL, NULL);
		/* Check the packet is for me */
		if (eh->ether_dhost[0] == DEST_MAC0 && eh->ether_dhost[1] == DEST_MAC1
				&& eh->ether_dhost[2] == DEST_MAC2
				&& eh->ether_dhost[3] == DEST_MAC3
				&& eh->ether_dhost[4] == DEST_MAC4
				&& eh->ether_dhost[5] == DEST_MAC5) {
			/* Correct destination MAC address */

			/* Get source IP */
			((struct sockaddr_in *) &their_addr)->sin_addr.s_addr = iph->saddr;
			inet_ntop(AF_INET, &((struct sockaddr_in*) &their_addr)->sin_addr, sender, sizeof sender);

			printf("Source IP: %s\n", sender);
			
			// Checks packet id conflicts
			for(int i = 0; i < packet_Id_Index ; i++){
				if(packet_Ids[i] == iph->id){
					printf("The packet %d has already been received\n", iph->id);
				    packet_Id_flag = true;
				}			
			}
			if( packet_Id_flag ){
			     continue;
			}else{
				packet_Ids[packet_Id_Index++] = iph->id;
			}
			packet_Id_Index %= 100;
			
			/* UDP payload length */
			ret = ntohs(udph->len) - sizeof(struct udphdr);

			printf("UDP Payload Length: %d\n", ret);


                        arg2 = (*env)->NewStringUTF(env, buf);
		}
	}

	close(sockfd);
	  
  }
