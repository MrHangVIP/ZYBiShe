package com.zy.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.zy.servlets.base.BaseServletFactory;

import net.sf.json.JSONArray;

public class ImageUpload extends BaseServletFactory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 222834079692167531L;

	@Override
	protected Map<String, String> dataModel(HttpServletRequest request, HttpServletResponse response) {
		String userPhone = request.getParameter("userPhone");
		String token = request.getParameter("token");
		boolean tokeneffect = tokenChecked(userPhone, token);
		if (!tokeneffect) {
			return getJsonMap();
		}
		// String getServletPath= request.getServletPath();
		// D:\GitWorkSpace\.metadata\.plugins\org.eclipse.wst.server.core\tmp1\wtpwebapps\jyzBiShe\jyzBiShe\ImageUpload
		// getRealPath=request.getSession().getServletContext().getRealPath(request.getRequestURI());
		// D:\GitWorkSpace\.metadata\.plugins\org.eclipse.wst.server.core\tmp1\wtpwebapps\jyzBiShe\
		String imagePath = this.getServletConfig().getServletContext().getRealPath("/") + "//image//";
		// /Users/songzhihang/Workspace/gitworkspace/webWorkSpace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/jyzbishe/image/1489657508511.png
		// windows 和mac 文件系统区别 �?以文件目录符号不�?
		// File imageFile = new File(imagePath + "\\image\\" +
		// fileName);
		// 根据文件创建文件的输出流
		List<String> images = new ArrayList<String>();
		List<String> imageNameList = new ArrayList<String>();
		Map<String, String[]> params = request.getParameterMap();
		for (int i = 1; i <= params.keySet().size() - 2; i++) {
			for (String key : params.keySet()) {
				if (key.equals("image" + i)) {
					images.add(request.getParameter(key));
				}
			}
		}
		for (int i = 0; i < images.size(); i++) {
			String fileName = System.currentTimeMillis() + ".png";
			// 获取base64的图片二进制
			byte[] imageByte = null;
			try {
				imageByte = Base64.decode(images.get(i));
			} catch (Base64DecodingException e) {
				e.printStackTrace();
				System.out.println("json  :" + e.toString());
			}
			// 将二进制转换成输出流
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			OutputStream out = null;
			try {
				bos.write(imageByte);
				// 写入文件
				File imagePathFile=new File(imagePath);
				if (!imagePathFile.exists()) {
					imagePathFile.mkdirs();
				}
				File imageFile = new File(imagePath + fileName);
				out = new FileOutputStream(imageFile);
				bos.writeTo(out);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					out.close();
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			imageNameList.add(fileName);
		}
		JSONArray jsonArray = JSONArray.fromObject(imageNameList);
		Map<String, String> map = new HashMap<>();
		map.put("result", "success");
		map.put("data", jsonArray.toString());
		return map;
	}

}
