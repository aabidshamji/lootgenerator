import java.io.*;
import java.util.*;

public class LootGenerator {

	public static String[] pickMonster(String size) throws FileNotFoundException {
		Scanner in;
		Random rand = new Random();
		
		int id = rand.nextInt(49);
		String stats = new String();
		String[] statsArr;
		
		if (size.equals("large")) {
			in = new Scanner(new File("data/large/monstats.txt"));
			for (int i = 0; i < id; i++) {
				stats = in.nextLine();
			}
			statsArr = stats.split("\t");
		} else {
			in = new Scanner(new File("data/small/monstats.txt"));
			statsArr = in.nextLine().split("\t");
		}
		in.close();
		return statsArr;
	}
	
	public static void main (String[] args) throws FileNotFoundException {
		boolean playing = true;
		Scanner in = new Scanner(System.in);
		String size = "large";
		while(playing) {
			String[] stats = pickMonster(size);
			String name = stats[0];
			Treasure t = new Treasure(size);
			String treasure = t.generateBaseItem(stats[stats.length - 1]);
			t.generateBaseStats(treasure);
			String[] baseStats = t.getBaseStats();
			t.generateAffix(baseStats);
			String[] affix = t.getAffix();
			String newTreasure = affix[0];
			String extraDefense = affix[1];

			System.out.println("Fighting " + name + "...");
			System.out.println("You have slain " + name + "!");
			System.out.println(name + " dropped:" + "\n");
			System.out.println(newTreasure);
			System.out.println("Defense: " + t.getDefenseValue());
			System.out.println(extraDefense);

			System.out.print("Fight again [y/n]? ");
			String answer = in.nextLine();
			System.out.println("");
			answer = answer.toLowerCase();
			
			if (answer.equals("n")) { 
				playing = false; 
			} 
		}
		in.close();
	}
}