package cn.wyb.sble.resources.queryword.model;

/**
 * 扇贝查单词
 * @author wangyongbing
 *
 */
public class QueryWord {

	/**
	 * array 返回英文释义的数组
	 */
	private String en_definitions; 
	/**
	 * object 英文释义
	 */
	private String en_definition; 
	/**
	 * object 中文释义
	 */
	private String cn_definition; 
	/**
	 * int 单词的id
	 */
	private String id; 
	/**
	 * int 单词的熟悉度
	 */
	private String retention; 
	/**
	 * string 中文释义
	 */
	private String definition; 
	/**
	 * int 用户自定义的目标学习度
	 */
	private String target_retention; 
	/**
	 * long learing id，如果未返回，在表明用户没学过该单词
	 */
	private String learning_id; 
	/**
	 * string 查询的单词
	 */
	private String content; 
	/**
	 * string 单词的音标
	 */
	private String pronunciation; 
	
	/**
	 * 英式发音
	 */
	private String uk_audio;
	
	/**
	 * 美式发音
	 */
	private String us_audio;

	
	public String getUk_audio() {
		return uk_audio;
	}

	public void setUk_audio(String uk_audio) {
		this.uk_audio = uk_audio;
	}

	public String getUs_audio() {
		return us_audio;
	}

	public void setUs_audio(String us_audio) {
		this.us_audio = us_audio;
	}

	public String getEn_definitions() {
		return en_definitions;
	}

	public void setEn_definitions(String en_definitions) {
		this.en_definitions = en_definitions;
	}

	public String getEn_definition() {
		return en_definition;
	}

	public void setEn_definition(String en_definition) {
		this.en_definition = en_definition;
	}

	public String getCn_definition() {
		return cn_definition;
	}

	public void setCn_definition(String cn_definition) {
		this.cn_definition = cn_definition;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRetention() {
		return retention;
	}

	public void setRetention(String retention) {
		this.retention = retention;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public String getTarget_retention() {
		return target_retention;
	}

	public void setTarget_retention(String target_retention) {
		this.target_retention = target_retention;
	}

	public String getLearning_id() {
		return learning_id;
	}

	public void setLearning_id(String learning_id) {
		this.learning_id = learning_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPronunciation() {
		return pronunciation;
	}

	public void setPronunciation(String pronunciation) {
		this.pronunciation = pronunciation;
	}

}
