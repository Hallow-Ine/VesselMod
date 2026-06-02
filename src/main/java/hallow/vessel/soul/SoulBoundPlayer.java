package hallow.vessel.soul;

/**
 * DO NOT USE THIS, USE SoulManager or esle client WILL desync
 */
public interface SoulBoundPlayer {
    /**
     * DO NOT USE THIS, USE SoulManager or esle client WILL desync
     * @return whether the player has lost their soul
     */
    Boolean isSoulBound();
    /**
     * DO NOT USE THIS, USE SoulManager or esle client WILL desync
     */
    void bindSoul();
    /**
     * DO NOT USE THIS, USE SoulManager or esle client WILL desync
     */
    void unBindSoul();
    /**
     * DO NOT USE THIS, USE SoulManager or esle client WILL desync
     * @param bound wether you want the player to lose or gain their soul
     */
    void setSoulBound(boolean bound);
}