package data;

import game.marketplace.Merchandise;

import java.util.List;

public interface MarketplaceDAO {
    /**
     * Method that returns the whole table
     *
     * @return cards table
     */

    List<Merchandise> getAll();

    /**
     * Method that returns merchandise by its UID
     *
     * @param UID merchandise UID
     * @return merchandise
     */

    Merchandise getEntityById(long UID);

    /**
     * Method that updates merchandise
     *
     * @param merchandise merchandise
     * @return updated merchandise
     */

    Merchandise update(Merchandise merchandise);

    /**
     * Method that deletes merchandise
     *
     * @param UID merchandise UID
     * @return true - if merchandise has been deleted, false - if not
     */

    boolean delete(long UID);

    /**
     * Method that puts merchandise into DB
     *
     * @param merchandise merchandise
     * @return true - if merchandise has been saved, false - if not
     */

    boolean create(Merchandise merchandise);
}
