package biz.sudden.designCoordination.collaborativePlanning.repository.hibernate;

import biz.sudden.baseAndUtility.repository.hibernate.generic.GenericRepositoryImpl;
import biz.sudden.designCoordination.collaborativePlanning.domain.ChatRoom;
import biz.sudden.designCoordination.collaborativePlanning.repository.ChatRoomRepository;

class ChatRoomRepositoryImpl extends GenericRepositoryImpl<ChatRoom, Long>
		implements ChatRoomRepository {

	public ChatRoomRepositoryImpl() {
		// TODO Auto-generated constructor stub
		super(ChatRoom.class);
	}
}
