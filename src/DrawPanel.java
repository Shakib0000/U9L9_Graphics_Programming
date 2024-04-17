import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Point;
import java.util.ArrayList;
import java.awt.Font;

class DrawPanel extends JPanel implements MouseListener {

    private ArrayList<Card> hand;
    private ArrayList<Card> highlightedHand;
    private Rectangle startOverButton;
    private Rectangle replaceCardsButton;
    private Deck cardDeck;

    public DrawPanel() {
        startOverButton = new Rectangle(87, 300, 160, 26);
        replaceCardsButton = new Rectangle(300, 20, 160, 26);
        this.addMouseListener(this);
        hand = Card.buildHand();
        highlightedHand = new ArrayList<Card>();
        cardDeck = new Deck();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 50;
        int y = 10;
        for (int i = 0; i < hand.size(); i++) {
            Card c = hand.get(i);
            if (c.getHighlight()) {
                g.drawRect(x, y, c.getImage().getWidth(), c.getImage().getHeight());
            }
            c.setRectangleLocation(x, y);
            g.drawImage(c.getImage(), x, y, null);
            x = x + c.getImage().getWidth() + 20;
            if ((i + 1) % 3 == 0) {
                x = 50;
                y += 100;
            }
        }
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.drawString("PLAY AGAIN", 105, 320);
        g.drawRect((int)startOverButton.getX(), (int)startOverButton.getY(), (int)startOverButton.getWidth(), (int)startOverButton.getHeight());
        g.drawString("REPLACE CARDS", 303, 40);
        g.drawRect((int)replaceCardsButton.getX(), (int)replaceCardsButton.getY(), (int)replaceCardsButton.getWidth(), (int)replaceCardsButton.getHeight());
        g.drawString("Cards left: " + cardDeck.getDeck().size(), 5, 450);
        checkGameStatus(g);
    }

    public void checkValues() {
        int value = 0;
        for (int i = 0; i < highlightedHand.size(); i++) {
            if (!highlightedHand.get(i).getValue().equals("A") && !highlightedHand.get(i).getValue().equals("J") && !highlightedHand.get(i).getValue().equals("K") && !highlightedHand.get(i).getValue().equals("Q")) {
                if (highlightedHand.get(i).getValue().substring(0,1).equals("0")) {
                    value += Integer.parseInt(highlightedHand.get(i).getValue().substring(1));
                }
                else {
                    value += Integer.parseInt(highlightedHand.get(i).getValue());
                }
            }
            else if (highlightedHand.get(i).getValue().equals("A")) {
                value += 1;
            }
            else {
                value += 10;
            }
        }
        if (value == 11 && highlightedHand.size() == 2) {
            String valueFirst = "";
            for (int i = 0; i < hand.size(); i++) {
                if ((hand.get(i).getValue().equals(highlightedHand.get(0).getValue()) && hand.get(i).getSuit().equals(highlightedHand.get(0).getSuit())) || (hand.get(i).getValue().equals(highlightedHand.get(1).getValue()) && hand.get(i).getSuit().equals(highlightedHand.get(1).getSuit()))) {
                    if (valueFirst.equals("")) {
                        valueFirst = hand.get(i).getValue();
                        for (int j = 0; j < cardDeck.getDeck().size(); j++) {
                            if (cardDeck.getDeck().get(j).getValue().equals(hand.get(i).getValue()) && cardDeck.getDeck().get(j).getSuit().equals(hand.get(i).getSuit())) {
                                cardDeck.getDeck().remove(j);
                                break;
                            }
                        }
                        hand.set(i, cardDeck.getDeck().get((int) (Math.random() * cardDeck.getDeck().size())));
                    }
                    else if (!hand.get(i).getValue().equals(valueFirst)) {
                        for (int j = 0; j < cardDeck.getDeck().size(); j++) {
                            if (cardDeck.getDeck().get(j).getValue().equals(hand.get(i).getValue()) && cardDeck.getDeck().get(j).getSuit().equals(hand.get(i).getSuit())) {
                                cardDeck.getDeck().remove(j);
                                break;
                            }
                        }
                        hand.set(i, cardDeck.getDeck().get((int) (Math.random() * cardDeck.getDeck().size())));
                        highlightedHand = new ArrayList<Card>();
                        break;
                    }
                }
            }
        }
        if (highlightedHand.size() == 3) {
            boolean hasJack = false;
            boolean hasKing = false;
            boolean hasQueen = false;
            for (int i = 0; i < highlightedHand.size(); i++) {
                if (highlightedHand.get(i).getValue().equals("J")) {
                    hasJack = true;
                }
                if (highlightedHand.get(i).getValue().equals("K")) {
                    hasKing = true;
                }
                if (highlightedHand.get(i).getValue().equals("Q")) {
                    hasQueen = true;
                }
             }
            if (hasJack && hasKing && hasQueen) {
                boolean jackRemoved = false;
                boolean kingRemoved = false;
                boolean queenRemoved = false;
                for (int i = 0; i < hand.size(); i++) {
                    if ((hand.get(i).getValue().equals(highlightedHand.get(0).getValue()) && hand.get(i).getSuit().equals(highlightedHand.get(0).getSuit())) || (hand.get(i).getValue().equals(highlightedHand.get(1).getValue()) && hand.get(i).getSuit().equals(highlightedHand.get(1).getSuit())) || (hand.get(i).getValue().equals(highlightedHand.get(2).getValue()) && hand.get(i).getSuit().equals(highlightedHand.get(2).getSuit()))) {
                        if (hand.get(i).getValue().equals("J") && !jackRemoved) {
                            jackRemoved = true;
                            for (int j = 0; j < cardDeck.getDeck().size(); j++) {
                                if (cardDeck.getDeck().get(j).getValue().equals(hand.get(i).getValue()) && cardDeck.getDeck().get(j).getSuit().equals(hand.get(i).getSuit())) {
                                    cardDeck.getDeck().remove(j);
                                    break;
                                }
                            }
                            hand.set(i, cardDeck.getDeck().get((int) (Math.random() * cardDeck.getDeck().size())));
                        }
                        else if (hand.get(i).getValue().equals("K") && !kingRemoved) {
                            kingRemoved = true;
                            for (int j = 0; j < cardDeck.getDeck().size(); j++) {
                                if (cardDeck.getDeck().get(j).getValue().equals(hand.get(i).getValue()) && cardDeck.getDeck().get(j).getSuit().equals(hand.get(i).getSuit())) {
                                    cardDeck.getDeck().remove(j);
                                    break;
                                }
                            }
                            hand.set(i, cardDeck.getDeck().get((int) (Math.random() * cardDeck.getDeck().size())));
                        }
                        else if (hand.get(i).getValue().equals("Q") && !queenRemoved) {
                            queenRemoved = true;
                            for (int j = 0; j < cardDeck.getDeck().size(); j++) {
                                if (cardDeck.getDeck().get(j).getValue().equals(hand.get(i).getValue()) && cardDeck.getDeck().get(j).getSuit().equals(hand.get(i).getSuit())) {
                                    cardDeck.getDeck().remove(j);
                                    break;
                                }
                            }
                            hand.set(i, cardDeck.getDeck().get((int) (Math.random() * cardDeck.getDeck().size())));
                        }
                        if (jackRemoved && kingRemoved && queenRemoved) {
                            highlightedHand = new ArrayList<Card>();
                            break;
                        }
                    }
                }
            }
        }
    }

    public void checkGameStatus(Graphics g) {
        if (cardDeck.getDeck().size() == 0) {
            g.drawString("Congrats, you win!", 5, 400);
        }
        boolean possibleEleven = false;
        boolean hasJack = false;
        boolean hasKing = false;
        boolean hasQueen = false;
        for (int i = 0; i < hand.size(); i++) {
            int val = 0;
            if (!hand.get(i).getValue().equals("A") && !hand.get(i).getValue().equals("J") && !hand.get(i).getValue().equals("K") && !hand.get(i).getValue().equals("Q")) {
                if (hand.get(i).getValue().substring(0,1).equals("0")) {
                    val += Integer.parseInt(hand.get(i).getValue().substring(1));
                }
                else {
                    val += Integer.parseInt(hand.get(i).getValue());
                }
            }
            else if (hand.get(i).getValue().equals("A")) {
                val += 1;
            }
            else if (hand.get(i).getValue().equals("J"))  {
                val += 10;
                hasJack = true;
            }
            else if (hand.get(i).getValue().equals("K"))  {
                val += 10;
                hasKing = true;
            }
            else if (hand.get(i).getValue().equals("Q"))  {
                val += 10;
                hasQueen = true;
            }
            for (int j = 0; j < hand.size(); j++) {
                if (i != j) {
                    int secondVal = 0;
                    if (!hand.get(j).getValue().equals("A") && !hand.get(j).getValue().equals("J") && !hand.get(j).getValue().equals("K") && !hand.get(j).getValue().equals("Q")) {
                        if (hand.get(j).getValue().substring(0,1).equals("0")) {
                            secondVal += Integer.parseInt(hand.get(j).getValue().substring(1));
                        }
                        else {
                            secondVal += Integer.parseInt(hand.get(j).getValue());
                        }
                    }
                    else if (hand.get(j).getValue().equals("A")) {
                        secondVal += 1;
                    }
                    else {
                        secondVal += 10;
                    }
                    if (val + secondVal == 11) {
                        possibleEleven = true;
                    }
                }
            }
        }
        if (!possibleEleven && (!hasJack || !hasKing || !hasQueen)) {
            g.drawString("No available moves! GAME OVER!", 5, 400);
        }
    }

    public void mousePressed(MouseEvent e) {

        Point clicked = e.getPoint();

        if (e.getButton() == 1) {
            if (startOverButton.contains(clicked)) {
                cardDeck = new Deck();
                hand = Card.buildHand();
                highlightedHand = new ArrayList<Card>();
            }
            else if (replaceCardsButton.contains(clicked)) {
                checkValues();
            }

            for (int i = 0; i < hand.size(); i++) {
                Rectangle box = hand.get(i).getCardBox();
                if (box.contains(clicked) && (!hand.get(i).getHighlight())) {
                    if (highlightedHand.size() < 3) {
                        hand.get(i).flipHighlight();
                        highlightedHand.add(hand.get(i));
                    }
                } else if (box.contains(clicked) && (hand.get(i).getHighlight())) {
                    hand.get(i).flipHighlight();
                    for (int j = 0; j < highlightedHand.size(); j++) {
                        if (highlightedHand.get(j).getValue().equals(hand.get(i).getValue())) {
                            highlightedHand.remove(j);
                            break;
                        }
                    }
                }
            }
        }

        if (e.getButton() == 3) {
            for (int i = 0; i < hand.size(); i++) {
                Rectangle box = hand.get(i).getCardBox();
                if (box.contains(clicked) && (!hand.get(i).getHighlight())) {
                    if (highlightedHand.size() < 3) {
                        hand.get(i).flipHighlight();
                        highlightedHand.add(hand.get(i));
                    }
                } else if (box.contains(clicked) && (hand.get(i).getHighlight())) {
                    hand.get(i).flipHighlight();
                    for (int j = 0; j < highlightedHand.size(); j++) {
                        if (highlightedHand.get(j).getValue().equals(hand.get(i).getValue())) {
                            highlightedHand.remove(j);
                            break;
                        }
                    }
                }
            }
        }


    }

    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
}