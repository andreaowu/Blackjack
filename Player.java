import java.util.ArrayList;
import java.util.Arrays;

public class Player {
	int number;
	ArrayList<Card> cards;
	ArrayList<Integer> sums;
	String display;
	int numOfAces;
	int sum;
	int maxSumWithoutBust;

	public Player(int number) {
		this.number = number + 1;
		cards = new ArrayList<Card>();
		sums = new ArrayList<Integer>();
		display = "Player " + (number + 1) + " has these cards: ";
		numOfAces = 0;
		sum = 0;
		maxSumWithoutBust = 0;
	}
	
	public int getNumber() {
		return number;
	}
	
	public void getCard(Card c) {
		cards.add(c);
		display += c.printInfo() + ", ";
		if (c.getValue() == 1) {
			numOfAces += 1;
			ArrayList<Integer> newSums = new ArrayList<Integer>();
			maxSumWithoutBust = -1;
			
			/* This is the first Ace encountered */
			if (sums.size() == 0) {
				newSums = addForAce(sum);
			} else {
				/* This is the second Ace encountered, so 
				 * there's already a list of possible sums */
				for (int i = 0; i < sums.size(); i++) {
					int sum = sums.get(i);
					newSums = addForAce(sum);
				}
			}
			sums = newSums;
		} else {
			if (numOfAces == 0) {
				sum += c.getValue();
				setMaxSumWithoutBust(sum);
			} else {
				for (int i = 0; i < sums.size(); i++) {
					sum = sums.get(i);
					sums.set(i, sum + c.getValue());
					setMaxSumWithoutBust(sum);
				}
			}
		}
		System.out.println("sums length " + sums.size());
	}
	
	public ArrayList<Integer> addForAce(int oneSum) {
		ArrayList<Integer> newSums = new ArrayList<Integer>();
		int addOne = oneSum + 1;
		int addEleven = oneSum + 11;
		newSums.add(addOne);
		newSums.add(addEleven);
		setMaxSumWithoutBust(addEleven);
		setMaxSumWithoutBust(addOne);
		return newSums;
	}
	
	public void setMaxSumWithoutBust(int sum) {
		if (sum <= 21) {
			if (sum > maxSumWithoutBust)
				maxSumWithoutBust = sum;
		} else {
			if (maxSumWithoutBust > 0)
				maxSumWithoutBust = -1;
		}
	}
	
	public int getMaxSumWithoutBust() {
		return maxSumWithoutBust;
	}
	
	public ArrayList<Integer> getSums() {
		if (numOfAces > 0)
			return sums;
		return new ArrayList<Integer>(Arrays.asList(sum));
	}
	
	public void printCards() {
		System.out.println(Globals.deleteComma(display));
	}
	
	public void printSums() {
		String possibleSums = "Possible sums: ";
		if (numOfAces == 0) {
			possibleSums += sum;
		} else {
			for (int i = 0; i < sums.size(); i++)  {
				possibleSums += sums.get(i) + ", ";
			}
			possibleSums = Globals.deleteComma(possibleSums);
		}
		System.out.println(possibleSums);
	}
}
