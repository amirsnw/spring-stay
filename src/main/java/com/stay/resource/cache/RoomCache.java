package com.stay.resource.cache;

import com.stay.model.Room;
import org.springframework.beans.factory.annotation.Value;

public class RoomCache extends BaseCacheImpl<Integer, Room> {

    {
        new Room(1, "01", null);
        new Room(2, "02", null);
        new Room(3, "03", null);
    }

    public RoomCache(@Value("120") long timeToLive,
                     @Value("60") long timerInterval,
                     @Value("10") int maxItems) {
        super(timeToLive, timerInterval, maxItems);
    }
	
	/*private static List<Room> rooms = null;

	private static int roomsCount = 3;

	static {
		new BaseCacheImpl<String, String>(200, 500, 6);
	}

	public List<Room> findAll() {
		return rooms;
	}

	public Room save(Room room) {
		if (room.getId() == null) {
			room.setId(++roomsCount);
		} else {
			Optional<Room> oldRoom = rooms.stream().filter(item -> item.getId().equals(room.getId())).findAny();
			if (oldRoom.isPresent())

		}
		rooms.add(room);
		return room;
	}

	public Room findOne(int id) {
		for (Room room : rooms) {
			if (room.getId() == id) {
				return room;
			}
		}
		return null;
	}

	public Room deleteById(int id) {
		Iterator<Room> iterator = rooms.iterator();
		while (iterator.hasNext()) {
			Room room = iterator.next();
			if (room.getId() == id) {
				iterator.remove();
				return room;
			}
		}
		return null;
	}*/
}
