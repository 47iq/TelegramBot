package game.battle;

import game.entity.User;

public class BattleState {
    private TurnOptions attackerOptions;
    private TurnOptions defenderOptions;
    private User first;
    private User second;

    public BattleState(TurnOptions attackerOptions, TurnOptions defenderOptions, User attacker, User defender) {
        this.attackerOptions = attackerOptions;
        this.defenderOptions = defenderOptions;
        this.first = attacker;
        this.second = defender;
    }

    public BattleState(User attacker, User defender) {
        this.attackerOptions = new TurnOptions();
        this.defenderOptions = new TurnOptions();
        this.first = attacker;
        this.second = defender;
    }

    public void setAttackerOptions(TurnOptions attackerOptions) {
        this.attackerOptions = attackerOptions;
    }

    public void setDefenderOptions(TurnOptions defenderOptions) {
        this.defenderOptions = defenderOptions;
    }

    public TurnOptions getAttackerOptions() {
        return attackerOptions;
    }

    public TurnOptions getDefenderOptions() {
        return defenderOptions;
    }

    public User getAttacker() {
        return first;
    }

    public User getDefender() {
        return second;
    }

    public void setAttacker(User attacker) {
        this.first = attacker;
    }

    public void setDefender(User defender) {
        this.second = defender;
    }

    public boolean isTurnReady() {
        return attackerOptions.isReady() && defenderOptions.isReady();
    }

    public void clear() {
        this.attackerOptions = new TurnOptions();
        this.defenderOptions = new TurnOptions();
    }
}
