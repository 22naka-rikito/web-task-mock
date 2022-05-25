package Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.Products;
import service.CategoryService;
import service.IndexService;
import service.InsertService;
import service.ProductsService;

/**
 * Servlet implementation class servlet
 */
@WebServlet("/servlet")
public class servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public servlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String btn = request.getParameter("btn");
		String path = "";

		if ("find".equals(btn)) {
			path = "menu.jsp";
			String findText = request.getParameter("search");
			ProductsService productsService = new ProductsService();
			request.setAttribute("findList", productsService.findResult(findText));
			request.setAttribute("findSize", "検索結果:" + productsService.getSize() + "件");
		} else if ("insert".equals(btn)) {
			path = "insert.jsp";
		} else if ("register".equals(btn)) {
			String productId = request.getParameter("prpduct_id");
			String name = request.getParameter("prpduct_name");
			String price = request.getParameter("price");
			String category = request.getParameter("category");
			String description = request.getParameter("description");

			InsertService insertService = new InsertService();
			String[] errorMsg = new String[4];
			path = "insert.jsp";

			if ((!"".equals(productId)) && (!"".equals(name)) && (!"".equals(price))) {
				if (insertService.productIdCheck(productId)) {
					errorMsg[3] = "商品IDが重複しています";
				} else {
					path = "menu.jsp";
					Products product = new Products(Integer.parseInt(productId), name, Integer.parseInt(price),
							description, Integer.parseInt(category));
					insertService.insertProduct(product);
					String str = "登録が完了しました";
					String findText = "";
					ProductsService productsService = new ProductsService();

					request.setAttribute("findList", productsService.findResult(findText));
					request.setAttribute("findSize", "検索結果:" + productsService.getSize() + "件");
					request.setAttribute("str", str);
				}
			} else {
				if (productId.equals("")) {
					errorMsg[0] = "商品IDは必須です";
				}
				if (name.equals("")) {
					errorMsg[1] = "商品名は必須です";
				}
				if (price.equals("")) {
					errorMsg[2] = "単価は必須です";
				}
				request.setAttribute("errorMsg", errorMsg);
			}
		}else if("back".equals(btn)) {
			path = "menu.jsp";
			String findText = "";
			ProductsService productsService = new ProductsService();

			request.setAttribute("findList", productsService.findResult(findText));
			request.setAttribute("findSize", "検索結果:" + productsService.getSize() + "件");
		}else if("detail".equals(btn)) {
			path = "detail.jsp";
			String id = request.getParameter("id");
			
			ProductsService productsService = new ProductsService();
			request.setAttribute("product", productsService.findProduct(Integer.parseInt(id)));
		}else if("delete".equals(btn)) {
			path = "menu.jsp";
			String id = request.getParameter("loginId");
			
			String findText = "";
			ProductsService productsService = new ProductsService();
			if(!"".equals(id)) {
				productsService.deleteProduct(Integer.parseInt(id));
			}
			
			request.setAttribute("findList", productsService.findResult(findText));
			request.setAttribute("findSize", "検索結果:" + productsService.getSize() + "件");
		}else if("edit".equals(btn)) {
			path = "update.jsp";
			String id = request.getParameter("id");
			
			ProductsService productsService = new ProductsService();
			CategoryService categoryService = new CategoryService();
			
			request.setAttribute("product", productsService.findProduct(Integer.parseInt(id)));
			request.setAttribute("category", categoryService.categoryAll());
		}

		request.getRequestDispatcher(path).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String btn = request.getParameter("btn");
		String path = "";

		if ("login".equals(btn)) {
			String id = request.getParameter("loginId");
			String pass = request.getParameter("pass");
			IndexService indexservice = new IndexService(id, pass);
			if (indexservice.login()) {
				path = "menu.jsp";
				HttpSession session = request.getSession(false);
				String findText = "";
				ProductsService productsService = new ProductsService();

				request.setAttribute("findList", productsService.findResult(findText));
				request.setAttribute("findSize", "検索結果:" + productsService.getSize() + "件");
				session.setAttribute("user", indexservice.getUser());
			} else {
				path = "index.jsp";
				request.setAttribute("errorMsg", indexservice.getErrorMsg());
			}
		}
		request.getRequestDispatcher(path).forward(request, response);
	}

}
