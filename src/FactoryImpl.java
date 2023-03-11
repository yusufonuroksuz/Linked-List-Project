import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
public class FactoryImpl implements Factory {
	
	private Holder first = null;
	private Holder last = null;
	private Integer size = 0;
	
	public FactoryImpl() {
		
	}
	@Override
	public void addFirst(Product product) {
		Holder first = new Holder(null, product, null);
		if (size == 0) {
			this.first = first;
			this.last = first;
		}
		else {
			this.first.setPreviousHolder(first);
			first.setNextHolder(this.first);
			this.first = first;
		}
		this.size += 1;
	}

	@Override
	public void addLast(Product product) {
		Holder last = new Holder(null, product, null);
		if (size == 0) {
			this.first = last;
			this.last = last;
		}
		else {
			this.last.setNextHolder(last);
			last.setPreviousHolder(this.last);
			this.last = last;
		}
		this.size += 1;
	}

	@Override
	public Product removeFirst() throws NoSuchElementException {
		if (size == 0) throw new NoSuchElementException();
		else if (size == 1) {
			Product first = this.first.getProduct();
			this.first = null;
			this.last = null;
			this.size -= 1;
			return first;
		}
		else if (size > 1) {
			Product first = this.first.getProduct();
			this.first.getNextHolder().setPreviousHolder(null);
			this.first = this.first.getNextHolder();
			this.size -= 1;
			return first;
		}
		return null;
	}

	@Override
	public Product removeLast() throws NoSuchElementException {
		if (size == 0) {
			throw new NoSuchElementException();
		}
		else if (size == 1) {
			Product last = this.last.getProduct();
			this.first = null;
			this.last = null;
			this.size -= 1;
			return last;
		}
		else if (size > 1) {
			Product last = this.last.getProduct();
			this.last.getPreviousHolder().setNextHolder(null);
			this.last = this.last.getPreviousHolder();
			this.size -= 1;
			return last;
		}
		return null;
	}

	@Override
	public Product find(int id) throws NoSuchElementException {
		Holder current = this.first;
		for (int i = 0; i < this.size; i++) {
			if (current.getProduct().getId() == id) return current.getProduct();
			current = current.getNextHolder();
		}
		if (current == null) throw new NoSuchElementException();
		return null;
	}

	@Override
	public Product update(int id, Integer value) throws NoSuchElementException {
		Holder current = this.first;
		for (int i = 0; i < this.size; i++) {
			if (current.getProduct().getId() == id) {
				Product returnProduct = new Product(id, current.getProduct().getValue());
				current.getProduct().setValue(value);
				return returnProduct;
			}
			current = current.getNextHolder();
		}
		if (current == null) throw new NoSuchElementException();
		return null;
	}

	@Override
	public Product get(int index) throws IndexOutOfBoundsException {
		if (index > this.size - 1 || index < 0) throw new IndexOutOfBoundsException();
		Holder current = this.first;
		for (int i = 0; i < index; i++) current = current.getNextHolder();
		return current.getProduct();
	}

	@Override
	public void add(int index, Product product) throws IndexOutOfBoundsException {
		if (index < 0 || index > size) throw new IndexOutOfBoundsException();
		else if (index == 0) this.addFirst(product);
		else if (index == size) this.addLast(product);
		else if (index > 0 && index < size) {
			Holder current = this.first;
			for (int i = 0; i < index - 1; i++) current = current.getNextHolder();
			Holder nextHolder = current.getNextHolder();
			Holder newHolder = new Holder(current, product, nextHolder);
			current.setNextHolder(newHolder);
			nextHolder.setPreviousHolder(newHolder);
			this.size += 1;
		}
		
	}

	@Override
	public Product removeIndex(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index > size - 1) throw new IndexOutOfBoundsException();
		else if (index == 0) return this.removeFirst();
		else if (index == size - 1) return this.removeLast();
		else if (index > 0 && index < (size - 1)) {
			Holder current = this.first;
			for (int i = 0; i < index - 1; i++) current = current.getNextHolder();
			Holder removedHolder = current.getNextHolder();
			Holder nextHolder = removedHolder.getNextHolder(); 
			current.setNextHolder(nextHolder);
			nextHolder.setPreviousHolder(current);
			this.size -= 1;
			return removedHolder.getProduct();
		}
		return null;
	}

	@Override
	public Product removeProduct(int value) throws NoSuchElementException {
		Holder current = this.first;
		for (int i = 0; i < size; i++) {
			if (current.getProduct().getValue() == value) return removeIndex(i);
			current = current.getNextHolder();
			if (current == null) break;
			
		}
		if (current == null) throw new NoSuchElementException();
		else return null;
	}

	@Override
	public int filterDuplicates() {
		int toBeRemovedCounter = 0;
		ArrayList<Integer> duplicateCheck = new ArrayList<>();
		ArrayList<Integer> toBeRemoved = new ArrayList<>();
		Holder current = this.first;
		for (int i = 0; i < size; i++) {
			Integer val = current.getProduct().getValue();
			if (duplicateCheck.contains(val)) {
				toBeRemovedCounter +=1;
				toBeRemoved.add(i);
			}
			else duplicateCheck.add(val);
			current = current.getNextHolder();
		}
		if (toBeRemovedCounter == 0) return 0;
		else {
			int c = 0;
			for (int i = 0; i < toBeRemovedCounter; i++) {
				this.removeIndex((int) toBeRemoved.get(i)-c);
				c++;
			}
			return toBeRemovedCounter;
		}
	}

	@Override
	public void reverse() {
		if (size < 2) return;
		Holder current = this.first;
		Holder oldPrev = this.first.getPreviousHolder();
		Holder oldNext = this.first.getNextHolder();
		for (int i = 0; i < size; i++) {
			oldPrev = current.getPreviousHolder();
			oldNext = current.getNextHolder();
			current.setNextHolder(oldPrev);
			current.setPreviousHolder(oldNext);
			current = current.getPreviousHolder();
		}
		Holder newFirst = this.first;
		this.first = this.last;
		this.last = newFirst;
	}
	public void productPrinter(FileWriter output, Product product) throws IOException  {
		String text = "(" + product.getId() + ", " + product.getValue() + ")";
		output.write(text);
	}
	public void factoryPrinter(FileWriter output) throws IOException {
		Holder current = this.first;
		output.write("{");
		for (int i = 0; i < size; i++) {
			productPrinter(output, current.getProduct());
			current = current.getNextHolder();
			if (current == null) break;
			output.write(",");
		}
		output.write("}\n");
	}
	public String factoryPrintr() {
		Holder current = this.first;
		String str = "{";
		for (int i = 0; i < size; i++) {
			str += current.getProduct().getId();
			str += " ";
			str += current.getProduct().getValue();
			current = current.getNextHolder();
			if (current == null) break;
			str += ",";
		}
		str += ("}\n");
		return str;
	}
	
	
}