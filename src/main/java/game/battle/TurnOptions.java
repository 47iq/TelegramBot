package game.battle;

import data.User;

public class TurnOptions {
    private AttackType attackType;
    private DefenceType defenceType;
    private boolean isReady;

    public AttackType getAttackType() {
        return attackType;
    }

    public void setAttackType(AttackType attackType) {
        this.attackType = attackType;
        setReady(true);
    }

    public DefenceType getDefenceType() {
        return defenceType;
    }

    public void setDefenceType(DefenceType defenceType) {
        this.defenceType = defenceType;
        setReady(true);
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }
}
