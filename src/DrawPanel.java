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
    private Rectangle button;
    private Deck cardDeck;

    public DrawPanel() {
        button = new Rectangle(147, 300, 160, 26);
        this.addMouseListener(this);
        hand = Card.buildHand();
        highlightedHand = new ArrayList<Card>();
        cardDeck = new Deck();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 110;
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
                x = 110;
                y += 100;
            }
        }
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.drawString("GET NEW CARDS", 150, 320);
        g.drawRect((int)button.getX(), (int)button.getY(), (int)button.getWidth(), (int)button.getHeight());
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
            System.out.println("RAHH");
            String valueFirst = "";
            for (int i = 0; i < hand.size(); i++) {
                if ((hand.get(i).getValue().equals(highlightedHand.get(0).getValue()) && hand.get(i).getSuit().equals(highlightedHand.get(0).getSuit())) || (hand.get(i).getValue().equals(highlightedHand.get(1).getValue()) && hand.get(i).getSuit().equals(highlightedHand.get(1).getSuit()))) {
                    if (valueFirst.equals("")) {
                        valueFirst = hand.get(i).getValue();
                        hand.remove(i);
                        i--;
                    }
                    else if (!hand.get(i).getValue().equals(valueFirst)) {
                        hand.remove(i);
                        i--;
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
                            hand.remove(i);
                            i--;
                        }
                        else if (hand.get(i).getValue().equals("K") && !kingRemoved) {
                            kingRemoved = true;
                            hand.remove(i);
                            i--;
                        }
                        else if (hand.get(i).getValue().equals("Q") && !queenRemoved) {
                            queenRemoved = true;
                            hand.remove(i);
                            i--;
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

    public void mousePressed(MouseEvent e) {

        Point clicked = e.getPoint();

        if (e.getButton() == 1) {
            if (button.contains(clicked)) {
                hand = Card.buildHand();
                highlightedHand = new ArrayList<Card>();
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
                checkValues();
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
                checkValues();
            }
        }


    }

    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
}