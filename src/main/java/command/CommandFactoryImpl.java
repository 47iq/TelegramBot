package command;

import command.tutorial.StartCommand;
import communication.keyboard.KeyboardType;
import communication.util.AnswerDTO;
import communication.util.CommandDTO;
import game.entity.User;
import game.service.UserService;
import game.service.AchievementService;
import util.MessageBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class CommandFactoryImpl implements CommandFactory {

    @Autowired
    StartCommand startCommand;
    @Autowired
    UserService userService;
    @Autowired
    AchievementService achievementService;

    private final Map<String, Command> commandMap;

    private final Map<String, Command> adminCommands;

    @Resource
    private Map<Command, KeyboardType> keyboardTypeMap;

    private final Map<User, LastCommand> lastCommands = new HashMap<>();

    private static final Logger LOGGER = LogManager.getLogger(CommandFactoryImpl.class);

    static class LastCommand {
        Command command;
        String arg;

        public LastCommand(Command command, String arg) {
            this.command = command;
            this.arg = arg;
        }
    }

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
        User user = commandDTO.getUser();
        if (lastCommands.get(user) != null) {
            commandDTO.setKeyboardType(keyboardTypeMap.get(lastCommands.get(user).command));
        }
        if (userService.getUserData(commandDTO.getUser()) == null)
            return startCommand.execute(commandDTO);
        else if (command == null) {
            if (lastCommands.get(user) != null && (lastCommands.get(user).command instanceof MultiStepCommand)) {
                command = lastCommands.get(user).command;
                commandDTO.setArg(lastCommands.get(user).arg + "-" + commandDTO.getMessageText());
                return command.execute(commandDTO);
            } else if (commandDTO.getUser().getUID().equals(MessageBundle.getSetting("ADMIN_UID")) && adminCommands.containsKey(commandDTO.getMessageText())) {
                return adminCommands.get(commandDTO.getMessageText()).execute(commandDTO);
            } else {
                LOGGER.info("Unknown command got from " + commandDTO.getUser().getUID() + ": "+ commandDTO.getMessageText() + " " + commandDTO.getArg());
                return new AnswerDTO(false, MessageBundle.getMessage("err_unk_command"), KeyboardType.MENU, null, null, commandDTO.getUser(), true);
            }
        }
        if(achievementService.getUsersAchievements(commandDTO.getUser()) == null)
            achievementService.create(commandDTO.getUser());
        lastCommands.put(user, new LastCommand(command, commandDTO.getArg()));
        userService.updateVisitTime(commandDTO.getUser());
        return command.execute(commandDTO);
    }
}
