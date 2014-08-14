package mars.database.bean;

/**
 * –««Ú£∫≤‚ ‘”√
 * 
 * @author TC
 * 
 */
public class Star extends BaseBean {
	private static final long serialVersionUID = 1L;
	private int id;
	private int weight;
	private int radius;
	private String name;

	public Star() {

	}

	public Star(int weight, int radius, String name) {
		this.weight = weight;
		this.radius = radius;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
