package mack.items;

public class Items {
	public static Item[] items_list;
	

	public static final Item toutprendre = add_item(new Item(0).set_icon(0, 0).set_name(" Tout prendre ").set_price(0).set_description(" Recupere tout les objets "));
	public static final Item dague = add_item(new ItemWeapon(1).set_icon(0, 1).set_name(" Dague ").set_character("Dagger").set_price(20).set_force(6).set_sagesse(5));
	public static final Item daguemagik = add_item(new ItemWeapon(2).set_icon(0, 1).set_name(" Dague Magik ").set_character("Dagger").set_price(40).set_force(8).set_sagesse(6));
	public static final Item kukri = add_item(new ItemWeapon(3).set_icon(1, 1).set_name(" Kukri ").set_character("Dagger").set_price(60).set_force(10).set_sagesse(8));
	public static final Item assassine = add_item(new ItemWeapon(4).set_icon(1, 1).set_name(" Assassine ").set_character("Dagger").set_price(120).set_force(12).set_sagesse(10));
	public static final Item organix = add_item(new ItemWeapon(5).set_icon(1, 1).set_name(" Organix ").set_character("Dagger").set_price(180).set_force(14).set_sagesse(13));
	public static final Item monarch = add_item(new ItemWeapon(6).set_icon(1, 1).set_name(" Monarch ").set_character("Dagger").set_price(230).set_force(17).set_sagesse(17));
	public static final Item hachette = add_item(new ItemWeapon(7).set_icon(2, 1).set_name(" Hachette ").set_character("Axe").set_price(30).set_force(10));
	public static final Item tomahawk = add_item(new ItemWeapon(8).set_icon(2, 1).set_name(" Tomahawk ").set_character("Axe").set_price(60).set_force(16));
	public static final Item fendeuse = add_item(new ItemWeapon(9).set_icon(3, 1).set_name(" Fendeuse ").set_character("Axe").set_price(120).set_force(21));
	public static final Item francisque = add_item(new ItemWeapon(10).set_icon(3, 1).set_name(" Francisque ").set_character("Axe").set_price(200).set_force(27));
	public static final Item glaive = add_item(new ItemWeapon(11).set_icon(4, 1).set_name(" Glaive ").set_character("Sword").set_price(40).set_force(7));
	public static final Item flamberge = add_item(new ItemWeapon(12).set_icon(5, 1).set_name(" Flamberge ").set_character("Sword").set_price(70).set_force(12).set_endurance(3));
	public static final Item sanguine = add_item(new ItemWeapon(13).set_icon(5, 1).set_name(" Sanguine ").set_character("Sword").set_price(120).set_force(15).set_endurance(5));
	public static final Item guardian = add_item(new ItemWeapon(14).set_icon(4, 1).set_name(" Guardian ").set_character("Sword").set_price(190).set_force(19).set_endurance(8));
	public static final Item ragnarock = add_item(new ItemWeapon(15).set_icon(5, 1).set_name(" Ragnarock ").set_character("Sword").set_price(240).set_force(22).set_endurance(11));
	public static final Item canne = add_item(new ItemWeapon(16).set_icon(6, 1).set_name(" Canne ").set_character("Staff").set_price(20).set_force(4).set_sagesse(8));
	public static final Item sceptre = add_item(new ItemWeapon(17).set_icon(7, 1).set_name(" Sceptre ").set_character("Staff").set_price(80).set_force(6).set_sagesse(12));
	public static final Item masse = add_item(new ItemWeapon(18).set_icon(7, 1).set_name(" Masse ").set_character("Staff").set_price(130).set_force(7).set_sagesse(16));
	public static final Item frapeterre = add_item(new ItemWeapon(19).set_icon(7, 1).set_name(" Frapeterre ").set_character("Staff").set_price(180).set_force(10).set_sagesse(23));
	public static final Item mandragore = add_item(new ItemWeapon(20).set_icon(7, 1).set_name(" Mandragore ").set_character("Staff").set_price(270).set_force(12).set_sagesse(28));
	public static final Item gantdecuir = add_item(new ItemWeapon(21).set_icon(12, 1).set_name(" Gant de cuir ").set_character("Poing").set_price(45).set_force(9));
	public static final Item gantdacier = add_item(new ItemWeapon(22).set_icon(12, 1).set_name(" Gant d'acier ").set_character("Poing").set_price(70).set_force(13));
	public static final Item arccourt = add_item(new ItemBow(23).set_icon(10, 1).set_name(" Arc court ").set_price(55).set_force(4));
	public static final Item sagittaire = add_item(new ItemBow(24).set_icon(10, 1).set_name(" Sagittaire ").set_price(90).set_force(7));
	public static final Item chasseur = add_item(new ItemBow(25).set_icon(11, 1).set_name(" Chasseur ").set_price(150).set_force(10));
	public static final Item sevensky = add_item(new ItemBow(26).set_icon(11, 1).set_name(" SevenSky ").set_price(205).set_force(12));
	public static final Item pique = add_item(new ItemSpear(27).set_icon(8, 1).set_name(" Pique ").set_price(70).set_force(5).set_endurance(3));
	public static final Item bardiche = add_item(new ItemSpear(28).set_icon(8, 1).set_name(" Bardiche ").set_price(120).set_force(9).set_endurance(5));
	public static final Item hallebarde = add_item(new ItemSpear(29).set_icon(9, 1).set_name(" Hallebarde ").set_price(180).set_force(11).set_endurance(8));
	public static final Item obelisque = add_item(new ItemSpear(30).set_icon(9, 1).set_name(" Obelisque ").set_price(225).set_force(13).set_endurance(10));
	public static final Item veste = add_item(new ItemArmor(31).set_icon(0, 2).set_name(" Veste ").set_price(10).set_endurance(3).set_esprit(3));
	public static final Item brigandine = add_item(new ItemArmor(32).set_icon(1, 2).set_name(" Brigandine ").set_price(30).set_endurance(4).set_esprit(5));
	public static final Item cuirasse = add_item(new ItemArmor(33).set_icon(2, 2).set_name(" Cuirasse ").set_price(70).set_endurance(6));
	public static final Item cottedacier = add_item(new ItemArmor(34).set_icon(3, 2).set_name(" Cotte d'acier ").set_price(110).set_endurance(7));
	public static final Item plaque = add_item(new ItemArmor(35).set_icon(4, 2).set_name(" Plaque ").set_price(160).set_endurance(9));
	public static final Item peytral = add_item(new ItemArmor(36).set_icon(5, 2).set_name(" Peytral ").set_price(280).set_endurance(11));
	public static final Item soierie = add_item(new ItemArmor(37).set_icon(6, 2).set_name(" Soierie ").set_price(60).set_endurance(3).set_esprit(8));
	public static final Item togemagus = add_item(new ItemArmor(38).set_icon(7, 2).set_name(" Toge Magus ").set_price(150).set_endurance(4).set_esprit(11));
	public static final Item corde = add_item(new ItemCorde(39).set_icon(5, 0).set_name(" Corde ").set_price(50));
	public static final Item potion = add_item(new Item(40).set_icon(1, 0).set_name(" Potion ").set_price(20).set_heal(50).set_description(" Hp+50 "));
	public static final Item potion2 = add_item(new Item(41).set_icon(2, 0).set_name(" Potion+ ").set_price(50).set_heal(100).set_description(" Hp+100 "));
	public static final Item potionx = add_item(new Item(42).set_icon(3, 0).set_name(" PotionX ").set_price(80).set_heal(200).set_description(" Hp+200 "));
	public static final Item potionmax = add_item(new Item(43).set_icon(4, 0).set_name(" Potion Max ").set_price(150).set_heal(9999).set_description(" Soigne tout les Hp "));
	public static final Item fireball = add_item(new ItemSpell(44,0).set_icon(0, 3).set_name(" Fireball ").set_price(50));
	public static final Item soin = add_item(new ItemSpell(45,2).set_icon(1, 3).set_name(" Soin ").set_price(30));
	public static final Item foudre = add_item(new ItemSpell(46,1).set_icon(2, 3).set_name(" Foudre ").set_price(50));
	public static final Item pomme = add_item(new ItemFood(47,6).set_icon(6, 0).set_name(" Pomme ").set_price(25));
	public static final Item couvercle = add_item(new ItemUnusable(48).set_icon(0, 4).set_name(" Couvercle ").set_price(30).set_endurance(2));
	public static final Item rondache = add_item(new ItemUnusable(49).set_icon(0, 4).set_name(" Rondache ").set_price(60).set_endurance(4));
	public static final Item ecu = add_item(new ItemUnusable(50).set_icon(1, 4).set_name(" Ecu ").set_price(90).set_endurance(5));
	public static final Item pavois = add_item(new ItemUnusable(51).set_icon(1, 4).set_name(" Pavois ").set_price(120).set_endurance(7));
	public static final Item templier = add_item(new ItemUnusable(52).set_icon(2, 4).set_name(" Templier ").set_price(180).set_endurance(8));
	public static final Item forciseur = add_item(new ItemUnusable(53).set_icon(0, 5).set_name(" Forciseur ").set_price(180).set_force(2));
	public static final Item arsenal = add_item(new ItemUnusable(54).set_icon(0, 5).set_name(" Arsenal ").set_price(180).set_force(3));
	public static final Item angelarme = add_item(new ItemUnusable(55).set_icon(0, 5).set_name(" Angelarme ").set_price(180).set_force(5));

	

	public static Item add_item(Item item) {
		if (items_list == null)
			items_list = new Item[999];
		if (item instanceof ItemWeapon)
			((ItemWeapon) item).create_descr(item);
		if (item instanceof ItemArmor)
			((ItemArmor) item).create_descr(item);
		if (item instanceof ItemUnusable)
			((ItemUnusable) item).create_descr(item);
		if (item instanceof ItemFood)
			((ItemFood) item).create_descr((ItemFood) item);
		items_list[item.id] = item;
		
		return item;
	}
}
