package command;

import command.tutorial.StartCommand;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import util.MessageBundle;
import data.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CommandFactoryImpl implements CommandFactory {

    @Autowired
    StartCommand startCommand;
    @Autowired
    UserDAO userDAO;

    private final Map<String, Command> commandMap;

    private final Map<String, Command> adminCommands;

    private static final Logger LOGGER = LogManager.getLogger(CommandFactoryImpl.class);

    /**
     * Default constructor
     *
     * @param commandMap    commands and their text equivalents
     * @param adminCommands admin commands and their text equivalents
     */

    public CommandFactoryImpl(Map<String, Command> commandMap, Map<String, Command> adminCommands) {
        this.commandMap = commandMap;
        this.adminCommands = adminCommands;
    }

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        Command command = commandMap.get(commandDTO.getMessageText());
        if (userDAO.getEntityById(commandDTO.getUser().getUID()) == null)
            return startCommand.execute(commandDTO);
        else if (command == null) {
            if (commandDTO.getUser().getUID().equals(MessageBundle.getSetting("ADMIN_UID")) && adminCommands.containsKey(commandDTO.getMessageText()))
                return adminCommands.get(commandDTO.getMessageText()).execute(commandDTO);
            else {
                LOGGER.info("Unknown command got from " + commandDTO.getUser().getUID() + ": "+ commandDTO.getMessageText() + " " + commandDTO.getArg());
                return new AnswerDTO(false, MessageBundle.getMessage("err_unk_command"), KeyboardType.CLASSIC, null, null, commandDTO.getUser());
            }
        }
        return command.execute(commandDTO);
    }
}
