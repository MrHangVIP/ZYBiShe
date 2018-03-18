package com.zy.utils;

import java.io.IOException;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class JsonHelp {
	/**
	 * 
	 * @author wangwei JSON工具�?
	 * @param <T>
	 * 
	 */

	/***
	 * 将List对象序列化为JSON文本
	 */
	public static <T> String toJSONString(List<T> list) {
		JSONArray jsonArray = JSONArray.fromObject(list);
		return jsonArray.toString();
	}

	/***
	 * 将对象序列化为JSON文本
	 * 
	 * @param object
	 * @return
	 */
	public static String toJSONString(Object object) {
		JSONArray jsonArray = JSONArray.fromObject(object);
		return jsonArray.toString();
	}

	/***
	 * 将JSON对象数组序列化为JSON文本
	 * 
	 * @param jsonArray
	 * @return
	 */
	public static String toJSONString(JSONArray jsonArray) {
		return jsonArray.toString();
	}

	/***
	 * 将JSON对象序列化为JSON文本
	 * 
	 * @param jsonObject
	 * @return
	 */
	public static String toJSONString(JSONObject jsonObject) {
		return jsonObject.toString();
	}

	/***
	 * 将对象转换为List对象
	 * 
	 * @param object
	 * @return
	 */
	public static List toArrayList(Object object) {
		List arrayList = new ArrayList();
		JSONArray jsonArray = JSONArray.fromObject(object);
		Iterator it = jsonArray.iterator();
		while (it.hasNext()) {
			JSONObject jsonObject = (JSONObject) it.next();
			Iterator keys = jsonObject.keys();
			while (keys.hasNext()) {
				Object key = keys.next();
				Object value = jsonObject.get(key);
				arrayList.add(value);
			}
		}
		return arrayList;
	}

	/***
	 * 将对象转换为Collection对象
	 * 
	 * @param object
	 * @return
	 */
	// public static Collection toCollection(Object object) {
	// JSONArray jsonArray = JSONArray.fromObject(object);
	// return JSONArray.toCollection(jsonArray);
	// }

	/***
	 * 将对象转换为JSON对象数组
	 * 
	 * @param object
	 * @return
	 */
	public static JSONArray toJSONArray(Object object) {
		return JSONArray.fromObject(object);
	}

	/***
	 * 将对象转换为JSON对象
	 * 
	 * @param object
	 * @return
	 */
	public static JSONObject toJSONObject(Object object) {
		return JSONObject.fromObject(object);
	}

	/***
	 * 将对象转换为HashMap
	 * 
	 * @param object
	 * @return
	 */
	public static HashMap toHashMap(Object object) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		JSONObject jsonObject = JsonHelp.toJSONObject(object);
		Iterator it = jsonObject.keys();
		while (it.hasNext()) {
			String key = String.valueOf(it.next());
			Object value = jsonObject.get(key);
			data.put(key, value);
		}
		return data;
	}

	/***
	 * 将对象转换为List<Map<String,Object>>
	 * 
	 * @param object
	 * @return
	 */
	// 返回非实体类�?(Map<String,Object>)的List
	public static List<Map<String, Object>> toList(Object object) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		JSONArray jsonArray = JSONArray.fromObject(object);
		for (Object obj : jsonArray) {
			JSONObject jsonObject = (JSONObject) obj;
			Map<String, Object> map = new HashMap<String, Object>();
			Iterator it = jsonObject.keys();
			while (it.hasNext()) {
				String key = (String) it.next();
				Object value = jsonObject.get(key);
				map.put((String) key, value);
			}
			list.add(map);
		}
		return list;
	}

	/***
	 * 将JSON对象数组转换为传入类型的List
	 * 
	 * @param <T>
	 * @param jsonArray
	 * @param objectClass
	 * @return
	 */
	public static <T> List<T> toList(JSONArray jsonArray, Class<T> objectClass) {
		return JSONArray.toList(jsonArray, objectClass);
	}

	/***
	 * 将对象转换为传入类型的List
	 * 
	 * @param <T>
	 * @param jsonArray
	 * @param objectClass
	 * @return
	 */
	public static <T> List<T> toList(Object object, Class<T> objectClass) {
		JSONArray jsonArray = JSONArray.fromObject(object);
		return JSONArray.toList(jsonArray, objectClass);
	}

	/***
	 * 将JSON对象转换为传入类型的对象
	 * 
	 * @param <T>
	 * @param jsonObject
	 * @param beanClass
	 * @return
	 */
	public static <T> T toBean(JSONObject jsonObject, Class<T> beanClass) {
		return (T) JSONObject.toBean(jsonObject, beanClass);
	}

	/***
	 * 将将对象转换为传入类型的对象
	 * 
	 * @param <T>
	 * @param object
	 * @param beanClass
	 * @return
	 */
	public static <T> T toBean(Object object, Class<T> beanClass) {
		JSONObject jsonObject = JSONObject.fromObject(object);
		return (T) JSONObject.toBean(jsonObject, beanClass);
	}

	/***
	 * 将JSON文本反序列化为主从关系的实体
	 * 
	 * @param <T>
	 *            泛型T 代表主实体类�?
	 * @param <D>
	 *            泛型D 代表从实体类�?
	 * @param jsonString
	 *            JSON文本
	 * @param mainClass
	 *            主实体类�?
	 * @param detailName
	 *            从实体类在主实体类中的属性名�?
	 * @param detailClass
	 *            从实体类�?
	 * @return
	 */
	public static <T, D> T toBean(String jsonString, Class<T> mainClass, String detailName, Class<D> detailClass) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		JSONArray jsonArray = (JSONArray) jsonObject.get(detailName);
		T mainEntity = JsonHelp.toBean(jsonObject, mainClass);
		List<D> detailList = JsonHelp.toList(jsonArray, detailClass);
		try {
			// BeanUtils.setProperty(mainEntity, detailName, detailList);
		} catch (Exception ex) {
			throw new RuntimeException("主从关系JSON反序列化实体失败�?");
		}
		return mainEntity;
	}

	/***
	 * 将JSON文本反序列化为主从关系的实体
	 * 
	 * @param <T>泛型T
	 *            代表主实体类�?
	 * @param <D1>泛型D1
	 *            代表从实体类�?
	 * @param <D2>泛型D2
	 *            代表从实体类�?
	 * @param jsonString
	 *            JSON文本
	 * @param mainClass
	 *            主实体类�?
	 * @param detailName1
	 *            从实体类在主实体类中的属�?
	 * @param detailClass1
	 *            从实体类�?
	 * @param detailName2
	 *            从实体类在主实体类中的属�?
	 * @param detailClass2
	 *            从实体类�?
	 * @return
	 */
	public static <T, D1, D2> T toBean(String jsonString, Class<T> mainClass, String detailName1,
			Class<D1> detailClass1, String detailName2, Class<D2> detailClass2) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		JSONArray jsonArray1 = (JSONArray) jsonObject.get(detailName1);
		JSONArray jsonArray2 = (JSONArray) jsonObject.get(detailName2);
		T mainEntity = JsonHelp.toBean(jsonObject, mainClass);
		List<D1> detailList1 = JsonHelp.toList(jsonArray1, detailClass1);
		List<D2> detailList2 = JsonHelp.toList(jsonArray2, detailClass2);
		try {
			// BeanUtils.setProperty(mainEntity, detailName1, detailList1);
			// BeanUtils.setProperty(mainEntity, detailName2, detailList2);
		} catch (Exception ex) {
			throw new RuntimeException("主从关系JSON反序列化实体失败�?");
		}
		return mainEntity;
	}

	/***
	 * 将JSON文本反序列化为主从关系的实体
	 * 
	 * @param <T>泛型T
	 *            代表主实体类�?
	 * @param <D1>泛型D1
	 *            代表从实体类�?
	 * @param <D2>泛型D2
	 *            代表从实体类�?
	 * @param jsonString
	 *            JSON文本
	 * @param mainClass
	 *            主实体类�?
	 * @param detailName1
	 *            从实体类在主实体类中的属�?
	 * @param detailClass1
	 *            从实体类�?
	 * @param detailName2
	 *            从实体类在主实体类中的属�?
	 * @param detailClass2
	 *            从实体类�?
	 * @param detailName3
	 *            从实体类在主实体类中的属�?
	 * @param detailClass3
	 *            从实体类�?
	 * @return
	 */
	public static <T, D1, D2, D3> T toBean(String jsonString, Class<T> mainClass, String detailName1,
			Class<D1> detailClass1, String detailName2, Class<D2> detailClass2, String detailName3,
			Class<D3> detailClass3) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		JSONArray jsonArray1 = (JSONArray) jsonObject.get(detailName1);
		JSONArray jsonArray2 = (JSONArray) jsonObject.get(detailName2);
		JSONArray jsonArray3 = (JSONArray) jsonObject.get(detailName3);
		T mainEntity = JsonHelp.toBean(jsonObject, mainClass);
		List<D1> detailList1 = JsonHelp.toList(jsonArray1, detailClass1);
		List<D2> detailList2 = JsonHelp.toList(jsonArray2, detailClass2);
		List<D3> detailList3 = JsonHelp.toList(jsonArray3, detailClass3);
		try {
			// BeanUtils.setProperty(mainEntity, detailName1, detailList1);
			// BeanUtils.setProperty(mainEntity, detailName2, detailList2);
			// BeanUtils.setProperty(mainEntity, detailName3, detailList3);
		} catch (Exception ex) {
			throw new RuntimeException("主从关系JSON反序列化实体失败�?");
		}
		return mainEntity;
	}

	/***
	 * 将JSON文本反序列化为主从关系的实体
	 * 
	 * @param <T>
	 *            主实体类�?
	 * @param jsonString
	 *            JSON文本
	 * @param mainClass
	 *            主实体类�?
	 * @param detailClass
	 *            存放了多个从实体在主实体中属性名称和类型
	 * @return
	 */
	public static <T> T toBean(String jsonString, Class<T> mainClass, HashMap<String, Class> detailClass) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		T mainEntity = JsonHelp.toBean(jsonObject, mainClass);
		for (Object key : detailClass.keySet()) {
			try {
				Class value = (Class) detailClass.get(key);
				// BeanUtils.setProperty(mainEntity, key.toString(), value);
			} catch (Exception ex) {
				throw new RuntimeException("主从关系JSON反序列化实体失败�?");
			}
		}
		return mainEntity;
	}

	/**
	 * 封装json数据从后台传�?
	 * 
	 * @param obj
	 */
	public static void outPutJson(Object obj) {
		// ActionContext context = ActionContext.getContext();
		// HttpServletResponse response = (HttpServletResponse)
		// context.get(ServletActionContext.HTTP_RESPONSE);
		// try {
		// response.getWriter().print(obj);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}

	//  /**
	//    * 将json转化为实体POJO
	//    * @param jsonStr
	//    * @param obj
	//    * @return
	//    */
	//   public static<T> Object JSONToObj(String jsonStr,Class<T> obj) {
	//     T t = null;
	//     try {
	//       ObjectMapper objectMapper = new ObjectMapper();
	//       t = objectMapper.readValue(jsonStr,
	//           obj);
	//     } catch (Exception e) {
	//       e.printStackTrace();
	//     }
	//     return t;
	//   }
	//   /**
	//    * 将实体POJO转化为JSON
	//    * @param obj
	//    * @return
	//    * @throws JSONException
	//    * @throws IOException
	//    */
	//   public static<T> JSONObject objectToJson(T obj) throws JSONException,
	// IOException {
	//     ObjectMapper mapper = new ObjectMapper();
	//     // Convert object to JSON string
	//     String jsonStr = "";
	//     try {
	//        jsonStr = mapper.writeValueAsString(obj);
	//     } catch (IOException e) {
	//       throw e;
	//     }
	//     return new JSONObject(jsonStr);
	//   }

	/**
	 * json 数据转实体ojb
	 * 
	 * @param jsonStr
	 * @param obj
	 * @return
	 */
	public static <T> Object JSONToOjb(String jsonStr, Class<T> obj) {
		T t = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			t = objectMapper.readValue(jsonStr, obj);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return t;

	}

	// public static <T> JSONObject objectToJson(T ojb){
	// ObjectMapper mapper = new ObjectMapper();
	// String jsonStr="";
	// try{
	// jsonStr=mapper.writeValueAsString(ojb);
	// }catch(IOException e){
	//
	// }
	// return new org.json.JSONObject(jsonStr);
	// }

	public static String resultSetToJson(java.sql.ResultSet rs) throws SQLException {
		// json数组
		JSONArray array = new JSONArray();

		// 获取列数
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();

		// 遍历ResultSet中的每条数据
		while (rs.next()) {
			JSONObject jsonObj = new JSONObject();

			// 遍历每一�?
			for (int i = 1; i <= columnCount; i++) {
				String columnName = metaData.getColumnLabel(i);
				String value = rs.getString(columnName);
				jsonObj.put(columnName, value);
			}
			array.add(jsonObj);
		}

		return array.toString();
	}
}
