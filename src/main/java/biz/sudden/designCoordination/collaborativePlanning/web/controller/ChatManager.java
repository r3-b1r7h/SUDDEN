package biz.sudden.designCoordination.collaborativePlanning.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.security.userdetails.UserDetails;

import biz.sudden.baseAndUtility.domain.SuddenUser;
import biz.sudden.baseAndUtility.service.UserService;
import biz.sudden.designCoordination.collaborativePlanning.domain.ChatRoom;
import biz.sudden.designCoordination.collaborativePlanning.domain.Communication;
import biz.sudden.designCoordination.collaborativePlanning.service.communication.CPChatRoomService;

public class ChatManager {

	private Logger logger = Logger.getLogger(this.getClass());

	private CPChatRoomService chatRoomService;
	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	private List<ChatRoom> chatRoomList = new ArrayList<ChatRoom>();

	public CPChatRoomService getChatRoomService() {
		return chatRoomService;
	}

	public void setChatRoomService(CPChatRoomService chatRoomService) {
		this.chatRoomService = chatRoomService;
	}

	public void addChatRoom(ChatRoom chatRoom) {
		chatRoomList.add(chatRoom);
		chatRoomService.createChatRoom(chatRoom);
	}

	public void addUserToChat(SuddenUser user, ChatRoom chatRoom) {
		// chatRoom.addUser(user);
		chatRoomService.createChatAndAddUser(user, chatRoom);
		// showChatPopupRenderer.requestRender();
	}

	public void addUserToChat(String userName, ChatRoom chatRoom) {
		UserDetails userDetails = userService.loadUserByUsername(userName);
		if (userDetails != null && userDetails instanceof SuddenUser) {
			SuddenUser user = (SuddenUser) userDetails;
			addUserToChat(user, chatRoom);
		}
	}

	public void addCommunicationToChat(Communication communication,
			ChatRoom chatRoom) {
		chatRoomService.addCommunicationToChat(communication, chatRoom);
		// chatRoomRepository.update(chatRoom);
	}

	public void removeUserFromChat(SuddenUser user, ChatRoom chatRoom) {
		chatRoom.removeUser(user);
	}

	public List<ChatRoom> isUserinChats(String userName) {
		UserDetails userDetails = userService.loadUserByUsername(userName);
		if (userDetails != null && userDetails instanceof SuddenUser) {
			SuddenUser user = (SuddenUser) userDetails;
			return isUserinChats(user);
		} else {
			return null;
		}
	}

	public List<ChatRoom> isUserinChats(SuddenUser user) {

		List<ChatRoom> returnChatRoomList = new ArrayList<ChatRoom>();

		for (ChatRoom chatRoom : chatRoomList) {
			if (chatRoom.getUsers().contains(user))
				returnChatRoomList.add(chatRoom);
		}

		return returnChatRoomList;
	}

}
