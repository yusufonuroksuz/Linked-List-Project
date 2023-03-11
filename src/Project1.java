import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Project1 {
	public static void main(String args[]) throws IOException {
		File inputFile = new File(args[0]);
		File outputFile = new File(args[1]);
		Scanner input = new Scanner(inputFile);
		FileWriter output = new FileWriter(outputFile, Charset.forName("UTF_16"));
		
		FactoryImpl factory = new FactoryImpl();
		while (input.hasNextLine()) {
			String[] command = input.nextLine().split(" ");
			if (command[0].equals("AF")) {
				int id = Integer.parseInt(command[1]);
				Integer val = Integer.parseInt(command[2]);
				Product product = new Product(id, val);
				factory.addFirst(product);
			}
			if (command[0].equals("AL")) {
				int id = Integer.parseInt(command[1]);
				Integer val = Integer.parseInt(command[2]);
				Product product = new Product(id, val);
				factory.addLast(product);
			}
			if (command[0].equals("A")) {
				try {
				int index = Integer.parseInt(command[1]);
				int id = Integer.parseInt(command[2]);
				Integer val = Integer.parseInt(command[3]);
				Product product = new Product(id, val);
				factory.add(index, product);
				}
				catch (IndexOutOfBoundsException e) {
					output.write("Index out of bounds.\n");
				}
			}
			if (command[0].equals("RF")) {
				try {
					Product toBePrinted = factory.removeFirst();
					factory.productPrinter(output, toBePrinted);
					output.write("\n");
				}
				catch (NoSuchElementException e) {
					output.write("Factory is empty.\n");
				}
			}
			if (command[0].equals("RL")) {
				try {
					Product toBePrinted = factory.removeLast();
					factory.productPrinter(output, toBePrinted);
					output.write("\n");
				}
				catch (NoSuchElementException e) {
					output.write("Factory is empty.\n");
				}
			}
			if (command[0].equals("RI")) {
				try {
					int index = Integer.parseInt(command[1]);
					Product toBePrinted = factory.removeIndex(index);
					factory.productPrinter(output, toBePrinted);
					output.write("\n");
				}
				catch (IndexOutOfBoundsException e) {
					output.write("Index out of bounds.\n");
				}	
			}
			if (command[0].equals("RP")) {
				try {
					int val = Integer.parseInt(command[1]);
					Product toBePrinted = factory.removeProduct(val);
					factory.productPrinter(output, toBePrinted);
					output.write("\n");
				}
				catch (NoSuchElementException e) {
					output.write("Product not found.\n");
				}
			}
			if (command[0].equals("F")) {
				try {
					int id = Integer.parseInt(command[1]);
					Product toBePrinted = factory.find(id);
					factory.productPrinter(output, toBePrinted);
					output.write("\n");
				}
				catch (NoSuchElementException e) {
					output.write("Product not found.\n");
				}
			}
			if (command[0].equals("G")) {
				try {
					int index = Integer.parseInt(command[1]);
					Product toBePrinted = factory.get(index);
					factory.productPrinter(output, toBePrinted);
					output.write("\n");
				}
				catch (IndexOutOfBoundsException e) {
					output.write("Index out of bounds.\n");
				}
			}
			if (command[0].equals("U")) {
				try {
					int id = Integer.parseInt(command[1]);
					Integer val = Integer.parseInt(command[2]);
					Product toBePrinted = factory.update(id, val);
					factory.productPrinter(output, toBePrinted);
					output.write("\n");
				}
				catch (NoSuchElementException e) {
					output.write("Product not found.\n");
				}
			}
			if (command[0].equals("FD")) {
				int duplicates = factory.filterDuplicates();
				output.write(Integer.toString(duplicates));
				output.write("\n");
			}
			if (command[0].equals("R")) {
				factory.reverse();
				factory.factoryPrinter(output);
			}
			if (command[0].equals("P")) {
				factory.factoryPrinter(output);
			}
			
		}
		input.close();
		output.close();
		
		
		
		
	}
}