package command;

import data.UserDAO;
import keyboard.KeyboardType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.AnswerDTO;
import util.CommandDTO;

@Component
public class RegisterCommand implements Command{
    @Autowired
    private UserDAO userDAO;

    @Override
    public AnswerDTO execute(CommandDTO commandDTO) {
        if(userDAO.create(commandDTO.getUser()) )
            return new AnswerDTO(true, null, KeyboardType.CLASSIC, null, null);
        else
            return new AnswerDTO(false, null, KeyboardType.CLASSIC, null, null);
    }
}
