package game;

import java.util.Objects;

public class ImageIdentifier {
    private CardName name;
    private CardType type;

    public ImageIdentifier(CardName name, CardType type) {
        this.name = name;
        this.type = type;
    }

    public CardName getName() {
        return name;
    }

    public CardType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageIdentifier that = (ImageIdentifier) o;
        return name == that.name && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }
}
