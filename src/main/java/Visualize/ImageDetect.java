package Visualize;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import com.google.cloud.vision.v1.Image;
import com.google.protobuf.ByteString;
import Visualize.CloudVisionClient.Status;

/**
 * Servlet implementation class ImageDetect
 */
public class ImageDetect extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ImageDetect() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		ByteString imgBytes = ByteString.readFrom(request.getInputStream());
		Image img = Image.newBuilder().setContent(imgBytes).build();
//      store the result text in a StringBuilder.
		StringBuilder result = new StringBuilder();
		Status status = CloudVisionClient.annotate(img, result);

		if (status == Status.OK) {
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("application/json");
			response.getWriter().print(new JSONObject().put("text", result.toString()));
		} else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

}
