package game.marketplace;

import communication.keyboard.KeyboardType;
import communication.notification.NotificationService;
import communication.util.AnswerDTO;
import data.CardService;
import data.MarketplaceDAO;
import data.User;
import data.UserService;
import game.entity.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.MessageFormatter;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Component
public class MarketplaceServiceImpl implements MarketplaceService {

    Deque<Merchandise> goods = new ArrayDeque<>();

    @Autowired
    MarketplaceDAO marketplaceDAO;
    @Autowired
    NotificationService notificationService;
    @Autowired
    MessageFormatter messageFormatter;
    @Autowired
    CardService cardService;
    @Autowired
    UserService userService;

    private static final ReentrantLock LOCK = new ReentrantLock();

    @PostConstruct
    public void init() {
        goods = marketplaceDAO.getAll().stream()
                .sorted((x, y) -> x.getListingTime().isAfter(y.getListingTime()) ? 1 : 0)
                .collect(Collectors.toCollection(ArrayDeque::new));
        new Thread(() -> {
            try {
                while (true) {
                    LOCK.lock();
                    Set<Merchandise> toRemove = new HashSet<>();
                    goods.forEach(x -> {
                        if (x.getListingTime().plusDays(1).isBefore(LocalDateTime.now()))
                            toRemove.add(x);
                    });
                    toRemove.forEach(this::forceRemove);
                    LOCK.unlock();
                    Thread.sleep(600000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void forceRemove(Merchandise merchandise) {
        AnswerDTO answerDTO = new AnswerDTO(true, messageFormatter.getMarketplaceTimeoutMessage(),
                KeyboardType.NONE, null, null, null, true);
        notificationService.notify(userService.getUserData(new User(cardService.getById(merchandise.getCardUID()).getOwner(), 0)), answerDTO);
        cancel(merchandise.getCardUID());
    }

    @Override
    public void list(Merchandise merchandise) {
        marketplaceDAO.create(merchandise);
    }

    @Override
    public void cancel(long id) {
        Merchandise merchandise = marketplaceDAO.getEntityById(id);
        marketplaceDAO.delete(id);
        goods.remove(merchandise);
    }

    @Override
    public List<Merchandise> getAll() {
        return marketplaceDAO.getAll();
    }

    @Override
    public boolean isPresent(long uid) {
        List<Merchandise> merchandises = getAll();
        if(merchandises == null)
            return false;
        else
            return merchandises.stream().filter(x -> x.getCardUID() == uid).findAny().orElse(null) != null;
    }

    @Override
    public boolean isPresent(User user) {
        return cardService.getAllCardsOf(user).stream().anyMatch(x -> isPresent(x.getUID()));
    }

    @Override
    public long getCost(long id) {
        return getById(id).getCost();
    }

    private Merchandise getById(long uid) {
        return getAll().stream().filter(x -> x.getCardUID() == uid).findAny().orElse(null);
    }

    @Override
    public void buy(long uid, User user) {
        Merchandise merchandise = getById(uid);
        long cost = merchandise.getCost();
        User owner = userService.getUserData(new User(cardService.getById(uid).getOwner(), 0));
        userService.lowerBalance(user, cost);
        userService.higherBalance(owner, cost);
        cardService.changeOwner(cardService.getById(merchandise.getCardUID()), user);
        notifyCardSold(owner, uid);
        LOCK.lock();
        goods.remove(merchandise);
        marketplaceDAO.delete(uid);
        LOCK.unlock();
    }

    private void notifyCardSold(User owner, long uid) {
        Card card = cardService.getById(uid);
        AnswerDTO answerDTO = new AnswerDTO(true, messageFormatter.getMarketplaceSoldMessage(card),
                KeyboardType.NONE, null, null, null, true);
        notificationService.notify(owner, answerDTO);
    }
}
