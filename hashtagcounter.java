import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * @author Akshay
 *
 */
public class hashtagcounter {

	/**
	 * Reads file name from input command line arguments and generates output_file.txt
	 * Implements fibonacci heap to sort the input data.
	 * @param args : Take path to the file as input argument
	 */
	public static void main(String[] args)  {
		String filename = args[0];
		File inputFile = new File(filename);
		Scanner scanner;
		try {
			scanner = new Scanner(inputFile);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			return;
		}
		File outputFile = new File("output_file.txt");
		//to write file 
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile)));
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			return;
		}
		String input = null;
		HashTag hashtag = null;
		String[] tagNCount = null;
		Hashtable<String, HashTag> hashtable = new Hashtable<String, HashTag>();
		FiboHeap<HashTag> tree = new FiboHeap<HashTag>();
		String hashTagTitle;
		while (scanner.hasNextLine()) {
			input = scanner.nextLine();
			if (input.trim().equalsIgnoreCase("stop")) {
				break;
			}
			// now check if we have to output the top n Hash Tags
			if (!(input.charAt(0) == '#')) {
				try {
					int pop_count = Integer.parseInt(input);
					if (pop_count > hashtable.size()) {
						System.out.println(pop_count+ " <=== pop count grater than number of Hash Tags in the tree===>" + hashtable.size());
						return;
					}
					tree = pop(tree, pop_count, bw);
					bw.newLine();
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (IOException e) {
					System.out.println(e.getMessage());
					return;
				}
				
			} else {
				tagNCount = input.split(" ");
				hashTagTitle = tagNCount[0].substring(1, tagNCount[0].length());
				hashtag = hashtable.get(hashTagTitle);
				if (hashtag == null) {
					hashtag = new HashTag();
					hashtag.setHashTagName(hashTagTitle);
					hashtag.setData(Integer.parseInt(tagNCount[1]));
					hashtable.put(hashTagTitle, hashtag);
					tree = tree.insert(tree, hashtag);
				} else {
					// increase key
					tree = tree.increaseKey(tree, hashtag,
							Integer.parseInt(tagNCount[1]));
				}
			}

		}
		try {
			bw.flush();
			bw.close();
		} catch (IOException e) {
			System.out.println();
			return;
		}
		scanner.close();
	}

	//pops and writes top hashtags on output_file.txt
	public static FiboHeap<HashTag> pop(FiboHeap<HashTag> H, int count, BufferedWriter bw) throws IOException {
		StringBuffer outputTags = new StringBuffer();
		HashTag topHashTags = null;
		List<HashTag> removedNodes = new ArrayList<HashTag>();
		for (int i = 0; i < count; i++) {
			topHashTags = H.getMax();
			removedNodes.add(topHashTags);
			// add the hashtag to the output buffer
			if (i > 0) {
				outputTags.append(",");
			}
			outputTags.append(topHashTags.getHashTagName());
			// to get highest count hashtag from the input data structure
			H = H.removeMax(H);
		}
		bw.write(outputTags.toString());
		//now reinsert these top popular Hash Tags into the heap
		for (Iterator<HashTag> iterator = removedNodes.iterator(); iterator.hasNext();) {
			HashTag hashTag = (HashTag) iterator.next();
			H = H.insert(H, hashTag);
		}
		return H;
	}

}
