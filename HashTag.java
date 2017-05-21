/**
 * @author Akshay
 */
public class HashTag extends FiboNode {

	//Name of the hashtag
	private String hashTagName;

	//Getter and setter for hashtags
	public String getHashTagName() {
		return hashTagName;
	}

	public void setHashTagName(String hashTagName) {
		this.hashTagName = hashTagName;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hashTagName == null) ? 0 : hashTagName.hashCode());
		return result;
	}

	//Overrides equals function to compare hashtags
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HashTag other = (HashTag) obj;
		if (hashTagName == null) {
			if (other.hashTagName != null)
				return false;
		} else if (!hashTagName.equals(other.hashTagName))
			return false;
		return true;
	}
	
	
}
