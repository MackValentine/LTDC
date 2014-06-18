package mack.items;

import java.util.Random;

public class ItemArmor extends Item implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6661663132438823969L;
	public int level;

	public ItemArmor(int i) {
		super(i);

		create_descr(this);
	}

	public int type() {
		return 2;
	}

	public Item randomize() throws CloneNotSupportedException {
		Item i = (Item) this.clone();
		int r = new Random().nextInt(30) / 10;
		int j = 0;
		if (r == 0) {
			j = -(new Random().nextInt(30) / 10 + 1);
		} else if (r == 1) {
			j = new Random().nextInt(30) / 10 - 1;
		} else {
			j = new Random().nextInt(30) / 10 + 1;
		}

		level = j;

		if (j > 0)
			i.name += "+" + j;
		else if (j < 0)
			i.name += j;

		if (i.force > 0)
			i.force += j;
		if (i.endurance > 0)
			i.endurance += j;
		if (i.sagesse > 0)
			i.sagesse += j;
		if (i.esprit > 0)
			i.esprit += j;

		create_descr(i);

		return i;
	}

	public void create_descr(Item i) {

		String s = "";
		if (i.force > 0)
			s += "Force+" + i.force + " ";
		if (i.endurance > 0)
			s += "Endurance+" + i.endurance + " ";
		if (i.sagesse > 0)
			s += "Sagesse+" + i.sagesse + " ";
		if (i.esprit > 0)
			s += "Esprit+" + i.esprit + " ";

		i.description = s;
	}

	public Item randomize(int j) throws CloneNotSupportedException {
		Item i = (Item) this.clone();
		if (j > 0)
			i.name += "+" + j;
		else if (j < 0)
			i.name += j;

		level = j;

		if (i.force > 0)
			i.force += j;
		if (i.endurance > 0)
			i.endurance += j;
		if (i.sagesse > 0)
			i.sagesse += j;
		if (i.esprit > 0)
			i.esprit += j;

		create_descr(i);

		return i;
	}

}
