package by.urbel.hotel.controller.command;

import by.urbel.hotel.controller.command.impl.*;
import by.urbel.hotel.controller.command.impl.admin.*;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
    private final Map<CommandName, Command> commands = new HashMap<>();

    public CommandProvider() {
        commands.put(CommandName.AUTHORIZE, new Authorize());
        commands.put(CommandName.REGISTER, new Register());
        commands.put(CommandName.LOGOUT, new Logout());
        commands.put(CommandName.FIND_FREE_CATEGORIES_BY_BEDS_NUMBER, new FindFreeCategoriesByBedsNumber());
        commands.put(CommandName.FIND_ALL_ROOMS, new FindAllRooms());
        commands.put(CommandName.FIND_ALL_CATEGORIES, new FindAllCategories());
        commands.put(CommandName.ADD_NEW_ROOM, new AddNewRoom());
        commands.put(CommandName.ADD_NEW_CATEGORY,new AddNewCategory());
        commands.put(CommandName.DELETE_ROOM, new DeleteRoom());
        commands.put(CommandName.DELETE_CATEGORY, new DeleteCategory());
        commands.put(CommandName.GO_TO_ADD_ROOM_PAGE, new GoToAddRoomPage());
        commands.put(CommandName.BLOCK_ROOM,new BlockRoom());
        commands.put(CommandName.UNBLOCK_ROOM,new UnblockRoom());
        commands.put(CommandName.RESERVE_ROOM, new ReserveRoom());
        commands.put(CommandName.GO_TO_PROFILE_PAGE, new GoToProfilePage());
        commands.put(CommandName.DELETE_RESERVATION, new DeleteReservation());
        commands.put(CommandName.FIND_ALL_RESERVATIONS, new FindAllReservations());
        commands.put(CommandName.UPDATE_USER, new UpdateUser());
    }

    public Command takeCommand(String command){
        CommandName commandName = CommandName.valueOf(command.toUpperCase());
        return commands.get(commandName);
    }
}
