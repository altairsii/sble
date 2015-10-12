package cn.wyb.sble.resources.queryword.model;

/**
 * �ȱ��鵥��
 * @author wangyongbing
 *
 */
public class QueryWord {

	/**
	 * array ����Ӣ�����������
	 */
	private String en_definitions; 
	/**
	 * object Ӣ������
	 */
	private String en_definition; 
	/**
	 * object ��������
	 */
	private String cn_definition; 
	/**
	 * int ���ʵ�id
	 */
	private String id; 
	/**
	 * int ���ʵ���Ϥ��
	 */
	private String retention; 
	/**
	 * string ��������
	 */
	private String definition; 
	/**
	 * int �û��Զ����Ŀ��ѧϰ��
	 */
	private String target_retention; 
	/**
	 * long learing id�����δ���أ��ڱ����û�ûѧ���õ���
	 */
	private String learning_id; 
	/**
	 * string ��ѯ�ĵ���
	 */
	private String content; 
	/**
	 * string ���ʵ�����
	 */
	private String pronunciation; 

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
