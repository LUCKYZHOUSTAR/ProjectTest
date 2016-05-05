package Apache;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class SampleBean {

	private String name;
	private int age;
	private String[] array;
	private List<String> list;
	private Map<String, String> map;
	private NestedBean nestedBean;
	private URL url;
	private Date date;

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String[] getArray() {
		return array;
	}

	public void setArray(String[] array) {
		this.array = array;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public NestedBean getNestedBean() {
		return nestedBean;
	}

	public void setNestedBean(NestedBean nestedBean) {
		this.nestedBean = nestedBean;
	}

	public static class NestedBean {

		private String nestedProperty;

		public String getNestedProperty() {
			return nestedProperty;
		}

		public void setNestedProperty(String nestedProperty) {
			this.nestedProperty = nestedProperty;
		}
	}

}
