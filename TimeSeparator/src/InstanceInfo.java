import org.joda.time.DateTime;

import weka.core.Instance;


public class InstanceInfo {

	private String userId;
	private String Date;
	private Instance instance;
	private String instanceMessage;
	private String relatedPostId;
	public InstanceInfo()
	{
		
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public Instance getInstance() {
		return instance;
	}

	public void setInstance(Instance instance) {
		this.instance = instance;
	}

	public String getInstanceMessage() {
		return instanceMessage;
	}

	public void setInstanceMessage(String instanceMessage) {
		this.instanceMessage = instanceMessage;
	}

	public String getRelatedPostId() {
		return relatedPostId;
	}

	public void setRelatedPostId(String relatedPostId) {
		this.relatedPostId = relatedPostId;
	}
}
