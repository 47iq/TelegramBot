package command;

import command.step.CommandStep;
import keyboard.KeyboardType;
import model.User;
import util.AnswerDTO;
import util.CommandDTO;
import util.MessageBundle;

import java.util.Map;

public class CommandFactoryImpl implements CommandFactory{

    private final Map<String, Command> commandMap;

    private final Map<Command, CommandStep> commandStepMap;

    private Map<User, Map<Command, CommandStep>> userCurrentSteps;

    public CommandFactoryImpl(Map<String, Command> commandMap, Map<Command, CommandStep> commandStepMap) {
        this.commandMap = commandMap;
        this.commandStepMap = commandStepMap;
    }

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        Command command = commandMap.get(commandDTO.getMessageText());
        if(command == null) {
            return new AnswerDTO(false, MessageBundle.getMessage("err_unk_command"), KeyboardType.CLASSIC, null,  null);
        }
        if(commandStepMap.get(command) == null)
            return command.execute(commandDTO);
        else {
            //TODO
            return null;
        }
    }
}
