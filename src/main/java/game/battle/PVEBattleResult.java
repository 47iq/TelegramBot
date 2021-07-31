package game.battle;

import communication.util.AnswerDTO;

public class PVEBattleResult {
    boolean hasWon;
    AnswerDTO resultMessage;

    public PVEBattleResult(boolean hasWon, AnswerDTO resultMessage) {
        this.hasWon = hasWon;
        this.resultMessage = resultMessage;
    }

    public boolean isHasWon() {
        return hasWon;
    }

    public void setHasWon(boolean hasWon) {
        this.hasWon = hasWon;
    }

    public AnswerDTO getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(AnswerDTO resultMessage) {
        this.resultMessage = resultMessage;
    }
}
