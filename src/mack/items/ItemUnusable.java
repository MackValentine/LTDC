package mack.items;


public class ItemUnusable extends Item {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1785350535509606812L;

	public ItemUnusable(int i) {
		super(i);
		set_usable(false);
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
}
