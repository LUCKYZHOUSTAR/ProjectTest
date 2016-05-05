package Apache;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import Apache.SampleBean.NestedBean;

public class BeanUtilsExample {

	public static void main(String[] args) {

		try {
			
			//BeanUtils
			testBeanUtils();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private static void testBeanUtils() throws Exception {
		
		//=======getProperty
		SampleBean bean1 = new SampleBean();
		bean1.setName("rensanning");
		bean1.setAge(31);

		String name = BeanUtils.getProperty(bean1, "name");
		String age  = BeanUtils.getProperty(bean1, "age");

		System.out.println(name);
		System.out.println(age);
		
		//=======setProperty
		SampleBean bean2 = new SampleBean();
		BeanUtils.setProperty(bean2, "name", "rensanning");
		BeanUtils.setProperty(bean2, "age", 31);

		System.out.println(bean2.getName());
		System.out.println(bean2.getAge());

		//=======cloneBean
		SampleBean bean3 = new SampleBean();
		bean3.setName("rensanning");
		bean3.setAge(31);

		SampleBean clone = (SampleBean) BeanUtils.cloneBean(bean3);

		System.out.println(clone.getName());
		System.out.println(clone.getAge());

		//=======describe
		SampleBean bean4 = new SampleBean();
		bean4.setName("rensanning");
		bean4.setAge(31);

		@SuppressWarnings("unchecked")
		Map<String, String> map4 = BeanUtils.describe(bean4);

		System.out.println(map4.get("name"));
		System.out.println(map4.get("age"));
		
		//=======populate
		SampleBean bean5 = new SampleBean();

		Map<String, String> map5 = new HashMap<String, String>();
		map5.put("name", "rensanning");
		map5.put("age", "31");

		BeanUtils.populate(bean5, map5);

		System.out.println(bean5.getName());
		System.out.println(bean5.getAge());
		
		//=======getArrayProperty
		SampleBean bean6 = new SampleBean();
		bean6.setArray(new String[]{"a", "b", "c"});
		List<String> list0 = new ArrayList<String>();
		list0.add("d");
		list0.add("e");
		list0.add("f");
		bean6.setList(list0);

		String[] array = BeanUtils.getArrayProperty(bean6, "array");

		System.out.println(array.length);//3
		System.out.println(array[0]);//"a"
		System.out.println(array[1]);//"b"
		System.out.println(array[2]);//"c"

		String[] list = BeanUtils.getArrayProperty(bean6, "list");
		System.out.println(list.length);//3
		System.out.println(list[0]);//"d"
		System.out.println(list[1]);//"e"
		System.out.println(list[2]);//"f"

		System.out.println(BeanUtils.getProperty(bean6, "array[1]"));//"b"
		System.out.println(BeanUtils.getIndexedProperty(bean6, "array", 2));//"c"
		
		//=======getMappedProperty
		SampleBean bean7 = new SampleBean();
		Map<String, String> map = new HashMap<String, String>();
		map.put("key1", "value1");
		map.put("key2", "value2");
		bean7.setMap(map);

		String value1 = BeanUtils.getMappedProperty(bean7, "map", "key1");
		System.out.println(value1);//"value1"

		String value2 = BeanUtils.getMappedProperty(bean7, "map", "key2");
		System.out.println(value2);//"value2"

		System.out.println(BeanUtils.getProperty(bean7, "map.key1"));//"value1"
		System.out.println(BeanUtils.getProperty(bean7, "map.key2"));//"value2"
		
		//=======getNestedProperty
		SampleBean bean = new SampleBean();
		NestedBean nestedBean = new NestedBean();
		nestedBean.setNestedProperty("xxx");
		bean.setNestedBean(nestedBean);

		String value = BeanUtils.getNestedProperty(bean, "nestedBean.nestedProperty");
		System.out.println(value);//"xxx"

		System.out.println(BeanUtils.getProperty(bean, "nestedBean.nestedProperty"));//"xxx"
		
		//=======testURLConversion
		SampleBean bean8 = new SampleBean();

		BeanUtils.setProperty(bean8, "url", "http://www.google.com/");

		URL url = bean8.getUrl();
		System.out.println(url.getProtocol());//"http"
		System.out.println(url.getHost());//"www.google.com"
		System.out.println(url.getPath());//"/"
		
		//=======testDateConversion
		SampleBean bean9 = new SampleBean();

		DateConverter converter = new DateConverter();
		converter.setPattern("yyyy/MM/dd HH:mm:ss");

		ConvertUtils.register(converter, Date.class);
		ConvertUtils.register(converter, String.class);

		BeanUtils.setProperty(bean9, "date", "2010/12/19 23:40:00");

		String value9 = BeanUtils.getProperty(bean9, "date");
		System.out.println(value9);//"2010/12/19 23:40:00"
	}


}
