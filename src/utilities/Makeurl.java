package utilities;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class Makeurl {
	
	
	static String finalPolicyNo = "";
	static String policyScheduleDocURL = "";
	static String dpath ="C:\\ccimage\\";
	
	public static String geturl(String downloadLocation) throws IOException{
		
		try {

		

			String imgNamePath = downloadLocation;

			policyScheduleDocURL = GetFileUrl("PendingStatus", imgNamePath, finalPolicyNo, "Image");

			FileUtils.deleteQuietly(new File(imgNamePath));

		} catch (Exception e) {

		}finally {

		FileUtils.cleanDirectory(new File(dpath));

	}
		return policyScheduleDocURL;

	}
	
	
	public static String GetFileUrl(String FileName, String pfilePath, String appNo, String type)
			throws IOException, URISyntaxException {
		String content = "";
		JSONObject docObj = new JSONObject();
		try {
			CloseableHttpClient client = HttpClientBuilder.create().build();
			StringBuilder payLoad = new StringBuilder("{").append("\"ApplicationNo\":\"").append(appNo).append("\"}");
			String encoSt = URLEncoder.encode(payLoad.toString(), "UTF-8");
			String url = "https://api.policybazaar.com/cs/repo/uploadInsurerPortalDoc?payloadJSON=" + encoSt;
			HttpPost post = new HttpPost(url);

			File file = new File(pfilePath);
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

			if (type.equalsIgnoreCase("Image")) {
				System.out.println("Image print");
				builder.addBinaryBody("file", file, ContentType.create("image/png"), pfilePath);
			} else {
				builder.addBinaryBody("file", file, ContentType.create("application/pdf"), pfilePath);
			}
			HttpEntity entity = builder.build();
			post.setEntity(entity);

			HttpResponse response = client.execute(post);
			content = EntityUtils.toString(response.getEntity());
			docObj = new JSONObject(content);
			System.out.println("output URL:" + content.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return docObj.getString("docUrl");
	}
	
	

	
}
