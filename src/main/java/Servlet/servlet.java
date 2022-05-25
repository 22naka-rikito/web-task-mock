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
import service.UpdateService;

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
		
		HttpSession session = request.getSession(false);
		if(session.getAttribute("user") == null) {
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return ;
		}

		if ("find".equals(btn)) {
			path = "menu.jsp";

			String findText = request.getParameter("search");
			ProductsService productsService = new ProductsService();
			request.setAttribute("findList", productsService.findResult(findText));
			if (productsService.getSize() == 0) {
				request.setAttribute("str", "検索結果がありません");
			}
			request.setAttribute("findSize", "検索結果:" + productsService.getSize() + "件");
		} else if ("insert".equals(btn)) {
			path = "insert.jsp";
			CategoryService categoryService = new CategoryService();
			request.setAttribute("category", categoryService.categoryAll());
		} else if ("register".equals(btn)) {
			String productId = request.getParameter("prpduct_id");
			String name = request.getParameter("prpduct_name");
			String price = request.getParameter("price");
			String category = request.getParameter("category");
			String description = request.getParameter("description");

			InsertService insertService = new InsertService();
			String[] errorMsg = new String[4];

			CategoryService categoryService = new CategoryService();

			path = "insert.jsp";

			if ((!"".equals(productId)) && (!"".equals(name)) && (!"".equals(price))) {
				if (insertService.productIdCheck(productId)) {
					errorMsg[3] = "商品IDが重複しています";
					request.setAttribute("category", categoryService.categoryAll());
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
				request.setAttribute("category", categoryService.categoryAll());
			}
			request.setAttribute("errorMsg", errorMsg);
		} else if ("back".equals(btn)) {
			path = "menu.jsp";
			String findText = "";
			ProductsService productsService = new ProductsService();

			request.setAttribute("findList", productsService.findResult(findText));
			request.setAttribute("findSize", "検索結果:" + productsService.getSize() + "件");
		} else if ("detail".equals(btn)) {
			path = "detail.jsp";
			String id = request.getParameter("id");

			ProductsService productsService = new ProductsService();
			request.setAttribute("product", productsService.findProduct(Integer.parseInt(id)));
		} else if ("delete".equals(btn)) {
			path = "menu.jsp";
			String id = request.getParameter("loginId");
			String str = "";
			ProductsService productsService = new ProductsService();
			if (!"".equals(id)) {
				String findText = "";
				productsService.deleteProduct(Integer.parseInt(id));
				str = "削除に成功しました";
				request.setAttribute("findList", productsService.findResult(findText));
				request.setAttribute("findSize", "検索結果:" + productsService.getSize() + "件");
			} else {
				str = "削除に失敗しました。";
				path = "detail.jsp";
			}
			request.setAttribute("str", str);
		} else if ("edit".equals(btn)) {
			path = "update.jsp";
			String id = request.getParameter("id");

			ProductsService productsService = new ProductsService();
			CategoryService categoryService = new CategoryService();

			request.setAttribute("product", productsService.findProduct(Integer.parseInt(id)));
			request.setAttribute("category", categoryService.categoryAll());
		} else if ("update".equals(btn)) {
			String productId = request.getParameter("prpduct_id");
			String productIdOld = request.getParameter("prpduct_id_old");
			String name = request.getParameter("prpduct_name");
			String price = request.getParameter("price");
			String category = request.getParameter("category");
			String description = request.getParameter("description");

			UpdateService updateService = new UpdateService();
			String[] errorMsg = new String[4];

			ProductsService productsService = new ProductsService();
			CategoryService categoryService = new CategoryService();

			path = "update.jsp";

			if ((!"".equals(productId)) && (!"".equals(name)) && (!"".equals(price))) {
				if (updateService.productIdCheck(productId, productIdOld)) {
					errorMsg[3] = "商品IDが重複しています";
					request.setAttribute("category", categoryService.categoryAll());
				} else {
					path = "menu.jsp";
					Products product = new Products(Integer.parseInt(productId), name, Integer.parseInt(price),
							description, Integer.parseInt(category));
					
					
					updateService.updateProduct(product, productIdOld);
					String str = "更新が完了しました";
					String findText = "";

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
				request.setAttribute("category", categoryService.categoryAll());
			}
			request.setAttribute("errorMsg", errorMsg);
			request.setAttribute("product", productsService.findProduct(Integer.parseInt(productIdOld)));
		} else if ("logout".equals(btn)) {
			path = "logout.jsp";
			session = request.getSession(false);
			session.setAttribute("user", null);
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
