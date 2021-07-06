package command;

import communication.keyboard.KeyboardType;
import data.User;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import communication.util.MessageBundle;

import java.util.Map;
import java.util.ResourceBundle;

public class CommandFactoryImpl implements CommandFactory{

    private final Map<String, Command> commandMap;

    private final Map<String, Command> adminCommands;

    public CommandFactoryImpl(Map<String, Command> commandMap, Map<String, Command> adminCommands) {
        this.commandMap = commandMap;
        this.adminCommands = adminCommands;
    }

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        Command command = commandMap.get(commandDTO.getMessageText());
        if(command == null) {
            if(commandDTO.getUser().getUID().equals(ResourceBundle.getBundle("settings").getString("ADMIN_UID")) && adminCommands.containsKey(commandDTO.getMessageText()))
                return adminCommands.get(commandDTO.getMessageText()).execute(commandDTO);
            else {
                //todo
                System.err.println(commandDTO.getMessageText() + " " + commandDTO.getArg());
                return new AnswerDTO(false, MessageBundle.getMessage("err_unk_command"), KeyboardType.CLASSIC, null, null);
            }
        }
        return command.execute(commandDTO);
    }
}
