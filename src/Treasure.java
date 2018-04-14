import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Treasure {
	private ArrayList<ArrayList<String>> treasure;
	private String size;
	private String[] affix;
	private String[] prefix;
	private String[] suffix;
	private String[] armor;
	
	public Treasure(String size) throws FileNotFoundException {
		Scanner in;
		this.size = size;
		if (size.equals("large")) {
			File f = new File("data/large/TreasureClassEx.txt");
			in = new Scanner(f);
		} else {
			File f = new File("data/small/TreasureClassEx.txt");
			in = new Scanner(f);
		}
		
		ArrayList<ArrayList<String>> treasureClass = new ArrayList<>();
		ArrayList<String> lineList;
		
		while (in.hasNextLine()) {
			String line = in.nextLine();
			String[] lineArray = line.split("\t");
			lineList = new ArrayList<>();
			for (int j = 0; j < lineArray.length; j++) {
				lineList.add(lineArray[j]);
			}
			treasureClass.add(lineList);
		}
		in.close();
		this.treasure = treasureClass;
	}
	
	public ArrayList<ArrayList<String>> fetchTreasureClass() {
		return this.treasure;
	}
	
	public String getSize() {
		return this.size;
	}
	

	public String generateBaseItem(String stats) {
		int index = 0;
		for (int i = 0; i < this.treasure.size(); i++) {
			if (stats.equals(this.treasure.get(i).get(0))) {
				index = i;
				break;
			}
		}
		Random rand = new Random();
		int id = rand.nextInt(3);
		String treasure = this.treasure.get(index).get(id);
		
		if (treasure.contains("armo")) {
			for (int i = 0; i < this.treasure.size(); i++) {
				if (treasure.equals(this.treasure.get(i).get(0))) {
					index = i;
					break;
				}
			}
			id = rand.nextInt(3);
			return this.treasure.get(index).get(id);
		} else {
			return generateBaseItem(treasure);
		}
	}
	

	public void generateAffix(String[] item) throws FileNotFoundException {
		Scanner pre;
		Scanner suf;
		int numAffix = 0;
		String[] ret = new String[2];
		
		if (this.size.equals("large")) {
			pre = new Scanner(new File("data/large/MagicPrefix.txt"));
			suf = new Scanner(new File("data/large/MagicSuffix.txt"));
			numAffix = 372;
		} else {
			pre = new Scanner(new File("data/small/MagicPrefix.txt"));
			suf = new Scanner(new File("data/small/MagicSuffix.txt"));
			numAffix = 5;
		}
		
		Random rand = new Random();
		
		int af = rand.nextInt(4);
		int newAffix = rand.nextInt(numAffix);
		generatePrefix(pre, newAffix);
		generateSuffix(suf, newAffix);
		
		for (int i = 0; i < numAffix; i++) {
			if (af == 0) { 
				pre.close();
				suf.close();
				affix = item; 
				return;
			} else if (af == 1) {
				ret[0] = prefix[0] + " " + item[0];
				ret[1] = prefix[1] + " " + prefix[2];
			} else if (af == 2) {
				ret[0] = item[0] + " " + suffix[0];
				ret[1] = suffix[1] + " " + suffix[2];
			} else if (af == 3) {
				ret[0] = prefix[0] + " " + item[0] + " " + suffix[0];
				int val = Integer.parseInt(suffix[1]) + Integer.parseInt(prefix[1]);
				ret[1] = val + " " + prefix[2];
			}
			
		}
		pre.close();
		suf.close();
		this.affix = ret;
	}
	

	public void generateSuffix(Scanner in, int index) throws FileNotFoundException {
		Random rand = new Random();
		String[] affixArr = new String[4];
		String affix;
		String[] ret = new String[3];
		
		affix = findAffix(index, in);
		
		if (affix != null) { 
			affixArr = affix.split("\t");
			ret[0] =  affixArr[0];
			int val = rand.nextInt(Integer.parseInt(affixArr[affixArr.length - 1]) - 
				Integer.parseInt(affixArr[affixArr.length - 2]) + 1) + Integer.parseInt(affixArr[affixArr.length - 2]);
			ret[1] = val + "";
			ret[2] = affixArr[1];
		}
		this.suffix = ret;
	}
	

	public void generatePrefix(Scanner in, int index) throws FileNotFoundException {
		Random rand = new Random();
		String[] arr = new String[4];
		String affix;
		String[] ret = new String[3];
		
		affix = findAffix(index, in);
		
		if (affix != null) { 
			arr = affix.split("\t");
			ret[0] =  arr[0];
			int val = rand.nextInt(Integer.parseInt(arr[arr.length - 1]) - 
				Integer.parseInt(arr[arr.length - 2]) + 1) + Integer.parseInt(arr[arr.length - 2]);
			ret[1] = val + "";
			ret[2] = arr[1];
		}
		this.prefix = ret;
	}
	

	public String findAffix(int index, Scanner in) {
		String aff = new String();
		for (int i = 0; i < index; i++) {
			if (in.hasNext()) { aff = in.nextLine(); }
			else {	return null; }
		}
		return aff;
	}
	
	public String[] getAffix() {
		return affix;
	}
	

	public void generateBaseStats(String treasure) throws FileNotFoundException {
		Scanner in;
		if (this.size.equals("large")) {
			in = new Scanner(new File("data/large/armor.txt"));
		} else {
			in = new Scanner(new File("data/small/armor.txt"));
		}
		
		String[] armor = new String[3];
		while (in.hasNextLine()) {
			String armorStats = in.nextLine();
			armor = armorStats.split("\t");
			if (treasure.equals(armor[0])) {
				in.close();
				this.armor = armor;
				return;
			}
		}
		in.close();
		this.armor = armor;
	}
	

	public int getDefenseValue() {
		Random rand = new Random();
		int defVal = rand.nextInt(Integer.parseInt(this.armor[2]) - 
				Integer.parseInt(this.armor[1]) + 1) + Integer.parseInt(this.armor[1]);
		
		return defVal;
	}
	
	public String[] getBaseStats() {
		return armor;
	}
}