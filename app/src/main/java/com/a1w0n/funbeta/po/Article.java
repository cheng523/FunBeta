package com.aiwan.funbeta.po;

public class Article {

	/**
	 * �������ͳ�����cnbeta�ֻ��
	 */
	public static final int TYPE_CNBETA_MOBILE = 1;

	/**
	 * ����
	 */
	private Integer id;
	/**
	 * ����
	 */
	private int type;
	/**
	 * url��ַ
	 */
	private String url;
	/**
	 * ���±���
	 */
	private String title;
	/**
	 * ��������
	 */
	private String content;

	public Article() {
	}

	public Article(int type, String url, String title, String content) {
		super();
		this.type = type;
		this.url = url;
		this.title = title;
		this.content = content;
	}

	public Article(Integer id, int type, String url, String title,
			String content) {
		super();
		this.id = id;
		this.type = type;
		this.url = url;
		this.title = title;
		this.content = content;
	}

	// getter & setter s~
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
