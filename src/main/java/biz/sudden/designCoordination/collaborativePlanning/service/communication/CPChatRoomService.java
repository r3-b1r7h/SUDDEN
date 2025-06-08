package biz.sudden.designCoordination.collaborativePlanning.service.communication;

import org.apache.log4j.Logger;

import biz.sudden.baseAndUtility.domain.SuddenUser;
import biz.sudden.designCoordination.collaborativePlanning.domain.ChatRoom;
import biz.sudden.designCoordination.collaborativePlanning.domain.Communication;
import biz.sudden.designCoordination.collaborativePlanning.repository.ChatRoomRepository;

public class CPChatRoomService {

	private Logger logger = Logger.getLogger(this.getClass());

	private ChatRoomRepository chatRoomRepository;

	/* Getters and Setters - BEGIN */
	public ChatRoomRepository getChatRoomRepository() {
		return chatRoomRepository;
	}

	public void setChatRoomRepository(ChatRoomRepository chatRoomRepository) {
		this.chatRoomRepository = chatRoomRepository;
	}

	/* Getters and Setters - END */

	public ChatRoom createChatRoom(ChatRoom chatRoom) {
		if (logger.isDebugEnabled()) {
			logger.debug("Create ChatRoom " + chatRoom);
		}
		chatRoomRepository.create(chatRoom);
		return chatRoom;
	}

	public void createChatAndAddUser(SuddenUser user, ChatRoom chatRoom) {

		logger.debug(chatRoom.getUsers().contains(user));

		if (!chatRoom.getUsers().contains(user))
			chatRoom.getUsers().add(user);
		//		
		// ChatRoom thisChatRoom =
		// chatRoomRepository.retrieve(chatRoom.getId());
		// logger.debug("This chatroom "+thisChatRoom);
		// thisChatRoom.addUser(user);
		// chatRoomRepository.retrieve(chatRoom.getId()).addUser(user);
		// chatRoomRepository.update(chatRoom);
	}

	public void addCommunicationToChat(Communication communication,
			ChatRoom chatRoom) {
		chatRoomRepository.retrieve(chatRoom.getId()).addCommunication(
				communication);
		// chatRoomRepository.update(chatRoom);
	}

}
